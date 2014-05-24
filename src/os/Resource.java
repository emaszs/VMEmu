package os;

public class Resource {
	
	public int intID;
	public String extID;
	public int creatorProcess;
	//os
	//resourseList
	public Process user;
	
	public Resource(int intID, String extID, int creatorProcess){
		this.intID = intID;
		this.extID = extID;
		this.creatorProcess = creatorProcess;
		user = null;
	}
}
