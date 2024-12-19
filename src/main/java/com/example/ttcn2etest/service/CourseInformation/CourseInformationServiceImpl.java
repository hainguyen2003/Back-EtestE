    package com.example.ttcn2etest.service.CourseInformation;

    import com.example.ttcn2etest.exception.MyCustomException;
    import com.example.ttcn2etest.model.dto.ConsultingRegistrationDTO;
    import com.example.ttcn2etest.model.dto.CourseInformationDTO;
    import com.example.ttcn2etest.model.etity.ConsultingRegistration;
    import com.example.ttcn2etest.model.etity.CourseInformation;
    import com.example.ttcn2etest.repository.CourseInformation.CourseInformationRepository;
    import com.example.ttcn2etest.repository.CourseInformation.CustomCourseInformationRepository;
    import com.example.ttcn2etest.repository.consultingRegistration.ConsultingRegistrationRepository;
    import com.example.ttcn2etest.repository.consultingRegistration.CustomConsultingRegistrationRepository;
    import com.example.ttcn2etest.request.CourseInformation.CreateCourseInformationRequest;
    import com.example.ttcn2etest.request.CourseInformation.FilterCourseInformationRequest;
    import com.example.ttcn2etest.request.CourseInformation.UpdateCourseInformationRequest;
    import com.example.ttcn2etest.request.consultingRegistration.CreateConsultingRegistrationRequest;
    import com.example.ttcn2etest.request.consultingRegistration.FilterConsultingRegistrationRequest;
    import com.example.ttcn2etest.request.consultingRegistration.UpdateConsultingRegistrationRequest;
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
    public class CourseInformationServiceImpl implements CourseInformationService {

        private final CourseInformationRepository courseInformationRepository;
        private final ModelMapper modelMapper = new ModelMapper();

        public CourseInformationServiceImpl(CourseInformationRepository courseInformationRepository) {
            this.courseInformationRepository = courseInformationRepository;
        }

//        @Override
//        public List<CourseInformationDTO> getAllCourseInformation() {
//            return courseInformationRepository.findAll().stream().map(
//                    consultingRegistration -> modelMapper.map(courseInformationRepository, CourseInformationDTO.class)
//            ).toList();
//        }
@Override
public List<CourseInformationDTO> getAllCourseInformation() {
    return courseInformationRepository.findAll().stream().map(
            courseInformation -> modelMapper.map(courseInformation, CourseInformationDTO.class)
    ).toList();
}


        @Override
        public CourseInformationDTO getByIdCourseInformation(Long id) {
            Optional<CourseInformation> courseInformation = courseInformationRepository.findById(id);
            if (courseInformation.isPresent()) {
                return modelMapper.map(courseInformation.get(), CourseInformationDTO.class);
            } else {
                throw new MyCustomException("ID không tồn tại trong hệ thống!");
            }
        }

        @Override
        public CourseInformationDTO createCourseInformation(CreateCourseInformationRequest request) {
            try {
                CourseInformation courseInformation = CourseInformation.builder()
                        .name(request.getName())
                        .phone(request.getPhone())
                        .email(request.getEmail())
                        .address(request.getAddress())
                        .courseInformation(request.getCourseInformation())
                        .status(request.getStatus())
                        .createdDate(new Timestamp(System.currentTimeMillis()))
                        .updateDate(new Timestamp(System.currentTimeMillis()))
                        .build();
                courseInformation = courseInformationRepository.saveAndFlush(courseInformation);
                return modelMapper.map(courseInformation, CourseInformationDTO.class);
            } catch (Exception ex) {
                throw new MyCustomException("Có lỗi xảy ra trong quá trình thêm  mới!");
            }
        }

        @Override
        @Transactional
        public CourseInformationDTO updateCourseInformation(UpdateCourseInformationRequest request, Long id) {
            Optional<CourseInformation> courseInformationOptional = courseInformationRepository.findById(id);
            if (courseInformationOptional.isPresent()) {
                CourseInformation courseInformation = courseInformationOptional.get();
                courseInformation.setName(request.getName());
                courseInformation.setPhone(request.getPhone());
                courseInformation.setEmail(request.getEmail());
                courseInformation.setAddress(request.getAddress());
                courseInformation.setStatus(request.getStatus());
                courseInformation.setCourseInformation(request.getCourseInformation());
                courseInformation.setUpdateDate(new Timestamp(System.currentTimeMillis()));
                return modelMapper.map(courseInformation, CourseInformationDTO.class);
            }
            throw new MyCustomException("Có lỗi xảy ra trong quá trình cập nhật!");
        }

        @Override
        @Transactional
        public CourseInformationDTO deleteByIdCourseInformation(Long id) {
            if (!courseInformationRepository.existsById(id)) {
                throw new MyCustomException("Id: " + id + " cần xóa không tồn tại trong hệ thống!");
            }
            Optional<CourseInformation> courseInformationOptional = courseInformationRepository.findById(id);
            if (courseInformationOptional.isPresent()) {
                courseInformationRepository.deleteById(id);
                return modelMapper.map(courseInformationOptional, CourseInformationDTO.class);
            }
            throw new MyCustomException("Có lỗi xảy ra trong quá trinh xóa!");
        }

        @Override
        public List<CourseInformationDTO> deleteAllCourseInformation(List<Long> ids) {
            List<CourseInformationDTO> courseInformationDTOS = new ArrayList<>();
            for (Long id : ids) {
                Optional<CourseInformation> optionalCourseInformation = courseInformationRepository.findById(id);
                if (optionalCourseInformation.isPresent()) {
                    CourseInformation courseInformation = optionalCourseInformation.get();
                    courseInformationDTOS.add(modelMapper.map(courseInformation, CourseInformationDTO.class));
                    courseInformationRepository.delete(courseInformation);
                } else {
                    throw new MyCustomException("Có lỗi xảy ra trong quá trình xóa danh sách đăng ký tư vấn!");
                }

            }
            return courseInformationDTOS;
        }

        @Override
        public Page<CourseInformation> filterCourseInformation(FilterCourseInformationRequest request, Date dateFrom, Date dateTo) {
            Specification<CourseInformation> specification = CustomCourseInformationRepository.filterSpecification(dateFrom, dateTo, request);
            Page<CourseInformation> courseInformationPage = courseInformationRepository.findAll(specification, PageRequest.of(request.getStart(), request.getLimit()));
            return courseInformationPage;
        }
//        private final CourseInformationRepository courseInformationRepository;
//        private final ModelMapper modelMapper = new ModelMapper();
//
//        public CourseInformationServiceImpl(CourseInformationRepository courseInformationRepository) {
//            this.courseInformationRepository = courseInformationRepository;
//        }
//
//        @Override
//        public List<CourseInformationDTO> getAllCourseInformation() {
//            return courseInformationRepository.findAll().stream()
//                    .map(course -> modelMapper.map(course, CourseInformationDTO.class))
//                    .toList();
//        }
//
//        @Override
//        public CourseInformationDTO getByIdCourseInformation(Long id) {
//            Optional<CourseInformation> courseOptional = courseInformationRepository.findById(id);
//            if (courseOptional.isPresent()) {
//                return modelMapper.map(courseOptional.get(), CourseInformationDTO.class);
//            } else {
//                throw new MyCustomException("ID không tồn tại trong hệ thống!");
//            }
//        }
//
//        @Override
//        public CourseInformationDTO createCourseInformation(CreateCourseInformationRequest request) {
//            try {
//                CourseInformation courseInformation = CourseInformation.builder()
//                        .name(request.getName())
//                        .phone(request.getPhone())
//                        .email(request.getEmail())
//                        .address(request.getAddress())
//                        .courseInformation(request.getCourseInformation())
//                        .status(request.getStatus())
//                        .createdDate(new Timestamp(System.currentTimeMillis()))
//                        .updateDate(new Timestamp(System.currentTimeMillis()))
//                        .build();
//                courseInformation = courseInformationRepository.saveAndFlush(courseInformation);
//                return modelMapper.map(courseInformation, CourseInformationDTO.class);
//            } catch (Exception ex) {
//                throw new MyCustomException("Có lỗi xảy ra trong quá trình thêm mới!");
//            }
//        }
//
//        @Override
//        @Transactional
//        public CourseInformationDTO updateCourseInformation(UpdateCourseInformationRequest request, Long id) {
//            Optional<CourseInformation> courseOptional = courseInformationRepository.findById(id);
//            if (courseOptional.isPresent()) {
//                CourseInformation courseInformation = courseOptional.get();
//                courseInformation.setName(request.getName());
//                courseInformation.setPhone(request.getPhone());
//                courseInformation.setEmail(request.getEmail());
//                courseInformation.setAddress(request.getAddress());
//                courseInformation.setCourseInformation(request.getCourseInformation());
//                courseInformation.setStatus(request.getStatus());
//                courseInformation.setUpdateDate(new Timestamp(System.currentTimeMillis()));
//                return modelMapper.map(courseInformation, CourseInformationDTO.class);
//            } else {
//                throw new MyCustomException("Có lỗi xảy ra trong quá trình cập nhật!");
//            }
//        }
//
//        @Override
//        @Transactional
//        public CourseInformationDTO deleteByIdCourseInformation(Long id) {
//            if (!courseInformationRepository.existsById(id)) {
//                throw new MyCustomException("Id: " + id + " cần xóa không tồn tại trong hệ thống!");
//            }
//            Optional<CourseInformation> courseOptional = courseInformationRepository.findById(id);
//            if (courseOptional.isPresent()) {
//                courseInformationRepository.deleteById(id);
//                return modelMapper.map(courseOptional.get(), CourseInformationDTO.class);
//            } else {
//                throw new MyCustomException("Có lỗi xảy ra trong quá trình xóa!");
//            }
//        }
//
//        @Override
//        public List<CourseInformationDTO> deleteAllCourseInformation(List<Long> ids) {
//            List<CourseInformationDTO> courseInformationDTOS = new ArrayList<>();
//            for (Long id : ids) {
//                Optional<CourseInformation> courseOptional = courseInformationRepository.findById(id);
//                if (courseOptional.isPresent()) {
//                    CourseInformation courseInformation = courseOptional.get();
//                    courseInformationDTOS.add(modelMapper.map(courseInformation, CourseInformationDTO.class));
//                    courseInformationRepository.delete(courseInformation);
//                } else {
//                    throw new MyCustomException("Có lỗi xảy ra trong quá trình xóa danh sách thông tin khóa học!");
//                }
//            }
//            return courseInformationDTOS;
//        }
//
//        @Override
//        public Page<CourseInformation> filterCourseInformation(FilterCourseInformationRequest request, Date dateFrom, Date dateTo) {
//            Specification<CourseInformation> specification = CustomCourseInformationRepository.filterSpecification(dateFrom, dateTo, request);
//            return courseInformationRepository.findAll(specification, PageRequest.of(request.getStart(), request.getLimit()));
//        }
    }
