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
@Table(name ="openfire_db.ofMucAffiliation")
@IdClass(MucAffiliationPK.class)
public class MucAffiliation {

    @Id
    @Column(name="roomID", nullable = false)
    private Integer roomID;

    @Id
    @Column(name="jid", nullable = false)
    private String jid;

    @Column(name="affiliation", nullable = false)
    private String affiliation;

}
