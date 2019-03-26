package edu.missouriwestern.csmp.gis.csp.course_prerequisites_wei;


//create an object for student
public class Course_prerequisite_wei {
    private String courseId;
    private String name;
    private int  setNum;

    public Course_prerequisite_wei(String courseId, String name,int setNum) {
        this.courseId = courseId;
        this.name=name;
        this.setNum = setNum;
    }


    public Course_prerequisite_wei() {
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

    public int getSetNum() {
        return setNum;
    }

    public void setSetNum(int setNum) {
        this.setNum = setNum;
    }
}