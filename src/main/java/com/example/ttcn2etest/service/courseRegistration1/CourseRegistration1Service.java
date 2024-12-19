package com.example.ttcn2etest.service.courseRegistration1;

import com.example.ttcn2etest.model.dto.CourseRegistration1DTO;
import com.example.ttcn2etest.model.etity.CourseRegistration1;
import com.example.ttcn2etest.request.courseRegistration1.CreateCourseRegistration1Request;
import com.example.ttcn2etest.request.courseRegistration1.FilterCourseRegistration1Request;
import com.example.ttcn2etest.request.courseRegistration1.UpdateCourseRegistration1Request;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface CourseRegistration1Service { List<CourseRegistration1DTO> getAllCourseRegistration1();

    CourseRegistration1DTO getByIdCourseRegistration1(Long id);

    CourseRegistration1DTO createCourseRegistration1(CreateCourseRegistration1Request request);

    CourseRegistration1DTO updateCourseRegistration1(UpdateCourseRegistration1Request request, Long id);

    CourseRegistration1DTO deleteByIdCourseRegistration1(Long id);

    List<CourseRegistration1DTO> deleteAllCourseRegistration1(List<Long> ids);

    Page<CourseRegistration1> filterCourseRegistration1(FilterCourseRegistration1Request request, Date dateFrom, Date dateTo);
}

