package os;

//import machine.Memory;
import machine.RM;

public class VirtualMachine extends Process {

	public VirtualMachine(int intID, String extID, int parentProcess, int priority, String startState) {
		super(intID, extID, parentProcess, priority, startState); 
	}
	
	public void run() {
		//Memory.printMemory();
		((JobGovernor) PyOS.findProcessByIntId(parentProcess)).loadState();
		RM.doStep();
		((JobGovernor) PyOS.findProcessByIntId(parentProcess)).saveState();
		//PyOS.createResource(20, intID);
		//ownedResList.add(createdResList.get(createdResList.size()-1));
		//((MessageAboutInterrupt)ownedResList.get(ownedResList.size()-1)).
	}
}
