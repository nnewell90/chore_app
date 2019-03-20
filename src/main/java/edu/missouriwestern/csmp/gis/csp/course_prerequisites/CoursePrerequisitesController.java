package edu.missouriwestern.csmp.gis.csp.course_prerequisites;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/coursePrerequisites")
public class CoursePrerequisitesController {
    @GetMapping
    public String check() {
        return "it works on course prerequisites";
    }

    @Autowired
    CoursePrerequisitesRepository coursePrerequisitesRepository;

    @GetMapping(path = "/size")
    public int getSize() {
        return coursePrerequisitesRepository.size();
    }

    @GetMapping(path = "/{id}")
    public List<Course_prerequisite> findcourse(@PathVariable String id) {
        return coursePrerequisitesRepository.findcourse(id);
    }

    @GetMapping(path = "/all")
    public List<Course_prerequisite> getAllCourses() {
        return coursePrerequisitesRepository.getAllCoursePrerequisites();
    }
}
