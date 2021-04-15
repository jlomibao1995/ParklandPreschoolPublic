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
 * Model class for authorized pickup
 * @author Jean Lomibao
 * @author Simon Ma
 */
@Entity
@Table(name = "authorized_pickup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AuthorizedPickup.findAll", query = "SELECT a FROM AuthorizedPickup a")
    , @NamedQuery(name = "AuthorizedPickup.findByAuthorizedPickupId", query = "SELECT a FROM AuthorizedPickup a WHERE a.authorizedPickupId = :authorizedPickupId")
    , @NamedQuery(name = "AuthorizedPickup.findByAuthorizedFirstName", query = "SELECT a FROM AuthorizedPickup a WHERE a.authorizedFirstName = :authorizedFirstName")
    , @NamedQuery(name = "AuthorizedPickup.findByAuthorizedLastName", query = "SELECT a FROM AuthorizedPickup a WHERE a.authorizedLastName = :authorizedLastName")
    , @NamedQuery(name = "AuthorizedPickup.findByAuthorizedPhoneNumber", query = "SELECT a FROM AuthorizedPickup a WHERE a.authorizedPhoneNumber = :authorizedPhoneNumber")})
public class AuthorizedPickup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "authorized_pickup_id")
    private Integer authorizedPickupId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "authorized_first_name")
    private String authorizedFirstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "authorized_last_name")
    private String authorizedLastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "authorized_phone_number")
    private String authorizedPhoneNumber;
    @JoinColumn(name = "child", referencedColumnName = "child_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Child child;

    /**
     * No arg constructor
     */
    public AuthorizedPickup() {
    }

    /**
     * User defined constructor
     * @param authorizedPickupId id  
     */
    public AuthorizedPickup(Integer authorizedPickupId) {
        this.authorizedPickupId = authorizedPickupId;
    }

    /**
     * User defined constructor
     * @param authorizedPickupId id
     * @param authorizedFirstName first name
     * @param authorizedLastName last name
     * @param authorizedPhoneNumber phone number
     */
    public AuthorizedPickup(Integer authorizedPickupId, String authorizedFirstName, String authorizedLastName, String authorizedPhoneNumber) {
        this.authorizedPickupId = authorizedPickupId;
        this.authorizedFirstName = authorizedFirstName;
        this.authorizedLastName = authorizedLastName;
        this.authorizedPhoneNumber = authorizedPhoneNumber;
    }

    /**
     * Retrieves id
     * @return id
     */
    public Integer getAuthorizedPickupId() {
        return authorizedPickupId;
    }

    /**
     * Modifies id
     * @param authorizedPickupId id
     */
    public void setAuthorizedPickupId(Integer authorizedPickupId) {
        this.authorizedPickupId = authorizedPickupId;
    }

    /**
     * Retrieves first name
     * @return first name
     */
    public String getAuthorizedFirstName() {
        return authorizedFirstName;
    }

    /**
     * Modifies first name
     * @param authorizedFirstName first name
     */
    public void setAuthorizedFirstName(String authorizedFirstName) {
        this.authorizedFirstName = authorizedFirstName;
    }

    /**
     * Retrieves last name
     * @return last name
     */
    public String getAuthorizedLastName() {
        return authorizedLastName;
    }

    /**
     * Modifies last name
     * @param authorizedLastName last name
     */
    public void setAuthorizedLastName(String authorizedLastName) {
        this.authorizedLastName = authorizedLastName;
    }

    /**
     * Retrieves phone number
     * @return phone number
     */
    public String getAuthorizedPhoneNumber() {
        return authorizedPhoneNumber;
    }

    /**
     * Modifies phone number
     * @param authorizedPhoneNumber phone number
     */
    public void setAuthorizedPhoneNumber(String authorizedPhoneNumber) {
        this.authorizedPhoneNumber = authorizedPhoneNumber;
    }

    /**
     * Retrieves child authorized to pick up
     * @return child
     */
    public Child getChild() {
        return child;
    }

    /**
     * Modifies child authorized to pickup
     * @param child child
     */
    public void setChild(Child child) {
        this.child = child;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (authorizedPickupId != null ? authorizedPickupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AuthorizedPickup)) {
            return false;
        }
        AuthorizedPickup other = (AuthorizedPickup) object;
        if ((this.authorizedPickupId == null && other.authorizedPickupId != null) || (this.authorizedPickupId != null && !this.authorizedPickupId.equals(other.authorizedPickupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "problemdomain.AuthorizedPickup[ authorizedPickupId=" + authorizedPickupId + " ]";
    }
    
}
