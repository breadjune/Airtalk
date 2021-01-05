package com.mobilepark.airtalk.data;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="TBL_USER")
public class User {

    @Id
    @Column(name="ID" , nullable = false)
    private String userId;

    @Column(name="PASSWORD" , nullable = false)
    private String password;

    @Column(name="NAME" , nullable = false)
    private String name;

    @Column(name="HP_NO" , nullable = false)
    private String hpNo;

    @Column(name="PUSH_KEY")
    private String pushKey;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name="REG_DATE", updatable = false ,nullable = false)
    private Date regDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name="MOD_DATE")
    private Date modDate;
    //columnDefinition = "DATETIME"
}
