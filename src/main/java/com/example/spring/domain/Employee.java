package com.example.spring.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "EMP")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer empId;
    private String empName;
    private Long empSalary;
}
