/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *  Model class for registrations
 * @author Jean Lomibao
 * @author Simon Ma
 */
@Entity
@Table(name = "registration")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Registration.findAll", query = "SELECT r FROM Registration r")
    , @NamedQuery(name = "Registration.findByRegistrationId", query = "SELECT r FROM Registration r WHERE r.registrationId = :registrationId")
    , @NamedQuery(name = "Registration.findByRegistrationActive", query = "SELECT r FROM Registration r WHERE r.registrationActive = :registrationActive")
    , @NamedQuery(name = "Registration.findByRegistrationStatus", query = "SELECT r FROM Registration r WHERE r.registrationStatus = :registrationStatus")
    , @NamedQuery(name = "Registration.findByRegistrationDate", query = "SELECT r FROM Registration r WHERE r.registrationDate = :registrationDate")
    , @NamedQuery(name = "Registration.findBySafetySigDateStamp", query = "SELECT r FROM Registration r WHERE r.safetySigDateStamp = :safetySigDateStamp")
    , @NamedQuery(name = "Registration.findByOutdoorSigDateStamp", query = "SELECT r FROM Registration r WHERE r.outdoorSigDateStamp = :outdoorSigDateStamp")
    , @NamedQuery(name = "Registration.findBySickSigDateStamp", query = "SELECT r FROM Registration r WHERE r.sickSigDateStamp = :sickSigDateStamp")
    , @NamedQuery(name = "Registration.findByDisciplineSigDateStamp", query = "SELECT r FROM Registration r WHERE r.disciplineSigDateStamp = :disciplineSigDateStamp")
    , @NamedQuery(name = "Registration.findByIsFullyPaid", query = "SELECT r FROM Registration r WHERE r.isFullyPaid = :isFullyPaid")})
public class Registration implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "registration_id")
    private Integer registrationId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "registration_active")
    private boolean registrationActive;
    @Basic(optional = false)
    @NotNull
    @Column(name = "registration_status")
    private Character registrationStatus;
    @Column(name = "registration_date")
    @Temporal(TemporalType.DATE)
    private Date registrationDate;
    @Column(name = "safety_sig_date_stamp")
    @Temporal(TemporalType.DATE)
    private Date safetySigDateStamp;
    @Column(name = "outdoor_sig_date_stamp")
    @Temporal(TemporalType.DATE)
    private Date outdoorSigDateStamp;
    @Column(name = "sick_sig_date_stamp")
    @Temporal(TemporalType.DATE)
    private Date sickSigDateStamp;
    @Column(name = "discipline_sig_date_stamp")
    @Temporal(TemporalType.DATE)
    private Date disciplineSigDateStamp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "is_fully_paid")
    private boolean isFullyPaid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "registration", fetch = FetchType.EAGER)
    private List<PaymentDetails> paymentDetailsList;
    @JoinColumn(name = "child", referencedColumnName = "child_id")
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Child child;
    @JoinColumn(name = "classroom", referencedColumnName = "classroom_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Classroom classroom;

        /**
     * No arg constructor
     */
    public Registration() {
    }

    /**
     * User defined constructor
     * @param registrationId id
     */
    public Registration(Integer registrationId) {
        this.registrationId = registrationId;
    }

    /**
     * User defined constructor
     * @param registrationId id
     * @param registrationActive active status
     * @param registrationStatus registration status
     * @param isFullyPaid fully paid status
     */
    public Registration(Integer registrationId, boolean registrationActive, Character registrationStatus, boolean isFullyPaid) {
        this.registrationId = registrationId;
        this.registrationActive = registrationActive;
        this.registrationStatus = registrationStatus;
        this.isFullyPaid = isFullyPaid;
    }

    /**
     * Retrieves id
     * @return id
     */
    public Integer getRegistrationId() {
        return registrationId;
    }

    /**
     * Modifies id
     * @param registrationId id
     */
    public void setRegistrationId(Integer registrationId) {
        this.registrationId = registrationId;
    }

    /**
     * Retrieves active status
     * @return true if registration is active
     */
    public boolean getRegistrationActive() {
        return registrationActive;
    }

    /**
     * Modifies active status
     * @param registrationActive active status 
     */
    public void setRegistrationActive(boolean registrationActive) {
        this.registrationActive = registrationActive;
    }

      /**
     * Retrieves registration status
     * @return registration status
     */
    public Character getRegistrationStatus() {
        return registrationStatus;
    }

    /**
     * Modifies registration status
     * @param registrationStatus registration status
     */
    public void setRegistrationStatus(Character registrationStatus) {
        this.registrationStatus = registrationStatus;
    }

    /**
     * Retrieves registration date
     * @return registration date
     */
    public Date getRegistrationDate() {
        return registrationDate;
    }

    /**
     * Modifies registration date
     * @param registrationDate registration date
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }


    /**
     * Retrieves the time stamp for the discipline policy signature
     * @return date stamp
     */
    public Date getDisciplineSigDateStamp() {
        return disciplineSigDateStamp;
    }

    /**
     * Modifies the time stamp for the discipline policy signature
     * @param disciplineSigDateStamp date stamp
     */
    public void setDisciplineSigDateStamp(Date disciplineSigDateStamp) {
        this.disciplineSigDateStamp = disciplineSigDateStamp;
    }

    /**
     * Retrieves time stamp for safety policy signature 
     * @return date stamp
     */
    public Date getSafetySigDateStamp() {
        return safetySigDateStamp;
    }

    /**
     * Modifies time stamp for safety signature
     * @param safetySigDateStamp date stamp
     */
    public void setSafetySigDateStamp(Date safetySigDateStamp) {
        this.safetySigDateStamp = safetySigDateStamp;
    }

    /**
     * Retrieves time stamp for outdoor policy signature
     * @return date stamp
     */
    public Date getOutdoorSigDateStamp() {
        return outdoorSigDateStamp;
    }

    /**
     * Modifies time stamp for outdoor policy signature
     * @param outdoorSigDateStamp date stamp
     */
    public void setOutdoorSigDateStamp(Date outdoorSigDateStamp) {
        this.outdoorSigDateStamp = outdoorSigDateStamp;
    }

    /**
     * Retrieves time stamp for sick policy signature
     * @return date stamp
     */
    public Date getSickSigDateStamp() {
        return sickSigDateStamp;
    }

    /**
     * Modifies time stamp for sick policy signature
     * @param sickSigDateStamp date stamp
     */
    public void setSickSigDateStamp(Date sickSigDateStamp) {
        this.sickSigDateStamp = sickSigDateStamp;
    }

    /**
     * Retrieves fully paid status
     * @return true if registration has been fully paid
     */
    public boolean getIsFullyPaid() {
        return isFullyPaid;
    }

    /**
     * Modifies fully paid status 
     * @param isFullyPaid fully paid status
     */
    public void setIsFullyPaid(boolean isFullyPaid) {
        this.isFullyPaid = isFullyPaid;
    }

    /**
     * List of payments made to the registration
     * @return payments list
     */
    @XmlTransient
    public List<PaymentDetails> getPaymentDetailsList() {
        return paymentDetailsList;
    }

    /**
     * Modifes list of payments
     * @param paymentDetailsList payments list 
     */
    public void setPaymentDetailsList(List<PaymentDetails> paymentDetailsList) {
        this.paymentDetailsList = paymentDetailsList;
    }

    /**
     * Retrieves child registration belongs to
     * @return child
     */
    public Child getChild() {
        return child;
    }

    /**
     * Modifies child the registration is for
     * @param child child
     */
    public void setChild(Child child) {
        this.child = child;
    }

    /**
     * Retrieves class registration is for
     * @return class 
     */
    public Classroom getClassroom() {
        return classroom;
    }

    /**
     * Modifies class registration is for
     * @param classroom class
     */
    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (registrationId != null ? registrationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Registration)) {
            return false;
        }
        Registration other = (Registration) object;
        if ((this.registrationId == null && other.registrationId != null) || (this.registrationId != null && !this.registrationId.equals(other.registrationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "problemdomain.Registration[ registrationId=" + registrationId + " ]";
    }
    
}
