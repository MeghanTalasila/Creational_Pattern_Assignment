package OutputWriter;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import InputReader.GradeBook;
import InputReader.Student;

public interface IWriter {
	
	public void writeToFile(List<String> header, GradeBook gradeBook, List<Student> studentList) 
			throws IOException, ParseException, ParserConfigurationException, SAXException;
}
