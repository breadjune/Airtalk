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

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
@Table(name ="TBL_FILE")
public class FileData {
    
    @Column(name="SEQ", nullable = false)
    private Integer seq;

    @Column(name="B_CODE", nullable = false)
    private String bCode;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FILE_SEQ", nullable = false)
    private Integer fileSeq;

    @Column(name="REAL_FILE_NAME")
    private String realFileName;

    @Column(name="NEW_FILE_NAME", nullable = false)
    private String newFileName;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyyMMddHHmmss", timezone="Asia/Seoul")
    @Column(name="REG_DATE", updatable=false, nullable = false)
    private Date regDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyyMMddHHmmss", timezone="Asia/Seoul")
    @Column(name="MOD_DATE")
    private Date modDate;

    // @ManyToOne(targetEntity = BoardFile.class)
    // @JoinColumn(name = "BOARD_SEQ", insertable = false, updatable = false)
    // private BoardFile boardFile;
}
