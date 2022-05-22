package org.pangcogadev.schoolevent.repository;

import org.pangcogadev.schoolevent.domain.EventStudent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EventStudent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventStudentRepository extends JpaRepository<EventStudent, Long> {}
