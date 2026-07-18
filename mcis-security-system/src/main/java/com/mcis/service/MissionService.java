package com.mcis.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mcis.entity.Mission;
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

    public Mission saveMission(Mission mission) {
        return missionRepository.save(mission);
    }

    public Mission getMission(Long id) {
        return missionRepository.findById(id).orElse(null);
    }

    public void deleteMission(Long id) {
        missionRepository.deleteById(id);
    }
    public Mission findById(Long id){

        return missionRepository.findById(id).orElse(null);

    }
}