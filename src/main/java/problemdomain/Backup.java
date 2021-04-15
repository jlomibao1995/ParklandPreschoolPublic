/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package problemdomain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jean
 */
@Entity
@Table(name = "backup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Backup.findAll", query = "SELECT b FROM Backup b")
    , @NamedQuery(name = "Backup.findByBackupId", query = "SELECT b FROM Backup b WHERE b.backupId = :backupId")
    , @NamedQuery(name = "Backup.findByFilepath", query = "SELECT b FROM Backup b WHERE b.filepath = :filepath")
    , @NamedQuery(name = "Backup.findByDate", query = "SELECT b FROM Backup b WHERE b.date = :date")
    , @NamedQuery(name = "Backup.findByFullBackup", query = "SELECT b FROM Backup b WHERE b.fullBackup = :fullBackup")})
public class Backup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "backup_id")
    private Integer backupId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "filepath")
    private String filepath;
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;
    @Basic(optional = false)
    @NotNull
    @Column(name = "full_backup")
    private boolean fullBackup;

    public Backup() {
    }

    public Backup(Integer backupId) {
        this.backupId = backupId;
    }

    public Backup(Integer backupId, String filepath, boolean fullBackup) {
        this.backupId = backupId;
        this.filepath = filepath;
        this.fullBackup = fullBackup;
    }

    public Integer getBackupId() {
        return backupId;
    }

    public void setBackupId(Integer backupId) {
        this.backupId = backupId;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean getFullBackup() {
        return fullBackup;
    }

    public void setFullBackup(boolean fullBackup) {
        this.fullBackup = fullBackup;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (backupId != null ? backupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Backup)) {
            return false;
        }
        Backup other = (Backup) object;
        if ((this.backupId == null && other.backupId != null) || (this.backupId != null && !this.backupId.equals(other.backupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "problemdomain.Backup[ backupId=" + backupId + " ]";
    }
    
}
