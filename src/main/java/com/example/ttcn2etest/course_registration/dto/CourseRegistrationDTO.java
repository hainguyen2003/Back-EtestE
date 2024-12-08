package com.example.ttcn2etest.course_registration.dto;

import com.example.ttcn2etest.constant.DateTimeConstant;
import com.example.ttcn2etest.course_registration.entity.CourseRegistration;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CourseRegistrationDTO {
    private Long id;
    private String customerName;
    private String email;
    private String phone;
    private String registeredCourse;
    private String address;
    private String notes;
    private CourseRegistration.Status status;

    @JsonFormat(pattern = DateTimeConstant.DATE_TIME_FORMAT, timezone = DateTimeConstant.TIME_ZONE)
    private Timestamp createdDate;

    @JsonFormat(pattern = DateTimeConstant.DATE_TIME_FORMAT, timezone = DateTimeConstant.TIME_ZONE)
    private Timestamp updateDate;
}

