package com.mcis.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mcis.entity.AuditLog;
import com.mcis.repository.AuditRepository;

@Service
public class AuditService {

    private final AuditRepository repository;

    public AuditService(AuditRepository repository) {
        this.repository = repository;
    }

    public void log(String username,
                    String role,
                    String action,
                    String mission,
                    String status,
                    String ip) {

        AuditLog log = new AuditLog();

        log.setUsername(username);
        log.setRole(role);
        log.setAction(action);
        log.setMission(mission);
        log.setStatus(status);
        log.setIpAddress(ip);
        log.setTimestamp(LocalDateTime.now());

        repository.save(log);
    }

    public List<AuditLog> getAllLogs() {
        return repository.findAll();
    }

}