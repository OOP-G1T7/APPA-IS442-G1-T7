package com.example.analyticsapp.log;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Integer>{
    
    @Query(value = "SELECT * FROM audit_log", nativeQuery = true)
    ArrayList<AuditLog> getAllLogs();

}
