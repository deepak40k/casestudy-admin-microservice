package com.casestudy.skilltracker.admin.unit.service;

import com.casestudy.skilltracker.admin.dto.AssociateProfileResponse;
import com.casestudy.skilltracker.admin.dto.SearchResponse;
import com.casestudy.skilltracker.admin.exception.ValidationException;
import com.casestudy.skilltracker.admin.mapper.DTOMapper;
import com.casestudy.skilltracker.admin.model.AssociateProfile;
import com.casestudy.skilltracker.admin.repository.AssociateRepository;
import com.casestudy.skilltracker.admin.service.MemcachedService;
import com.casestudy.skilltracker.admin.service.SearchService;
import com.casestudy.skilltracker.admin.utility.ConditionType;
import com.casestudy.skilltracker.admin.utility.TestUtility;
import net.spy.memcached.MemcachedClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchServiceTest {

    @Autowired
    private SearchService searchService;

    @MockBean
    private  MemcachedClient memcachedClient ;

    @MockBean
    private MemcachedService<AssociateProfile> memcachedService;

    @MockBean
    AssociateRepository associateRepository;

    @Autowired
    DTOMapper<AssociateProfileResponse, AssociateProfile> mapper;


    @BeforeEach
    void init() {
        Mockito.when(this.associateRepository.findByAssociateId(Mockito.any(String.class), Mockito.any(Pageable.class))).thenReturn(getPagingResponse());
        Mockito.when(this.associateRepository.findByName(Mockito.any(String.class), Mockito.any(Pageable.class))).thenReturn(getPagingResponse());
        Mockito.when(this.associateRepository.findBySkill(Mockito.any(String.class), Mockito.any(Pageable.class))).thenReturn(getPagingResponse());
    }

    @Test
    void testSearchByName_Success() {
        SearchResponse searchResponse = searchService.find("NAME", "deepak", 0, 5);
        assertNotNull(searchResponse.getAssociates());
    }

    @Test
    void testSearchByAssociateIdFoundInCache_Success() {
        AssociateProfileResponse associateProfileResponse = TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
        Mockito.when(this.memcachedService.get(Mockito.any(String.class))).thenReturn(mapper.mapToEntity(associateProfileResponse));
        Mockito.when(this.associateRepository.findByAssociateId(Mockito.any(String.class), Mockito.any(Pageable.class))).thenReturn(getPagingResponse());
        SearchResponse searchResponse = searchService.find("ASSOCIATEID", "CTS12346", 0, 5);
        assertNotNull(searchResponse.getAssociates());
    }

    @Test
    void testSearchByAssociateIdNotFoundInCache_Success() {
        SearchResponse searchResponse = searchService.find("ASSOCIATEID", "CTS12346", 0, 5);
        assertNotNull(searchResponse.getAssociates());
    }

    @Test
    void testSearchBySkill_Success() {
        SearchResponse searchResponse = searchService.find("SKILL", "DOCKER", 0, 5);
        assertNotNull(searchResponse.getAssociates());
    }

    @Test
    void testSearch_InvalidCriterion() {
        ValidationException exception = assertThrows(
                ValidationException.class,
                () -> searchService.find("DUMMY", "DOCKER", 0, 5));
        assertEquals("Invalid Search Criterion:DUMMY", exception.getMessage());
    }

    private Page<AssociateProfile> getPagingResponse() {
        AssociateProfileResponse associateProfileResponse = TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
        Page<AssociateProfile> associateProfilesPage;
        Pageable paging = PageRequest.of(0, 5);
        List<AssociateProfile> associateProfiles = Arrays.asList(mapper.mapToEntity(associateProfileResponse));
        associateProfilesPage = new PageImpl<>(associateProfiles, paging, associateProfiles.size());
        return associateProfilesPage;
    }

}
