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
@Table(name ="TBL_MENU")
public class Menu {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="MENU_SEQ")
    private Integer menuSeq;

    @Column(name="PARENT_SEQ")
    private Integer parentSeq;

    @Column(name="TITLE")
    private String title;

    @Column(name="TYPE")
    private String type;

    @Column(name="SORT")
    private Integer sort;

    @Column(name="URL")
    private String url;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="REG_DATE", updatable=false)
    private Date regDate;

    @Column(name="MOD_DATE")
    private Date modDate;

    @Column(name="MENU_ICON")
    private String menuIcon;
}
