package com.casestudy.skilltracker.admin.mapper.impl;

import com.casestudy.skilltracker.admin.dto.AssociateProfileResponse;
import com.casestudy.skilltracker.admin.mapper.DTOMapper;
import com.casestudy.skilltracker.admin.model.AssociateProfile;
import com.casestudy.skilltracker.admin.model.Skill;
import com.casestudy.skilltracker.admin.model.SkillSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class AssociateProfileResponseMapper implements DTOMapper<AssociateProfileResponse, AssociateProfile> {

    @Override
    public AssociateProfile mapToEntity(AssociateProfileResponse associateProfileResponse) {
        List<Skill> skills = new ArrayList<>();
        if (associateProfileResponse.getSkills() != null) {
            if (associateProfileResponse.getSkills().getTechnical() != null) {
                List<Skill> technicalSkills = associateProfileResponse.getSkills().getTechnical();
                technicalSkills.forEach(
                        x -> x.setType("Technical")
                );
                skills.addAll(technicalSkills);
            }
            if (associateProfileResponse.getSkills().getNonTechnical() != null) {
                List<Skill> nonTechnicalSkills = associateProfileResponse.getSkills().getNonTechnical();
                nonTechnicalSkills.forEach(
                        x -> x.setType("NonTechnical")
                );
                skills.addAll(nonTechnicalSkills);
            }
        }
        AssociateProfile associateProfile =
                AssociateProfile.builder().userId(associateProfileResponse.getUserId())
                        .associateId(associateProfileResponse.getAssociateId())
                        .email(associateProfileResponse.getEmail())
                        .name(associateProfileResponse.getName())
                        .mobile(associateProfileResponse.getMobile())
                        .skills(skills).build();
        log.debug("Mapped AssociateProfile:"+associateProfile);
        return associateProfile;
    }

    @Override
    public AssociateProfileResponse mapToDto(AssociateProfile associateProfile) {
        List<Skill> skills = associateProfile.getSkills();
        List<Skill> technicalSkills = skills.stream().
                filter(x -> x.getType().equals("Technical")).collect(Collectors.toList());
        List<Skill> nonTechnicalSkills = skills.stream().
                filter(x -> x.getType().equals("NonTechnical")).collect(Collectors.toList());
        SkillSet skillSet = new SkillSet(technicalSkills, nonTechnicalSkills);
        AssociateProfileResponse associateProfileResponse= new AssociateProfileResponse();
        associateProfileResponse.setUserId(associateProfile.getUserId());
        associateProfileResponse.setAssociateId(associateProfile.getAssociateId());
        associateProfileResponse.setEmail(associateProfile.getEmail());
        associateProfileResponse.setName(associateProfile.getName());
        associateProfileResponse.setMobile(associateProfile.getMobile());
        associateProfileResponse.setSkills(skillSet);
        log.debug("Mapped AssociateProfileResponse:"+associateProfileResponse);
        return associateProfileResponse;
    }
}

