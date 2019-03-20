package edu.missouriwestern.csmp.gis.csp.course_sections;


import java.time.LocalTime;
import java.util.Date;

//create an object for student
public class Course_section {
    private String courseSectionId;
    private String courseId;
    private String section;
    private String type;
    private String dayesOffered;
    private String timeOffered;
    private String room;
    private String instructor;
    private String semester;
    private int credits;
    private String term;
    private String name;
    private String beginDate;
    private String enddate;

    public Course_section(String courseSectionId, String courseId, String section, String type,
                          String dayesOffered, String timeOffered, String room, String instructor,
                          String semester, int credits, String term, String name, String beginDate, String enddate) {
        this.courseSectionId = courseSectionId;
        this.courseId = courseId;
        this.section = section;
        this.type = type;
        this.dayesOffered = dayesOffered;
        this.timeOffered = timeOffered;
        this.room = room;
        this.instructor = instructor;
        this.semester = semester;
        this.credits = credits;
        this.term = term;
        this.name = name;
        this.beginDate = beginDate;
        this.enddate = enddate;
    }


    public Course_section() {
    }

    public String getCourseSectionId() {
        return courseSectionId;
    }

    public void setCourseSectionId(String courseSectionId) {
        this.courseSectionId = courseSectionId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDayesOffered() {
        return dayesOffered;
    }

    public void setDayesOffered(String dayesOffered) {
        this.dayesOffered = dayesOffered;
    }

    public String getTimeOffered() {
        return timeOffered;
    }

    public void setTimeOffered(String timeOffered) {
        this.timeOffered = timeOffered;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }
}