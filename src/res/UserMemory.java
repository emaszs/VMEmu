package res;

import java.util.ArrayList;

//TODO
public class UserMemory extends Resource {

	ArrayList<Integer> memoryBlockList = new ArrayList<Integer>();
	
	public UserMemory(int intID, String extID, int creatorProcess) {
		super(intID, extID, creatorProcess);
		
	}
	
}
