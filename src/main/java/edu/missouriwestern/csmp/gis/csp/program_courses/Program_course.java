package edu.missouriwestern.csmp.gis.csp.program_courses;


//create an object for student
public class Program_course {
    private String programCourseId;
    private String  programId;
    private String courseId;

    public Program_course(String programCourseId, String programId, String courseId) {
        this.courseId = courseId;
        this.programCourseId = programCourseId;
        this.programId = programId;
    }


    public Program_course() {
    }

    public String getProgramCourseId() {
        return programCourseId;
    }

    public void setProgramCourseId(String programCourseId) {
        this.programCourseId = programCourseId;
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        programId = programId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}