package com.exemplo.taskmanager.repository;

import com.exemplo.taskmanager.model.Responsible;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponsibleRepository extends JpaRepository<Responsible, Long> {
}
