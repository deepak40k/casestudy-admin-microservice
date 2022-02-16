package com.casestudy.skilltracker.admin.unit.service;

import com.casestudy.skilltracker.admin.dto.AssociateProfileResponse;
import com.casestudy.skilltracker.admin.dto.SearchResponse;
import com.casestudy.skilltracker.admin.exception.ValidationException;
import com.casestudy.skilltracker.admin.mq.receiver.impl.RabbitMqReceiver;
import com.casestudy.skilltracker.admin.service.AssociateService;
import com.casestudy.skilltracker.admin.service.SearchService;
import com.casestudy.skilltracker.admin.utility.ConditionType;
import com.casestudy.skilltracker.admin.utility.TestUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchServiceTest {

	@Autowired
	private AssociateService associateService;

	@Autowired
	private SearchService searchService;

	@Autowired
	private RabbitMqReceiver rabbitMqReceiver;

	@BeforeEach
	void init()
	{
		AssociateProfileResponse associateProfileResponse= TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
		rabbitMqReceiver.receiveMessage(associateProfileResponse);
	}

	@Test
	void testSearchByName_Success() {
		SearchResponse searchResponse=searchService.find("NAME","deepak",0,5);
		assertNotNull(searchResponse.getCurrentPage());
		assertNotNull(searchResponse.getTotal());
		assertNotNull(searchResponse.getTotalPages());
		assertNotNull(searchResponse.getAssociates());
	}

	@Test
	void testSearchByAssociateId_Success() {
		SearchResponse searchResponse=searchService.find("ASSOCIATEID","CTS12346",0,5);
		assertNotNull(searchResponse.getCurrentPage());
		assertNotNull(searchResponse.getTotal());
		assertNotNull(searchResponse.getTotalPages());
		assertNotNull(searchResponse.getAssociates());
	}

	@Test
	void testSearchBySkill_Success() {
		SearchResponse searchResponse=searchService.find("SKILL","DOCKER",0,5);
		assertNotNull(searchResponse.getCurrentPage());
		assertNotNull(searchResponse.getTotal());
		assertNotNull(searchResponse.getTotalPages());
		assertNotNull(searchResponse.getAssociates());
	}
	@Test
	void testSearch_InvalidCriterion() {
		ValidationException exception = assertThrows(
				ValidationException.class,
				() -> searchService.find("DUMMY","DOCKER",0,5));
		assertEquals("Invalid Search Criterion:DUMMY" ,exception.getMessage());
	}


}
