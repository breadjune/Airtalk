package com.mobilepark.airtalk.data;

import lombok.Data;

import java.io.Serializable;

@Data
public class AdminGroupAuthPK implements Serializable {
    private Integer menuSeq;
    private Integer adminGroupSeq;
}
