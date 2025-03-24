package com.my.api.service.implement;

import com.my.api.model.AuditTraceModel;
import com.my.api.repository.AuditTraceRepository;
import com.my.api.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    AuditTraceRepository auditTraceRepository;

    @Override
    public void saveAudit(AuditTraceModel auditTraceModel) {
        try {
            auditTraceRepository.save(auditTraceModel);
        }
        catch (Exception ex) {

        }
    }

}
