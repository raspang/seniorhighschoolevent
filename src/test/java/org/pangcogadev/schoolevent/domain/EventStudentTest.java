package org.pangcogadev.schoolevent.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.pangcogadev.schoolevent.web.rest.TestUtil;

class EventStudentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventStudent.class);
        EventStudent eventStudent1 = new EventStudent();
        eventStudent1.setId(1L);
        EventStudent eventStudent2 = new EventStudent();
        eventStudent2.setId(eventStudent1.getId());
        assertThat(eventStudent1).isEqualTo(eventStudent2);
        eventStudent2.setId(2L);
        assertThat(eventStudent1).isNotEqualTo(eventStudent2);
        eventStudent1.setId(null);
        assertThat(eventStudent1).isNotEqualTo(eventStudent2);
    }
}
