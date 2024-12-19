package com.example.ttcn2etest.admissions.request;

import com.example.ttcn2etest.validator.DateValidateAnnotation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.sql.Date;

@Data
public class FilterAdmissionRequest {

    @NotBlank(message = "Start cannot be empty!")
    private Integer start;

    @NotBlank(message = "Limit cannot be empty!")
    private Integer limit;

    @DateValidateAnnotation(message = "DateFrom must have the format dd/MM/yyyy")
    private String dateFrom;

    @DateValidateAnnotation(message = "DateTo must have the format dd/MM/yyyy")
    private String dateTo;

    private String title;

    private String program;

    @NotNull(message = "status ẩn / hiện")
    private Boolean status;


}