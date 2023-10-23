package com.example.analyticsapp.log;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/logs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @GetMapping("")
    public ArrayList<AuditLog> retrieveAllLogs() {

        ArrayList<AuditLog> result = auditLogService.retrieveAllLogs();
        return result;
    }

    
}
