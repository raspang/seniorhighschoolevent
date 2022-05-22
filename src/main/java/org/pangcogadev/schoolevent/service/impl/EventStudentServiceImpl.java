package org.pangcogadev.schoolevent.service.impl;

import java.util.Optional;
import org.pangcogadev.schoolevent.domain.EventStudent;
import org.pangcogadev.schoolevent.repository.EventStudentRepository;
import org.pangcogadev.schoolevent.service.EventStudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EventStudent}.
 */
@Service
@Transactional
public class EventStudentServiceImpl implements EventStudentService {

    private final Logger log = LoggerFactory.getLogger(EventStudentServiceImpl.class);

    private final EventStudentRepository eventStudentRepository;

    public EventStudentServiceImpl(EventStudentRepository eventStudentRepository) {
        this.eventStudentRepository = eventStudentRepository;
    }

    @Override
    public EventStudent save(EventStudent eventStudent) {
        log.debug("Request to save EventStudent : {}", eventStudent);
        return eventStudentRepository.save(eventStudent);
    }

    @Override
    public EventStudent update(EventStudent eventStudent) {
        log.debug("Request to save EventStudent : {}", eventStudent);
        return eventStudentRepository.save(eventStudent);
    }

    @Override
    public Optional<EventStudent> partialUpdate(EventStudent eventStudent) {
        log.debug("Request to partially update EventStudent : {}", eventStudent);

        return eventStudentRepository
            .findById(eventStudent.getId())
            .map(existingEventStudent -> {
                return existingEventStudent;
            })
            .map(eventStudentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EventStudent> findAll(Pageable pageable) {
        log.debug("Request to get all EventStudents");
        return eventStudentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EventStudent> findOne(Long id) {
        log.debug("Request to get EventStudent : {}", id);
        return eventStudentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete EventStudent : {}", id);
        eventStudentRepository.deleteById(id);
    }
}
