package org.pangcogadev.schoolevent.service.impl;

import java.util.Optional;
import org.pangcogadev.schoolevent.domain.Student;
import org.pangcogadev.schoolevent.repository.StudentRepository;
import org.pangcogadev.schoolevent.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Student}.
 */
@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student save(Student student) {
        log.debug("Request to save Student : {}", student);
        return studentRepository.save(student);
    }

    @Override
    public Student update(Student student) {
        log.debug("Request to save Student : {}", student);
        return studentRepository.save(student);
    }

    @Override
    public Optional<Student> partialUpdate(Student student) {
        log.debug("Request to partially update Student : {}", student);

        return studentRepository
            .findById(student.getId())
            .map(existingStudent -> {
                if (student.getFirstName() != null) {
                    existingStudent.setFirstName(student.getFirstName());
                }
                if (student.getMiddleName() != null) {
                    existingStudent.setMiddleName(student.getMiddleName());
                }
                if (student.getLastName() != null) {
                    existingStudent.setLastName(student.getLastName());
                }
                if (student.getGender() != null) {
                    existingStudent.setGender(student.getGender());
                }
                if (student.getLrn() != null) {
                    existingStudent.setLrn(student.getLrn());
                }
                if (student.getYearLevel() != null) {
                    existingStudent.setYearLevel(student.getYearLevel());
                }
                if (student.getStrand() != null) {
                    existingStudent.setStrand(student.getStrand());
                }
                if (student.getSection() != null) {
                    existingStudent.setSection(student.getSection());
                }
                if (student.getInstitutionalEmail() != null) {
                    existingStudent.setInstitutionalEmail(student.getInstitutionalEmail());
                }

                return existingStudent;
            })
            .map(studentRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Student> findAll(Pageable pageable) {
        log.debug("Request to get all Students");
        return studentRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Student> findOne(Long id) {
        log.debug("Request to get Student : {}", id);
        return studentRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Student : {}", id);
        studentRepository.deleteById(id);
    }
}
