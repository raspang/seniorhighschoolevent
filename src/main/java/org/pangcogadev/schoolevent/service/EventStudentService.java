package org.pangcogadev.schoolevent.service;

import java.util.Optional;
import org.pangcogadev.schoolevent.domain.EventStudent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link EventStudent}.
 */
public interface EventStudentService {
    /**
     * Save a eventStudent.
     *
     * @param eventStudent the entity to save.
     * @return the persisted entity.
     */
    EventStudent save(EventStudent eventStudent);

    /**
     * Updates a eventStudent.
     *
     * @param eventStudent the entity to update.
     * @return the persisted entity.
     */
    EventStudent update(EventStudent eventStudent);

    /**
     * Partially updates a eventStudent.
     *
     * @param eventStudent the entity to update partially.
     * @return the persisted entity.
     */
    Optional<EventStudent> partialUpdate(EventStudent eventStudent);

    /**
     * Get all the eventStudents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EventStudent> findAll(Pageable pageable);

    /**
     * Get the "id" eventStudent.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EventStudent> findOne(Long id);

    /**
     * Delete the "id" eventStudent.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
