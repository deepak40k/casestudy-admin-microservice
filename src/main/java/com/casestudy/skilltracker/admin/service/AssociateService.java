package com.casestudy.skilltracker.admin.service;

import com.casestudy.skilltracker.admin.validator.AssociateID;
import com.casestudy.skilltracker.admin.dto.SearchResponse;
import com.casestudy.skilltracker.admin.validator.ValidSkill;
import org.springframework.validation.annotation.Validated;

@Validated
public interface AssociateService {
    SearchResponse findBySkill(@ValidSkill String value, int page, int size);
    SearchResponse findByName(String value, int page, int size);
    SearchResponse findByAssociateId(@AssociateID String value, int page, int size);
}
