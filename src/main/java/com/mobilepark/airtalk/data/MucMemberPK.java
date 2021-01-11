package com.mobilepark.airtalk.data;

import lombok.Data;

import java.io.Serializable;

@Data
class MucMemberPK implements Serializable {
    private Integer roomID;
    private String jid;
}