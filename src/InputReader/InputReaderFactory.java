package InputReader;

import OutputWriter.IWriter;

public class InputReaderFactory extends AbstractFactory {
public IReader GradeReaderFactoryMethod(String extension, String type){            
	IReader reader = null;       
        switch (type.toUpperCase()) {
            case "PG":
            	if(extension.equalsIgnoreCase("xml")){
                reader = new InputXml();
            	} else{
            		System.out.println("Please use .xml format for PG students");
            	}
                break;
            case "UG":
            	if(extension.equalsIgnoreCase("json")){
                reader = new InputJSON();
            	} else{
            		System.out.println("Please use .json format for PG students");
            	}
                break;
            default:
                break;
        }
        return reader;
    }

public IWriter GradeWriterFactoryMethod(String type) {
	// TODO Auto-generated method stub
	return null;
}
}
