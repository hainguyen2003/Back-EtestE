package com.example.ttcn2etest.repository.CourseRegistration1;

import com.example.ttcn2etest.exception.MyCustomException;
import com.example.ttcn2etest.model.etity.CourseRegistration1;
import com.example.ttcn2etest.request.courseRegistration1.FilterCourseRegistration1Request;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomCourseRegistration1Repository {
    public static Specification<CourseRegistration1> filterSpecification(Date dateFrom, Date dateTo,
                                                                         FilterCourseRegistration1Request request) {
        return ((((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (dateFrom != null && dateTo != null) {
                predicates.add(criteriaBuilder.between(root.get("createdDate"), dateFrom, dateTo));
            }
            if (StringUtils.hasText(request.getName())) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
            }
            if (StringUtils.hasText(request.getPhone())) {
                predicates.add(criteriaBuilder.like(root.get("phone"), "%" + request.getPhone() + "%"));
            }
            if (StringUtils.hasText(request.getEmail())) {
                predicates.add(criteriaBuilder.equal(root.get("email"), request.getEmail()));
            }
            if (StringUtils.hasText(request.getAddress())) {
                predicates.add(criteriaBuilder.equal(root.get("address"), request.getAddress()));
            }
            if(request.getStatus() != null && !request.getStatus().equals("")){
                try{
                    CourseRegistration1.Status status = CourseRegistration1.Status.valueOf(String.valueOf(request.getStatus()));
                    predicates.add(criteriaBuilder.equal(root.get("status"), status));
                }catch (Exception e) {
                    throw new MyCustomException("Trạng thái đăng ký không hợp lệ!");
                }
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        })));
    }
}
