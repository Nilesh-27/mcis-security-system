package com.mcis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mcis.entity.AuditLog;

public interface AuditRepository extends JpaRepository<AuditLog, Long> {

    List<AuditLog> findByMissionClassificationIn(List<String> classifications);

    long countByStatus(String status);

}