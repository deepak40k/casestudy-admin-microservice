package com.casestudy.skilltracker.admin.mapper.impl;

import com.casestudy.skilltracker.admin.dto.AssociateProfileResponse;
import com.casestudy.skilltracker.admin.dto.SearchResponse;
import com.casestudy.skilltracker.admin.exception.ValidationException;
import com.casestudy.skilltracker.admin.mapper.DTOMapper;
import com.casestudy.skilltracker.admin.model.AssociateProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SearchResponseMapper implements DTOMapper<SearchResponse, Page<AssociateProfile>> {

    @Autowired
    DTOMapper<AssociateProfileResponse, AssociateProfile> responseMapper;

    @Override
    public SearchResponse mapToDto(Page<AssociateProfile> associateProfilesPage) {

        List<AssociateProfile> associateProfiles = null;
        associateProfiles = associateProfilesPage.getContent();
        List<AssociateProfileResponse> associates = associateProfiles.stream().map(
                x -> responseMapper.mapToDto(x)
        ).collect(Collectors.toList());
         SearchResponse searchResponse=SearchResponse.builder().associates(associates)
                .currentPage(associateProfilesPage.getNumber())
                .totalPages(associateProfilesPage.getTotalPages())
                .total(associateProfilesPage.getTotalElements())
                .build();
        log.debug("Mapped SearchResponse:"+searchResponse);
        return searchResponse;
    }

    @Override
    public Page<AssociateProfile> mapToEntity(SearchResponse searchResponse) {
        log.error("Mapping Not Allowed");
        throw new ValidationException("Mapping Not Allowed");
    }

}

