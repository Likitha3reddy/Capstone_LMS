package com.example.Practice_SpringBoot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Dashboards")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Dashboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dashboardId;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private DashboardType dashboardType; // INSTRUCTOR or STUDENT

    @Lob
    private String data; // JSON data for dashboard


}
