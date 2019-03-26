package edu.missouriwestern.csmp.gis.csp.course_prerequisites_wei;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/coursePrerequisiteswei")
public class CoursePrerequisitesControllerWei {
    @GetMapping
    public String check() {
        return "it works on course prerequisites_wei";
    }

    @Autowired
    CoursePrerequisitesRepositoryWei coursePrerequisitesRepositoryWei;

    @GetMapping(path = "/size")
    public int getSize() {
        return coursePrerequisitesRepositoryWei.size();
    }

    @GetMapping(path = "/{id}")
    public List<Course_prerequisite_wei> findcourse(@PathVariable String id) {
        return coursePrerequisitesRepositoryWei.findcourse(id);
    }

    @GetMapping(path = "/all")
    public List<Course_prerequisite_wei> getAllCourses() {
        return coursePrerequisitesRepositoryWei.getAllCoursePrerequisites();
    }
}
