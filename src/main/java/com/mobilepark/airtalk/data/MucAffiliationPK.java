package com.mobilepark.airtalk.data;

import lombok.Data;

import java.io.Serializable;

@Data
class MucAffiliationPK implements Serializable {
    private Integer roomID;
    private String jid;
}