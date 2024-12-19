package com.example.ttcn2etest.controller;

import com.example.ttcn2etest.constant.DateTimeConstant;


import com.example.ttcn2etest.model.dto.CourseRegistration1DTO;
import com.example.ttcn2etest.model.etity.CourseRegistration1;
import com.example.ttcn2etest.request.courseRegistration1.CreateCourseRegistration1Request;
import com.example.ttcn2etest.request.courseRegistration1.FilterCourseRegistration1Request;
import com.example.ttcn2etest.request.courseRegistration1.UpdateCourseRegistration1Request;
import com.example.ttcn2etest.service.courseRegistration1.CourseRegistration1Service;
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
@RequestMapping("/course/registrationn")
public class CourseRegistration1Controller extends BaseController{
    private final CourseRegistration1Service curseRegistration1Service;
    private final ModelMapper modelMapper;

    public CourseRegistration1Controller(CourseRegistration1Service curseRegistration1Service, ModelMapper modelMapper) {
        this.curseRegistration1Service = curseRegistration1Service;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/all")
    ResponseEntity<?> getAllCourseRegistration1() {
        try {
            List<CourseRegistration1DTO> response = curseRegistration1Service.getAllCourseRegistration1();
            return buildListItemResponse(response, response.size());
        } catch (Exception ex) {
            return buildResponse();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getByIdConsultingRegistration1(@PathVariable Long id) {
        CourseRegistration1DTO response = curseRegistration1Service.getByIdCourseRegistration1(id);
        return buildItemResponse(response);
    }

    @PostMapping("")
    ResponseEntity<?> createConsultingRegistration1(@Validated @RequestBody CreateCourseRegistration1Request request) {
        CourseRegistration1DTO response = curseRegistration1Service.createCourseRegistration1(request);
        return buildItemResponse(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    ResponseEntity<?> updateConsultingRegistration1(@Validated @RequestBody UpdateCourseRegistration1Request request,
                                                   @PathVariable("id") Long id) {
        CourseRegistration1DTO response = curseRegistration1Service.updateCourseRegistration1(request, id);
        return buildItemResponse(response);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    ResponseEntity<?> deleteByIdCourseRegistration1(@PathVariable Long id) {
        CourseRegistration1DTO response = curseRegistration1Service.deleteByIdCourseRegistration1(id);
        return buildItemResponse(response);
    }

    @DeleteMapping("/delete/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    ResponseEntity<?> deleteAllCourseRegistration1(@RequestBody List<Long> ids) {
        try {
            List<CourseRegistration1DTO> response = curseRegistration1Service.deleteAllCourseRegistration1(ids);
            return buildListItemResponse(response, response.size());
        } catch (Exception ex) {
            return buildResponse();
        }
    }

    @PostMapping("/filter")
    @PreAuthorize("hasAnyAuthority('ADMIN','STAFF')")
    public ResponseEntity<?> filterCourseRegistration1(@Validated @RequestBody FilterCourseRegistration1Request request) throws ParseException {
        Page<CourseRegistration1> courseRegistration1Page = curseRegistration1Service.filterCourseRegistration1(
                request,
                !Strings.isEmpty(request.getDateFrom()) ? MyUtils.convertDateFromString(request.getDateFrom(), DateTimeConstant.DATE_FORMAT) : null,
                !Strings.isEmpty(request.getDateTo()) ? MyUtils.convertDateFromString(request.getDateTo(), DateTimeConstant.DATE_FORMAT) : null);
        List<CourseRegistration1DTO> courseRegistration1DTOS = courseRegistration1Page.getContent().stream().map(
                courseRegistration1 -> modelMapper.map(courseRegistration1, CourseRegistration1DTO.class)
        ).toList();
        return buildListItemResponse(courseRegistration1DTOS, courseRegistration1Page.getTotalElements());
    }
}
