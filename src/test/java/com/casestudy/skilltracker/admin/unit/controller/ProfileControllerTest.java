package com.casestudy.skilltracker.admin.unit.controller;

import com.casestudy.skilltracker.admin.controller.AdminController;
import com.casestudy.skilltracker.admin.dto.SearchResponse;
import com.casestudy.skilltracker.admin.exception.ValidationException;
import com.casestudy.skilltracker.admin.service.SearchService;
import com.casestudy.skilltracker.admin.utility.ConditionType;
import com.casestudy.skilltracker.admin.utility.TestUtility;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = {AdminController.class})
class ProfileControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private SearchService service;

    @Test
    void testSearchProfileByName_Success() throws Exception {
        SearchResponse expectedResponse= TestUtility.getExpectedResponse(ConditionType.SEARCH_BY_NAME);
        Mockito.when(this.service.find(Mockito.any(String.class), Mockito.any(String.class),Mockito.any(Integer.class),Mockito.any(Integer.class))).thenReturn(expectedResponse);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/NAME/deepak")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(ConditionType.SEARCH_BY_NAME.getHttpStatus(), status);
        assertEquals(TestUtility.getStringFormat(expectedResponse), actualResponse);
    }

    @Test
    void testSearchProfileByAssociateID_Success() throws Exception {
        SearchResponse expectedResponse= TestUtility.getExpectedResponse(ConditionType.SEARCH_BY_NAME);
        Mockito.when(this.service.find(Mockito.any(String.class), Mockito.any(String.class),Mockito.any(Integer.class),Mockito.any(Integer.class))).thenReturn(expectedResponse);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/ASSOCIATEID/CTS12345")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(ConditionType.SEARCH_BY_NAME.getHttpStatus(), status);
        assertEquals(TestUtility.getStringFormat(expectedResponse), actualResponse);
    }
    @Test
    void testSearchProfileBySkill_Success() throws Exception {
        SearchResponse expectedResponse= TestUtility.getExpectedResponse(ConditionType.SEARCH_BY_NAME);
        Mockito.when(this.service.find(Mockito.any(String.class), Mockito.any(String.class),Mockito.any(Integer.class),Mockito.any(Integer.class))).thenReturn(expectedResponse);
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/SKILL/DOCKER")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(ConditionType.SEARCH_BY_NAME.getHttpStatus(), status);
        assertEquals(TestUtility.getStringFormat(expectedResponse), actualResponse);
    }

    @Test
    void testSearchProfile_InvalidCriterion() throws Exception {
        SearchResponse expectedResponse= TestUtility.getExpectedResponse(ConditionType.SEARCH_BY_NAME);
        Mockito.when(this.service.find(Mockito.any(String.class), Mockito.any(String.class),Mockito.any(Integer.class),Mockito.any(Integer.class))).thenThrow(new ValidationException("Invalid Search Criterion"));
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/DUMMY/DOCKER")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(400, status);
        assertTrue(actualResponse.contains("Invalid Search Criterion"));
    }

    @Test
    void testSearch_InternalServerError() throws Exception {
        SearchResponse expectedResponse= TestUtility.getExpectedResponse(ConditionType.SEARCH_BY_NAME);
        Mockito.when(this.service.find(Mockito.any(String.class), Mockito.any(String.class),Mockito.any(Integer.class),Mockito.any(Integer.class))).thenThrow(new RuntimeException("Internal Server Error"));
        MvcResult mvcResult = this.mvc.perform(MockMvcRequestBuilders.get("/api/v1/admin/NAME/deepak")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), status);
    }


}
