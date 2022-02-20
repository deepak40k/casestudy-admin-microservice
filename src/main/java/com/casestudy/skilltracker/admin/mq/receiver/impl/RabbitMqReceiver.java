package com.casestudy.skilltracker.admin.mq.receiver.impl;

import com.casestudy.skilltracker.admin.mq.receiver.MessageReceiver;
import com.casestudy.skilltracker.admin.repository.AssociateRepository;
import com.casestudy.skilltracker.admin.dto.AssociateProfileResponse;
import com.casestudy.skilltracker.admin.mapper.DTOMapper;
import com.casestudy.skilltracker.admin.model.AssociateProfile;
import com.casestudy.skilltracker.admin.service.MemcachedService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.spy.memcached.MemcachedClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class RabbitMqReceiver implements RabbitListenerConfigurer, MessageReceiver<AssociateProfileResponse> {
    @Autowired
    AssociateRepository associateRepository;

    @Autowired
    DTOMapper<AssociateProfileResponse, AssociateProfile> mapper;

    @Autowired
    private MemcachedService<AssociateProfile> memcachedService;

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue}")
    public void receiveMessage(AssociateProfileResponse associateProfileResponse) {
        log.debug("Profile received:{}",associateProfileResponse.toString());
        if (this.validate(associateProfileResponse)) {
            AssociateProfile associateProfile = mapper.mapToEntity(associateProfileResponse);
            associateProfile = associateRepository.save(associateProfile);
            memcachedService.set(associateProfile.getAssociateId(), associateProfile);
            log.debug("Search Database updated with:{}",associateProfile.toString());
        }
    }

    private boolean validate(AssociateProfileResponse associateProfileResponse) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<AssociateProfileResponse>> constraintViolations = validator.validate(associateProfileResponse);
        if (!constraintViolations.isEmpty()) {
            log.error("Skip Message due to validation failure:{}",constraintViolations.iterator().next().getMessage());
            return false;
        }
        return true;
    }
}
