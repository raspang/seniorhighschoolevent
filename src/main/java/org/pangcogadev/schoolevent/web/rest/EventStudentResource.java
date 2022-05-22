package org.pangcogadev.schoolevent.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.pangcogadev.schoolevent.domain.EventStudent;
import org.pangcogadev.schoolevent.repository.EventStudentRepository;
import org.pangcogadev.schoolevent.service.EventStudentService;
import org.pangcogadev.schoolevent.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link org.pangcogadev.schoolevent.domain.EventStudent}.
 */
@RestController
@RequestMapping("/api")
public class EventStudentResource {

    private final Logger log = LoggerFactory.getLogger(EventStudentResource.class);

    private static final String ENTITY_NAME = "eventStudent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventStudentService eventStudentService;

    private final EventStudentRepository eventStudentRepository;

    public EventStudentResource(EventStudentService eventStudentService, EventStudentRepository eventStudentRepository) {
        this.eventStudentService = eventStudentService;
        this.eventStudentRepository = eventStudentRepository;
    }

    /**
     * {@code POST  /event-students} : Create a new eventStudent.
     *
     * @param eventStudent the eventStudent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventStudent, or with status {@code 400 (Bad Request)} if the eventStudent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-students")
    public ResponseEntity<EventStudent> createEventStudent(@RequestBody EventStudent eventStudent) throws URISyntaxException {
        log.debug("REST request to save EventStudent : {}", eventStudent);
        if (eventStudent.getId() != null) {
            throw new BadRequestAlertException("A new eventStudent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventStudent result = eventStudentService.save(eventStudent);
        return ResponseEntity
            .created(new URI("/api/event-students/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-students/:id} : Updates an existing eventStudent.
     *
     * @param id the id of the eventStudent to save.
     * @param eventStudent the eventStudent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventStudent,
     * or with status {@code 400 (Bad Request)} if the eventStudent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventStudent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-students/{id}")
    public ResponseEntity<EventStudent> updateEventStudent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventStudent eventStudent
    ) throws URISyntaxException {
        log.debug("REST request to update EventStudent : {}, {}", id, eventStudent);
        if (eventStudent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventStudent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventStudentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EventStudent result = eventStudentService.update(eventStudent);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventStudent.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /event-students/:id} : Partial updates given fields of an existing eventStudent, field will ignore if it is null
     *
     * @param id the id of the eventStudent to save.
     * @param eventStudent the eventStudent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventStudent,
     * or with status {@code 400 (Bad Request)} if the eventStudent is not valid,
     * or with status {@code 404 (Not Found)} if the eventStudent is not found,
     * or with status {@code 500 (Internal Server Error)} if the eventStudent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/event-students/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<EventStudent> partialUpdateEventStudent(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EventStudent eventStudent
    ) throws URISyntaxException {
        log.debug("REST request to partial update EventStudent partially : {}, {}", id, eventStudent);
        if (eventStudent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, eventStudent.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!eventStudentRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EventStudent> result = eventStudentService.partialUpdate(eventStudent);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventStudent.getId().toString())
        );
    }

    /**
     * {@code GET  /event-students} : get all the eventStudents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventStudents in body.
     */
    @GetMapping("/event-students")
    public ResponseEntity<List<EventStudent>> getAllEventStudents(@org.springdoc.api.annotations.ParameterObject Pageable pageable) {
        log.debug("REST request to get a page of EventStudents");
        Page<EventStudent> page = eventStudentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /event-students/:id} : get the "id" eventStudent.
     *
     * @param id the id of the eventStudent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventStudent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-students/{id}")
    public ResponseEntity<EventStudent> getEventStudent(@PathVariable Long id) {
        log.debug("REST request to get EventStudent : {}", id);
        Optional<EventStudent> eventStudent = eventStudentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventStudent);
    }

    /**
     * {@code DELETE  /event-students/:id} : delete the "id" eventStudent.
     *
     * @param id the id of the eventStudent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-students/{id}")
    public ResponseEntity<Void> deleteEventStudent(@PathVariable Long id) {
        log.debug("REST request to delete EventStudent : {}", id);
        eventStudentService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
