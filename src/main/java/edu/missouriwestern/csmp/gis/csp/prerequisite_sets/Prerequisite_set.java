package edu.missouriwestern.csmp.gis.csp.prerequisite_sets;


//create an object for student
public class Prerequisite_set {
    private String prerequisiteSetId;
    private int  setNum;
    private String courseId;

    public Prerequisite_set(String prerequisiteSetId, int setNum,String courseId) {
        this.courseId = courseId;
        this.prerequisiteSetId = prerequisiteSetId;
        this.setNum = setNum;
    }


    public Prerequisite_set() {
    }

    public String getPrerequisiteSetId() {
        return prerequisiteSetId;
    }

    public void setPrerequisiteSetId(String prerequisiteSetId) {
        this.prerequisiteSetId = prerequisiteSetId;
    }

    public int getSetNum() {
        return setNum;
    }

    public void setSetNum(int setNum) {
        this.setNum = setNum;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}