package edu.missouriwestern.csmp.gis.csp.course_prerequisites;


//create an object for student
public class Course_prerequisite {
    private String prerequisiteId;
    private String courseId;
    private int  setNum;

    public Course_prerequisite(String prerequisiteId, String courseId, int setNum) {
        this.courseId = courseId;
        this.prerequisiteId = prerequisiteId;
        this.setNum = setNum;
    }


    public Course_prerequisite() {
    }

    public String getPrerequisiteId() {
        return prerequisiteId;
    }

    public void setPrerequisiteId(String prerequisiteId) {
        this.prerequisiteId = prerequisiteId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getSetNum() {
        return setNum;
    }

    public void setSetNum(int setNum) {
        this.setNum = setNum;
    }
}