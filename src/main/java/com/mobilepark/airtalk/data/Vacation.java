package com.mobilepark.airtalk.data;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table (name="TBL_VACATION")
public class Vacation extends BaseSerializable {

    private static final long serialVersionUID = -2382480158604649420L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column (name="VACATION_SEQ")
    private Integer vacationSeq;

    @Column (name="TYPE")
    private String type;

    @Column (name="START_DATE")
    private String startDate;

    @Column (name="END_DATE")
    private String endDate;

    @Column (name="PERIOD")
    private String period;

    @Column (name="RANK")
    private String rank;

    @Column (name="PHONE_NUM")
    private String phoneNum;

    @Column (name="DESCRIPTION")
    private String description;

    @Column (name="ADMIN_ID")
    private String adminId;

    @OneToOne(targetEntity = Admin.class)
    @JoinColumn(name="ADMIN_ID", insertable = false, updatable = false)
    private Admin admin;
}
