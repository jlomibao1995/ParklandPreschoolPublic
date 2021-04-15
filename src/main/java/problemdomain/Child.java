/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemdomain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Model class for a child
 * @author Jean Lomibao
 * @author Simon Ma
 */
@Entity
@Table(name = "child")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Child.findAll", query = "SELECT c FROM Child c")
    , @NamedQuery(name = "Child.findByChildId", query = "SELECT c FROM Child c WHERE c.childId = :childId")
    , @NamedQuery(name = "Child.findByChildBirthdate", query = "SELECT c FROM Child c WHERE c.childBirthdate = :childBirthdate")
    , @NamedQuery(name = "Child.findByChildGender", query = "SELECT c FROM Child c WHERE c.childGender = :childGender")
    , @NamedQuery(name = "Child.findByChildFirstName", query = "SELECT c FROM Child c WHERE c.childFirstName = :childFirstName")
    , @NamedQuery(name = "Child.findByChildLastName", query = "SELECT c FROM Child c WHERE c.childLastName = :childLastName")
    , @NamedQuery(name = "Child.findByChildPhoneNumber", query = "SELECT c FROM Child c WHERE c.childPhoneNumber = :childPhoneNumber")
    , @NamedQuery(name = "Child.findByChildAddress", query = "SELECT c FROM Child c WHERE c.childAddress = :childAddress")
    , @NamedQuery(name = "Child.findByChildCity", query = "SELECT c FROM Child c WHERE c.childCity = :childCity")
    , @NamedQuery(name = "Child.findByChildProvince", query = "SELECT c FROM Child c WHERE c.childProvince = :childProvince")
    , @NamedQuery(name = "Child.findByChildPostalCode", query = "SELECT c FROM Child c WHERE c.childPostalCode = :childPostalCode")
    , @NamedQuery(name = "Child.findByDoctor", query = "SELECT c FROM Child c WHERE c.doctor = :doctor")
    , @NamedQuery(name = "Child.findByClinic", query = "SELECT c FROM Child c WHERE c.clinic = :clinic")
    , @NamedQuery(name = "Child.findByImmunizationUpdated", query = "SELECT c FROM Child c WHERE c.immunizationUpdated = :immunizationUpdated")
    , @NamedQuery(name = "Child.findByHadChickenPox", query = "SELECT c FROM Child c WHERE c.hadChickenPox = :hadChickenPox")
    , @NamedQuery(name = "Child.findByMedicalPhoneNumber", query = "SELECT c FROM Child c WHERE c.medicalPhoneNumber = :medicalPhoneNumber")
    , @NamedQuery(name = "Child.findByHealthcareNum", query = "SELECT c FROM Child c WHERE c.healthcareNum = :healthcareNum")
    , @NamedQuery(name = "Child.findByAllergy", query = "SELECT c FROM Child c WHERE c.allergy = :allergy")
    , @NamedQuery(name = "Child.findByMedicalConditions", query = "SELECT c FROM Child c WHERE c.medicalConditions = :medicalConditions")
    , @NamedQuery(name = "Child.findByMedications", query = "SELECT c FROM Child c WHERE c.medications = :medications")})
public class Child implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "child_id")
    private Integer childId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "child_birthdate")
    @Temporal(TemporalType.DATE)
    private Date childBirthdate;
    @Basic(optional = false)
    @NotNull
    @Column(name = "child_gender")
    private Character childGender;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "child_first_name")
    private String childFirstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "child_last_name")
    private String childLastName;
    @Size(max = 20)
    @Column(name = "child_phone_number")
    private String childPhoneNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "child_address")
    private String childAddress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "child_city")
    private String childCity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "child_province")
    private String childProvince;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "child_postal_code")
    private String childPostalCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "doctor")
    private String doctor;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "clinic")
    private String clinic;
    @Basic(optional = false)
    @NotNull
    @Column(name = "immunization_updated")
    private boolean immunizationUpdated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "had_chicken_pox")
    private boolean hadChickenPox;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "medical_phone_number")
    private String medicalPhoneNumber;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "healthcare_num")
    private String healthcareNum;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "allergy")
    private String allergy;
    @Size(max = 500)
    @Column(name = "medical_conditions")
    private String medicalConditions;
    @Column(name = "medications")
    private Boolean medications;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "child", fetch = FetchType.LAZY)
    private List<AuthorizedPickup> authorizedPickupList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "child", fetch = FetchType.LAZY)
    private List<Registration> registrationList;
    @JoinColumn(name = "account", referencedColumnName = "account_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Account account;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "child", fetch = FetchType.LAZY)
    private List<EmergencyContact> emergencyContactList;

    /**
     * No arg constructor
     */
    public Child() {
    }

    /**
     * User defined constructor
     * @param childId id
     */
    public Child(Integer childId) {
        this.childId = childId;
    }

    /**
     * User defined constructor
     * @param childId id
     * @param childBirthdate birth date
     * @param childGender gender
     * @param childFirstName first name
     * @param childLastName last name
     * @param childAddress address
     * @param childCity city
     * @param childProvince province
     * @param childPostalCode postal code
     * @param doctor doctor
     * @param clinic clinic
     * @param immunizationUpdated immunizations up to date
     * @param hadChickenPox child has had chicken pox
     * @param medicalPhoneNumber medical phone number
     * @param healthcareNum healthcare number
     * @param allergy allergy
     */
    public Child(Integer childId, Date childBirthdate, Character childGender, String childFirstName, String childLastName, String childAddress, String childCity, String childProvince, String childPostalCode, String doctor, String clinic, boolean immunizationUpdated, boolean hadChickenPox, String medicalPhoneNumber, String healthcareNum, String allergy) {
        this.childId = childId;
        this.childBirthdate = childBirthdate;
        this.childGender = childGender;
        this.childFirstName = childFirstName;
        this.childLastName = childLastName;
        this.childAddress = childAddress;
        this.childCity = childCity;
        this.childProvince = childProvince;
        this.childPostalCode = childPostalCode;
        this.doctor = doctor;
        this.clinic = clinic;
        this.immunizationUpdated = immunizationUpdated;
        this.hadChickenPox = hadChickenPox;
        this.medicalPhoneNumber = medicalPhoneNumber;
        this.healthcareNum = healthcareNum;
        this.allergy = allergy;
    }

    /**
     * Retrieves id
     * @return id
     */
    public Integer getChildId() {
        return childId;
    }

    /**
     * Modifies id
     * @param childId id 
     */
    public void setChildId(Integer childId) {
        this.childId = childId;
    }

    /**
     * Retrieves birth date
     * @return birth date
     */
    public Date getChildBirthdate() {
        return childBirthdate;
    }

    /**
     * Modifies birth date
     * @param childBirthdate birth date
     */
    public void setChildBirthdate(Date childBirthdate) {
        this.childBirthdate = childBirthdate;
    }

    /**
     * Retrieves child gender
     * @return gender
     */
    public Character getChildGender() {
        return childGender;
    }

    /**
     * Modifies child gender
     * @param childGender gender
     */
    public void setChildGender(Character childGender) {
        this.childGender = childGender;
    }

    /**
     * Retrieves first name
     * @return first name
     */
    public String getChildFirstName() {
        return childFirstName;
    }

    /**
     * Modifies first name
     * @param childFirstName first name 
     */
    public void setChildFirstName(String childFirstName) {
        this.childFirstName = childFirstName;
    }

    /**
     * Retrieves last name
     * @return last name
     */
    public String getChildLastName() {
        return childLastName;
    }

    /**
     * Modifies last name
     * @param childLastName last name 
     */
    public void setChildLastName(String childLastName) {
        this.childLastName = childLastName;
    }

    /**
     * Retrieves phone number
     * @return phone number
     */
    public String getChildPhoneNumber() {
        return childPhoneNumber;
    }

    /**
     * Modifies phone number
     * @param childPhoneNumber phone number
     */
    public void setChildPhoneNumber(String childPhoneNumber) {
        this.childPhoneNumber = childPhoneNumber;
    }

    /**
     * Retrieves address
     * @return address
     */
    public String getChildAddress() {
        return childAddress;
    }

    /**
     * Modifies address
     * @param childAddress address 
     */
    public void setChildAddress(String childAddress) {
        this.childAddress = childAddress;
    }

    /**
     * Retrieves city
     * @return city
     */
    public String getChildCity() {
        return childCity;
    }

    /**
     * Modifies city
     * @param childCity city 
     */
    public void setChildCity(String childCity) {
        this.childCity = childCity;
    }

    /**
     * Retrieves province
     * @return province
     */
    public String getChildProvince() {
        return childProvince;
    }

    /**
     * Modifies province 
     * @param childProvince province 
     */
    public void setChildProvince(String childProvince) {
        this.childProvince = childProvince;
    }

    /**
     * Retrieves postal code
     * @return postal code
     */
    public String getChildPostalCode() {
        return childPostalCode;
    }

    /**
     * Modifes postal code
     * @param childPostalCode postal code
     */
    public void setChildPostalCode(String childPostalCode) {
        this.childPostalCode = childPostalCode;
    }

    /**
     * Retrieves child doctor
     * @return doctor
     */
    public String getDoctor() {
        return doctor;
    }

    /**
     * Modifes child doctor
     * @param doctor doctor
     */
    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    /**
     * Retrieves child's clinic
     * @return clinic
     */
    public String getClinic() {
        return clinic;
    }

    /**
     * Modifies child's clinic
     * @param clinic clinic
     */
    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    /**
     * Retrieves status of immunizations
     * @return true if immunizations are updated 
     */
    public boolean getImmunizationUpdated() {
        return immunizationUpdated;
    }

    /**
     * Modifies status of immunizations 
     * @param immunizationUpdated immunization status
     */
    public void setImmunizationUpdated(boolean immunizationUpdated) {
        this.immunizationUpdated = immunizationUpdated;
    }

    /**
     * Retrieves if child has had chicken pox
     * @return true if child has had chicken pox
     */
    public boolean getHadChickenPox() {
        return hadChickenPox;
    }

    /**
     * Modifies chicken pox status
     * @param hadChickenPox chicken pox status
     */
    public void setHadChickenPox(boolean hadChickenPox) {
        this.hadChickenPox = hadChickenPox;
    }

    /**
     * Retrieves medical phone number
     * @return medical phone number
     */
    public String getMedicalPhoneNumber() {
        return medicalPhoneNumber;
    }

    /**
     * Modifies medical phone number
     * @param medicalPhoneNumber medical phone number
     */
    public void setMedicalPhoneNumber(String medicalPhoneNumber) {
        this.medicalPhoneNumber = medicalPhoneNumber;
    }

    /**
     * Retrieves health care number
     * @return health care number
     */
    public String getHealthcareNum() {
        return healthcareNum;
    }

    /**
     * Modifies health care number
     * @param healthcareNum health care number
     */
    public void setHealthcareNum(String healthcareNum) {
        this.healthcareNum = healthcareNum;
    }

    /**
     * Retrieves allergies if any
     * @return allergy
     */
    public String getAllergy() {
        return allergy;
    }

    /**
     * Modifies allergy
     * @param allergy allergies
     */
    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    /**
     * Retrieves medical conditions if any
     * @return medical conditions
     */
    public String getMedicalConditions() {
        return medicalConditions;
    }

    /**
     * Modifies medical conditions
     * @param medicalConditions medical conditions
     */
    public void setMedicalConditions(String medicalConditions) {
        this.medicalConditions = medicalConditions;
    }

    /**
     * Retrieves if child has medications
     * @return true if child does have medications
     */
    public Boolean getMedications() {
        return medications;
    }

    /**
     * Modifies medication status
     * @param medications medication status
     */
    public void setMedications(Boolean medications) {
        this.medications = medications;
    }

    /**
     * Retrieves list of authorized persons for pickup
     * @return list of authorized pickups
     */
    @XmlTransient
    public List<AuthorizedPickup> getAuthorizedPickupList() {
        return authorizedPickupList;
    }

    /**
     * Modifies list of persons authorized to pickup child
     * @param authorizedPickupList list of authorized pickups
     */
    public void setAuthorizedPickupList(List<AuthorizedPickup> authorizedPickupList) {
        this.authorizedPickupList = authorizedPickupList;
    }

    /**
     * Retrieves list of registrations
     * @return registration list
     */
    @XmlTransient
    public List<Registration> getRegistrationList() {
        return registrationList;
    }

    /**
     * Modifies list of registrations 
     * @param registrationList registration list
     */
    public void setRegistrationList(List<Registration> registrationList) {
        this.registrationList = registrationList;
    }

    /**
     * Retrieves account child belongs to
     * @return account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Modifies account associated to child
     * @param account account
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Retrieves list of emergency contacts associated to child
     * @return emergency contact list
     */
    @XmlTransient
    public List<EmergencyContact> getEmergencyContactList() {
        return emergencyContactList;
    }

    /**
     * Modifies emergency contact list
     * @param emergencyContactList emergency contact list
     */
    public void setEmergencyContactList(List<EmergencyContact> emergencyContactList) {
        this.emergencyContactList = emergencyContactList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (childId != null ? childId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Child)) {
            return false;
        }
        Child other = (Child) object;
        if ((this.childId == null && other.childId != null) || (this.childId != null && !this.childId.equals(other.childId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "problemdomain.Child[ childId=" + childId + " ]";
    }
    
    public int getAge(){
        String year = new SimpleDateFormat("yyyy").format(childBirthdate);
        String month = new SimpleDateFormat("MM").format(childBirthdate);
        String day = new SimpleDateFormat("d").format(childBirthdate);
        return Period.between(LocalDate.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day)), LocalDate.now()).getYears();
    }

    
}
