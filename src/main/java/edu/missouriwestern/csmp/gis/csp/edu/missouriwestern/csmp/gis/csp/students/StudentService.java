package edu.missouriwestern.csmp.gis.csp.edu.missouriwestern.csmp.gis.csp.students;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    //Put the Objects into an JSON array list
    public ArrayList allStudents() {
        List<Student> students = new ArrayList<>();
        readJsonFile object = new readJsonFile();
        Object obj = object.newObject();
        JSONArray jsonArray = (JSONArray) obj;
        //loop thought all the students
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject studentObject = (JSONObject) jsonArray.get(i);
            String id = (String) studentObject.get("id");
            String name = (String) studentObject.get("name");
            String email = (String) studentObject.get("email");
            String username = (String) studentObject.get("username");
            JSONArray semesterArray = (JSONArray) studentObject.get("semester");
            //create a new student every time
            Student newStudent = new Student(id, name, username, email, semesterArray);
            //put the new student into the array list
            students.add(newStudent);
        }
        //convert the JSONArray into ArrayList
        return (ArrayList) students;
    }

    private ArrayList<Student> students = allStudents();
    //return all the students
    public List<Student> getAllStudents() {
        return students;
    }
    //return one of the student with specific ID
    public Student getStudent(String id) {
        return students.stream().filter(t -> t.getId().equals(id)).findFirst().get();
    }
    //update the information about one of the student with specific ID
    public void updateStudent(String id, Student student) {
        for (int i = 0; i < students.size(); i++) {
            Student t = students.get(i);
            if (t.getId().equals(id)) {
                students.set(i, student);
                return;
            }
        }
    }
    //delete a student with specific ID
    public void deleteStudent(String id) {
        students.removeIf(t -> t.getId().equals(id));
    }
}
