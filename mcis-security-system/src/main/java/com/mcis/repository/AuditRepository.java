package com.mcis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mcis.entity.AuditLog;

public interface AuditRepository extends JpaRepository<AuditLog, Long> {

}