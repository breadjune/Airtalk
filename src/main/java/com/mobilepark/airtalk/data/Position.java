package com.mobilepark.airtalk.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
@Table(name ="TBL_POSITION")
public class Position {

    @Id
    @Column(name="USER_ID", nullable = false)
    private String userId;

    @Column(name="LATITUDE", nullable = false)
    private Double latitude;

    @Column(name="LONGITUDE", nullable = false)
    private Double longitude;

    @Column(name="ADDRESS")
    private String address;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="REG_DATE", nullable = false)
    private Date regDate;

    @Column(name="MOD_DATE")
    private Date modDate;
}
