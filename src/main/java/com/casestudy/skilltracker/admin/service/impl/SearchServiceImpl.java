package com.casestudy.skilltracker.admin.service.impl;

import com.casestudy.skilltracker.admin.enums.SearchCriterion;
import com.casestudy.skilltracker.admin.exception.ValidationException;
import com.casestudy.skilltracker.admin.dto.SearchResponse;
import com.casestudy.skilltracker.admin.service.AssociateService;
import com.casestudy.skilltracker.admin.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SearchServiceImpl implements SearchService {
    @Autowired
    AssociateService associateService;

    @Override
    public SearchResponse find(String criterion, String value, int page, int size) {
        if(SearchCriterion.ASSOCIATEID.name().equals(criterion))
            return associateService.findByAssociateId(value,page,size);
        else if(SearchCriterion.NAME.name().equals(criterion))
            return associateService.findByName(value,page,size);
        else if(SearchCriterion.SKILL.name().equals(criterion))
            return associateService.findBySkill(value,page,size);
        else {
            log.error("Invalid Search Criterion:" + criterion);
            throw new ValidationException("Invalid Search Criterion:" + criterion);
        }
    }
}
