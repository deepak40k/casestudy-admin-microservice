package com.casestudy.skilltracker.admin.unit.mq;

import com.casestudy.skilltracker.admin.dto.AssociateProfileResponse;
import com.casestudy.skilltracker.admin.mapper.DTOMapper;
import com.casestudy.skilltracker.admin.model.AssociateProfile;
import com.casestudy.skilltracker.admin.mq.receiver.impl.RabbitMqReceiver;
import com.casestudy.skilltracker.admin.repository.AssociateRepository;
import com.casestudy.skilltracker.admin.utility.ConditionType;
import com.casestudy.skilltracker.admin.utility.TestUtility;
import net.spy.memcached.MemcachedClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class MQReceiverTest {

	@Autowired
	private RabbitMqReceiver rabbitMqReceiver;

	@MockBean
	private  MemcachedClient memcachedClient;

	@MockBean
    AssociateRepository associateRepository;

	@Autowired
	DTOMapper<AssociateProfileResponse, AssociateProfile> mapper;

	@BeforeEach
	void init() {
		AssociateProfileResponse associateProfileResponse = TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
		Mockito.when(this.associateRepository.save(Mockito.any(AssociateProfile.class))).thenReturn(mapper.mapToEntity(associateProfileResponse));
	}
	@Test
	void testCreateProfile_Success() {
		AssociateProfileResponse associateProfileResponse= TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
		rabbitMqReceiver.receiveMessage(associateProfileResponse);
		verify(associateRepository,times(1)).save(Mockito.any(AssociateProfile.class));
	}
	@Test
	void testCreateProfile_ValidationFailure() {
		AssociateProfileResponse associateProfileResponse= TestUtility.getSampleRequest(ConditionType.CREATE_PROFILE);
		associateProfileResponse.getSkills().getTechnical().get(0).setName("DUMMY");
		rabbitMqReceiver.receiveMessage(associateProfileResponse);
		verify(associateRepository,times(0)).save(Mockito.any(AssociateProfile.class));
	}

}
