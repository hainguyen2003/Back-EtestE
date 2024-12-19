package com.example.ttcn2etest.repository.CourseInformation;

import com.example.ttcn2etest.model.etity.CourseInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourseInformationRepository extends JpaRepository<CourseInformation,Long>, JpaSpecificationExecutor<CourseInformation> {
}
