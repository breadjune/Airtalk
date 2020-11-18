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
import javax.persistence.IdClass;
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

    @Column(name="RECEIVE_DATE")
    private String ReceiveDate;

    @Column(name="REG_DATE", nullable = false)
    private String regDate;

    @Column(name="MOD_DATE")
    private String modDate;

    // @ManyToOne(targetEntity = Alarm.class)
    // @JoinColumn(name = "SEQ", insertable = false)
    // private Alarm alarm;
}
