package InputReader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class InputXml implements IReader{

	Student student;
	GradeBook gradeBook = new GradeBook();
	List<Student> studentlist = new ArrayList<>();
	
	public GradeBook readInputGradeBook(String filePath) throws ParserConfigurationException, SAXException, IOException{
		Map<String, String> gradeItemMap = new HashMap<>();
		List<String> category = new ArrayList<>();
		
		NodeList gradingSchemaList = readXMLFile("GradingSchema", filePath);		
		if(null == gradingSchemaList){
			return gradeBook;
		}
		for (int i = 0; i < gradingSchemaList.getLength(); i++) {
			Node gradingSchemaNode = gradingSchemaList.item(i);							
			if (gradingSchemaNode.getNodeType() == Node.ELEMENT_NODE) {
				Element gradingSchemaElement = (Element) gradingSchemaNode;
				
				NodeList gradeItemList = gradingSchemaElement.getElementsByTagName("GradeItem");
				gradeItemMap = new HashMap<>();
				for (int j = 0; j < gradeItemList.getLength(); j++) {
					Node gradeItemNode = gradeItemList.item(j);
					if (gradeItemNode.getNodeType() == Node.ELEMENT_NODE) {
						Element gradeItemElement = (Element) gradeItemNode;
						
						category.add(gradeItemElement.getElementsByTagName("Category").item(0).getTextContent());					    
						gradeItemMap.put(gradeItemElement.getElementsByTagName("Category").item(0).getTextContent(),
						gradeItemElement.getElementsByTagName("Percentage").item(0).getTextContent());
					}
				}
			}
		}
		gradeBook.setCategory(category);
		gradeBook.setGradeItem(gradeItemMap);
		return gradeBook;
	}

	public List<Student> readInputStudent(String filePath) throws ParserConfigurationException, SAXException, IOException{
		
		Map<String, List<String>> assignedWorkMap;
		List<String> gradedWorkList;
		String category;
			
		File fXmlFile = new File(filePath);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = null;
        try{
        	doc = dBuilder.parse(fXmlFile);
        } catch(Exception e){
            	System.out.println("File Not Found");
            	return new ArrayList<>();
        }
		doc.getDocumentElement().normalize();	
		
		Node gradeBookNode = doc.getElementsByTagName("GradeBook").item(0);
		String subject = null;
		if (gradeBookNode.getNodeType() == Node.ELEMENT_NODE) {
			Element gradeBookElement = (Element) gradeBookNode;
			subject = gradeBookElement.getAttribute("class");
		}
		NodeList studentList = doc.getElementsByTagName("Student");	
		for (int i = 0; i < studentList.getLength(); i++) {
			student = new Student();
			Node studentNode = studentList.item(i);							
			if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
				Element studentElement = (Element) studentNode;
				
				student.setSubject(subject);
				student.setName(studentElement.getElementsByTagName("Name").item(0).getTextContent());
				student.setId(studentElement.getElementsByTagName("ID").item(0).getTextContent());
				
				NodeList AssignedWorkList = studentElement.getElementsByTagName("AssignedWork");
				assignedWorkMap = new HashMap<>();
				for (int j = 0; j < AssignedWorkList.getLength(); j++) {
					Node assignedWorkNode = AssignedWorkList.item(j);
					if (assignedWorkNode.getNodeType() == Node.ELEMENT_NODE) {
						Element assignedWorkElement = (Element) assignedWorkNode;
						
						category = assignedWorkElement.getAttribute("category");					    
						NodeList gradedWorkNodeList = assignedWorkElement.getElementsByTagName("GradedWork");
						gradedWorkList = new ArrayList<>();
						for (int k = 0; k < gradedWorkNodeList.getLength(); k++) {
							Node gradedWorkNode = gradedWorkNodeList.item(k);
							
							if (gradedWorkNode.getNodeType() == Node.ELEMENT_NODE) {
								Element gradedWorkElement = (Element) gradedWorkNode;
								gradedWorkList.add(gradedWorkElement.getElementsByTagName("Grade").item(0).getTextContent());
							}
						}
						assignedWorkMap.put(category, gradedWorkList);    

					}
				}
				student.setAssignedwork(assignedWorkMap);
			}
			studentlist.add(student);
		}
		
		return studentlist;
	}
	
	private NodeList readXMLFile(String elementName,String filePath) throws ParserConfigurationException, SAXException, IOException{
		File fXmlFile = new File(filePath);
		NodeList nodeList = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = null;
        try{
        	doc = dBuilder.parse(fXmlFile);
        } catch(Exception e){
            	System.out.println("File Not Found");
            	return nodeList;
        }
		doc.getDocumentElement().normalize();				
		
		nodeList = doc.getElementsByTagName(elementName);	
		return nodeList;
	}
	
	public List<String> CreateSchemaJSON(String filePath) throws ParserConfigurationException, SAXException, IOException {

		List<String> schema = new ArrayList<>();
			
		NodeList studentList = readXMLFile("Student", filePath);
		if(null == studentList){
			return new ArrayList<>();
		}
		Node studentNode = studentList.item(0);							
		if (studentNode.getNodeType() == Node.ELEMENT_NODE) {
			Element studentElement = (Element) studentNode;
			schema.add("Name");
			schema.add("ID");
			NodeList AssignedWorkList = studentElement.getElementsByTagName("AssignedWork");
			for (int j = 0; j < AssignedWorkList.getLength(); j++) {
				Node assignedWorkNode = AssignedWorkList.item(j);
				if (assignedWorkNode.getNodeType() == Node.ELEMENT_NODE) {
					Element assignedWorkElement = (Element) assignedWorkNode;
					NodeList gradedWorkNodeList = assignedWorkElement.getElementsByTagName("GradedWork");
					for (int k = 0; k < gradedWorkNodeList.getLength(); k++) {
						Node gradedWorkNode = gradedWorkNodeList.item(k);
						if (gradedWorkNode.getNodeType() == Node.ELEMENT_NODE) {
							Element gradedWorkElement = (Element) gradedWorkNode;
							schema.add(gradedWorkElement.getElementsByTagName("Name").item(0).getTextContent());
						}
					}
				}
			}
		}		         
	    schema.add("Total Percentage");
	    schema.add("Letter Grade");
		return schema;
	}

}
