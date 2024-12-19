package com.example.ttcn2etest.request.courseRegistration1;

import com.example.ttcn2etest.model.etity.CourseRegistration1;
import com.example.ttcn2etest.validator.DateValidateAnnotation;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FilterCourseRegistration1Request {
    @NotNull(message = "Start không được để trống")
    private Integer start;
    @NotNull(message = "Limit không được để trống")
    private Integer limit;
    @DateValidateAnnotation(message = "DateFrom phải có định dạng dd/MM/yyyy")
    private String dateFrom;
    @DateValidateAnnotation(message = "DateTo phải có định dạng dd/MM/yyyy")
    private String dateTo;
    private String name;
    private String email;
    private String phone;
    private String address;
    private CourseRegistration1.Status status;
}
