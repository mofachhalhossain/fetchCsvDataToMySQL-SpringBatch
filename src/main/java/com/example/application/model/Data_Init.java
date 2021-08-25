package com.example.application.model;

public class Data_Init {
    private String Date_reported;
    private String Country_code;
    private String Country;
    private String WHO_region;
    private int New_cases;
    private int Cumulative_cases;
    private int New_deaths;
    private int Cumulative_deaths;

    public Data_Init() {

    }

    public Data_Init(String date_reported, String country_code, String country, String WHO_region, int new_cases,
                     int cumulative_cases, int new_deaths, int cumulative_deaths) {
        Date_reported = date_reported;
        Country_code = country_code;
        Country = country;
        this.WHO_region = WHO_region;
        New_cases = new_cases;
        Cumulative_cases = cumulative_cases;
        New_deaths = new_deaths;
        Cumulative_deaths = cumulative_deaths;
    }

    public String getDate_reported() {
        return Date_reported;
    }

    public void setDate_reported(String date_reported) {
        Date_reported = date_reported;
    }

    public String getCountry_code() {
        return Country_code;
    }

    public void setCountry_code(String country_code) {
        Country_code = country_code;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getWHO_region() {
        return WHO_region;
    }

    public void setWHO_region(String WHO_region) {
        this.WHO_region = WHO_region;
    }

    public int getNew_cases() {
        return New_cases;
    }

    public void setNew_cases(int new_cases) {
        New_cases = new_cases;
    }

    public int getCumulative_cases() {
        return Cumulative_cases;
    }

    public void setCumulative_cases(int cumulative_cases) {
        Cumulative_cases = cumulative_cases;
    }

    public int getNew_deaths() {
        return New_deaths;
    }

    public void setNew_deaths(int new_deaths) {
        New_deaths = new_deaths;
    }

    public int getCumulative_deaths() {
        return Cumulative_deaths;
    }

    public void setCumulative_deaths(int cumulative_deaths) {
        Cumulative_deaths = cumulative_deaths;
    }
}
