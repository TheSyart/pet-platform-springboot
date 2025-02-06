package com.pets.pojo.dto;

import lombok.Data;

@Data
public class IpLocationDTO {

    private String status;

    private String message;

    private String country;

    private String countryCode;

    private String region;

    private String regionName;

    private String city;

    private String zip;

    private Double lat;

    private Double lon;

    private String timezone;

    private String isp;

    private String org;

    private String as;

    private String query;
}
