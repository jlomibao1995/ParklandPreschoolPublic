package problemdomain;

import java.io.Serializable;
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
 * Model class for an account
 *
 * @author Jean Lomibao
 * @author Simon Ma
 */
@Entity
@Table(name = "account")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Account.findAll", query = "SELECT a FROM Account a")
    , @NamedQuery(name = "Account.findByAccountId", query = "SELECT a FROM Account a WHERE a.accountId = :accountId")
    , @NamedQuery(name = "Account.findByEmail", query = "SELECT a FROM Account a WHERE a.email = :email")
    , @NamedQuery(name = "Account.findByPassword", query = "SELECT a FROM Account a WHERE a.password = :password")
    , @NamedQuery(name = "Account.findByAccountFirstName", query = "SELECT a FROM Account a WHERE a.accountFirstName = :accountFirstName")
    , @NamedQuery(name = "Account.findByAccountLastName", query = "SELECT a FROM Account a WHERE a.accountLastName = :accountLastName")
    , @NamedQuery(name = "Account.findByAccountAddress", query = "SELECT a FROM Account a WHERE a.accountAddress = :accountAddress")
    , @NamedQuery(name = "Account.findByAccountCity", query = "SELECT a FROM Account a WHERE a.accountCity = :accountCity")
    , @NamedQuery(name = "Account.findByAccountProvince", query = "SELECT a FROM Account a WHERE a.accountProvince = :accountProvince")
    , @NamedQuery(name = "Account.findByAccountPostalCode", query = "SELECT a FROM Account a WHERE a.accountPostalCode = :accountPostalCode")
    , @NamedQuery(name = "Account.findByAccountPhoneNumber", query = "SELECT a FROM Account a WHERE a.accountPhoneNumber = :accountPhoneNumber")
    , @NamedQuery(name = "Account.findByWorkPhoneNumber", query = "SELECT a FROM Account a WHERE a.workPhoneNumber = :workPhoneNumber")
    , @NamedQuery(name = "Account.findByCellphoneNumber", query = "SELECT a FROM Account a WHERE a.cellphoneNumber = :cellphoneNumber")
    , @NamedQuery(name = "Account.findByAccountType", query = "SELECT a FROM Account a WHERE a.accountType = :accountType")
    , @NamedQuery(name = "Account.findByRelationToChild", query = "SELECT a FROM Account a WHERE a.relationToChild = :relationToChild")
    , @NamedQuery(name = "Account.findByAccountStatus", query = "SELECT a FROM Account a WHERE a.accountStatus = :accountStatus")
    , @NamedQuery(name = "Account.findByActivateAccountUuid", query = "SELECT a FROM Account a WHERE a.activateAccountUuid = :activateAccountUuid")
    , @NamedQuery(name = "Account.findByResetPasswordUuid", query = "SELECT a FROM Account a WHERE a.resetPasswordUuid = :resetPasswordUuid")
    , @NamedQuery(name = "Account.findByCreationDate", query = "SELECT a FROM Account a WHERE a.creationDate = :creationDate")
    , @NamedQuery(name = "Account.findByLastLoginDate", query = "SELECT a FROM Account a WHERE a.lastLoginDate = :lastLoginDate")
    , @NamedQuery(name = "Account.findBySalt", query = "SELECT a FROM Account a WHERE a.salt = :salt")})
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "account_id")
    private Integer accountId;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "account_first_name")
    private String accountFirstName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "account_last_name")
    private String accountLastName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "account_address")
    private String accountAddress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "account_city")
    private String accountCity;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "account_province")
    private String accountProvince;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "account_postal_code")
    private String accountPostalCode;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "account_phone_number")
    private String accountPhoneNumber;
    @Size(max = 20)
    @Column(name = "work_phone_number")
    private String workPhoneNumber;
    @Size(max = 20)
    @Column(name = "cellphone_number")
    private String cellphoneNumber;
    @Basic(optional = false)
    @NotNull
    @Column(name = "account_type")
    private Character accountType;
    @Size(max = 20)
    @Column(name = "relation_to_child")
    private String relationToChild;
    @Basic(optional = false)
    @NotNull
    @Column(name = "account_status")
    private boolean accountStatus;
    @Size(max = 50)
    @Column(name = "activate_account_uuid")
    private String activateAccountUuid;
    @Size(max = 50)
    @Column(name = "reset_password_uuid")
    private String resetPasswordUuid;
    @Column(name = "creation_date")
    @Temporal(TemporalType.DATE)
    private Date creationDate;
    @Column(name = "last_login_date")
    @Temporal(TemporalType.DATE)
    private Date lastLoginDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "salt")
    private String salt;
    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private List<Classroom> classroomList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "account", fetch = FetchType.EAGER)
    private List<Child> childList;

    /**
     * No arg constructor
     */
    public Account() {
    }

    /**
     * User defined constructor
     *
     * @param accountId account id
     */
    public Account(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * User defined constructor
     * @param accountId account id
     * @param email account email
     * @param password account password
     * @param accountFirstName first name
     * @param accountLastName last name
     * @param accountAddress account address
     * @param accountCity city user lives in
     * @param accountProvince province user lives in
     * @param accountPostalCode postal code of user
     * @param accountPhoneNumber home phone number
     * @param accountType account type
     * @param accountStatus account status
     * @param salt password salt
     */
    public Account(Integer accountId, String email, String password, String accountFirstName, 
            String accountLastName, String accountAddress, String accountCity, 
            String accountProvince, String accountPostalCode, String accountPhoneNumber, 
            Character accountType, boolean accountStatus, String salt) {
        this.accountId = accountId;
        this.email = email;
        this.password = password;
        this.accountFirstName = accountFirstName;
        this.accountLastName = accountLastName;
        this.accountAddress = accountAddress;
        this.accountCity = accountCity;
        this.accountProvince = accountProvince;
        this.accountPostalCode = accountPostalCode;
        this.accountPhoneNumber = accountPhoneNumber;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.salt = salt;
    }
    
    /**
     * User defined constructor
     * @param accountFirstName first name
     * @param relationToChild relation to child
     */
    public Account(String accountFirstName, String relationToChild) {
        this.accountFirstName = accountFirstName;
        this.relationToChild = relationToChild;
    }

    /**
     * Retrieves the account id
     * @return accountId
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * Modifies the accountId
     * @param accountId account id
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * Retrieves the account email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Modifies the email
     * @param email email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Modifies the password
     * @param password password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the first name
     * @return first name
     */
    public String getAccountFirstName() {
        return accountFirstName;
    }

    /**
     * Modifies the account first name
     * @param accountFirstName first name
     */
    public void setAccountFirstName(String accountFirstName) {
        this.accountFirstName = accountFirstName;
    }

    /**
     * Retrieves the account last name
     * @return last name
     */
    public String getAccountLastName() {
        return accountLastName;
    }

    /**
     * Modifies the account last name
     * @param accountLastName last name
     */
    public void setAccountLastName(String accountLastName) {
        this.accountLastName = accountLastName;
    }

    /**
     * Retrieves the account address
     * @return address
     */
    public String getAccountAddress() {
        return accountAddress;
    }

    /**
     * Modifies the address
     * @param accountAddress address 
     */
    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    /**
     * Retrieves the city
     * @return city
     */
    public String getAccountCity() {
        return accountCity;
    }

    /**
     * Modifies the city
     * @param accountCity city
     */
    public void setAccountCity(String accountCity) {
        this.accountCity = accountCity;
    }

    /**
     * Retrieves the province
     * @return province
     */
    public String getAccountProvince() {
        return accountProvince;
    }

    /**
     * Modifies the province
     * @param accountProvince province 
     */
    public void setAccountProvince(String accountProvince) {
        this.accountProvince = accountProvince;
    }

    /**
     * Retrieves the postal code
     * @return postal code
     */
    public String getAccountPostalCode() {
        return accountPostalCode;
    }

    /**
     * Modifies the postal code
     * @param accountPostalCode postal code 
     */
    public void setAccountPostalCode(String accountPostalCode) {
        this.accountPostalCode = accountPostalCode;
    }

    /**
     * Retrieves the home phone number
     * @return home phone number
     */
    public String getAccountPhoneNumber() {
        return accountPhoneNumber;
    }

    /**
     * Modifies the home phone number
     * @param accountPhoneNumber home phone humber
     */
    public void setAccountPhoneNumber(String accountPhoneNumber) {
        this.accountPhoneNumber = accountPhoneNumber;
    }

    /**
     * Retrieves the work phone number
     * @return work phone number
     */
    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    /**
     * Modifies the work phone number
     * @param workPhoneNumber work phone number
     */
    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    /**
     * Retrieves the cell phone number
     * @return cell phone number
     */
    public String getCellphoneNumber() {
        return cellphoneNumber;
    }

    /**
     * Modifies the cell phone number
     * @param cellphoneNumber cell phone number
     */
    public void setCellphoneNumber(String cellphoneNumber) {
        this.cellphoneNumber = cellphoneNumber;
    }

    /**
     * Retrieves the account type
     * @return account type
     */
    public Character getAccountType() {
        return accountType;
    }

    /**
     * Modifies the account type
     * @param accountType account type
     */
    public void setAccountType(Character accountType) {
        this.accountType = accountType;
    }

    /**
     * Retrieves the releation to child of account
     * @return relation to child
     */
    public String getRelationToChild() {
        return relationToChild;
    }

    /**
     * Modifies the relation to child of account
     * @param relationToChild relation to child
     */
    public void setRelationToChild(String relationToChild) {
        this.relationToChild = relationToChild;
    }

    /**
     * Retrieves the account status
     * @return true if account is active
     */
    public boolean getAccountStatus() {
        return accountStatus;
    }

    /**
     * Modifies account status
     * @param accountStatus account status
     */
    public void setAccountStatus(boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    /**
     * Retrieves the activation uuid
     * @return activation uuid
     */
    public String getActivateAccountUuid() {
        return activateAccountUuid;
    }

    /**
     * Modifies the activation uuid
     * @param activateAccountUuid activation uuid
     */
    public void setActivateAccountUuid(String activateAccountUuid) {
        this.activateAccountUuid = activateAccountUuid;
    }

    /**
     * Retrieves the reset uuid
     * @return reset uuid
     */
    public String getResetPasswordUuid() {
        return resetPasswordUuid;
    }

    /**
     * Modifies the reset uuid 
     * @param resetPasswordUuid reset uuid
     */
    public void setResetPasswordUuid(String resetPasswordUuid) {
        this.resetPasswordUuid = resetPasswordUuid;
    }

    /**
     * Retrieves the creation date of account
     * @return creation date
     */
    public Date getCreationDate() {
        return creationDate;
    }

    /**
     * Modifies creation date of account
     * @param creationDate creation date
     */
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * Retrieves last login date of account
     * @return last login date
     */
    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    /**
     * Modifies last login date
     * @param lastLoginDate last login date
     */
    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    /**
     * Retrieves salt
     * @return salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * Modifies salt
     * @param salt password salt
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

        /**
     * List of classrooms the account is associated with
     * If account is a staff or admin they may teach a class
     * @return classroom list
     */
    @XmlTransient
    public List<Classroom> getClassroomList() {
        return classroomList;
    }

    /**
     * Modifies the classroom list
     * @param classroomList classroom list
     */
    public void setClassroomList(List<Classroom> classroomList) {
        this.classroomList = classroomList;
    }

    /**
     * Retrieves the list of children associated to the account
     * @return child list
     */
    @XmlTransient
    public List<Child> getChildList() {
        return childList;
    }

    /**
     * Modifies the list of children associated to account
     * @param childList child list
     */
    public void setChildList(List<Child> childList) {
        this.childList = childList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (accountId != null ? accountId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Account)) {
            return false;
        }
        Account other = (Account) object;
        if ((this.accountId == null && other.accountId != null) || (this.accountId != null && !this.accountId.equals(other.accountId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "problemdomain.Account[ accountId=" + accountId + " ]";
    }

}
