package com.example.ttcn2etest.course_registration.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "course_registration")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseRegistration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String customerName;

    @NotBlank
    private String email;

    @NotBlank
    @Size(max = 15)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "registered_course")
    @Size(max = 1000)
    private String registeredCourse;

    @Column(name = "address")
    private String address;

    @Column(name = "notes")
    private String notes;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "update_date")
    private Timestamp updateDate;

    public enum Status {
        REGISTERED, APPROVED, REJECTED  // Đăng ký, Được duyệt, Từ chối
    }
}