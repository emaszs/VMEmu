package os;

import res.Resource;

//TODO
public class JobGovernor extends Process {

	public JobGovernor(int intID, String extID, int parentProcess, int priority, String startState, Resource startingResource) {
		super(intID, extID, parentProcess, priority, startState);
		ownedResList.add(startingResource);
	}
	
	public void run() {
		
		//1) asks for User memory
		if ((phase == 0) && (pState.equals("ru"))) {	
			PyOS.askForResource(PyOS.waitingList3, 3);
			neededResource = 3;
			phase = 1;
		}
		
		
		//2) frees message from JobGovernor
		if ((phase == 1) && (receivedResource == 3) && (pState.equals("ru"))) {
			neededResource = 0;
			PyOS.freeResource(PyOS.waitingList8, 8, null);
			phase = 2;
		}
		
		
		//3) asks for Task in user memory
		if ((phase == 2) && (pState.equals("ru"))) {	
			PyOS.askForResource(PyOS.waitingList9, 9);
			neededResource = 9;
			phase = 3;
		}
		
		
		
		//4) creates VirtualMachine
		//TODO
		if ((phase == 3) && (receivedResource == 9) && (pState.equals("ru"))) {
			neededResource = 0;
			//create VM
			
			
			
			
			phase = 4;
		}
		
		
		
		//5) asks for Message about identified interrupt 
		if ((phase == 4) && (pState.equals("ru"))) {	
			PyOS.askForResource(PyOS.waitingList10, 10);
			neededResource = 9;
			phase = 5;
		}
		
		
		//6-7) stops VM, check interrupt
		//TODO
		if ((phase == 5) && (receivedResource == 10) && (pState.equals("ru"))) {	
			
			
			
			
			phase = 4;
		}
		
		
	}
	
}
