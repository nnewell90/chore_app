package edu.missouriwestern.csmp.gis.csp.program_courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProgramCoursesRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Program_course> findProgramCoursesByCourseId(String id) {
        //get the course id
        List<Program_course> findProgramCourses = new ArrayList<Program_course>();
        findProgramCourses.addAll(getAllProgramCourses().stream().filter(t -> t.getCourseId().equals(id)).collect(Collectors.toList()));
        return findProgramCourses;
    }

    public int size() {
        return jdbcTemplate.queryForObject("select count(*) from gis.program_courses", Integer.class);
    }

    public List<Program_course> getAllProgramCourses() {

        int size = jdbcTemplate.queryForObject("select count(*) from gis.program_courses", Integer.class);
        List<Program_course> prerequisitesSets = new ArrayList<Program_course>();

        for (int i = 0; i < size; i++) {
            //get the program Course id
            String programCourseId = jdbcTemplate.queryForObject("select ProgramCourseId from gis.program_courses limit " + i + ",1", String.class);
            //get the program id
            String programId = jdbcTemplate.queryForObject("select ProgramId from gis.program_courses limit " + i + ",1", String.class);
            //get the course id
            String courseId = jdbcTemplate.queryForObject("select CourseId from gis.program_courses limit " + i + ",1", String.class);
            Program_course newPrerequisitesSet = new Program_course(programCourseId,programId,courseId);
            prerequisitesSets.add(newPrerequisitesSet);

        }
        return prerequisitesSets;
    }
}
