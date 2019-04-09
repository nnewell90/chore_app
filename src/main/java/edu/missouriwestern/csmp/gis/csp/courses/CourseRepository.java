package edu.missouriwestern.csmp.gis.csp.courses;

import edu.missouriwestern.csmp.gis.csp.prerequisite_sets.Prerequisite_set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public List<Course> getProgramCourses(int programId) {
        String coursesSql = "select c.Name as CourseName, c.CourseId, c.Title, c.Credits, c.Department from programs a \n" +
                "join program_courses b on a.ProgramId = b.ProgramId\n" +
                "join courses c on c.CourseId = b.CourseId\n" +
                "where a.ProgramId = ?";

        String prerequisiteSql =  "SELECT a.CourseId as CourseId, b.CourseId as PreCourseId, b.SetNumber, PrerequisiteSetId\n" +
                "        FROM gis.course_prerequisites a\n" +
                "        join gis.prerequisite_sets b on a.SetNumber = b.SetNumber";

        List<Course> courses = new ArrayList<>();
        List<Prerequisite_set> prerequisite_sets = new ArrayList<>();

        List<Map<String, Object>> courseRows = jdbcTemplate.queryForList(coursesSql, programId);
        List<Map<String, Object>> prerequisiteRows = jdbcTemplate.queryForList(prerequisiteSql);

        for(Map courseRow: courseRows) {
            Course course = new Course();
            List<Prerequisite_set> coursePrerequisites = new ArrayList<>();

            course.setCourseId(courseRow.get("CourseId").toString());
            course.setName(courseRow.get("CourseName").toString());
            course.setTitle((String)courseRow.get("Title"));
            course.setCredits((int)courseRow.get("Credits"));
            course.setDepartment((String)courseRow.get("Department"));

            for(Map prerequisiteRow: prerequisiteRows) {
                Prerequisite_set prerequisite_set = new Prerequisite_set();
                if(course.getCourseId().equals(prerequisiteRow.get("CourseId").toString())) {
                    prerequisite_set.setCourseId(prerequisiteRow.get("PreCourseId").toString());
                    prerequisite_set.setPrerequisiteSetId(prerequisiteRow.get("PrerequisiteSetId").toString());
                    coursePrerequisites.add(prerequisite_set);
                }
            }

            course.setPrerequisiteSetList(coursePrerequisites);
            courses.add(course);
        }

        return courses;
    }
}
