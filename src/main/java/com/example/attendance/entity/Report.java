package com.example.attendance.entity;

import jakarta.persistence.*;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String registerNumber;
    private int totalDays;
    private int daysPresent;

    // Getters and Setters
}
