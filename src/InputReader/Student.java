package InputReader;

import java.util.List;
import java.util.Map;

public class Student {
 private String subject;
 private String name;
 private String id;
 Map<String, List<String>> assignedwork;
 
public String getSubject() {
	return subject;
}
public void setSubject(String subject) {
	this.subject = subject;
}
public Map<String, List<String>> getAssignedwork() {
	return assignedwork;
}
public void setAssignedwork(Map<String, List<String>> assignedwork) {
	this.assignedwork = assignedwork;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}


}
