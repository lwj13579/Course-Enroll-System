import React from 'react';
import CourseTable from "../components/CourseTable";
import {CourseService} from "../services/CourseService";

export default class EnrolledCourses extends React.Component {
    state = {
        courses: []
    };

    componentDidMount() {
        this.getSelectedCourses();
    }

    render() {
        return (
            <div>
                <CourseTable courses={this.state.courses}  action={this.dropCourse.bind(this)} label="Drop"/>
            </div>
        );
    }

    dropCourse(courseName) {
        console.log("dropCourse this", this);
        CourseService.dropCourse(courseName)
            .then(response => {
                alert(`Successfully dropped course ${courseName}.`);
                // "this" does not always pointing to the class itself.
                // By default, "this" will point to the context.
                this.getSelectedCourses();
            })
            .catch(error => {
                alert(`Failed to dropped course ${courseName}.`);
            })
    }

    getSelectedCourses() {
        CourseService.getSelectedCourse()
            .then(response => {
                this.setState({courses: response.data});
            })
            //setState cause rerender
    }
}
