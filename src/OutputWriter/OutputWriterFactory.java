package OutputWriter;

import InputReader.AbstractFactory;
import InputReader.IReader;

public class OutputWriterFactory extends AbstractFactory{
	public IWriter GradeWriterFactoryMethod(String type){            
		IWriter writer = null;       
	        switch (type.toUpperCase()) {
	            case "CSV":
	                writer = new OutputCSV();
	                break;
	            case "HTML":
	                writer = new OutputHTML();
	                break;
	            case "XML":
	                writer = new OutputXML();
	                break;
	            default:
	                break;
	        }
	        return writer;
	    }

	@Override
	public IReader GradeReaderFactoryMethod(String extension, String type) {
		return null;
	}
}
