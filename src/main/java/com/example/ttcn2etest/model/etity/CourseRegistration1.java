package com.example.ttcn2etest.model.etity;

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
public class CourseRegistration1 {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Size(max = 100)
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    @Size(max = 15)
    private String phone;
    @NotBlank
    private String address;
    @Enumerated(EnumType.STRING)
    private CourseRegistration1.Status status;
    @Column(name = "content_advice")
    private String information;
    @Column(name = "created_date")
    private Timestamp createdDate;
    @Column(name = "update_date")
    private Timestamp updateDate;

    public enum Status {
        APPROVED,WAITING_FOR_APPROVEDED
    }
}
