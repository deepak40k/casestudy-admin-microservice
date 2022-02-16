package com.casestudy.skilltracker.admin.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString
public class SearchResponse {
    int totalPages;
    int currentPage;
    long total;
    List<AssociateProfileResponse> associates;
}
