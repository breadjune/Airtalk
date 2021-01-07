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
import javax.persistence.IdClass;
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
@Table(name ="TBL_ALARM_RECEIVER")
@IdClass(AlarmRecvPK.class)
public class AlarmRecv {

    @Id
    @Column(name="ALARM_SEQ", nullable = false)
    private Integer alarmSeq;

    @Id
    @Column(name="USER_ID", nullable = false)
    private String userId;

    @Column(name="HP_NO")
    private String hpNo;

    @Column(name="RECEIVE_YN")
    private Character receiveYn;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name="RECEIVE_DATE")
    private Date ReceiveDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name="REG_DATE", nullable = false, updatable=false)
    private Date regDate;

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name="MOD_DATE")
    private Date modDate;

    // @ManyToOne(targetEntity = Alarm.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // @JoinColumn(name = "alarm_seq", referencedColumnName = "seq", insertable = false, updatable = false)
    // private Alarm alarm;
}
