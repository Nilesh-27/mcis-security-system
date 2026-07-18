package com.mcis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mcis.entity.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long> {

}