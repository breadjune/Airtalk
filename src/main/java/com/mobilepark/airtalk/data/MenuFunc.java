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
@Table(name = "TBL_MENU_FUNC")
public class MenuFunc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FUNC_SEQ")
    private Integer funcSeq;

    @Column(name="MENU_SEQ")
    private Integer menuSeq;

    @Column(name="NAME")
    private String name;

    @Column(name="URL")
    private String url;

    @Column(name="AUTH")
    private String auth;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="REG_DATE", updatable = false)
    private Date regDate;

    @Column(name="MOD_DATE")
    private Date modDate;
}
