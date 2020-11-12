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
@Table(name ="TBL_EDITOR")
public class Editor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="EDITOR_SEQ", nullable = false)
    private Integer editorSeq;

    @Column(name="TITLE", nullable = false)
    private String title;

    @Column(name="CONTENTS")
    private String contents;

    @Column(name="ADMIN_ID", nullable = false)
    private String adminId;

    @Column(name="REG_DATE", updatable=false, nullable = false)
    private Date  regDate;

    @Column(name="MOD_DATE")
    private Date  modDate;
}
