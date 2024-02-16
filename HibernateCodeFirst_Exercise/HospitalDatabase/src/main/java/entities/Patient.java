package entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "first_name", length = 40, nullable = false)
    private String firstName;
    @Column(name = "last_name", length = 40, nullable = false)
    private String lastName;
    @Column(length = 40, nullable = false)
    private String address;
    @Column(length = 40, nullable = false, unique = true)
    private String email;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;
    @Basic
    private String picture;
    @Column(name = "has_medical_insurance", nullable = false)
    private Boolean medicalInsurance;

    @OneToMany
    @JoinTable(joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "diagnose_id", referencedColumnName = "id"))
    private List<Diagnose> diagnoses;
    @OneToMany
    @JoinTable(joinColumns = @JoinColumn(name = "patient_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "medicament_id", referencedColumnName = "id"))
    private List<Medicament> medicines;

    public Patient() {
        this.diagnoses = new ArrayList<>();
        this.medicines = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Boolean getMedicalInsurance() {
        return medicalInsurance;
    }

    public void setMedicalInsurance(Boolean medicalInsurance) {
        this.medicalInsurance = medicalInsurance;
    }

    public List<Diagnose> getDiagnoses() {
        return Collections.unmodifiableList(diagnoses);
    }

    public void setDiagnoses(List<Diagnose> diagnoses) {
        this.diagnoses = diagnoses;
    }

    public List<Medicament> getMedicaments() {
        return Collections.unmodifiableList(medicines);
    }

    public void setMedicaments(List<Medicament> medicaments) {
        this.medicines = medicaments;
    }
}
