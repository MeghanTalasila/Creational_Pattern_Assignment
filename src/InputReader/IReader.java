package InputReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

public interface IReader {

	public GradeBook readInputGradeBook(String filePath) throws IOException, ParseException, ParserConfigurationException, SAXException;
	public List<Student> readInputStudent(String filePath) throws IOException, ParseException, ParserConfigurationException, SAXException;
	public List<String> CreateSchemaJSON(String filePath) throws FileNotFoundException, IOException, ParseException, ParserConfigurationException, SAXException;

}
