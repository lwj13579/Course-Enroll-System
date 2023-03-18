package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.UserCourse;
import com.mycompany.myapp.repository.CourseRepository;
import com.mycompany.myapp.repository.UserCourseRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.dto.CourseDTO;
import com.mycompany.myapp.service.mapper.CourseMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {
    private UserRepository userRepository;
    private CourseRepository courseRepository;
    private UserCourseRepository userCourseRepository;
    private CourseMapper courseMapper;

    /**
     * 1. User exists?
     * 2. Course exists?
     * 3. UserCourse combination not exist (de-dupe)
     * 4. Save UserCourse to DB
     * @param username
     * @param courseName
     */
    public void enrollCourse(String username, String courseName) {
        // 1 + 2
        UserCourse userCourse = getUserCourse(username, courseName);
        // 3. UserCourse combination not exist (de-dupe)
        userCourseRepository.findOneByUserAndCourse(userCourse.getUser(), userCourse.getCourse())
            .ifPresent(existingUserCourse -> {
                throw new IllegalArgumentException(String.format("UserCourse:{} already exists!", existingUserCourse));
            });
        // 4. Save UserCourse to DB
        userCourseRepository.save(userCourse);
    }

    private UserCourse getUserCourse(String username, String courseName) {
        //1. If User exists?
        Optional<User> optionalUser = userRepository.findOneByLogin(username);
        // throw exception if no such user found
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException(String.format("No such user: {}", username)));

        //2. Course exists
        Optional<Course> optionalCourse = courseRepository.findByCourseName(courseName);
        // throw exception if no such course found
        Course course = optionalCourse.orElseThrow(() -> new IllegalArgumentException(String.format("No such course: {}", courseName)));
        return new UserCourse(user, course);
    }


    public List<CourseDTO> getCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(course -> courseMapper.convert(course)).collect(Collectors.toList());
    }

    /**
     * 1. User exists?
     * 2. Find UserCourse by user
     * 3. Covert to List of CourseDTO
     * @param username
     * @return List<CourseDTO>
     */
    public List<CourseDTO> getSelectedCourses(String username) {
        //1. If User exists?
        Optional<User> optionalUser = userRepository.findOneByLogin(username);
        // throw exception if no such user found
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException(String.format("No such user: {}", username)));

        // 2. Find UserCourse by user
        List<UserCourse> userCourseList = userCourseRepository.findAllByUser(user);

        // 3. Covert to List of CourseDTO
        return userCourseList.stream().map(userCourse -> userCourse.getCourse()).map(course -> courseMapper.convert(course)).collect(Collectors.toList());
    }

    /**
     * 1. User exists?
     * 2. Course exists?
     * 3. Drop UserCourse(不需要查看这个entry是否存在于UserCourse表中，只保证删完表中没有这个entry）
     * @param username
     * @param courseName
     */
    public void dropCourse(String username, String courseName) {
        // 1 + 2
        UserCourse userCourse = getUserCourse(username, courseName);
        // 3 Drop UserCourse(不需要查看这个entry是否存在于UserCourse表中，只保证删完表中没有这个entry）
        userCourseRepository.deleteByUserAndCourse(userCourse.getUser(), userCourse.getCourse());
    }
}

