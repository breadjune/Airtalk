package com.mobilepark.airtalk.data;

import lombok.Data;

import java.io.Serializable;

@Data
class MucRoomPK implements Serializable {
    private Integer serviceID;
    private String name;
}