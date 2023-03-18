package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.UserCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserCourseRepository extends JpaRepository<UserCourse, Long> {
    Optional<UserCourse> findOneByUserAndCourse(User user, Course course);

    List<UserCourse> findAllByUser(User user);

    @Transactional//必须要加，不加删不掉，只要有关增删改的操作都需要加这个annotation
    void deleteByUserAndCourse(User user, Course course);
}
