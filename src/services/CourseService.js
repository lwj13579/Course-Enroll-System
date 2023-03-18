import axios from "../axios";

export const CourseService = {
    getAllCourses: function() {
        //XHR
        return axios.get('/api/courses');
    },
    getSelectedCourse: function () {
        return axios.get("/api/student/selected-courses");
    },
    enrollCourse: function (courseName) {
        return axios.post(`/api/student/course/${courseName}`);
    },
    dropCourse: function (courseName) {
        return axios.delete(`/api/student/course/${courseName}`);
    },
};

// 前端连后端