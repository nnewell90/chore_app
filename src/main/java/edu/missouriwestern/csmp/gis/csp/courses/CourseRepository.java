package edu.missouriwestern.csmp.gis.csp.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CourseRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Course findcourse(String id) {
        //get the course id
        return getAllCourses().stream().filter(t -> t.getCourseId().equals(id)).findFirst().get();
    }

    public int size() {
        return jdbcTemplate.queryForObject("select count(*) from gis.courses", Integer.class);
    }

    public List<Course> getAllCourses() {

        int size = jdbcTemplate.queryForObject("select count(*) from gis.courses", Integer.class);
        List<Course> courses = new ArrayList<Course>();

        for (int i = 0; i < size; i++) {
            //get the course id
            String courseId = jdbcTemplate.queryForObject("select CourseId from gis.courses limit " + i + ",1", String.class);
            //get the course name
            String name = jdbcTemplate.queryForObject("select Name from gis.courses limit " + i + ",1", String.class);
            //get the credit
            int credit = jdbcTemplate.queryForObject("select Credits from gis.courses limit " + i + ",1", Integer.class);
            //get the course de
            String title = jdbcTemplate.queryForObject("select Title from gis.courses limit " + i + ",1", String.class);
            //get the department
            String department = jdbcTemplate.queryForObject("select Department from gis.courses limit " + i + ",1", String.class);
            //get the semester number
            int semesterNum=jdbcTemplate.queryForObject("select SemesterNum from gis.courses limit " + i + ",1", Integer.class);
            Course newCourse = new Course(courseId, name, credit, title, department,semesterNum);
            courses.add(newCourse);

        }
        return courses;
    }
}
