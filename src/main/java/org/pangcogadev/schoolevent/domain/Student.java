package org.pangcogadev.schoolevent.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.*;

/**
 * A Student.
 */
@Entity
@Table(name = "student")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @NotNull
    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "lrn")
    private String lrn;

    @Column(name = "year_level")
    private String yearLevel;

    @Column(name = "strand")
    private String strand;

    @Column(name = "section")
    private String section;

    @Column(name = "institutional_email")
    private String institutionalEmail;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Student id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public Student firstName(String firstName) {
        this.setFirstName(firstName);
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public Student middleName(String middleName) {
        this.setMiddleName(middleName);
        return this;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Student lastName(String lastName) {
        this.setLastName(lastName);
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return this.gender;
    }

    public Student gender(String gender) {
        this.setGender(gender);
        return this;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLrn() {
        return this.lrn;
    }

    public Student lrn(String lrn) {
        this.setLrn(lrn);
        return this;
    }

    public void setLrn(String lrn) {
        this.lrn = lrn;
    }

    public String getYearLevel() {
        return this.yearLevel;
    }

    public Student yearLevel(String yearLevel) {
        this.setYearLevel(yearLevel);
        return this;
    }

    public void setYearLevel(String yearLevel) {
        this.yearLevel = yearLevel;
    }

    public String getStrand() {
        return this.strand;
    }

    public Student strand(String strand) {
        this.setStrand(strand);
        return this;
    }

    public void setStrand(String strand) {
        this.strand = strand;
    }

    public String getSection() {
        return this.section;
    }

    public Student section(String section) {
        this.setSection(section);
        return this;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getInstitutionalEmail() {
        return this.institutionalEmail;
    }

    public Student institutionalEmail(String institutionalEmail) {
        this.setInstitutionalEmail(institutionalEmail);
        return this;
    }

    public void setInstitutionalEmail(String institutionalEmail) {
        this.institutionalEmail = institutionalEmail;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Student)) {
            return false;
        }
        return id != null && id.equals(((Student) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Student{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", middleName='" + getMiddleName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", gender='" + getGender() + "'" +
            ", lrn='" + getLrn() + "'" +
            ", yearLevel='" + getYearLevel() + "'" +
            ", strand='" + getStrand() + "'" +
            ", section='" + getSection() + "'" +
            ", institutionalEmail='" + getInstitutionalEmail() + "'" +
            "}";
    }
}
