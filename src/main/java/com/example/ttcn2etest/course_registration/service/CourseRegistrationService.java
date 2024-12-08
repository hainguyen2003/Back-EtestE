package com.example.ttcn2etest.course_registration.service;

import com.example.ttcn2etest.course_registration.dto.CourseRegistrationDTO;
import com.example.ttcn2etest.course_registration.entity.CourseRegistration;
import com.example.ttcn2etest.course_registration.request.CreateCourseRegistrationRequest;
import com.example.ttcn2etest.course_registration.request.FilterCourseRegistrationRequest;
import com.example.ttcn2etest.course_registration.request.UpdateCourseRegistrationRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

public interface CourseRegistrationService {
    // Lấy tất cả đăng ký khóa học
    List<CourseRegistrationDTO> getAllCourseRegistration();

    // Lấy đăng ký khóa học theo ID
    CourseRegistrationDTO getByIdCourseRegistration(Long id);

    // Tạo đăng ký khóa học mới
    CourseRegistrationDTO createCourseRegistration(CreateCourseRegistrationRequest request);

    // Cập nhật đăng ký khóa học
    CourseRegistrationDTO updateCourseRegistration(UpdateCourseRegistrationRequest request, Long id);

    // Xóa đăng ký khóa học theo ID
    CourseRegistrationDTO deleteByIdCourseRegistration(Long id);

    // Xóa tất cả đăng ký khóa học
    List<CourseRegistrationDTO> deleteAllCourseRegistration(List<Long> ids);

    // Lọc đăng ký khóa học theo các tiêu chí
    Page<CourseRegistration> filterCourseRegistration(FilterCourseRegistrationRequest request, Date dateFrom, Date dateTo);
}