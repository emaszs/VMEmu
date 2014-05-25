package os;

import machine.RM;

public class VirtualMachine extends Process {

	public VirtualMachine(int intID, String extID, int parentProcess, int priority, String startState) {
		super(intID, extID, parentProcess, priority, startState); 
	}
	
	public void run() {
		((JobGovernor) PyOS.findProcessByIntId(parentProcess)).loadState();
		RM.doStep();
		((JobGovernor) PyOS.findProcessByIntId(parentProcess)).saveState();
	}
}
