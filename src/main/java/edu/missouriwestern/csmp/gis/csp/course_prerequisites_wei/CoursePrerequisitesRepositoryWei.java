package edu.missouriwestern.csmp.gis.csp.course_prerequisites_wei;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CoursePrerequisitesRepositoryWei {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Course_prerequisite_wei> findcourse(String id) {
        //get the course id
        List<Course_prerequisite_wei> findCoursePrerequisitesByCourseId = new ArrayList<Course_prerequisite_wei>();
        findCoursePrerequisitesByCourseId.addAll(getAllCoursePrerequisites().stream().filter(t -> t.getCourseId().equals(id)).collect(Collectors.toList()));
        return findCoursePrerequisitesByCourseId;
    }

    public int size() {
        return jdbcTemplate.queryForObject("select count(*) from gis.precourses", Integer.class);
    }

    public List<Course_prerequisite_wei> getAllCoursePrerequisites() {

        int size = jdbcTemplate.queryForObject("select count(*) from gis.precourses", Integer.class);
        List<Course_prerequisite_wei> CoursePrerequisites = new ArrayList<Course_prerequisite_wei>();

        for (int i = 0; i < size; i++) {
            //get the course id
            String name = jdbcTemplate.queryForObject("select Name from gis.precourses limit " + i + ",1", String.class);
            //get the course name
            String courseId = jdbcTemplate.queryForObject("select CourseId from gis.precourses limit " + i + ",1", String.class);
            //get the credit
            int setNum = jdbcTemplate.queryForObject("select SetNumber from gis.precourses limit " + i + ",1", Integer.class);
            Course_prerequisite_wei newCoursePrerequisite = new Course_prerequisite_wei(courseId,name,setNum);
            CoursePrerequisites.add(newCoursePrerequisite);

        }
        return CoursePrerequisites;
    }
}
