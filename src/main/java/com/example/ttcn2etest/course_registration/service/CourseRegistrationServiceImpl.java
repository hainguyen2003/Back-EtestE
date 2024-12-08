package com.example.ttcn2etest.course_registration.service;

import com.example.ttcn2etest.course_registration.dto.CourseRegistrationDTO;
import com.example.ttcn2etest.course_registration.entity.CourseRegistration;
import com.example.ttcn2etest.course_registration.repository.CourseRegistrationRepository;
import com.example.ttcn2etest.course_registration.repository.CustomCourseRegistrationRepository;
import com.example.ttcn2etest.course_registration.request.CreateCourseRegistrationRequest;
import com.example.ttcn2etest.course_registration.request.FilterCourseRegistrationRequest;
import com.example.ttcn2etest.course_registration.request.UpdateCourseRegistrationRequest;
import com.example.ttcn2etest.exception.MyCustomException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CourseRegistrationServiceImpl implements CourseRegistrationService {

    private final CourseRegistrationRepository courseRegistrationRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    public CourseRegistrationServiceImpl(CourseRegistrationRepository courseRegistrationRepository) {
        this.courseRegistrationRepository = courseRegistrationRepository;
    }

    @Override
    public List<CourseRegistrationDTO> getAllCourseRegistration() {
        return courseRegistrationRepository.findAll().stream()
                .map(courseRegistration -> modelMapper.map(courseRegistration, CourseRegistrationDTO.class))
                .toList();
    }

    @Override
    public CourseRegistrationDTO getByIdCourseRegistration(Long id) {
        Optional<CourseRegistration> courseRegistration = courseRegistrationRepository.findById(id);
        if (courseRegistration.isPresent()) {
            return modelMapper.map(courseRegistration.get(), CourseRegistrationDTO.class);
        } else {
            throw new MyCustomException("ID không tồn tại trong hệ thống!");
        }
    }

    @Override
    public CourseRegistrationDTO createCourseRegistration(CreateCourseRegistrationRequest request) {
        try {
            CourseRegistration courseRegistration = CourseRegistration.builder()
                    .customerName(request.getCustomerName())
                    .phone(request.getPhone())
                    .email(request.getEmail())
                    .notes(request.getNotes())
                    .status(request.getStatus())
                    .registeredCourse(request.getRegisteredCourse())
                    .createdDate(new Timestamp(System.currentTimeMillis()))
                    .updateDate(new Timestamp(System.currentTimeMillis()))
                    .build();
            courseRegistration = courseRegistrationRepository.saveAndFlush(courseRegistration);
            return modelMapper.map(courseRegistration, CourseRegistrationDTO.class);
        } catch (Exception ex) {
            throw new MyCustomException("Có lỗi xảy ra trong quá trình thêm mới!");
        }
    }

    @Override
    @Transactional
    public CourseRegistrationDTO updateCourseRegistration(UpdateCourseRegistrationRequest request, Long id) {
        Optional<CourseRegistration> courseRegistrationOptional = courseRegistrationRepository.findById(id);
        if (courseRegistrationOptional.isPresent()) {
            CourseRegistration courseRegistration = courseRegistrationOptional.get();
            courseRegistration.setCustomerName(request.getCustomerName());
            courseRegistration.setPhone(request.getPhone());
            courseRegistration.setEmail(request.getEmail());
            courseRegistration.setStatus(request.getStatus());
            courseRegistration.setRegisteredCourse(request.getRegisteredCourse());
            courseRegistration.setNotes(request.getNotes());
            courseRegistration.setUpdateDate(new Timestamp(System.currentTimeMillis()));
            return modelMapper.map(courseRegistration, CourseRegistrationDTO.class);
        }
        throw new MyCustomException("Có lỗi xảy ra trong quá trình cập nhật!");
    }

    @Override
    @Transactional
    public CourseRegistrationDTO deleteByIdCourseRegistration(Long id) {
        if (!courseRegistrationRepository.existsById(id)) {
            throw new MyCustomException("Id: " + id + " cần xóa không tồn tại trong hệ thống!");
        }
        Optional<CourseRegistration> courseRegistrationOptional = courseRegistrationRepository.findById(id);
        if (courseRegistrationOptional.isPresent()) {
            courseRegistrationRepository.deleteById(id);
            return modelMapper.map(courseRegistrationOptional.get(), CourseRegistrationDTO.class);
        }
        throw new MyCustomException("Có lỗi xảy ra trong quá trình xóa!");
    }

    @Override
    public List<CourseRegistrationDTO> deleteAllCourseRegistration(List<Long> ids) {
        List<CourseRegistrationDTO> courseRegistrationDTOS = new ArrayList<>();
        for (Long id : ids) {
            Optional<CourseRegistration> optionalCourseRegistration = courseRegistrationRepository.findById(id);
            if (optionalCourseRegistration.isPresent()) {
                CourseRegistration courseRegistration = optionalCourseRegistration.get();
                courseRegistrationDTOS.add(modelMapper.map(courseRegistration, CourseRegistrationDTO.class));
                courseRegistrationRepository.delete(courseRegistration);
            } else {
                throw new MyCustomException("Có lỗi xảy ra trong quá trình xóa danh sách đăng ký khóa học!");
            }
        }
        return courseRegistrationDTOS;
    }

    @Override
    public Page<CourseRegistration> filterCourseRegistration(FilterCourseRegistrationRequest request, Date dateFrom, Date dateTo) {
        Specification<CourseRegistration> specification = CustomCourseRegistrationRepository.filterSpecification(dateFrom, dateTo, request);
        return courseRegistrationRepository.findAll(specification, PageRequest.of(request.getStart(), request.getLimit()));
    }
}