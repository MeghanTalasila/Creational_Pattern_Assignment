package Main;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.ParserConfigurationException;

import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import InputReader.AbstractFactory;
import InputReader.FactoryProducer;
import InputReader.GradeBook;
import InputReader.IReader;
import InputReader.Student;
import OutputWriter.IWriter;

public class Client {
	
	public static void main(String args[]) throws ParserConfigurationException, SAXException, IOException, ParseException{
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter type of student (PG / UG). PG means Graduate. UG means Under Graduate");
		String inputDataType = scanner.nextLine();
		
		System.out.println("Please enter input file path.");
		System.out.println("UG Students src/Resources/data.json");
		System.out.println("PG Students src/Resources/DataXML.xml");
		String filePath = scanner.nextLine();

		
		String[] parts = filePath.split("\\.");
		
		if(null != parts && parts.length > 1){
		String extension = parts[1]; 

	    AbstractFactory readerFactory = FactoryProducer.getFactory("Read");

		IReader reader = readerFactory.GradeReaderFactoryMethod(extension, inputDataType);
		System.out.println("Enter type of output: (XML/ CSV/ HTML): ");
		String outputDataType = scanner.nextLine();
		
	    AbstractFactory writerFactory = FactoryProducer.getFactory("Write");

		IWriter writer = writerFactory.GradeWriterFactoryMethod(outputDataType);
 
		if(null != reader){
		GradeBook gradeBook = reader.readInputGradeBook(filePath);
		List<Student> studentList = reader.readInputStudent(filePath);
		List<String> header = reader.CreateSchemaJSON(filePath);
		if(null != writer || null != gradeBook || null != studentList || null != header ){
			writer.writeToFile(header, gradeBook, studentList);
			System.out.println("Please refresh 'Resources' folder in project and then open newly created output file.");
			}
		} else{
			System.out.println("Please enter valid file path");
		}
		 	
		} else{
			System.out.println("Please enter valid file path");
		}
		
		scanner.close();
	}

	
}
