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
@Table(name ="openfire_db.ofMucRoom")
@IdClass(MucRoomPK.class)
public class MucRoom {
    
    @Id
    @Column(name="serviceID", nullable = false)
    private Integer serviceID;

    @Column(name="roomID", nullable = false)
    private Integer roomID;

    @Column(name="creationDate", nullable = false)
    private String creationDate;

    @Column(name="modificationDate", nullable = false)
    private String modificationDate;

    @Id
    @Column(name="name", nullable = false)
    private String name;

    @Column(name="naturalName", nullable = false)
    private String naturalName;

    @Column(name="description")
    private String description;

    @Column(name="lockedDate", nullable = false)
    private String lockedDate;

    @Column(name="emptyDate")
    private String emptyDate;

    @Column(name="canChangeSubject", nullable = false)
    private Integer canChangeSubject;

    @Column(name="maxUser", nullable = false)
    private Integer maxUser;

    @Column(name="publicRoom", nullable = false)
    private Integer publicRoom;

    @Column(name="moderated", nullable = false)
    private Integer moderated;

    @Column(name="membersOnly", nullable = false)
    private Integer membersOnly;

    @Column(name="canInvite", nullable = false)
    private Integer canInvite;

    @Column(name="roomPassword")
    private String roomPassword;

    @Column(name="canDiscoverJID", nullable = false)
    private Integer canDiscoverJID;

    @Column(name="logEnabled", nullable = false)
    private Integer logEnabled;

    @Column(name="subject")
    private String subject;

    @Column(name="rolesToBroadcast", nullable = false)
    private Integer rolesToBroadcast;

    @Column(name="userReservedNick", nullable = false)
    private Integer userReservedNick;

    @Column(name="canChangeNick", nullable = false)
    private Integer canChangeNick;

    @Column(name="canRegister", nullable = false)
    private Integer canRegister;

    @Column(name="allowpm")
    private Integer allowpm;

}
