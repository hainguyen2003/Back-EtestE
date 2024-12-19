package com.example.ttcn2etest.service.CourseInformation;


import com.example.ttcn2etest.model.dto.CourseInformationDTO;

import com.example.ttcn2etest.model.etity.CourseInformation;
import com.example.ttcn2etest.request.CourseInformation.CreateCourseInformationRequest;
import com.example.ttcn2etest.request.CourseInformation.FilterCourseInformationRequest;
import com.example.ttcn2etest.request.CourseInformation.UpdateCourseInformationRequest;



import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

public interface CourseInformationService {
    List<CourseInformationDTO> getAllCourseInformation();

    CourseInformationDTO getByIdCourseInformation(Long id);

    CourseInformationDTO createCourseInformation(CreateCourseInformationRequest request);

    CourseInformationDTO updateCourseInformation(UpdateCourseInformationRequest request, Long id);

    CourseInformationDTO deleteByIdCourseInformation(Long id);

    List<CourseInformationDTO> deleteAllCourseInformation(List<Long> ids);

    Page<CourseInformation> filterCourseInformation(FilterCourseInformationRequest request, Date dateFrom, Date dateTo);
}
