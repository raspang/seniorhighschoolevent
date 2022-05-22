package org.pangcogadev.schoolevent.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.pangcogadev.schoolevent.IntegrationTest;
import org.pangcogadev.schoolevent.domain.EventStudent;
import org.pangcogadev.schoolevent.repository.EventStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link EventStudentResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EventStudentResourceIT {

    private static final String ENTITY_API_URL = "/api/event-students";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private EventStudentRepository eventStudentRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEventStudentMockMvc;

    private EventStudent eventStudent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventStudent createEntity(EntityManager em) {
        EventStudent eventStudent = new EventStudent();
        return eventStudent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EventStudent createUpdatedEntity(EntityManager em) {
        EventStudent eventStudent = new EventStudent();
        return eventStudent;
    }

    @BeforeEach
    public void initTest() {
        eventStudent = createEntity(em);
    }

    @Test
    @Transactional
    void createEventStudent() throws Exception {
        int databaseSizeBeforeCreate = eventStudentRepository.findAll().size();
        // Create the EventStudent
        restEventStudentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventStudent)))
            .andExpect(status().isCreated());

        // Validate the EventStudent in the database
        List<EventStudent> eventStudentList = eventStudentRepository.findAll();
        assertThat(eventStudentList).hasSize(databaseSizeBeforeCreate + 1);
        EventStudent testEventStudent = eventStudentList.get(eventStudentList.size() - 1);
    }

    @Test
    @Transactional
    void createEventStudentWithExistingId() throws Exception {
        // Create the EventStudent with an existing ID
        eventStudent.setId(1L);

        int databaseSizeBeforeCreate = eventStudentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEventStudentMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventStudent)))
            .andExpect(status().isBadRequest());

        // Validate the EventStudent in the database
        List<EventStudent> eventStudentList = eventStudentRepository.findAll();
        assertThat(eventStudentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEventStudents() throws Exception {
        // Initialize the database
        eventStudentRepository.saveAndFlush(eventStudent);

        // Get all the eventStudentList
        restEventStudentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(eventStudent.getId().intValue())));
    }

    @Test
    @Transactional
    void getEventStudent() throws Exception {
        // Initialize the database
        eventStudentRepository.saveAndFlush(eventStudent);

        // Get the eventStudent
        restEventStudentMockMvc
            .perform(get(ENTITY_API_URL_ID, eventStudent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(eventStudent.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingEventStudent() throws Exception {
        // Get the eventStudent
        restEventStudentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEventStudent() throws Exception {
        // Initialize the database
        eventStudentRepository.saveAndFlush(eventStudent);

        int databaseSizeBeforeUpdate = eventStudentRepository.findAll().size();

        // Update the eventStudent
        EventStudent updatedEventStudent = eventStudentRepository.findById(eventStudent.getId()).get();
        // Disconnect from session so that the updates on updatedEventStudent are not directly saved in db
        em.detach(updatedEventStudent);

        restEventStudentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedEventStudent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedEventStudent))
            )
            .andExpect(status().isOk());

        // Validate the EventStudent in the database
        List<EventStudent> eventStudentList = eventStudentRepository.findAll();
        assertThat(eventStudentList).hasSize(databaseSizeBeforeUpdate);
        EventStudent testEventStudent = eventStudentList.get(eventStudentList.size() - 1);
    }

    @Test
    @Transactional
    void putNonExistingEventStudent() throws Exception {
        int databaseSizeBeforeUpdate = eventStudentRepository.findAll().size();
        eventStudent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventStudentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, eventStudent.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventStudent))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventStudent in the database
        List<EventStudent> eventStudentList = eventStudentRepository.findAll();
        assertThat(eventStudentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEventStudent() throws Exception {
        int databaseSizeBeforeUpdate = eventStudentRepository.findAll().size();
        eventStudent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventStudentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(eventStudent))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventStudent in the database
        List<EventStudent> eventStudentList = eventStudentRepository.findAll();
        assertThat(eventStudentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEventStudent() throws Exception {
        int databaseSizeBeforeUpdate = eventStudentRepository.findAll().size();
        eventStudent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventStudentMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(eventStudent)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventStudent in the database
        List<EventStudent> eventStudentList = eventStudentRepository.findAll();
        assertThat(eventStudentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEventStudentWithPatch() throws Exception {
        // Initialize the database
        eventStudentRepository.saveAndFlush(eventStudent);

        int databaseSizeBeforeUpdate = eventStudentRepository.findAll().size();

        // Update the eventStudent using partial update
        EventStudent partialUpdatedEventStudent = new EventStudent();
        partialUpdatedEventStudent.setId(eventStudent.getId());

        restEventStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventStudent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventStudent))
            )
            .andExpect(status().isOk());

        // Validate the EventStudent in the database
        List<EventStudent> eventStudentList = eventStudentRepository.findAll();
        assertThat(eventStudentList).hasSize(databaseSizeBeforeUpdate);
        EventStudent testEventStudent = eventStudentList.get(eventStudentList.size() - 1);
    }

    @Test
    @Transactional
    void fullUpdateEventStudentWithPatch() throws Exception {
        // Initialize the database
        eventStudentRepository.saveAndFlush(eventStudent);

        int databaseSizeBeforeUpdate = eventStudentRepository.findAll().size();

        // Update the eventStudent using partial update
        EventStudent partialUpdatedEventStudent = new EventStudent();
        partialUpdatedEventStudent.setId(eventStudent.getId());

        restEventStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEventStudent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEventStudent))
            )
            .andExpect(status().isOk());

        // Validate the EventStudent in the database
        List<EventStudent> eventStudentList = eventStudentRepository.findAll();
        assertThat(eventStudentList).hasSize(databaseSizeBeforeUpdate);
        EventStudent testEventStudent = eventStudentList.get(eventStudentList.size() - 1);
    }

    @Test
    @Transactional
    void patchNonExistingEventStudent() throws Exception {
        int databaseSizeBeforeUpdate = eventStudentRepository.findAll().size();
        eventStudent.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEventStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, eventStudent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventStudent))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventStudent in the database
        List<EventStudent> eventStudentList = eventStudentRepository.findAll();
        assertThat(eventStudentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEventStudent() throws Exception {
        int databaseSizeBeforeUpdate = eventStudentRepository.findAll().size();
        eventStudent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventStudentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(eventStudent))
            )
            .andExpect(status().isBadRequest());

        // Validate the EventStudent in the database
        List<EventStudent> eventStudentList = eventStudentRepository.findAll();
        assertThat(eventStudentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEventStudent() throws Exception {
        int databaseSizeBeforeUpdate = eventStudentRepository.findAll().size();
        eventStudent.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEventStudentMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(eventStudent))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the EventStudent in the database
        List<EventStudent> eventStudentList = eventStudentRepository.findAll();
        assertThat(eventStudentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEventStudent() throws Exception {
        // Initialize the database
        eventStudentRepository.saveAndFlush(eventStudent);

        int databaseSizeBeforeDelete = eventStudentRepository.findAll().size();

        // Delete the eventStudent
        restEventStudentMockMvc
            .perform(delete(ENTITY_API_URL_ID, eventStudent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EventStudent> eventStudentList = eventStudentRepository.findAll();
        assertThat(eventStudentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
