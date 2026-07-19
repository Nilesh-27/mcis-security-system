package com.mcis.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mcis.entity.Mission;
import com.mcis.enums.ClearanceLevel;
import com.mcis.repository.MissionRepository;

@Service
public class MissionService {

    private final MissionRepository missionRepository;

    public MissionService(MissionRepository missionRepository) {
        this.missionRepository = missionRepository;
    }

    public List<Mission> getAllMissions() {
        return missionRepository.findAll();
    }

    public List<Mission> getAllowedMissions(ClearanceLevel clearance) {

        switch (clearance) {

            case TOP_SECRET:
                return missionRepository.findAll();

            case SECRET:
                return missionRepository.findByClassificationIn(
                        Arrays.asList(
                                ClearanceLevel.SECRET,
                                ClearanceLevel.CONFIDENTIAL,
                                ClearanceLevel.RESTRICTED));

            case CONFIDENTIAL:
                return missionRepository.findByClassificationIn(
                        Arrays.asList(
                                ClearanceLevel.CONFIDENTIAL,
                                ClearanceLevel.RESTRICTED));

            case RESTRICTED:
                return missionRepository.findByClassificationIn(
                        Arrays.asList(
                                ClearanceLevel.RESTRICTED));

            default:
                return List.of();
        }
    }

    public Mission saveMission(Mission mission) {
        return missionRepository.save(mission);
    }

    public Mission getMission(Long id) {
        return missionRepository.findById(id).orElse(null);
    }

    public void deleteMission(Long id) {
        missionRepository.deleteById(id);
    }

    public Mission findById(Long id) {
        return missionRepository.findById(id).orElse(null);
    }
    public long getMissionCount() {
        return missionRepository.count();
    }
}