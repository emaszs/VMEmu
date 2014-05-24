package os;

import java.util.ArrayList;

public abstract class Process {

	//ArrayList processList;
	public int intID;
	public String extID;
	public RMState savedState;  
	//cpu
	//os
	public String pState; //ru - running, bl - blocked, bs - blocked stopped, re - ready, rs - ready stopped
	public ArrayList<Resource> createdResList;
	public ArrayList<Resource> ownedResList;
	int parentProcess;
	public ArrayList<Process> childrenList;
	public int phase;
	public int priority;
	public int receivedResource;
	public int neededResource;
	
	/* Priorities
	StartStop 100
	ReadJob 80
	MainProc 90
	Loader 70
	JobGovernor 75
	Interrupt 60
	PrintLine 55
	ReadLineFromFlash 54
	WriteLineToHardDrive 50
	ReadLineFromHardDrive 45
	VirtualMachine 40
	 */
	
	
	public Process(int intID, String extID, int parentProcess, int priority, String startState) {
		this.intID = intID;
		this.extID = extID;
		this.parentProcess = parentProcess;
		savedState = new RMState();
		pState = startState;
		createdResList = new ArrayList<Resource>();
		ownedResList = new ArrayList<Resource>();
		childrenList = new ArrayList<Process>();
		phase = 0;
		this.priority = priority;
		neededResource = 0;
		receivedResource = 0;
	}
	
	
	abstract public void run();
	
	
}
