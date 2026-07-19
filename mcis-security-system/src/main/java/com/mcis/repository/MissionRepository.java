package com.mcis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mcis.entity.Mission;
import com.mcis.enums.ClearanceLevel;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    List<Mission> findByClassificationIn(List<ClearanceLevel> classifications);

}