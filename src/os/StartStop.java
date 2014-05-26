package os;

import res.Resource;

//TODO
public class StartStop extends Process {

	public StartStop(int intID, String extID, int parentProcess, int priority, String startState) {
		super(intID, extID, parentProcess, priority, startState); 
	}
	
	public void run() {
		//1-3) Initialization, asks for MOS end
		//TODO
		if (phase == 0) {
			//System resource initialization
			PyOS.createResource(resourceNo, creatorID);
			
			
			
			
			//System process initialization
			PyOS.createProcess(2, intID, 80, null);
			PyOS.createProcess(3, intID, 90, null);
			PyOS.createProcess(4, intID, 70, null);
			PyOS.createProcess(5, intID, 60, null);
			PyOS.createProcess(6, intID, 55, null);
			PyOS.createProcess(7, intID, 54, null);
			PyOS.createProcess(8, intID, 50, null);
			PyOS.createProcess(9, intID, 45, null);
			PyOS.askForResource(PyOS.waitingList1, 1);
			neededResource = 1;	
			phase = 1;
		}
		
		
		//4-5) End, deletion
		//TODO
		if ((phase == 1) && (receivedResource == 1) && (pState.equals("ru"))) {
			neededResource = 0;	
			
			
			
			//Process deletion
			while (PyOS.processList.size()>1) {
				PyOS.deleteProcess(PyOS.processList.get(1).intID, PyOS.findChildrenListIndex(PyOS.processList.get(1).intID));
			}
			PyOS.MOSEnd = 1;
		}
	}
	
}
