package edu.missouriwestern.csmp.gis.csp.prerequisite_sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class PrerequisiteSetsRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Prerequisite_set> findcourse(String id) {
        //get the course id
        List<Prerequisite_set> findCoursePrerequisitesByCourseId = new ArrayList<Prerequisite_set>();
        findCoursePrerequisitesByCourseId.addAll(getAllCoursePrerequisites().stream().filter(t -> t.getCourseId().equals(id)).collect(Collectors.toList()));
        return findCoursePrerequisitesByCourseId;
    }

    public int size() {
        return jdbcTemplate.queryForObject("select count(*) from gis.prerequisite_sets", Integer.class);
    }

    public List<Prerequisite_set> getAllCoursePrerequisites() {

        int size = jdbcTemplate.queryForObject("select count(*) from gis.prerequisite_sets", Integer.class);
        List<Prerequisite_set> prerequisitesSets = new ArrayList<Prerequisite_set>();

        for (int i = 0; i < size; i++) {
            //get the course id
            String prerequisiteSetId = jdbcTemplate.queryForObject("select PrerequisiteSetId from gis.prerequisite_sets limit " + i + ",1", String.class);
            //get the course name
            String courseId = jdbcTemplate.queryForObject("select CourseId from gis.prerequisite_sets limit " + i + ",1", String.class);
            //get the credit
            int setNum = jdbcTemplate.queryForObject("select SetNumber from gis.prerequisite_sets limit " + i + ",1", Integer.class);
            Prerequisite_set newPrerequisitesSet = new Prerequisite_set(prerequisiteSetId,setNum,courseId);
            prerequisitesSets.add(newPrerequisitesSet);

        }
        return prerequisitesSets;
    }
}
