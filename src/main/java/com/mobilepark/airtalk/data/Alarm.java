package com.mobilepark.airtalk.data;

import java.math.BigDecimal;
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

    @Column(name="USER_ID", nullable = false)
    private String userId;

    @Column(name="MESSAGE", nullable = false)
    private String message;

    @Column(name="CODE", nullable = false)
    private String code;

    @Column(name="LATITUDE", nullable = false)
    private BigDecimal latitude;

    @Column(name="LONGITUDE", nullable = false)
    private BigDecimal longitude;

    @Column(name="BD_NM")
    private String bdNm;

    @Column(name="RESERV_DATE", updatable=false, nullable = false)
    private String reservDate;

    @Column(name="REG_DATE", updatable=false, nullable = false)
    private String regDate;

    @Column(name="MOD_DATE")
    private String modDate;

    // @OneToMany(mappedBy = "alarm", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    // private List<AlarmRecv> alarmRecvList;

}
