package com.example.analyticsapp.log;

import java.util.ArrayList;

import org.springframework.http.HttpStatusCode;

public interface AuditLogService {

    void logAuditEvent(int userId, String detail, HttpStatusCode status);

    ArrayList<AuditLog> retrieveAllLogs();

}
