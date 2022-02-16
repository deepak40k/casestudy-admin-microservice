package com.casestudy.skilltracker.admin.utility;

public enum ConditionType {

    CREATE_PROFILE("CREATE_PROFILE"),
    SEARCH_BY_NAME("SEARCH_BY_NAME"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR");
    private String requestFileName;
    private String responseFileName;
    private long httpStatus;

    ConditionType(String result) {
        if ("CREATE_PROFILE".equals(result)) {
            this.requestFileName = "SampleProfile.json";
            this.responseFileName = "SampleProfile.json";
            httpStatus = 200;
        }

        if ("SEARCH_BY_NAME".equals(result)) {
            this.requestFileName = "SearchResponse.json";
            this.responseFileName = "SearchResponse.json";
            httpStatus = 200;
        }

        if ("INTERNAL_SERVER_ERROR".equals(result)) {
            this.requestFileName = "SampleRequest500.json";
            this.responseFileName = "Response500.json";
            httpStatus = 500;
        }
    }

    public String getRequestFileName() {
        return requestFileName;
    }

    public String getResponseFileName() {
        return responseFileName;
    }

    public long getHttpStatus() {
        return httpStatus;
    }

}