package com.mobilepark.airtalk.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
@Table(name ="TBL_PHONE_BOOK")
public class PhoneBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SEQ", nullable = false)
    private Integer seq;

    @Column(name="USER_ID", nullable = false)
    private String userId;

    @Column(name="NAME", nullable = false)
    private String name;

    @Column(name="PHONE_NUMBER", nullable = false)
    private String phoneNumber;

    @Column(name="REG_DATE", nullable = false)
    private Date regDate;

    @Column(name="MOD_DATE")
    private Date modDate;
}
