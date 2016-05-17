package OutputWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import Grade.CalculateLetterGrade;
import Grade.GradeFactory;
import Grade.IGrader;
import InputReader.GradeBook;
import InputReader.Student;

public class OutputXML implements IWriter {
	
	CalculateLetterGrade calculateGrade = new CalculateLetterGrade();


	@SuppressWarnings("deprecation")
	public void writeToFile(List<String> header, GradeBook gradeBook, List<Student> studentList) 
			throws IOException, ParseException, ParserConfigurationException, SAXException{
		DocumentBuilderFactory icFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder icBuilder = icFactory.newDocumentBuilder();
        Document doc = icBuilder.newDocument();
		File xmlFile = new File("src/Resources/OutputDataXML.xml");
		List<String> categoryList = gradeBook.getCategory();
		List<String> gradeWork;
        Element gradeBookElement = doc.createElement("GradeBook");
        doc.appendChild(gradeBookElement);
        Element gradeElement = doc.createElement("Grade");

        Student student;
        int k=0;
        Map<String, List<String>> assignedWork;
        for(int i=0; i< studentList.size(); i++){
        	student = studentList.get(i);
			
            Element studentElement = doc.createElement("Student");
        	gradeElement.appendChild(studentElement);

            Element nameElement = doc.createElement(header.get(0));
            Element idElement = doc.createElement(header.get(1));


        	Text nameText = doc.createTextNode(student.getName());
        	nameElement.appendChild(nameText);

        	Text idText = doc.createTextNode(student.getId());
        	idElement.appendChild(idText);
        	
        	studentElement.appendChild(nameElement);
        	studentElement.appendChild(idElement);

        	
        	Element assignedWorkElement = doc.createElement("AssignedWork");
        	assignedWork = student.getAssignedwork();
        	Float total = (float) 0;
        	for(int j=0; j<categoryList.size(); j++){
        		
            	Element gradedWorkElement = doc.createElement("GradedWork");
				gradedWorkElement.setAttribute("category", categoryList.get(j));

				gradeWork = assignedWork.get(categoryList.get(j));
				float gradeItemScore = Float.parseFloat(gradeBook.getGradeItem().get(categoryList.get(j)));
				float currentScore = 0;
				int value=1;
				if(null == gradeWork){
					System.out.println("Bad File");
					break;
					
				} else{
				for(k=0 ; k<gradeWork.size(); k++){

					Element gradedWorkNameElement = doc.createElement("Name");
	            	Element gradedWorkGradeElement = doc.createElement("Grade");

	            	Text gradedWorkNameText = doc.createTextNode(header.get(value++));
	            	gradedWorkNameElement.appendChild(gradedWorkNameText);
	            	
	            	Text gradedWorkGradeText = doc.createTextNode(gradeWork.get(k));
	            	gradedWorkGradeElement.appendChild(gradedWorkGradeText);
	            	
	            	IGrader grader = GradeFactory.GradeReaderFactoryMethod(calculateGrade.isLetter(gradeWork.get(k)));
					float temp = grader.isLetterGrade(gradeWork.get(k));
					currentScore = currentScore + ( temp/ 100) ;
					gradedWorkElement.appendChild(gradedWorkNameElement);
					gradedWorkElement.appendChild(gradedWorkGradeElement);
		        	assignedWorkElement.appendChild(gradedWorkElement);
				}
				total = total + ((currentScore/ k) * gradeItemScore);
			} }
        	
        	if(total > 0){
        	studentElement.appendChild(assignedWorkElement);
        	Element totalElement = doc.createElement("Total");
        	Text totalText = doc.createTextNode(total.toString());
        	totalElement.appendChild(totalText);
        	studentElement.appendChild(totalElement);

        	
        	Element letterGradeElement = doc.createElement("Grade");
        	Text letterGradeText = doc.createTextNode(calculateGrade.calculateGrade(total));
        	letterGradeElement.appendChild(letterGradeText);
        	studentElement.appendChild(letterGradeElement);
        	}
        	
        	gradeBookElement.setAttribute("class", student.getSubject());

        }

    	gradeBookElement.appendChild(gradeElement);

        FileOutputStream outStream = new FileOutputStream(xmlFile);
		OutputFormat outFormat = new OutputFormat(doc);
		outFormat.setIndenting(true);
		XMLSerializer serializer = new XMLSerializer(outStream, outFormat);
		serializer.serialize(doc);
	}
}
