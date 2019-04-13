package edu.missouriwestern.csmp.gis.csp.semester_type;

public class Semester_Type {
    private int semesterTypeId;
    private String description;

    public Semester_Type(int semesterTypeId, String description) {
        this.semesterTypeId = semesterTypeId;
        this.description = description;
    }

    public  Semester_Type(){};

    public void setSemesterTypeId(int semesterTypeId) {
        this.semesterTypeId = semesterTypeId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSemesterTypeId() {
        return semesterTypeId;
    }

    public String getDescription() {
        return description;
    }
}
