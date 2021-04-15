package problemdomain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Model class for a class room
 * @author Jean Lomibao
 * @author Simon Ma
 */
@Entity
@Table(name = "classroom")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Classroom.findAll", query = "SELECT c FROM Classroom c")
    , @NamedQuery(name = "Classroom.findByClassroomId", query = "SELECT c FROM Classroom c WHERE c.classroomId = :classroomId")
    , @NamedQuery(name = "Classroom.findByCapacity", query = "SELECT c FROM Classroom c WHERE c.capacity = :capacity")
    , @NamedQuery(name = "Classroom.findByStartDate", query = "SELECT c FROM Classroom c WHERE c.startDate = :startDate")
    , @NamedQuery(name = "Classroom.findByEndDate", query = "SELECT c FROM Classroom c WHERE c.endDate = :endDate")
    , @NamedQuery(name = "Classroom.findByEnrolled", query = "SELECT c FROM Classroom c WHERE c.enrolled = :enrolled")
    , @NamedQuery(name = "Classroom.findByAgeGroup", query = "SELECT c FROM Classroom c WHERE c.ageGroup = :ageGroup")
    , @NamedQuery(name = "Classroom.findByYear", query = "SELECT c FROM Classroom c WHERE c.year = :year")
    , @NamedQuery(name = "Classroom.findByCostPerMonth", query = "SELECT c FROM Classroom c WHERE c.costPerMonth = :costPerMonth")
    , @NamedQuery(name = "Classroom.findByDays", query = "SELECT c FROM Classroom c WHERE c.days = :days")
    , @NamedQuery(name = "Classroom.findByDescription", query = "SELECT c FROM Classroom c WHERE c.description = :description")})
public class Classroom implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "classroom_id")
    private Integer classroomId;
    @Column(name = "capacity")
    private Integer capacity;
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    @Column(name = "enrolled")
    private Integer enrolled;
    @Basic(optional = false)
    @NotNull
    @Column(name = "age_group")
    private int ageGroup;
    @Column(name = "year")
    private Integer year;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cost_per_month")
    private Double costPerMonth;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "days")
    private String days;
    @Size(max = 30)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "account", referencedColumnName = "account_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;
    @OneToMany(mappedBy = "classroom", fetch = FetchType.LAZY)
    private List<Registration> registrationList;

    /**
     * No arg constructor
     */
    public Classroom() {
    }

    /**
     * User defined constructor
     * @param classroomId id
     */
    public Classroom(Integer classroomId) {
        this.classroomId = classroomId;
    }

    /**
     * User defined constructor
     * @param classroomId id
     * @param ageGroup age group
     * @param days days class is in session
     */
    public Classroom(Integer classroomId, int ageGroup, String days) {
        this.classroomId = classroomId;
        this.ageGroup = ageGroup;
        this.days = days;
    }

    /**
     * Retrieve classroom id
     * @return id
     */
    public Integer getClassroomId() {
        return classroomId;
    }

    /**
     * Modifies class room id 
     * @param classroomId id
     */
    public void setClassroomId(Integer classroomId) {
        this.classroomId = classroomId;
    }

    /**
     * Retrieves capacity
     * @return capacity 
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * Modifies capacity
     * @param capacity capacity
     */
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    /**
     * Retrieves start date
     * @return  start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * modifies start date
     * @param startDate start date
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Retrieves end date
     * @return end date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Modifies end date
     * @param endDate end date
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Retrieves number of enrolled children
     * @return enrolled
     */
    public Integer getEnrolled() {
        return enrolled;
    }

    /**
     * Modifies number of enrolled children
     * @param enrolled enrolled 
     */
    public void setEnrolled(Integer enrolled) {
        this.enrolled = enrolled;
    }

    /**
     * Retrieves age group
     * @return age group
     */
    public int getAgeGroup() {
        return ageGroup;
    }

    /**
     * Modifies age group
     * @param ageGroup age group
     */
    public void setAgeGroup(int ageGroup) {
        this.ageGroup = ageGroup;
    }

    /**
     * Retrieves year
     * @return year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Modifies year
     * @param year year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Retrieves cost per month
     * @return cost per month
     */
    public Double getCostPerMonth() {
        return costPerMonth;
    }

    /**
     * Modifies cost per month
     * @param costPerMonth cost per month
     */
    public void setCostPerMonth(Double costPerMonth) {
        this.costPerMonth = costPerMonth;
    }

    /**
     * Retrieves days class is in session 
     * @return days
     */
    public String getDays() {
        return days;
    }

    /**
     * Modifies days
     * @param days days
     */
    public void setDays(String days) {
        this.days = days;
    }

    /**
     * Retrieves class description
     * @return class description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Modifies class description
     * @param description description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Retrieves staff account teaching class
     * @return account
     */
    public Account getAccount() {
        return account;
    }

    /**
     * Modifies account teaching class
     * @param account account
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * List of registration associated to the class
     * @return registration list
     */
    @XmlTransient
    public List<Registration> getRegistrationList() {
        return registrationList;
    }

    /**
     * Modifies list registrations associated to class
     * @param registrationList registration list
     */
    public void setRegistrationList(List<Registration> registrationList) {
        this.registrationList = registrationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (classroomId != null ? classroomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Classroom)) {
            return false;
        }
        Classroom other = (Classroom) object;
        if ((this.classroomId == null && other.classroomId != null) || (this.classroomId != null && !this.classroomId.equals(other.classroomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "problemdomain.Classroom[ classroomId=" + classroomId + " ]";
    }
    
}
