package com.mobilepark.airtalk.data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
@Table(name ="TBL_ALARM")
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="SEQ", nullable = false)
    private Integer seq;

    @Column(name="USER_ID", nullable = false, updatable=false)
    private String userId;

    @Column(name="MESSAGE", nullable = false)
    private String message;

    @Column(name="CODE", nullable = false, updatable=false)
    private String code;

    @Column(name="LATITUDE", nullable = false, updatable=false)
    private BigDecimal latitude;

    @Column(name="LONGITUDE", nullable = false, updatable=false)
    private BigDecimal longitude;

    @Column(name="BD_NM", updatable=false)
    private String bdNm;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyyMMddHHmmss", timezone="Asia/Seoul")
    @Column(name="RESERV_DATE", nullable = false)
    private Date reservDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyyMMddHHmmss", timezone="Asia/Seoul")
    @Column(name="REG_DATE", updatable=false, nullable = false)
    private Date regDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyyMMddHHmmss", timezone="Asia/Seoul")
    @Column(name="MOD_DATE", insertable = false)
    private Date modDate;


    // @OneToMany(mappedBy = "alarm", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    // private List<AlarmRecv> alarmRecvList;

}
