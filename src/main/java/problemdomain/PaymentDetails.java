package problemdomain;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model class for payment information
 * @author Jean Lomibao
 * @author Simon Ma
 */
@Entity
@Table(name = "payment_details")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentDetails.findAll", query = "SELECT p FROM PaymentDetails p")
    , @NamedQuery(name = "PaymentDetails.findByPaymentId", query = "SELECT p FROM PaymentDetails p WHERE p.paymentId = :paymentId")
    , @NamedQuery(name = "PaymentDetails.findByInvoiceId", query = "SELECT p FROM PaymentDetails p WHERE p.invoiceId = :invoiceId")
    , @NamedQuery(name = "PaymentDetails.findByPayee", query = "SELECT p FROM PaymentDetails p WHERE p.payee = :payee")
    , @NamedQuery(name = "PaymentDetails.findByPayer", query = "SELECT p FROM PaymentDetails p WHERE p.payer = :payer")
    , @NamedQuery(name = "PaymentDetails.findByPaymentStatus", query = "SELECT p FROM PaymentDetails p WHERE p.paymentStatus = :paymentStatus")
    , @NamedQuery(name = "PaymentDetails.findByPaymentSubtotal", query = "SELECT p FROM PaymentDetails p WHERE p.paymentSubtotal = :paymentSubtotal")
    , @NamedQuery(name = "PaymentDetails.findByPaymentTotal", query = "SELECT p FROM PaymentDetails p WHERE p.paymentTotal = :paymentTotal")
    , @NamedQuery(name = "PaymentDetails.findByPaymentDate", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDate = :paymentDate")
    , @NamedQuery(name = "PaymentDetails.findByPaymentMonth", query = "SELECT p FROM PaymentDetails p WHERE p.paymentMonth = :paymentMonth")
    , @NamedQuery(name = "PaymentDetails.findByPaymentDescription", query = "SELECT p FROM PaymentDetails p WHERE p.paymentDescription = :paymentDescription")
    , @NamedQuery(name = "PaymentDetails.findByPaymentMethod", query = "SELECT p FROM PaymentDetails p WHERE p.paymentMethod = :paymentMethod")
    , @NamedQuery(name = "PaymentDetails.findUniqueMonths", query = "SELECT DISTINCT p.paymentMonth FROM PaymentDetails p")})
public class PaymentDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "payment_id")
    private Integer paymentId;
    @Size(max = 20)
    @Column(name = "invoice_id")
    private String invoiceId;
    @Size(max = 50)
    @Column(name = "payee")
    private String payee;
    @Size(max = 50)
    @Column(name = "payer")
    private String payer;
    @Basic(optional = false)
    @NotNull
    @Column(name = "payment_status")
    private Character paymentStatus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "payment_subtotal")
    private double paymentSubtotal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "payment_total")
    private double paymentTotal;
    @Column(name = "payment_date")
    @Temporal(TemporalType.DATE)
    private Date paymentDate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "payment_month")
    private String paymentMonth;
    @Size(max = 50)
    @Column(name = "payment_description")
    private String paymentDescription;
    @Size(max = 10)
    @Column(name = "payment_method")
    private String paymentMethod;
    @JoinColumn(name = "registration", referencedColumnName = "registration_id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Registration registration;

    /**
     * No arg constructor
     */
    public PaymentDetails() {
    }

    /**
     * User defined constructor
     * @param paymentId id
     */
    public PaymentDetails(Integer paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * User defined constructor
     * @param paymentId id
     * @param paymentStatus payment status
     * @param paymentSubtotal subtotal
     * @param paymentTotal total
     * @param paymentMonth month
     */
    public PaymentDetails(Integer paymentId, Character paymentStatus, double paymentSubtotal, double paymentTotal, String paymentMonth) {
        this.paymentId = paymentId;
        this.paymentStatus = paymentStatus;
        this.paymentSubtotal = paymentSubtotal;
        this.paymentTotal = paymentTotal;
        this.paymentMonth = paymentMonth;
    }

    /**
     * Retrieves id
     * @return id
     */
    public Integer getPaymentId() {
        return paymentId;
    }

    /**
     * Modifies id
     * @param paymentId id
     */
    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * Retrieves invoice id
     * @return invoice id
     */
    public String getInvoiceId() {
        return invoiceId;
    }

    /**
     * Modifies invoice id
     * @param invoiceId invoice id
     */
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }

    /**
     * Retrieves payee
     * @return payee
     */
    public String getPayee() {
        return payee;
    }

    /**
     * Modifes payee
     * @param payee payee
     */
    public void setPayee(String payee) {
        this.payee = payee;
    }

    /**
     * Retrieves payer
     * @return payer
     */
    public String getPayer() {
        return payer;
    }

    /**
     * Modifies payer
     * @param payer payer
     */
    public void setPayer(String payer) {
        this.payer = payer;
    }

    /**
     * Retrieves payment status
     * @return payment status
     */
    public Character getPaymentStatus() {
        return paymentStatus;
    }

    /**
     * Modifies payment status
     * @param paymentStatus payment status
     */
    public void setPaymentStatus(Character paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    /**
     * Retrieves subtotal
     * @return subtotal
     */
    public double getPaymentSubtotal() {
        return paymentSubtotal;
    }

    /**
     * Modifies subtotal
     * @param paymentSubtotal subtotal
     */
    public void setPaymentSubtotal(double paymentSubtotal) {
        this.paymentSubtotal = paymentSubtotal;
    }

    /**
     * Retrieves total
     * @return total
     */
    public double getPaymentTotal() {
        return paymentTotal;
    }

    /**
     * Modifies total
     * @param paymentTotal total 
     */
    public void setPaymentTotal(double paymentTotal) {
        this.paymentTotal = paymentTotal;
    }

    /**
     * Retrieves date
     * @return date
     */
    public Date getPaymentDate() {
        return paymentDate;
    }

    /**
     * Modifies date
     * @param paymentDate date
     */
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    /**
     * Retrieves month payment is for
     * @return month
     */
    public String getPaymentMonth() {
        return paymentMonth;
    }

    /**
     * Modifies month 
     * @param paymentMonth month
     */
    public void setPaymentMonth(String paymentMonth) {
        this.paymentMonth = paymentMonth;
    }

    /**
     * Retrieves payment description
     * @return payment description
     */
    public String getPaymentDescription() {
        return paymentDescription;
    }

    /**
     * Modifies payment description
     * @param paymentDescription payment description
     */
    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    /**
     * Retrieves payment method
     * @return payment method
     */
    public String getPaymentMethod() {
        return paymentMethod;
    }

    /**
     * Modifies payment method
     * @param paymentMethod payment method
     */
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    /**
     * Retrieves registration payment is for
     * @return registration
     */
    public Registration getRegistration() {
        return registration;
    }

    /**
     * Modifies registration payment is for
     * @param registration registration
     */
    public void setRegistration(Registration registration) {
        this.registration = registration;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentId != null ? paymentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentDetails)) {
            return false;
        }
        PaymentDetails other = (PaymentDetails) object;
        if ((this.paymentId == null && other.paymentId != null) || (this.paymentId != null && !this.paymentId.equals(other.paymentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "problemdomain.PaymentDetails[ paymentId=" + paymentId + " ]";
    }
    
}
