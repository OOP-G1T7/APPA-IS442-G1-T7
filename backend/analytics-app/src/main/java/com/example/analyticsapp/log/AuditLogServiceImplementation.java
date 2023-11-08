package com.example.analyticsapp.log;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

@Service
public class AuditLogServiceImplementation implements AuditLogService {
    
    @Autowired
    private AuditLogRepository auditLogRepository;

    @Override
    public void logAuditEvent(String detail, HttpStatusCode status) {
        AuditLog auditLog = new AuditLog();
        // TODO: get user id from session
        auditLog.setUserId(1);
        auditLog.setDetail(detail);
        if (status == HttpStatus.OK) {
            auditLog.setOutcome("Success");
        } else {
            auditLog.setOutcome("Failure");
        }
        auditLogRepository.save(auditLog);
    }

    public ArrayList<AuditLog> retrieveAllLogs() {
        ArrayList<AuditLog> result = auditLogRepository.getAllLogs();
        return result;
    }
}
