package com.my.api.repository;

import com.my.api.model.AuditTraceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditTraceRepository extends JpaRepository<AuditTraceModel, Long> {
}
