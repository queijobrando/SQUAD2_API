package com.example.squad2_suporte.repositorios;

import com.example.squad2_suporte.Usuario.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}