package edu.missouriwestern.csmp.gis.csp.edu.missouriwestern.csmp.gis.csp.students;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {

    public ArrayList allStudents()
    {
        List<Student> students = new ArrayList<>();
        readJsonFile object = new readJsonFile();
        Object obj = object.newObject();
        JSONArray jsonArray=(JSONArray) obj;
        for (int i = 0; i <jsonArray.size(); i++) {
            JSONObject studentObject = (JSONObject) jsonArray.get(i);
            String id = (String) studentObject.get("id");
            String name = (String) studentObject.get("name");
            String email = (String) studentObject.get("email");
            String username = (String) studentObject.get("username");
            JSONArray semesterArray = (JSONArray) studentObject.get("semester");
            Student newStudent = new Student(id, name, username, email, semesterArray);
            students.add(newStudent);
        }
        return (ArrayList) students;
    }
    private ArrayList<Student> students=allStudents();

    public List<Student> getAllStudents(){
        return students;
    }
    public Student getStudent(String id){
        return students.stream().filter(t->t.getId().equals(id)).findFirst().get();
    }
}
