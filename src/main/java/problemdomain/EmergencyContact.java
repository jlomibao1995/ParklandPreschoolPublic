package problemdomain;

import java.io.Serializable;
import javax.persistence.Basic;
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
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class for emergency contacts (second guardian, other care giver,
 * and emergency contact)
 * @author Jean Lomibao
 * @author Simon Ma
 */
@Entity
@Table(name = "emergency_contact")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EmergencyContact.findAll", query = "SELECT e FROM EmergencyContact e")
    , @NamedQuery(name = "EmergencyContact.findByEmergencyContactId", query = "SELECT e FROM EmergencyContact e WHERE e.emergencyContactId = :emergencyContactId")
    , @NamedQuery(name = "EmergencyContact.findByRelationToChild", query = "SELECT e FROM EmergencyContact e WHERE e.relationToChild = :relationToChild")
    , @NamedQuery(name = "EmergencyContact.findByEmergencyFirstName", query = "SELECT e FROM EmergencyContact e WHERE e.emergencyFirstName = :emergencyFirstName")
    , @NamedQuery(name = "EmergencyContact.findByEmergencyLastName", query = "SELECT e FROM EmergencyContact e WHERE e.emergencyLastName = :emergencyLastName")
    , @NamedQuery(name = "EmergencyContact.findByHomePhoneNumber", query = "SELECT e FROM EmergencyContact e WHERE e.homePhoneNumber = :homePhoneNumber")
    , @NamedQuery(name = "EmergencyContact.findByWorkPhoneNumber", query = "SELECT e FROM EmergencyContact e WHERE e.workPhoneNumber = :workPhoneNumber")
    , @NamedQuery(name = "EmergencyContact.findByCellphoneNumber", query = "SELECT e FROM EmergencyContact e WHERE e.cellphoneNumber = :cellphoneNumber")
    , @NamedQuery(name = "EmergencyContact.findByEmergencyAddress", query = "SELECT e FROM EmergencyContact e WHERE e.emergencyAddress = :emergencyAddress")
    , @NamedQuery(name = "EmergencyContact.findByEmergencyCity", query = "SELECT e FROM EmergencyContact e WHERE e.emergencyCity = :emergencyCity")
    , @NamedQuery(name = "EmergencyContact.findByEmergencyProvince", query = "SELECT e FROM EmergencyContact e WHERE e.emergencyProvince = :emergencyProvince")
    , @NamedQuery(name = "EmergencyContact.findByEmergencyPostalCode", query = "SELECT e FROM EmergencyContact e WHERE e.emergencyPostalCode = :emergencyPostalCode")})
public class EmergencyContact implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "emergency_contact_id")
    private Integer emergencyContactId;
    @Size(max = 20)
    @Column(name = "relation_to_child")
    private String relationToChild;
    @Size(max = 50)
    @Column(name = "emergency_first_name")
    private String emergencyFirstName;
    @Size(max = 50)
    @Column(name = "emergency_last_name")
    private String emergencyLastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "home_phone_number")
    private String homePhoneNumber;
    @Size(max = 20)
    @Column(name = "work_phone_number")
    private String workPhoneNumber;
    @Size(max = 20)
    @Column(name = "cellphone_number")
    private String cellphoneNumber;
    @Size(max = 100)
    @Column(name = "emergency_address")
    private String emergencyAddress;
    @Size(max = 20)
    @Column(name = "emergency_city")
    private String emergencyCity;
    @Size(max = 20)
    @Column(name = "emergency_province")
    private String emergencyProvince;
    @Size(max = 8)
    @Column(name = "emergency_postal_code")
    private String emergencyPostalCode;
    @JoinColumn(name = "child", referencedColumnName = "child_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Child child;

    /**
     * No arg constructor
     */
    public EmergencyContact() {
    }

    /**
     * User defined constructor
     * @param emergencyContactId id 
     */
    public EmergencyContact(Integer emergencyContactId) {
        this.emergencyContactId = emergencyContactId;
    }

    /**
     * User defined constructor
     * @param emergencyContactId id
     * @param homePhoneNumber home phone number
     */
    public EmergencyContact(Integer emergencyContactId, String homePhoneNumber) {
        this.emergencyContactId = emergencyContactId;
        this.homePhoneNumber = homePhoneNumber;
    }
    
    /**
     * User defined constructor
     * @param emergencyContactId id
     * @param relationToChild relation to child
     * @param emergencyFirstName first name
     * @param emergencyLastName last name
     * @param emergencyAddress address
     * @param homePhoneNumber home phone number
     * @param workPhoneNumber work phone number
     * @param cellphoneNumber cell phone number
     */
    public EmergencyContact(Integer emergencyContactId, String relationToChild, String emergencyFirstName, String emergencyLastName, String emergencyAddress, String homePhoneNumber, String workPhoneNumber, String cellphoneNumber) {
        this.emergencyContactId = emergencyContactId;
        this.relationToChild = relationToChild;
        this.emergencyFirstName = emergencyFirstName;
        this.emergencyLastName = emergencyLastName;
        this.emergencyAddress = emergencyAddress;
        this.homePhoneNumber = homePhoneNumber;
        this.workPhoneNumber = workPhoneNumber;
        this.cellphoneNumber = cellphoneNumber;
    }

    /**
     * Retrieves id
     * @return id
     */
    public Integer getEmergencyContactId() {
        return emergencyContactId;
    }

    /**
     * Modifies id
     * @param emergencyContactId id
     */
    public void setEmergencyContactId(Integer emergencyContactId) {
        this.emergencyContactId = emergencyContactId;
    }

    /**
     * Retrieves relation to child
     * @return relation to child
     */
    public String getRelationToChild() {
        return relationToChild;
    }

    /**
     * Modifies relation to child
     * @param relationToChild relation to child
     */
    public void setRelationToChild(String relationToChild) {
        this.relationToChild = relationToChild;
    }

    /**
     * Retrieves first name
     * @return first name
     */
    public String getEmergencyFirstName() {
        return emergencyFirstName;
    }

    /**
     * Modifies first name
     * @param emergencyFirstName first name 
     */
    public void setEmergencyFirstName(String emergencyFirstName) {
        this.emergencyFirstName = emergencyFirstName;
    }

    /**
     * Retrieves last name
     * @return last name
     */
    public String getEmergencyLastName() {
        return emergencyLastName;
    }

    /**
     * Modifies last name
     * @param emergencyLastName last name
     */
    public void setEmergencyLastName(String emergencyLastName) {
        this.emergencyLastName = emergencyLastName;
    }

    /**
     * Retrieves home phone number
     * @return home phone number
     */
    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    /**
     * Modifies home phone number
     * @param homePhoneNumber home phone number
     */
    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    /**
     * Retrieves work phone number
     * @return work phone number
     */
    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    /**
     * Modifies work phone number
     * @param workPhoneNumber work phone number
     */
    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    /**
     * Retrieves cell phone number
     * @return cell phone number
     */
    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    /**
     * Modifies cell phone number
     * @param cellphoneNumber cell phone number
     */
    public void setCellphoneNumber(String cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }

    /**
     * Retrieves address
     * @return address
     */
    public String getEmergencyAddress() {
        return emergencyAddress;
    }

    /**
     * Modifies address
     * @param emergencyAddress address
     */
    public void setEmergencyAddress(String emergencyAddress) {
        this.emergencyAddress = emergencyAddress;
    }

    /**
     * Retrieves city
     * @return city
     */
    public String getEmergencyCity() {
        return emergencyCity;
    }

    /**
     * Modifies city
     * @param emergencyCity city
     */
    public void setEmergencyCity(String emergencyCity) {
        this.emergencyCity = emergencyCity;
    }

    /**
     * Retrieves province 
     * @return province 
     */
    public String getEmergencyProvince() {
        return emergencyProvince;
    }

    /**
     * Modifies province
     * @param emergencyProvince province 
     */
    public void setEmergencyProvince(String emergencyProvince) {
        this.emergencyProvince = emergencyProvince;
    }

    /**
     * Retrieves postal code
     * @return postal code
     */
    public String getEmergencyPostalCode() {
        return emergencyPostalCode;
    }

    /**
     * Modifies postal code
     * @param emergencyPostalCode postal code
     */
    public void setEmergencyPostalCode(String emergencyPostalCode) {
        this.emergencyPostalCode = emergencyPostalCode;
    }

    /**
     * Retrieves child associated with this emergency contact
     * @return child
     */
    public Child getChild() {
        return child;
    }

    /**
     * Modifies child associated to this emergency contact
     * @param child child
     */
    public void setChild(Child child) {
        this.child = child;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (emergencyContactId != null ? emergencyContactId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EmergencyContact)) {
            return false;
        }
        EmergencyContact other = (EmergencyContact) object;
        if ((this.emergencyContactId == null && other.emergencyContactId != null) || (this.emergencyContactId != null && !this.emergencyContactId.equals(other.emergencyContactId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "problemdomain.EmergencyContact[ emergencyContactId=" + emergencyContactId + " ]";
    }
    
}
