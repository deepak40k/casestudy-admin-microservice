package com.casestudy.skilltracker.admin.utility;

import com.casestudy.skilltracker.admin.dto.ErrorResponse;
import com.casestudy.skilltracker.admin.dto.SearchResponse;
import com.casestudy.skilltracker.admin.model.SkillSet;
import com.casestudy.skilltracker.admin.dto.AssociateProfileResponse;
import com.casestudy.skilltracker.admin.model.SearchResponseHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;

public class TestUtility {

    public static SearchResponse getExpectedResponse(ConditionType type) {
        SearchResponseHelper searchResponseHelper;
        ObjectMapper mapper = new ObjectMapper();
        try {
            searchResponseHelper = mapper.readValue(
                    new ClassPathResource(type.getResponseFileName()).getFile(),
                    SearchResponseHelper.class);
        } catch (IOException e) {
            e.printStackTrace();
            searchResponseHelper =new SearchResponseHelper();
        }
        return mapToDto(searchResponseHelper);
    }

    public static AssociateProfileResponse getSampleRequest(ConditionType type) {
        AssociateProfileResponse request;
        ObjectMapper mapper = new ObjectMapper();
        try {
            request = mapper.readValue(
                    new ClassPathResource(type.getRequestFileName()).getFile(),
                    AssociateProfileResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            request = new AssociateProfileResponse();
        }
        return request;
    }

    public static ErrorResponse getErrorResponse(ConditionType type) {
        ErrorResponse errorResponse;
        ObjectMapper mapper = new ObjectMapper();
        try {
            errorResponse = mapper.readValue(
                    new ClassPathResource(type.getResponseFileName()).getFile(),
                    ErrorResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            errorResponse =new ErrorResponse(400,"Bad request");
        }
        return errorResponse;
    }

    public static String getStringFormat(Object request) {
        ObjectMapper mapper = new ObjectMapper();
        String inputJson = "";
        if (request != null) {
            try {
                if( request instanceof SearchResponse||request instanceof ErrorResponse
                        || request instanceof SkillSet)
                inputJson = mapper.writeValueAsString(request);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return inputJson;
    }
    public static SearchResponse mapToDto(SearchResponseHelper searchResponseHelper) {
        SearchResponse searchResponse=
                SearchResponse.builder()
                        .currentPage(searchResponseHelper.getCurrentPage())
                        .total(searchResponseHelper.getTotal())
                        .totalPages(searchResponseHelper.getTotalPages())
                        .associates(searchResponseHelper.getAssociates())
                        .build();
        return searchResponse;
    }


}
