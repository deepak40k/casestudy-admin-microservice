package com.casestudy.skilltracker.admin.model;

import com.casestudy.skilltracker.admin.dto.AssociateProfileResponse;
import lombok.Data;

import java.util.List;

@Data
public class SearchResponseHelper {
    int totalPages;
    int currentPage;
    long total;
    List<AssociateProfileResponse> associates;
}
