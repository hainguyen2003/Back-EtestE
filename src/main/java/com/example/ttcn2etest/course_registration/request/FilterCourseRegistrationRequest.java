package com.example.ttcn2etest.course_registration.request;

import com.example.ttcn2etest.course_registration.entity.CourseRegistration;
import com.example.ttcn2etest.validator.DateValidateAnnotation;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FilterCourseRegistrationRequest {
    @NotNull(message = "Start không được để trống")
    private Integer start;

    @NotNull(message = "Limit không được để trống")
    private Integer limit;

    @DateValidateAnnotation(message = "DateFrom phải có định dạng dd/MM/yyyy")
    private String dateFrom;

    @DateValidateAnnotation(message = "DateTo phải có định dạng dd/MM/yyyy")
    private String dateTo;


    // Thêm trường registeredCourse
    private String registeredCourse;
    private String customerName;
    private String email;
    private String phone;
    private CourseRegistration.Status status;
}
