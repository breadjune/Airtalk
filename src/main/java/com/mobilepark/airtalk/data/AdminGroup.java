package com.mobilepark.airtalk.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter
// @ToString(exclude = {"adminList", "adminGroupAuthList"})
@Table(name="TBL_ADMIN_GROUP")
public class AdminGroup {

    private static final long serialVersionUID = -2382480158604649420L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ADMIN_GROUP_SEQ")
    private Integer adminGroupSeq;

    @Column(name="NAME")
    private String name;

    @Column(name="DESCRIPTION")
    private String description;
    
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name="REG_DATE", columnDefinition = "DATETIME", updatable = false)
    private Date regDate;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    @Column(name="MOD_DATE", columnDefinition = "DATETIME")
    private Date modDate;

    // @OneToMany(mappedBy = "adminGroup", /*fetch = FetchType.EAGER,*/ cascade = CascadeType.REMOVE)
    // private List<Admin> adminList;

    // @OneToMany(mappedBy = "adminGroup", /*fetch = FetchType.EAGER,*/ cascade = CascadeType.REMOVE)
    // private List<AdminGroupAuth> adminGroupAuthList;
}
