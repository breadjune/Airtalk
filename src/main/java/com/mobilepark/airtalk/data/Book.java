package com.mobilepark.airtalk.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name="TBL_BOOK")
public class Book extends BaseSerializable{

    private static final long serialVersionUID = -2382480158604649420L;


    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name="BOOK_SEQ")
    private int bookSeq;

    @Column(name="TITLE")
    private String title;

    @Column(name="GENRE")
    private String genre;

    @Column(name="WRITER")
    private String writer;

    @Column(name="COMPANY")
    private String company;

    @Column(name="PRICE")
    private String price;

    @Column(name="REQUESTOR")
    private String requestor;

    @Column(name="DESCRIPTION")
    private String description;

    @Column(name="admin_id")
    private String adminId;

} //end class
