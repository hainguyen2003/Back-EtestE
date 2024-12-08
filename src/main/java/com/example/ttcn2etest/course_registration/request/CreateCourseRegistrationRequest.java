package com.example.ttcn2etest.course_registration.request;

import com.example.ttcn2etest.course_registration.entity.CourseRegistration;
import com.example.ttcn2etest.validator.EmailAnnotation;
import com.example.ttcn2etest.validator.NameAnnotation;
import com.example.ttcn2etest.validator.PhoneNumber;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static com.example.ttcn2etest.course_registration.entity.CourseRegistration.Status.REGISTERED;

@Data
public class CreateCourseRegistrationRequest {
    @NotBlank(message = "Họ và tên không được để trống!")
    @Size(min = 6, max = 100, message = "Họ và tên phải có ít nhất 6, nhiều nhất 100 kí tự!")
    @NameAnnotation
    private String customerName;

    @NotBlank(message = "Email không được để trống!")
    @EmailAnnotation
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống!")
    @PhoneNumber
    private String phone;

    @NotBlank(message = "Nội dung ghi chú không được để trống!")
    private String notes;


    // Thêm trường registeredCourse
    @NotBlank(message = "Khóa học đăng ký không được để trống!")
    private String registeredCourse;  // Đây là tên hoặc mã của khóa học đã đăng ký

    @NotNull(message = "Trạng thái không được để trống!")
    @Enumerated(EnumType.STRING)
    private CourseRegistration.Status status = REGISTERED;
}
