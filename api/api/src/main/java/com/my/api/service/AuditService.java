package com.my.api.service;

import com.my.api.model.AuditTraceModel;

public interface AuditService {
    void saveAudit(AuditTraceModel auditTraceModel);
}
