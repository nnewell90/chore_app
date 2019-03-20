package edu.missouriwestern.csmp.gis.csp.programs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ProgramsRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Program> findProgramsByProgramId(String id) {
        //get the course id
        List<Program> findPrograms = new ArrayList<Program>();
        findPrograms.addAll(getAllPrograms().stream().filter(t -> t.getProgramId().equals(id)).collect(Collectors.toList()));
        return findPrograms;
    }

    public int size() {
        return jdbcTemplate.queryForObject("select count(*) from gis.programs", Integer.class);
    }

    public List<Program> getAllPrograms() {

        int size = jdbcTemplate.queryForObject("select count(*) from gis.programs", Integer.class);
        List<Program> programs = new ArrayList<Program>();

        for (int i = 0; i < size; i++) {
            //get the program Course id
            String programId = jdbcTemplate.queryForObject("select ProgramId from gis.programs limit " + i + ",1", String.class);
            //get the program id
            String name = jdbcTemplate.queryForObject("select Name from gis.programs limit " + i + ",1", String.class);
            //get the course id
            String degreeType = jdbcTemplate.queryForObject("select DegreeType from gis.programs limit " + i + ",1", String.class);
            //
            String concentration = jdbcTemplate.queryForObject("select Concentration from gis.programs limit " + i + ",1", String.class);

            Program newProgram = new Program(programId,name,degreeType,concentration);
            programs.add(newProgram);

        }
        return programs;
    }
}
