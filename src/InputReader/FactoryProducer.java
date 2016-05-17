package InputReader;

import OutputWriter.OutputWriterFactory;

public class FactoryProducer {

	

	public static AbstractFactory getFactory(String choice) {
		  if(choice.equalsIgnoreCase("Read")){
		         return new InputReaderFactory();
		         
		      }else if(choice.equalsIgnoreCase("Write")){
		         return new OutputWriterFactory();
		      }
		      
		      return null;
	}
}
