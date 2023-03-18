package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CourseService;
import com.mycompany.myapp.service.dto.CourseDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api") // path common prefix
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /*
    1. Requirement: 实现学生选课功能
    2. HTTP method: POST
    3. URL: /student/course/{courseId}
    4. Request body: null
    5. Response body: null
    6. HTTP Status code:  200/201/204
    7. (Request) header: jwtToken
     */
    @PostMapping(path = "/student/course/{courseId}")
    public void enrollCourse(@PathVariable String courseId) {
        String username = getUsername();
        courseService.enrollCourse(username, courseId);
    }

    /*
    1. Requirement: 列出所有已有课程
    2. HTTP method: GET
    3. URL: /courses
    4. Request body: null
    5. Response body: List of courses
    6. HTTP Status code:  200
    7. (Request) header: jwtToken
     */
    @GetMapping(path = "/courses")
    public List<CourseDTO> getCourses() {
        return courseService.getCourses();
    }

    /*
    1. Requirement: 列出学生所有已选课程
    2. HTTP method: GET
    3. URL: /student/selected-courses
    4. Request body: null
    5. Response body: List of selected courses
    6. HTTP Status code:  200
    7. (Request) header: jwtToken
     */
    @GetMapping(path = "/student/selected-courses")
    public List<CourseDTO> getSelectedCourses() {
        String username = getUsername();
        return courseService.getSelectedCourses(username);
    }

    /*
    1. Requirement: 提供学生drop课程功能
    2. HTTP method: DELETE
    3. URL: /student/course/{courseId}
    4. Request body: null
    5. Response body: null
    6. HTTP Status code:  200
    7. (Request) header: jwtToken
     */
    @DeleteMapping(path = "/student/course/{courseId}")
    public void dropCourse(@PathVariable String courseId) {
        String username = getUsername();
        courseService.dropCourse(username, courseId);
    }

    private String getUsername() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
