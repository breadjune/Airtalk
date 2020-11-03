package com.mobilepark.airtalk.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
@Table(name ="TBL_NOTICE_FILE")
public class NoticeFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BOARD_FILE_SEQ", nullable = false)
    private Integer boardfileSeq;

    @ManyToOne(targetEntity = Notice.class)
    @JoinColumn(name="BOARD_SEQ", insertable = false, updatable = false)
    private Integer boardseq;

    @Column(name="FILE_NAME", nullable = false)
    private String filename;

    @Column(name="FILE_URL")
    private String fileurl;

    @Column(name="REG_DATE", updatable=false, nullable = false)
    private Date regDate;

    @Column(name="MOD_DATE")
    private Date modDate;
}
