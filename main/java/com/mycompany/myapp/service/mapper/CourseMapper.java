package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.service.dto.CourseDTO;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper {
    public CourseDTO convert(Course course) {
        //builder pattern
        return CourseDTO.builder()
            .courseContent(course.getCourseContent())
            .teacherId(course.getTeacherId())
            .courseLocation(course.getCourseLocation())
            .courseName(course.getCourseName())
            .build();
    }
}
