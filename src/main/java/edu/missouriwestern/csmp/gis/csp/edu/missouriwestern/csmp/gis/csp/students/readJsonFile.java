package edu.missouriwestern.csmp.gis.csp.edu.missouriwestern.csmp.gis.csp.students;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class readJsonFile {
    public static Object newObject(){
        JSONParser parser = new JSONParser();
        Object obj=null;
        try
        {
             obj=parser.parse(new FileReader("D:\\springbootProjects\\cspprojectUpdated\\src\\main\\resources\\static\\students.json"));
    }
        catch (
                FileNotFoundException e){e.printStackTrace();}
        catch (
                IOException e){e.printStackTrace();}
        catch (
                ParseException e){e.printStackTrace();}
        catch (Exception e){e.printStackTrace();}
        return obj;
}
}
