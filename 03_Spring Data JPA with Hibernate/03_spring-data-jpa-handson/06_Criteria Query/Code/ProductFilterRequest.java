package com.cognizant.ormlearn.model;

public class ProductFilterRequest {

    private Double minCpuSpeed;
    private Integer minRamSize;
    private Integer minHddSize;
    private String operatingSystem;
    private Double maxWeight;
    private String cpu;
    private Double minReview;

    // Getters and Setters
    public Double getMinCpuSpeed() {
        return minCpuSpeed;
    }

    public void setMinCpuSpeed(Double minCpuSpeed) {
        this.minCpuSpeed = minCpuSpeed;
    }

    public Integer getMinRamSize() {
        return minRamSize;
    }

    public void setMinRamSize(Integer minRamSize) {
        this.minRamSize = minRamSize;
    }

    public Integer getMinHddSize() {
        return minHddSize;
    }

    public void setMinHddSize(Integer minHddSize) {
        this.minHddSize = minHddSize;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public Double getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(Double maxWeight) {
        this.maxWeight = maxWeight;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public Double getMinReview() {
        return minReview;
    }

    public void setMinReview(Double minReview) {
        this.minReview = minReview;
    }
}
