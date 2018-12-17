package com.example.saif.qrcodeapp;

/**
 * Created by Saif on 2/10/2017.
 */

public class MetalPart {

    String TagNo="";
    String PartNo="";
    String LocationCode="";
    String pcs;
    String grossWeight;
    String description;
    String DateTagged;

    public String getDateTagged() {
        return DateTagged;
    }

    public void setDateTagged(String dateTagged) {
        DateTagged = dateTagged;
    }

    public String getTagNo() {
        return TagNo;
    }

    public void setTagNo(String tagNo) {
        TagNo = tagNo;
    }

    public String getPartNo() {
        return PartNo;
    }

    public void setPartNo(String partNo) {
        PartNo = partNo;
    }

    public String getLocationCode() {
        return LocationCode;
    }

    public void setLocationCode(String locationCode) {
        LocationCode = locationCode;
    }

    public String getPcs() {
        return pcs;
    }

    public void setPcs(String pcs) {
        this.pcs = pcs;
    }

    public String getGrossWeight() {
        return grossWeight;
    }

    public void setGrossWeight(String grossWeight) {
        this.grossWeight = grossWeight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
