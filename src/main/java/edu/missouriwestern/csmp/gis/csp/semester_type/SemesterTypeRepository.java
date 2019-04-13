package edu.missouriwestern.csmp.gis.csp.semester_type;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class SemesterTypeRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Semester_Type> getAllSemesterTypes() {
     String sql = "Select * " +
             "from gis.semester_type";

     List<Semester_Type> semester_types = new ArrayList<>();

     List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

     for (Map row: rows){
         Semester_Type semester_type = new Semester_Type();
         semester_type.setSemesterTypeId((int) row.get("SemesterTypeId"));
         semester_type.setDescription((String)row.get("Description"));
         semester_types.add(semester_type);
     }

     return  semester_types;
    }
}
