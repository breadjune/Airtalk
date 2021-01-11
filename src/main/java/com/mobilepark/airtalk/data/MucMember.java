package com.mobilepark.airtalk.data;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter @Setter
@ToString
@Table(name ="openfire_db.ofMucMember")
@IdClass(MucMemberPK.class)
public class MucMember {
    
    @Id
    @Column(name="roomID", nullable = false)
    private Integer roomID;

    @Id
    @Column(name="jid", nullable = false)
    private String jid;

    @Column(name="nickname")
    private String nickname;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="url")
    private String url;

    @Column(name="email")
    private String email;

    @Column(name="faqentry")
    private String faqentry;

}
