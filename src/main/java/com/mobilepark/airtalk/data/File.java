package com.mobilepark.airtalk.data;

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
@Table(name ="TBL_BOARD_FILE")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="FILE_SEQ", nullable = false)
    private Integer fileSeq;

    @Column(name="BOARD_SEQ", nullable = false)
    private Integer boardSeq;

    @Column(name="REAL_FILE_NAME")
    private String realFileName;

    @Column(name="NEW_FILE_NAME", nullable = false)
    private String newFileName;

    @Column(name="REG_DATE", updatable=false, nullable = false)
    private String regDate;

    @Column(name="MOD_DATE")
    private String modDate;

    // @ManyToOne(targetEntity = BoardFile.class)
    // @JoinColumn(name = "BOARD_SEQ", insertable = false, updatable = false)
    // private BoardFile boardFile;
}
