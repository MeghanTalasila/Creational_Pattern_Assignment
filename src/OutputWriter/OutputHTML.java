package OutputWriter;

import java.io.BufferedWriter;
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
import InputReader.Student;

public class OutputHTML implements IWriter{
	public void writeToFile(List<String> header, GradeBook gradeBook, List<Student> studentList) 
			throws IOException, ParseException, ParserConfigurationException, SAXException{
		 StringBuilder sb = new StringBuilder();
		    sb.append("<html>");
		    sb.append("<head>");
		    sb.append("<title>Student Grades</title>");
		    sb.append("</head>");
		    sb.append("<body>");
		    sb.append("<table border='1'>");
		    sb.append("<tr>");
		    
		    for(int i=0; i<header.size(); i++){
			    sb.append("<th>");
		    	sb.append(header.get(i));
			    sb.append("</th>");
			}
		    sb.append("</tr>");

			WriteDataToHTML(sb, gradeBook, studentList);
			sb.append("</body>");
		    sb.append("</html>");
		    FileWriter fstream = new FileWriter("src/Resources/OutputDataHTML.html");
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.write(sb.toString());
		    out.close();
	}
	
	public void WriteDataToHTML(StringBuilder sb, GradeBook gradeBook, List<Student> studentList){
		Student student;
		CalculateLetterGrade calculateGrade = new CalculateLetterGrade();
		Map<String, List<String>> assignedWork;
		List<String> gradeWork;
		int k=0;

		
		List<String> categoryList = gradeBook.getCategory();
		for(int i=0; i<studentList.size(); i++){
		    
			student = studentList.get(i);
			sb.append("<tr>");
		    sb.append("<td>"+student.getName()+"</td>");
		    sb.append("<td>"+student.getId()+"</td>");

			assignedWork = student.getAssignedwork();
			Float total = (float) 0;
			for(int j=0; j<categoryList.size(); j++){
				gradeWork = assignedWork.get(categoryList.get(j));
				float gradeItemScore = Float.parseFloat(gradeBook.getGradeItem().get(categoryList.get(j)));
				float currentScore = 0;
				if(null == gradeWork){
					System.out.println("Bad File");
					sb.append("<td> File contains bad data</td>");
					break;
				} else{
				for(k=0 ; k<gradeWork.size(); k++){
					sb.append("<td>"+gradeWork.get(k)+"</td>");
					IGrader grader = GradeFactory.GradeReaderFactoryMethod(calculateGrade.isLetter(gradeWork.get(k)));
					float temp = grader.isLetterGrade(gradeWork.get(k));
					currentScore = currentScore + ( temp/ 100) ;
				}
				total = total + ((currentScore/ k) * gradeItemScore);
			} }
			if(total > 0){
			sb.append("<td>"+total.toString()+"</td>");
			sb.append("<td>"+calculateGrade.calculateGrade(total)+"</td>");
			}
		}
	}

}
