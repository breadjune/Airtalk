package com.mobilepark.airtalk.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name="TBL_ADMIN")
public class Admin extends BaseSerializable {
    
    private static final long serialVersionUID = -2382480158604649420L;

    @Id
    @Column(name="ADMIN_ID")
    private String adminId;

    @Column(name="ADMIN_NAME")
    private String adminName;

    @Column(name="PASSWORD")
    private String password;

    @Column(name="PASSWORD_UPDATE_DATE")
    private Date passwordUpdateDate;

    @Column(name="ADMIN_GROUP_SEQ")
    private int adminGroupSeq;
}
