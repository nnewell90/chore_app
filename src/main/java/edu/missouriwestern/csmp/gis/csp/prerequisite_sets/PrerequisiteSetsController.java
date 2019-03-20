package edu.missouriwestern.csmp.gis.csp.prerequisite_sets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/prerequisitessets")
public class PrerequisiteSetsController {
    @GetMapping
    public String check() {
        return "it works on prerequisites sets";
    }

    @Autowired
    PrerequisiteSetsRepository prerequisiteSetsRepository;

    @GetMapping(path = "/size")
    public int getSize() {
        return prerequisiteSetsRepository.size();
    }

    @GetMapping(path = "/{id}")
    public List<Prerequisite_set> findcourse(@PathVariable String id) {
        return prerequisiteSetsRepository.findcourse(id);
    }

    @GetMapping(path = "/all")
    public List<Prerequisite_set> getAllCourses() {
        return prerequisiteSetsRepository.getAllCoursePrerequisites();
    }
}
