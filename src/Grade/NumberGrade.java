package Grade;

public class NumberGrade implements IGrader{

	public float isLetterGrade(String grade){
		
		return Float.parseFloat(grade);
	}
}
