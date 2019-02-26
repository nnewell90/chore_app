package edu.missouriwestern.csmp.gis.csp.edu.missouriwestern.csmp.gis.csp.students;


import org.json.simple.JSONArray;

public class Student {
    private String id;
    private String name;
    private String username;
    private String email;
    private JSONArray schdule;

    @Override
    public String toString() {
        return "schdule=" + schdule;
    }

    public Student(String id, String name, String username, String email, JSONArray schdule) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.schdule = schdule;
    }
    public Student(String id, String name, String username, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
    }

    public Student() {
    }

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

    public JSONArray getSchdule() {
        return schdule;
    }

    public void setSchdule(JSONArray schdule) {
        this.schdule = schdule;
    }
}
