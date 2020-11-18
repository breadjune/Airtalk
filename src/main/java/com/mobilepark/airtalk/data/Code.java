package com.mobilepark.airtalk.data;

import java.util.Date;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="TBL_SERVICE_CODE")
public class Code extends BaseSerializable {

    private static final long serialVersionUID = -2382480158604649420L;
    
    @Id
    @Column(name="CODE" , nullable = false)
    private String code;

    @Column(name="CODE_NAME" , nullable = false)
    private String codeName;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name="REG_DATE", updatable = false)
    private Date regDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name="MOD_DATE")
    private Date modDate;
    //columnDefinition = "DATETIME"
}