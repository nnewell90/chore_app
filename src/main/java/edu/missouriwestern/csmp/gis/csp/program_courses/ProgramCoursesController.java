package edu.missouriwestern.csmp.gis.csp.program_courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/programcourses")
public class ProgramCoursesController {
    @GetMapping
    public String check() {
        return "it works on program courses";
    }

    @Autowired
    ProgramCoursesRepository programCoursesRepository;

    @GetMapping(path = "/size")
    public int getSize() {
        return programCoursesRepository.size();
    }

    @GetMapping(path = "/{id}")
    public List<Program_course> findProgramCoursesByCourseId(@PathVariable String id) {
        return programCoursesRepository.findProgramCoursesByCourseId(id);
    }

    @GetMapping(path = "/all")
    public List<Program_course> getAllCourses() {
        return programCoursesRepository.getAllProgramCourses();
    }
}
