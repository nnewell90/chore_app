package edu.missouriwestern.csmp.gis.csp.edu.missouriwestern.csmp.gis.csp.students;


import org.json.simple.JSONArray;

//create an object for student
public class Student extends JSONArray {
    private String id;
    private String name;
    private String username;
    private String email;
    private JSONArray schedule;

    public Student(String id,JSONArray schedule) {
        this.schedule = schedule;
        this.id=id;
    }


    public Student() {
    }
    //getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public JSONArray getSchedule() {
        return schedule;
    }

    public void setSchedule(JSONArray schedule) {
        this.schedule = schedule;
    }

//    @Override
//    public Object[] toArray(IntFunction generator) {
//        return new Object[0];
//    }
}
