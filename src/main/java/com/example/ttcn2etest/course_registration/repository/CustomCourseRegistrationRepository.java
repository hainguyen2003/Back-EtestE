package com.example.ttcn2etest.course_registration.repository;

import com.example.ttcn2etest.course_registration.entity.CourseRegistration;
import com.example.ttcn2etest.course_registration.request.FilterCourseRegistrationRequest;
import com.example.ttcn2etest.exception.MyCustomException;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class CustomCourseRegistrationRepository {
    public static Specification<CourseRegistration> filterSpecification(Date dateFrom, Date dateTo,
                                                                        FilterCourseRegistrationRequest request) {
        return ((((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (dateFrom != null && dateTo != null) {
                predicates.add(criteriaBuilder.between(root.get("createdDate"), dateFrom, dateTo));
            }
            if (StringUtils.hasText(request.getCustomerName())) {
                predicates.add(criteriaBuilder.like(root.get("customerName"), "%" + request.getCustomerName() + "%"));
            }
            if (StringUtils.hasText(request.getPhone())) {
                predicates.add(criteriaBuilder.like(root.get("phone"), "%" + request.getPhone() + "%"));
            }
            if (StringUtils.hasText(request.getEmail())) {
                predicates.add(criteriaBuilder.equal(root.get("email"), request.getEmail()));
            }
            if (StringUtils.hasText(request.getRegisteredCourse())) {
                predicates.add(criteriaBuilder.equal(root.get("registeredCourse"), request.getRegisteredCourse()));
            }
            if (request.getStatus() != null && !request.getStatus().equals("")) {
                try {
                    CourseRegistration.Status status = CourseRegistration.Status.valueOf(String.valueOf(request.getStatus()));
                    predicates.add(criteriaBuilder.equal(root.get("status"), status));
                } catch (Exception e) {
                    throw new MyCustomException("Trạng thái đăng ký không hợp lệ!");
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        })));
    }
}
