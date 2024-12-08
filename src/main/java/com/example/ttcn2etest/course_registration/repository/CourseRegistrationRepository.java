package com.example.ttcn2etest.course_registration.repository;

import com.example.ttcn2etest.course_registration.entity.CourseRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, Long>,
        JpaSpecificationExecutor<CourseRegistration> {
}