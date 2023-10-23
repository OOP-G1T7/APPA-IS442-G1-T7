package com.example.analyticsapp.log;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "audit_log")
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private int logId;

    @Column(name = "user_id")
    private int userId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "log_ts")
    private Date timestamp;

    @Column(name = "detail")
    private String detail;

    @Column(name = "outcome")
    private String outcome;


    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    @PrePersist
    protected void onCreate() {
        timestamp = new Date(System.currentTimeMillis());
    }

    public String toString() {
        return "AuditLog [logId=" + logId + ", userId=" + userId + ", timestamp=" + timestamp + ", detail=" + detail
                + ", outcome=" + outcome + "]";
    }
}