package com.mcis.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mcis.entity.Mission;
import com.mcis.enums.ClearanceLevel;
import com.mcis.repository.MissionRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(MissionRepository repository) {

        return args -> {

            if (repository.count() == 0) {

                Mission m1 = new Mission();
                m1.setMissionCode("OP-001");
                m1.setMissionName("Operation Eagle");
                m1.setDescription("Border surveillance mission.");
                m1.setClassification(ClearanceLevel.TOP_SECRET);
                m1.setCommander("Commander Alpha");
                m1.setActive(true);

                Mission m2 = new Mission();
                m2.setMissionCode("OP-002");
                m2.setMissionName("General Bravo");
                m2.setDescription("Reconnaissance mission.");
                m2.setClassification(ClearanceLevel.SECRET);
                m2.setCommander("General Bravo");
                m2.setActive(true);

                Mission m3 = new Mission();
                m3.setMissionCode("OP-003");
                m3.setMissionName("Colonel Smith");
                m3.setDescription("Training documentation.");
                m3.setClassification(ClearanceLevel.RESTRICTED);
                m3.setCommander("Colonel Smith");
                m3.setActive(true);
                
                Mission m4 = new Mission();

                m4.setMissionCode("OP-004");
                m4.setMissionName("Intelligence Briefing");
                m4.setDescription("Enemy movement intelligence report.");
                m4.setClassification(ClearanceLevel.SECRET);
                m4.setCommander("Intelligence Commander");
                m4.setActive(true);
                Mission m5 = new Mission();

                m5.setMissionCode("OP-005");
                m5.setMissionName("Supply Logistics");

                m5.setDescription("Logistics planning and supply chain operations.");

                m5.setClassification(ClearanceLevel.CONFIDENTIAL);

                m5.setCommander("Logistics Officer");

                m5.setActive(true);



                repository.save(m1);
                repository.save(m2);
                repository.save(m3);
                repository.save(m4);
                repository.save(m5);



            }

        };

    }

}