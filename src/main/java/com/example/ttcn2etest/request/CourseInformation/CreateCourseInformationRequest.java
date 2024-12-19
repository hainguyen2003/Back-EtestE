package com.example.ttcn2etest.request.CourseInformation;

import com.example.ttcn2etest.model.etity.ConsultingRegistration;
import com.example.ttcn2etest.model.etity.CourseInformation;
import com.example.ttcn2etest.validator.EmailAnnotation;
import com.example.ttcn2etest.validator.NameAnnotation;
import com.example.ttcn2etest.validator.PhoneNumber;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import static com.example.ttcn2etest.model.etity.CourseInformation.Status.WAITING_FOR_APPROVED;

@Data
public class CreateCourseInformationRequest {
    @NotBlank(message = "Họ và tên không được để trống!")
    @Size(min = 6, max = 100, message = "Họ và tên phải có ít nhất 6, nhiều nhất 100 kí tự!")

    private String name;

    //    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Email không hợp lệ!")
    @NotBlank(message = "Email không được để trống!")
    @EmailAnnotation
    private String email;
    @NotBlank(message = "địa chỉ không được để trống!")

    private String address;
    @NotBlank(message = "Số điện thoại không được để trống!")

    private String phone;
    @NotBlank(message = "khoa hoc không được để trống!")
    private String courseInformation;

    @NotNull(message = "Trạng thái không được để trống!")
    @Enumerated(EnumType.STRING)
    private CourseInformation.Status status = WAITING_FOR_APPROVED ;
}
