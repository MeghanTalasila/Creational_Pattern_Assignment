package InputReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class InputJSON implements IReader{
	Student student;
    GradeBook gradeBook = new GradeBook();

	public GradeBook readInputGradeBook(String filePath) throws IOException, ParseException {
		List<String> categoryList = new ArrayList<>(); 
        Map<String, String> gradeItem = new HashMap<>();
        JSONParser parser = new JSONParser();	
        Object obj = null;
        try{
           obj = parser.parse(new FileReader(filePath));
        } catch(Exception e){
        	System.out.println("File Not Found");
        	return gradeBook;
        }
        JSONArray jsonArray;
        JSONObject jsonObject = null;
        if (obj instanceof JSONArray) {
        	jsonArray = (JSONArray) obj;  
        	jsonObject=(JSONObject) jsonArray.get(0);
        } else if (obj instanceof JSONObject) {
            jsonObject = (JSONObject)obj;
        }
        

        JSONObject gradeBookObject = returnJSONObject(jsonObject, "GradeBook");
        
        JSONObject gradingSchemaObject = returnJSONObject(gradeBookObject, "GradingSchema");

        JSONArray gradeItemArray;
        JSONObject gradeItemObject = null;
        if(gradingSchemaObject.get("GradeItem") instanceof JSONArray){
        	gradeItemArray = (JSONArray) gradingSchemaObject.get("GradeItem");
        	for(int i=0; i<gradeItemArray.size();i++){
    	        gradeItemObject=(JSONObject) gradeItemArray.get(i);
    	        String category = (String) gradeItemObject.get("Category");
    	        String percentage = (String) gradeItemObject.get("Percentage");
    	        categoryList.add(category);
    	        gradeItem.put(category, percentage);
            }        
        } else if(gradingSchemaObject.get("GradeItem") instanceof JSONObject){
        	gradeItemObject = (JSONObject) gradingSchemaObject.get("GradeItem");
        	String category = (String) gradeItemObject.get("Category");
	        String percentage = (String) gradeItemObject.get("Percentage");
	        categoryList.add(category);
	        gradeItem.put(category, percentage);
        }
                
        gradeBook.setGradeItem(gradeItem);
    	gradeBook.setCategory(categoryList);
        return gradeBook;
	}
	
	public JSONObject returnJSONObject(JSONObject value, String name){
		JSONArray tempArray;
        JSONObject tempObject = null;
        if(value.get(name) instanceof JSONArray){
        	tempArray = (JSONArray) value.get(name);
        	tempObject=(JSONObject) tempArray.get(0);
        } else if(value.get(name) instanceof JSONObject){
        	tempObject=(JSONObject) value.get(name);
        }
		
        return tempObject;
	}
	
	public List<Student> readInputStudent(String filePath) throws IOException, ParseException {
		List<Student> studentList = new ArrayList<>();
		
		JSONParser parser = new JSONParser();		
        Object obj = null;
        try{
            obj = parser.parse(new FileReader(filePath));
        } catch(Exception e){
            	System.out.println("File Not Found");
            	return new ArrayList<>();
        }
        JSONArray jsonArray;
        JSONObject jsonObject = null;
        if (obj instanceof JSONArray) {
        	jsonArray = (JSONArray) obj;  
        	jsonObject=(JSONObject) jsonArray.get(0);
        } else if (obj instanceof JSONObject) {
            jsonObject = (JSONObject)obj;
        }
        
        JSONObject gradeBookObject = returnJSONObject(jsonObject, "GradeBook");
	    String subject = (String) gradeBookObject.get("-class");
        JSONObject gradesObject = returnJSONObject(gradeBookObject, "Grades");

        JSONArray studentArray;
        JSONObject studentObject;
        if(gradesObject.get("Student") instanceof JSONArray){
        	studentArray = (JSONArray) gradesObject.get("Student");
        	 for(int i=0; i<studentArray.size(); i++){
      	        studentObject = (JSONObject) studentArray.get(i);
        		student = createStudentList(studentObject, subject);
    	        studentList.add(student);
             } 
        } else if(gradesObject.get("Student") instanceof JSONObject){
           studentObject = (JSONObject) gradesObject.get("Student");
           student = createStudentList(studentObject, subject);
	       studentList.add(student);
        }
               
       return studentList;
	}
	
	public List<String> CreateSchemaJSON(String filePath) throws FileNotFoundException, IOException, ParseException{

		List<String> schema = new ArrayList<>();
		JSONParser parser = new JSONParser();		
		Object obj = null;
        try{
            obj = parser.parse(new FileReader(filePath));
        } catch(Exception e){
            	System.out.println("File Not Found");
            	return new ArrayList<>();
        }
        JSONArray jsonArray;
        JSONObject jsonObject = null;
        if (obj instanceof JSONArray) {
        	jsonArray = (JSONArray) obj;  
        	jsonObject=(JSONObject) jsonArray.get(0);
        } else if (obj instanceof JSONObject) {
            jsonObject = (JSONObject)obj;
        }
        
        JSONObject gradeBookObject = returnJSONObject(jsonObject, "GradeBook");        
        JSONObject gradesObject = returnJSONObject(gradeBookObject, "Grades");
	
        JSONArray studentArray;
        JSONObject studentObject = null;
        if(gradesObject.get("Student") instanceof JSONArray){
        	studentArray = (JSONArray) gradesObject.get("Student");
        	studentObject = (JSONObject) studentArray.get(0);
        } else if(gradesObject.get("Student") instanceof JSONObject){
           studentObject = (JSONObject) gradesObject.get("Student");
        }
        
        schema.add("Name");
        schema.add("ID");
        
        JSONArray assignedWorkArray;
        JSONObject assignedWorkObject = null;
        if(studentObject.get("AssignedWork") instanceof JSONArray){
        	assignedWorkArray = (JSONArray) studentObject.get("AssignedWork");
        	for(int j=0; j<assignedWorkArray.size(); j++){
    		    assignedWorkObject = (JSONObject) assignedWorkArray.get(j);
    		    schema = createSchema(assignedWorkObject, schema);
    	    }               	
        } else if(studentObject.get("AssignedWork") instanceof JSONObject){
        	assignedWorkObject = (JSONObject) studentObject.get("AssignedWork");
        	schema = createSchema(assignedWorkObject, schema);
        }
              
	    schema.add("Total Percentage");
	    schema.add("Letter Grade");
		return schema;
	}
	
	public List<String> createSchema(JSONObject assignedWorkObject, List<String> schema){
		
		JSONArray gradedWorkArray;
		JSONObject gradedWorkObject = null;
		
		if(assignedWorkObject.get("GradedWork") instanceof JSONArray){
			gradedWorkArray = (JSONArray) assignedWorkObject.get("GradedWork");
        	for(int k=0; k<gradedWorkArray.size(); k++){
        		gradedWorkObject = (JSONObject) gradedWorkArray.get(k);
     		    schema.add((String)gradedWorkObject.get("Name"));
    	    }               	
        } else if(assignedWorkObject.get("GradedWork") instanceof JSONObject){
        	gradedWorkObject = (JSONObject) assignedWorkObject.get("GradedWork");
 		    schema.add((String)gradedWorkObject.get("Name"));
        }
		
	    return schema;
	}
	
	public Student createStudentList(JSONObject studentObject, String subject){
		student = new Student();
		Map<String, List<String>> assignedWorkMap = new HashMap<>();

        student.setSubject(subject);
	    student.setName((String) studentObject.get("Name"));
	    student.setId((String) studentObject.get("ID"));
	       
	    JSONArray assignedWorkArray;
	    JSONObject assignedWorkObject = null;
        if(studentObject.get("AssignedWork") instanceof JSONArray){
        	assignedWorkArray = (JSONArray) studentObject.get("AssignedWork");
        	 for(int j=0; j<assignedWorkArray.size(); j++){
        		 assignedWorkObject = (JSONObject) assignedWorkArray.get(j);
        		 assignedWorkMap = createStudentMarksList(assignedWorkObject, assignedWorkMap);
        	 }       
        	 student.setAssignedwork(assignedWorkMap);
        } else if(studentObject.get("AssignedWork") instanceof JSONObject){
        	assignedWorkObject = (JSONObject) studentObject.get("AssignedWork");
        	assignedWorkMap = createStudentMarksList(assignedWorkObject, assignedWorkMap);
       	    student.setAssignedwork(assignedWorkMap);
        }
        
	    return student;
	}
	
	public Map<String, List<String>> createStudentMarksList(JSONObject assignedWorkObject, Map<String, List<String>>  assignedWorkMap){
		 List<String> gradedWorkList;
	     String category = (String) assignedWorkObject.get("-category");
	     JSONArray gradedWorkArray;
	     JSONObject gradedWorkObject;
	     if(assignedWorkObject.get("GradedWork") instanceof JSONArray){
	    	 gradedWorkArray = (JSONArray) assignedWorkObject.get("GradedWork");
		     gradedWorkList = new ArrayList<>();
	         for(int k=0; k<gradedWorkArray.size(); k++){
	        	   gradedWorkObject = (JSONObject) gradedWorkArray.get(k);
			       gradedWorkList.add((String)gradedWorkObject.get("Grade"));
		       }  
		       assignedWorkMap.put(category, gradedWorkList);
	      } else if(assignedWorkObject.get("GradedWork") instanceof JSONObject){
			  gradedWorkList = new ArrayList<>();
	    	  gradedWorkObject = (JSONObject) assignedWorkObject.get("GradedWork");
	    	  gradedWorkList.add((String)gradedWorkObject.get("Grade"));
		      assignedWorkMap.put(category, gradedWorkList);
	      }	        
	      return assignedWorkMap;
	}
}
