import React, { useEffect, useState } from 'react';
import CourseTable from '../components/CourseTable';
import { CourseService } from '../services/CourseService';

export default function AllCourses() {
    const [courses, setCourses] = useState([]);
    // ComponentDidMount
    useEffect(() => {
        // AJAX
        // 1. Make XHR call
        CourseService.getAllCourses() //Promise
        .then(response => {
            // 2. Display result on the page
            // courses are fectched from response.data
            setCourses(response.data);
        })
        .catch(error => console.error(error));
    }, []);
    return (
        <div>
            <CourseTable courses={courses} action={enrollCourse} label="Enroll"/>     
        </div>
    );
    
    function enrollCourse(courseName) {
        CourseService.enrollCourse(courseName)
            .then(response => {
                alert(`Successfully enrolled course ${courseName}.`);
            })
            .catch(error => {
                alert(`Failed to enrolled course ${courseName}.`);
            })
    }
}