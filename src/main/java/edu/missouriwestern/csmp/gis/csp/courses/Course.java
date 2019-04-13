package edu.missouriwestern.csmp.gis.csp.courses;


import edu.missouriwestern.csmp.gis.csp.prerequisite_sets.Prerequisite_set;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.List;

//create an object for student
public class Course {
    private int courseId;
    private String name;
    private int credits;
    private String title;
    private String department;
    private int SemesterTypeId;
    private List<Prerequisite_set> prerequisiteSetList;

    public Course(int courseId, String name, int credits, String title, String department,int semesterTypeId) {
        this.courseId = courseId;
        this.name = name;
        this.credits = credits;
        this.title = title;
        this.department = department;
        this.SemesterTypeId = semesterTypeId;
    }


    public Course() {
        this.prerequisiteSetList = new ArrayList<>();
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
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

    public int getSemesterTypeId() {
        return this.SemesterTypeId;
    }

    public void setSemesterTypeId(int semesterTypeId) {
        this.SemesterTypeId = semesterTypeId;
    }

    public void setPrerequisiteSetList(List<Prerequisite_set> prerequisiteSetList) {this.prerequisiteSetList = prerequisiteSetList;}

    public void addToPrerequisiteList(Prerequisite_set prerequisite_set) {
        this.prerequisiteSetList.add(prerequisite_set);
    }

    public List<Prerequisite_set> getPrerequisiteSetList() { return prerequisiteSetList; }
}