package com.example.ttcn2etest.course_registration.controller;

import com.example.ttcn2etest.constant.DateTimeConstant;
import com.example.ttcn2etest.controller.BaseController;
import com.example.ttcn2etest.course_registration.dto.CourseRegistrationDTO;
import com.example.ttcn2etest.course_registration.entity.CourseRegistration;
import com.example.ttcn2etest.course_registration.request.CreateCourseRegistrationRequest;
import com.example.ttcn2etest.course_registration.request.FilterCourseRegistrationRequest;
import com.example.ttcn2etest.course_registration.request.UpdateCourseRegistrationRequest;
import com.example.ttcn2etest.course_registration.service.CourseRegistrationService;

import com.example.ttcn2etest.utils.MyUtils;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/course/registration")
public class CourseRegistrationController extends BaseController {

    private final CourseRegistrationService courseRegistrationService;
    private final ModelMapper modelMapper;

    public CourseRegistrationController(CourseRegistrationService courseRegistrationService, ModelMapper modelMapper) {
        this.courseRegistrationService = courseRegistrationService;
        this.modelMapper = modelMapper;
    }

    // Lấy tất cả đăng ký khóa học
    @GetMapping("/all")
    public ResponseEntity<?> getAllCourseRegistration() {
        try {
            List<CourseRegistrationDTO> response = courseRegistrationService.getAllCourseRegistration();
            return buildListItemResponse(response, response.size());
        } catch (Exception ex) {
            return buildErrorResponse("Đã xảy ra lỗi khi lấy danh sách đăng ký khóa học.");
        }
    }

    // Lấy đăng ký khóa học theo ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getByIdCourseRegistration(@PathVariable Long id) {
        CourseRegistrationDTO response = courseRegistrationService.getByIdCourseRegistration(id);
        return buildItemResponse(response);
    }

        // Tạo đăng ký khóa học mới
        @PostMapping("")
        public ResponseEntity<?> createCourseRegistration(@Validated @RequestBody CreateCourseRegistrationRequest request) {
            CourseRegistrationDTO response = courseRegistrationService.createCourseRegistration(request);
            return buildItemResponse(response);
        }


    // Cập nhật đăng ký khóa học
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    public ResponseEntity<?> updateCourseRegistration(@Validated @RequestBody UpdateCourseRegistrationRequest request,
                                                      @PathVariable("id") Long id) {
        CourseRegistrationDTO response = courseRegistrationService.updateCourseRegistration(request, id);
        return buildItemResponse(response);
    }

    // Xóa đăng ký khóa học theo ID
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    public ResponseEntity<?> deleteByIdCourseRegistration(@PathVariable Long id) {
        CourseRegistrationDTO response = courseRegistrationService.deleteByIdCourseRegistration(id);
        return buildItemResponse(response);
    }

    // Xóa tất cả đăng ký khóa học
    @DeleteMapping("/delete/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    public ResponseEntity<?> deleteAllCourseRegistration(@RequestBody List<Long> ids) {
        try {
            List<CourseRegistrationDTO> response = courseRegistrationService.deleteAllCourseRegistration(ids);
            return buildListItemResponse(response, response.size());
        } catch (Exception ex) {
            return buildErrorResponse("Có lỗi khi xóa danh sách đăng ký khóa học.");
        }
    }

    // Lọc đăng ký khóa học theo yêu cầu
    @PostMapping("/filter")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    public ResponseEntity<?> filterCourseRegistration(@Validated @RequestBody FilterCourseRegistrationRequest request) throws ParseException {
        Page<CourseRegistration> courseRegistrationPage = courseRegistrationService.filterCourseRegistration(
                request,
                !Strings.isEmpty(request.getDateFrom()) ? MyUtils.convertDateFromString(request.getDateFrom(), DateTimeConstant.DATE_FORMAT) : null,
                !Strings.isEmpty(request.getDateTo()) ? MyUtils.convertDateFromString(request.getDateTo(), DateTimeConstant.DATE_FORMAT) : null);

        List<CourseRegistrationDTO> courseRegistrationDTOS = courseRegistrationPage.getContent().stream().map(
                courseRegistration -> modelMapper.map(courseRegistration, CourseRegistrationDTO.class)
        ).toList();
        return buildListItemResponse(courseRegistrationDTOS, courseRegistrationPage.getTotalElements());
    }


}