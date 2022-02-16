package com.casestudy.skilltracker.admin.service;

import com.casestudy.skilltracker.admin.dto.SearchResponse;
import org.springframework.validation.annotation.Validated;

@Validated
public interface SearchService {
    SearchResponse find(String criterion, String value, int page, int size);
}
