package com.mcis.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mcis.entity.AuditLog;
import com.mcis.enums.ClearanceLevel;
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
            String missionClassification,
            String status,
            String ip) {

			AuditLog log = new AuditLog();
			
			log.setUsername(username);
			log.setRole(role);
			log.setAction(action);
			log.setMission(mission);
			log.setMissionClassification(missionClassification);
			log.setStatus(status);
			log.setIpAddress(ip);
			log.setTimestamp(LocalDateTime.now());
			
			repository.save(log);
    }

    public List<AuditLog> getAllLogs() {
        return repository.findAll();
    }
    public List<AuditLog> getAllowedLogs(ClearanceLevel clearance) {

        switch (clearance) {

            case TOP_SECRET:
                return repository.findAll();

            case SECRET:
                return repository.findByMissionClassificationIn(
                        List.of(
                                "SECRET",
                                "CONFIDENTIAL",
                                "RESTRICTED"));

            case CONFIDENTIAL:
                return repository.findByMissionClassificationIn(
                        List.of(
                                "CONFIDENTIAL",
                                "RESTRICTED"));

            case RESTRICTED:
                return repository.findByMissionClassificationIn(
                        List.of(
                                "RESTRICTED"));

            default:
                return List.of();
        }

    }
    public long getAuditCount() {
        return repository.count();
    }

    public long getGrantedCount() {
        return repository.countByStatus("GRANTED");
    }

    public long getDeniedCount() {
        return repository.countByStatus("DENIED");
    }

}