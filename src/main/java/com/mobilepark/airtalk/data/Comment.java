package com.mobilepark.airtalk.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
@Table(name ="TBL_COMMENT")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="COMMENT_SEQ", nullable = false)
    private Integer commentSeq;

    @Column(name="SEQ", nullable = false)
    private Integer seq;

    @Column(name="ADMIN_ID", nullable = false)
    private String adminId;

    @Column(name="COMMENT", nullable = false)
    private String comment;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name="REG_DATE", updatable=false, nullable = false)
    private Date regDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name="MOD_DATE")
    private Date modDate;

}
