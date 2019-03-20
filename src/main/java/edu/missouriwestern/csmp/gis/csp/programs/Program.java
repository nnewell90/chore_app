package edu.missouriwestern.csmp.gis.csp.programs;


//create an object for student
public class Program {
    private String  programId;
    private String name;
    private String degreeType;
    private String concentration;


    public Program(String programId, String name, String degreeType,String concentration) {
        this.programId = programId;
        this.name = name;
        this.degreeType = degreeType;
        this.concentration = concentration;

    }


    public Program() {
    }

    public String getProgramId() {
        return programId;
    }

    public void setProgramId(String programId) {
        this.programId = programId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDegreeType() {
        return degreeType;
    }

    public void setDegreeType(String degreeType) {
        this.degreeType = degreeType;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }
}