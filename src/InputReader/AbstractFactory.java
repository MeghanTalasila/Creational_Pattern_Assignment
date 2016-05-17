package InputReader;

import OutputWriter.IWriter;

public abstract class AbstractFactory {

	public abstract IReader GradeReaderFactoryMethod(String extension, String type);   
	public abstract IWriter GradeWriterFactoryMethod(String outputDataType) ;

}
