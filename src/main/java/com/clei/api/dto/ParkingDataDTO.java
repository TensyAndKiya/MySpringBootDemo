package com.clei.api.dto;

public class ParkingDataDTO {
    private String carLicense;
    private String carStatus;
    private String carType;
    private String emptySpaces;
    private String inPassId;
    private String inTime;
    private String orderId;
    private String outPassId;
    private String outTime;
    private String parkingFee;
    private String parkingName;
    private String totalSpaces;

    public ParkingDataDTO() {
        super();
    }

    public ParkingDataDTO(String carLicense, String carStatus, String carType, String emptySpaces, String inPassId, String inTime, String orderId, String outPassId, String outTime, String parkingFee, String parkingName, String totalSpaces) {
        super();
        this.carLicense = carLicense;
        this.carStatus = carStatus;
        this.carType = carType;
        this.emptySpaces = emptySpaces;
        this.inPassId = inPassId;
        this.inTime = inTime;
        this.orderId = orderId;
        this.outPassId = outPassId;
        this.outTime = outTime;
        this.parkingFee = parkingFee;
        this.parkingName = parkingName;
        this.totalSpaces = totalSpaces;
    }

    @Override
    public String toString() {
        return "ParkingDataDTO{" +
                "carLicense='" + carLicense + '\'' +
                ", carStatus='" + carStatus + '\'' +
                ", carType='" + carType + '\'' +
                ", emptySpaces='" + emptySpaces + '\'' +
                ", inPassId='" + inPassId + '\'' +
                ", inTime='" + inTime + '\'' +
                ", orderId='" + orderId + '\'' +
                ", outPassId='" + outPassId + '\'' +
                ", outTime='" + outTime + '\'' +
                ", parkingFee='" + parkingFee + '\'' +
                ", parkingName='" + parkingName + '\'' +
                ", totalSpaces='" + totalSpaces + '\'' +
                '}';
    }

    public String getCarLicense() {
        return carLicense;
    }

    public void setCarLicense(String carLicense) {
        this.carLicense = carLicense;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getEmptySpaces() {
        return emptySpaces;
    }

    public void setEmptySpaces(String emptySpaces) {
        this.emptySpaces = emptySpaces;
    }

    public String getInPassId() {
        return inPassId;
    }

    public void setInPassId(String inPassId) {
        this.inPassId = inPassId;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOutPassId() {
        return outPassId;
    }

    public void setOutPassId(String outPassId) {
        this.outPassId = outPassId;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(String parkingFee) {
        this.parkingFee = parkingFee;
    }

    public String getParkingName() {
        return parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getTotalSpaces() {
        return totalSpaces;
    }

    public void setTotalSpaces(String totalSpaces) {
        this.totalSpaces = totalSpaces;
    }
}
