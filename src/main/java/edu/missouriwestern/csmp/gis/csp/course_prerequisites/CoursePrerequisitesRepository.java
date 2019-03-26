package edu.missouriwestern.csmp.gis.csp.course_prerequisites;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import edu.missouriwestern.csmp.gis.csp.courses.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CoursePrerequisitesRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Course_prerequisite> findcourse(String id) {
        //get the course id
        List<Course_prerequisite> findCoursePrerequisitesByCourseId = new ArrayList<Course_prerequisite>();
        findCoursePrerequisitesByCourseId.addAll(getAllCoursePrerequisites().stream().filter(t -> t.getCourseId().equals(id)).collect(Collectors.toList()));
        return findCoursePrerequisitesByCourseId;
    }

    public int size() {
        return jdbcTemplate.queryForObject("select count(*) from gis.course_prerequisites", Integer.class);
    }

    public List<Course_prerequisite> getAllCoursePrerequisites() {

        int size = jdbcTemplate.queryForObject("select count(*) from gis.course_prerequisites", Integer.class);
        List<Course_prerequisite> CoursePrerequisites = new ArrayList<Course_prerequisite>();

        for (int i = 0; i < size; i++) {
            //get the course id
            String prerequisiteId = jdbcTemplate.queryForObject("select PrerequisiteId from gis.course_prerequisites limit " + i + ",1", String.class);
            //get the course name
            String courseId = jdbcTemplate.queryForObject("select CourseId from gis.course_prerequisites limit " + i + ",1", String.class);
            //get the credit
            int setNum = jdbcTemplate.queryForObject("select SetNumber from gis.course_prerequisites limit " + i + ",1", Integer.class);
            Course_prerequisite newCoursePrerequisite = new Course_prerequisite(prerequisiteId,courseId,setNum);
            CoursePrerequisites.add(newCoursePrerequisite);

        }
        return CoursePrerequisites;
    }
}
