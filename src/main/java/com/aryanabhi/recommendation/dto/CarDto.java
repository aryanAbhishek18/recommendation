package com.aryanabhi.recommendation.dto;

public class CarDto {
    private Long id;
    private String name;
    private String type;
    private String company;
    private Integer capacity;
    private Float mileage;
    private Integer year;

    public CarDto() {
    }

    public CarDto(Long id, String name, String type, String company, Integer capacity, Float mileage, Integer year) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.company = company;
        this.capacity = capacity;
        this.mileage = mileage;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Float getMileage() {
        return mileage;
    }

    public void setMileage(Float mileage) {
        this.mileage = mileage;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
