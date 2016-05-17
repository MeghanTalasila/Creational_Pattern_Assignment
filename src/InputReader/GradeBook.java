package InputReader;

import java.util.List;
import java.util.Map;

public class GradeBook {
	List<String> category;
	Map<String, String> gradeItem;

	
	 public List<String> getCategory() {
		return category;
	}
	public void setCategory(List<String> category) {
		this.category = category;
	}
	 
	 
	 public Map<String, String> getGradeItem() {
		return gradeItem;
	}
	public void setGradeItem(Map<String, String> gradeItem) {
		this.gradeItem = gradeItem;
	}
}
