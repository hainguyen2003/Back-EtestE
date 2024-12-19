package com.example.ttcn2etest.repository.CourseRegistration1;

import com.example.ttcn2etest.model.etity.CourseRegistration1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourseRegistration1Repository extends JpaRepository<CourseRegistration1, Long>, JpaSpecificationExecutor<CourseRegistration1> {
}
