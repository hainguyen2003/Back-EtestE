package com.example.ttcn2etest.service.courseRegistration1;

import com.example.ttcn2etest.exception.MyCustomException;
import com.example.ttcn2etest.model.dto.CourseRegistration1DTO;
import com.example.ttcn2etest.model.etity.CourseRegistration1;
import com.example.ttcn2etest.repository.CourseRegistration1.CourseRegistration1Repository;
import com.example.ttcn2etest.repository.CourseRegistration1.CustomCourseRegistration1Repository;
import com.example.ttcn2etest.request.courseRegistration1.CreateCourseRegistration1Request;
import com.example.ttcn2etest.request.courseRegistration1.FilterCourseRegistration1Request;
import com.example.ttcn2etest.request.courseRegistration1.UpdateCourseRegistration1Request;

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
public class CourseRegistration1ServiceImpl implements CourseRegistration1Service {
    private final CourseRegistration1Repository courseRegistration1Repository;
    private final ModelMapper modelMapper = new ModelMapper();

    public CourseRegistration1ServiceImpl(CourseRegistration1Repository courseRegistration1Repository) {
        this.courseRegistration1Repository = courseRegistration1Repository;
    }

    @Override
    public List<CourseRegistration1DTO> getAllCourseRegistration1() {
        return courseRegistration1Repository.findAll().stream().map(
                consultingRegistration1 -> modelMapper.map(consultingRegistration1, CourseRegistration1DTO.class)
        ).toList();
    }

    @Override
    public CourseRegistration1DTO getByIdCourseRegistration1(Long id) {
        Optional<CourseRegistration1> consultingRegistration1 = courseRegistration1Repository.findById(id);
        if (consultingRegistration1.isPresent()) {
            return modelMapper.map(consultingRegistration1.get(), CourseRegistration1DTO.class);
        } else {
            throw new MyCustomException("ID không tồn tại trong hệ thống!");
        }
    }

    @Override
    public CourseRegistration1DTO createCourseRegistration1(CreateCourseRegistration1Request request) {
        try {
            CourseRegistration1 consultingRegistration1 = CourseRegistration1.builder()
                    .name(request.getName())
                    .phone(request.getPhone())
                    .address(request.getAddress())
                    .email(request.getEmail())
                    .information(request.getInformation())
                    .status(request.getStatus())
                    .createdDate(new Timestamp(System.currentTimeMillis()))
                    .updateDate(new Timestamp(System.currentTimeMillis()))
                    .build();
            consultingRegistration1 = courseRegistration1Repository.saveAndFlush(consultingRegistration1);
            return modelMapper.map(consultingRegistration1, CourseRegistration1DTO.class);
        } catch (Exception ex) {
            throw new MyCustomException("Có lỗi xảy ra trong quá trình thêm  mới!");
        }
    }

    @Override
    @Transactional
    public CourseRegistration1DTO updateCourseRegistration1(UpdateCourseRegistration1Request request, Long id) {
        Optional<CourseRegistration1> consultingRegistration1Optional = courseRegistration1Repository.findById(id);
        if (consultingRegistration1Optional.isPresent()) {
            CourseRegistration1 consultingRegistration1 = consultingRegistration1Optional.get();
            consultingRegistration1.setName(request.getName());
            consultingRegistration1.setPhone(request.getPhone());
            consultingRegistration1.setEmail(request.getEmail());
            consultingRegistration1.setAddress(request.getAddress());
            consultingRegistration1.setStatus(request.getStatus());
            consultingRegistration1.setInformation(request.getInformation());
            consultingRegistration1.setUpdateDate(new Timestamp(System.currentTimeMillis()));
            return modelMapper.map(consultingRegistration1, CourseRegistration1DTO.class);
        }
        throw new MyCustomException("Có lỗi xảy ra trong quá trình cập nhật!");
    }

    @Override
    @Transactional
    public CourseRegistration1DTO deleteByIdCourseRegistration1(Long id) {
        if (!courseRegistration1Repository.existsById(id)) {
            throw new MyCustomException("Id: " + id + " cần xóa không tồn tại trong hệ thống!");
        }
        Optional<CourseRegistration1> courseRegistration1Optional = courseRegistration1Repository.findById(id);
        if (courseRegistration1Optional.isPresent()) {
            courseRegistration1Repository.deleteById(id);
            return modelMapper.map(courseRegistration1Optional, CourseRegistration1DTO.class);
        }
        throw new MyCustomException("Có lỗi xảy ra trong quá trinh xóa!");
    }

    @Override
    public List<CourseRegistration1DTO> deleteAllCourseRegistration1(List<Long> ids) {
        List<CourseRegistration1DTO> consultingRegistration1DTOS = new ArrayList<>();
        for (Long id : ids) {
            Optional<CourseRegistration1> optionalCourseRegistration1 = courseRegistration1Repository.findById(id);
            if (optionalCourseRegistration1.isPresent()) {
                CourseRegistration1 consultingRegistration1 = optionalCourseRegistration1.get();
                consultingRegistration1DTOS.add(modelMapper.map(consultingRegistration1, CourseRegistration1DTO.class));
                courseRegistration1Repository.delete(consultingRegistration1);
            } else {
                throw new MyCustomException("Có lỗi xảy ra trong quá trình xóa danh sách đăng ký tư vấn!");
            }

        }
        return consultingRegistration1DTOS;
    }

    @Override
    public Page<CourseRegistration1> filterCourseRegistration1(FilterCourseRegistration1Request request, Date dateFrom, Date dateTo) {
        Specification<CourseRegistration1> specification = CustomCourseRegistration1Repository.filterSpecification(dateFrom, dateTo, request);
        Page<CourseRegistration1> courseRegistration1Page = courseRegistration1Repository.findAll(specification, PageRequest.of(request.getStart(), request.getLimit()));
        return courseRegistration1Page;
    }

}
