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
    public static final int CAR_RECOMMENDATION_DEFAULT_LIMIT = 10;

    /**
     * PAGINATION:
     */
    public static final int CAR_PAGE_SIZE = 20;

    /**
     * CACHING:
     */
    public static final String CAR_CACHE_NAME = "carCache";
    public static final String TYPE_CACHE_NAME = "typeCache";
    public static final long CACHE_CLEAR_INTERVAL = 720;
    public static final long CACHE_CLEAR_INITIAL_DELAY = 1;
}
