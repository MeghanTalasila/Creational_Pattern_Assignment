package Grade;


public class GradeFactory {
	public static IGrader GradeReaderFactoryMethod(String type){            
		IGrader grader = null;       
	        switch (type) {
	            case "Letter":
	            	grader = new LetterGrade();
	            	break;
	            case "Number":
	            	grader = new NumberGrade();
	            	break;
	            default:
	                break;
	        }
	        return grader;
	    }

}
