package com.example.Practice_SpringBoot.repository;

import com.example.Practice_SpringBoot.entity.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DashboardRepository extends JpaRepository<Dashboard, Long> {
}
