package edu.missouriwestern.csmp.gis.csp.courses;


import org.json.simple.JSONArray;

//create an object for student
public class Course {
    private String courseId;
    private String name;
    private int credits;
    private String title;
    private String department;
    private int semesterNum;

    public Course(String courseId, String name, int credits, String title, String department,int semesterNum) {
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
        this.title = title;
        this.department = department;
        this.semesterNum=semesterNum;
    }


    public Course() {
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public int getSemesterNum() {
        return semesterNum;
    }

    public void setSemesterNum(int semesterNum) {
        this.semesterNum = semesterNum;
    }
}