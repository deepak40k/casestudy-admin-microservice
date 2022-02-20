package com.casestudy.skilltracker.admin.service.impl;

import com.casestudy.skilltracker.admin.dto.SearchResponse;
import com.casestudy.skilltracker.admin.mapper.DTOMapper;
import com.casestudy.skilltracker.admin.model.AssociateProfile;
import com.casestudy.skilltracker.admin.repository.AssociateRepository;
import com.casestudy.skilltracker.admin.service.AssociateService;
import com.casestudy.skilltracker.admin.service.MemcachedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class AssociateServiceImpl implements AssociateService {
    @Autowired
    AssociateRepository associateRepository;
    @Autowired
    DTOMapper<SearchResponse, Page<AssociateProfile>> mapper;
   // @Autowired
   // private MemcachedClient memcachedClient;
    @Autowired
    private MemcachedService<AssociateProfile> memcachedService;

    public SearchResponse findBySkill(String value, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<AssociateProfile> associateProfilesPage = associateRepository.findBySkill(value, paging);
        return mapper.mapToDto(associateProfilesPage);
    }

    public SearchResponse findByName(String value, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<AssociateProfile> associateProfilesPage = associateRepository.findByName("^" + value, paging);
        return mapper.mapToDto(associateProfilesPage);
    }

    public SearchResponse findByAssociateId(String value, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<AssociateProfile> associateProfilesPage;
        AssociateProfile associateProfile = null;
        associateProfile=memcachedService.get(value);
        /*if (memcachedClient != null)
            associateProfile = (AssociateProfile) memcachedClient.get(value);*/
        if (associateProfile != null) {
            List<AssociateProfile> associateProfiles = Arrays.asList(associateProfile);
            associateProfilesPage = new PageImpl<>(associateProfiles, paging, associateProfiles.size());
            log.info("profile found in cache for associate id:" + value);
            return mapper.mapToDto(associateProfilesPage);
        }
        associateProfilesPage = associateRepository.findByAssociateId(value, paging);
        if (associateProfilesPage.getContent().size() > 0)
            memcachedService.set(value, associateProfilesPage.getContent().get(0));
        return mapper.mapToDto(associateProfilesPage);
    }
}
