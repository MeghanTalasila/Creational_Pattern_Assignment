package OutputWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import Grade.CalculateLetterGrade;
import Grade.GradeFactory;
import Grade.IGrader;
import InputReader.GradeBook;
import InputReader.InputXml;
import InputReader.Student;

public class OutputCSV implements IWriter {
	
	private static final String COMMA_DELIMITER = ",";
	private static final String NEW_LINE_SEPARATOR = "\n";

	//InputJson inputJSON = new InputJson();
	InputXml inputXML = new InputXml();
	CalculateLetterGrade calculateGrade = new CalculateLetterGrade();

	
	public void writeToFile(List<String> header, GradeBook gradeBook, List<Student> studentList) throws IOException, ParseException, ParserConfigurationException, SAXException{
		
		FileWriter fileWriter = new FileWriter("src/Resources/OutputDataCSV.csv");
		
		for(int i=0; i<header.size(); i++){
			fileWriter.append(header.get(i));
			fileWriter.append(COMMA_DELIMITER);
		}
		fileWriter.append(NEW_LINE_SEPARATOR);
		WriteDataToCSV(fileWriter, gradeBook, studentList);
		fileWriter.flush();
		fileWriter.close();
	}	
	
	public void WriteDataToCSV(FileWriter fileWriter, GradeBook gradeBook, List<Student> studentList) throws IOException, ParseException, ParserConfigurationException, SAXException{
		
		Student student;
		Map<String, List<String>> assignedWork;
		List<String> gradeWork;
		int k=0;

		
		List<String> categoryList = gradeBook.getCategory();
		for(int i=0; i<studentList.size(); i++){
			student = studentList.get(i);
			
			fileWriter.append(student.getName());
			fileWriter.append(COMMA_DELIMITER);

			fileWriter.append(student.getId());
			fileWriter.append(COMMA_DELIMITER);

			assignedWork = student.getAssignedwork();
			Float total = (float) 0;
			
			for(int j=0; j<categoryList.size(); j++){
				gradeWork = assignedWork.get(categoryList.get(j));
				float gradeItemScore = Float.parseFloat(gradeBook.getGradeItem().get(categoryList.get(j)));
				float currentScore = 0;
				if(null == gradeWork){
					System.out.println("Bad File");
					fileWriter.append("Bad Data");
					fileWriter.append(COMMA_DELIMITER);
					break;
				} else {
				for(k=0 ; k<gradeWork.size(); k++){
					fileWriter.append(gradeWork.get(k));
					fileWriter.append(COMMA_DELIMITER);
					IGrader grader = GradeFactory.GradeReaderFactoryMethod(calculateGrade.isLetter(gradeWork.get(k)));
					float temp = grader.isLetterGrade(gradeWork.get(k));
					currentScore = currentScore + ( temp/ 100) ;
				}
				total = total + ((currentScore/ k) * gradeItemScore);
			} 
			}
			if(total > 0){
				fileWriter.append(total.toString());
				fileWriter.append(COMMA_DELIMITER);
				fileWriter.append(calculateGrade.calculateGrade(total));
				fileWriter.append(NEW_LINE_SEPARATOR);
			}
		}
	}
}
