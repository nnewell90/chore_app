package edu.missouriwestern.csmp.gis.csp.course_sections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CourseSectionRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Course_section> findCourseSection(String id) {
        //get the course id
        List<Course_section> findCourseSectionsbByCourseId = new ArrayList<Course_section>();
        findCourseSectionsbByCourseId.addAll(getAllCourseSections().stream().filter(t -> t.getCourseId().equals(id)).collect(Collectors.toList()));
        return findCourseSectionsbByCourseId;
    }

    public int size() {
        return jdbcTemplate.queryForObject("select count(*) from gis.course_sections", Integer.class);
    }

    public List<Course_section> getAllCourseSections() {

        int size = jdbcTemplate.queryForObject("select count(*) from gis.course_sections", Integer.class);
        List<Course_section> courseScetions = new ArrayList<Course_section>();

        for (int i = 0; i < size; i++) {
            //get the course section id
            String courseSectionId = jdbcTemplate.queryForObject("SELECT CourseSectionId FROM gis.course_sections limit " + i + ",1", String.class);
            //get the course id
            String courseId = jdbcTemplate.queryForObject("select CourseId from gis.course_sections limit " + i + ",1", String.class);
            //get the section
            String section = jdbcTemplate.queryForObject("select Section from gis.course_sections limit " + i + ",1", String.class);
            //get the type
            String type = jdbcTemplate.queryForObject("select Type from gis.course_sections limit " + i + ",1", String.class);
            //get the days offered
            String daysOffered = jdbcTemplate.queryForObject("select DaysOffered from gis.course_sections limit " + i + ",1", String.class);
            //get times offered
            String timeOffered = jdbcTemplate.queryForObject("select TimeOffered from gis.course_sections limit " + i + ",1", String.class);
            //get the room
            String room = jdbcTemplate.queryForObject("select Room from gis.course_sections limit " + i + ",1", String.class);
            //get instructor
            String instructor = jdbcTemplate.queryForObject("select Intructor from gis.course_sections limit " + i + ",1", String.class);
            //get semester
            String semester = jdbcTemplate.queryForObject("select Semester from gis.course_sections limit " + i + ",1", String.class);
            //get credit
            int credit = jdbcTemplate.queryForObject("select Credits from gis.course_sections limit " + i + ",1", Integer.class);
            //get term
            String term = jdbcTemplate.queryForObject("select Term from gis.course_sections limit " + i + ",1", String.class);
            //get name
            String name = jdbcTemplate.queryForObject("select Name from gis.course_sections limit " + i + ",1", String.class);
            //get begin date
            String beginDate = jdbcTemplate.queryForObject("select BeginDate from gis.course_sections limit " + i + ",1", String.class);
            //get end date
            String endDate = jdbcTemplate.queryForObject("select EndDate from gis.course_sections limit " + i + ",1", String.class);

            Course_section newCourseSection = new Course_section(courseSectionId, courseId, section, type, daysOffered, timeOffered, room, instructor, semester, credit, term, name, beginDate, endDate);
            courseScetions.add(newCourseSection);

        }
        return courseScetions;
    }
}
