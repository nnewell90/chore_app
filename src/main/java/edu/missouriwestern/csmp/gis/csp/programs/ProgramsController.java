package edu.missouriwestern.csmp.gis.csp.programs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/programs")
public class ProgramsController {
    @GetMapping
    public String check() {
        return "it works on programs";
    }

    @Autowired
    ProgramsRepository programsRepository;

    @GetMapping(path = "/size")
    public int getSize() {
        return programsRepository.size();
    }

    @GetMapping(path = "/{id}")
    public List<Program> findProgramsByCourseId(@PathVariable String id) {
        return programsRepository.findProgramsByProgramId(id);
    }

    @GetMapping(path = "/all")
    public List<Program> getAllPrograms() {
        return programsRepository.getAllPrograms();
    }
}
