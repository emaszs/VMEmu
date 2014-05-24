package res;

import os.Process;

//TODO
public class WriteLineToHardDrive extends Process {
	
	String line;
	String fileId;

	public WriteLineToHardDrive(int intID, String extID, int parentProcess, int priority, String startState) {
		super(intID, extID, parentProcess, priority, startState); 
	}
	
	public void run() {
		
	}
}
