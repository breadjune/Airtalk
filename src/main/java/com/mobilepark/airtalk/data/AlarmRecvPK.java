package com.mobilepark.airtalk.data;

import lombok.Data;

import java.io.Serializable;

@Data
class AlarmRecvPK implements Serializable {
    private Integer alarmSeq;
    private String userId;
}