package Grade;

public class LetterGrade implements IGrader{

	public float isLetterGrade(String grade){
		float value = 0;
		switch(grade){
		case "A+":
			value = (float) (99 + 100)/2;
			break;
		case "A":
			value = (float) ((95 + 98.99)/2);
			break;
		case "A-":
			value = (float) ((90 + 94.99)/2);
			break;
		case "B+":
			value = (float) ((87 + 89.99)/2);
			break;
		case "B":
			value = (float) ((84 + 86.99)/2);
			break;
		case "B-":
			value = (float) ((80 + 83.99)/2);
			break;
		case "C+":
			value = (float) ((75 + 79.99)/2);
			break;
		case "C":
			value = (float) ((70 + 74.99)/2);
			break;
		case "D":
			value = (float) ((60 + 69.99)/2);
			break;
		case "E":
			value = (float) ((0 + 59.99)/2);
			break;
		default:
			value=0;
			break;
		}
			
		return value;
	}

	
}
