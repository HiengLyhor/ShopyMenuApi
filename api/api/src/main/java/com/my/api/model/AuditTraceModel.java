package com.my.api.model;

import com.my.api.enums.ActionType;
import com.my.api.enums.TableName;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "Audit_Trace")
public class AuditTraceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "auditId")
    Long auditId;

    @Column(name = "tableName")
    String tableName;

    @Column(name = "actionType")
    String actionType;

    @Column(name = "actionDate")
    Timestamp actionDate;

    @Column(name = "username")
    String username;

    @Column(name = "oldData")
    String oldData;

    @Column(name = "newData")
    String newData;

    @Column(name = "additionalInfo")
    String additionalInfo;

    public AuditTraceModel(TableName tableName, ActionType actionType, String username, String oldData, String newData ) {

        this.actionDate = new Timestamp(System.currentTimeMillis());
        this.tableName = tableName.toString();
        this.actionType = actionType.toString();
        this.username = username;
        this.oldData = oldData;
        this.newData = newData;

    }
}
