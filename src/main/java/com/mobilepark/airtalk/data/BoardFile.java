package com.mobilepark.airtalk.data;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
@Table(name ="TBL_BOARD_FILE")
public class BoardFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SEQ", nullable = false)
    private Integer seq;

    @Column(name="TITLE", nullable = false)
    private String title;

    @Column(name="CONTENTS")
    private String contents;

    @Column(name="WRITER")
    private String writer;

    @Column(name="FILE_NAME", nullable = false)
    private String FileName;

    @Column(name="FILE_REAL_NAME", nullable = false)
    private String fileRealName;

    @Column(name="REG_DATE", updatable=false, nullable = false)
    private String regDate;

    @Column(name="MOD_DATE")
    private String modDate;
}
