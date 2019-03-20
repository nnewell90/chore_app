package edu.missouriwestern.csmp.gis.csp.course_sections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/coursesection")
public class CourseSectionController {
    @GetMapping
    public String check() {
        return "it works on course section";
    }

    @Autowired
    CourseSectionRepository courseSectionRepository;

    @GetMapping(path = "/size")
    public int getSize() {
        return courseSectionRepository.size();
    }

    @GetMapping(path = "/{id}")
    public List<Course_section> findCourseSection(@PathVariable String id) {
        return courseSectionRepository.findCourseSection(id);
    }

    @GetMapping(path = "/all")
    public List<Course_section> getAllCoursesSections() {
        return courseSectionRepository.getAllCourseSections();
    }
}
