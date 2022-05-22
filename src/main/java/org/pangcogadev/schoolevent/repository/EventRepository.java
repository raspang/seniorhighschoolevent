package org.pangcogadev.schoolevent.repository;

import org.pangcogadev.schoolevent.domain.Event;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Event entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {}
