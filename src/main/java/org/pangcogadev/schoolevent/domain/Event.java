package org.pangcogadev.schoolevent.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Event.
 */
@Entity
@Table(name = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "date_event")
    private Instant dateEvent;

    @NotNull
    @Size(min = 2)
    @Column(name = "name_event", nullable = false)
    private String nameEvent;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Event id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getDateEvent() {
        return this.dateEvent;
    }

    public Event dateEvent(Instant dateEvent) {
        this.setDateEvent(dateEvent);
        return this;
    }

    public void setDateEvent(Instant dateEvent) {
        this.dateEvent = dateEvent;
    }

    public String getNameEvent() {
        return this.nameEvent;
    }

    public Event nameEvent(String nameEvent) {
        this.setNameEvent(nameEvent);
        return this;
    }

    public void setNameEvent(String nameEvent) {
        this.nameEvent = nameEvent;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Event)) {
            return false;
        }
        return id != null && id.equals(((Event) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", dateEvent='" + getDateEvent() + "'" +
            ", nameEvent='" + getNameEvent() + "'" +
            "}";
    }
}
