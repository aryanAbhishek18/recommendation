package com.aryanabhi.recommendation;

public interface Constants {
    Integer PAGE_SIZE = 2;
    Integer NUMBER_OF_RECOMMENDATIONS = 4;

    String CAR_API = "/api/car";
    String HEALTH_API = "/health";
    String GET_ALL_CARS_API = "/allcars";
    String DELETE_ALL_CARS_API = "/delete";
    String DELETE_CAR_API = "/delete/{id}";
    String CREATE_CAR_API = "/create";
    String RECOMMEND_API = "/recommend/{id}";
}
