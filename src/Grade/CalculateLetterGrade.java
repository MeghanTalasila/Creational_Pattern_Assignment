package Grade;

public class CalculateLetterGrade {
	public String calculateGrade(float total){
		String grade = null;
			if(total >= 99){
				grade = "A+";
			} else if(total >= 95){
				grade = "A";
			} else if(total >= 90){
				grade = "A-";
			} else if(total >= 87){
				grade = "B+";
			} else if(total >= 84){
				grade = "B";
			} else if(total >= 80){
				grade = "B-";
			} else if(total >= 75){
				grade = "C+";
			} else if(total >= 70){
				grade = "C";
			} else if(total >= 60){
				grade = "D";
			} else if(total < 60){
				grade = "E";
			}
			
			return grade;
		}
	
	
	
	public String isLetter(String grade){
		String type = "Number";
		if(grade.equalsIgnoreCase("A+") || grade.equalsIgnoreCase("A") || grade.equalsIgnoreCase("A-") || 
		   grade.equalsIgnoreCase("B+") || grade.equalsIgnoreCase("B") || grade.equalsIgnoreCase("B-") ||
		   grade.equalsIgnoreCase("C+") || grade.equalsIgnoreCase("C") || grade.equalsIgnoreCase("D") ||
		   grade.equalsIgnoreCase("E") ){	
		type = "Letter";
		}	
		return type;
	}

	
}
