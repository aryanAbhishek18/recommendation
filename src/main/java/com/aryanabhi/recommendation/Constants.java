package com.aryanabhi.recommendation;

public class Constants {

    /**
     * API PATHS:
     */
    public static final String BASE_URL = "/api";
    public static final String CAR_API_BASE_URL = BASE_URL + "/cars";
    public static final String CAR_COMPARISON_URL = CAR_API_BASE_URL + "/comparisons";
    public static final String CAR_RECOMMENDATION_URL = CAR_API_BASE_URL + "/recommendations";
    public static final String WEIGHT_API_BASE_URL = BASE_URL + "/weights";

    /**
     * RECOMMENDATIONS:
     */
    public static final Integer CAR_RECOMMENDATION_DEFAULT_LIMIT = 10;

    /**
     * PAGINATION:
     */
    public static final Integer CAR_PAGE_SIZE = 20;
}
