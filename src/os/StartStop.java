package os;
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
			PyOS.createResource(2, intID);
			PyOS.elementList2.add(createdResList.get(0));
			PyOS.elementList2.add(createdResList.get(0));
			PyOS.elementList2.add(createdResList.get(0));
			PyOS.elementList2.add(createdResList.get(0));
			PyOS.createResource(3, intID);
			PyOS.elementList3.add(createdResList.get(1));
			PyOS.createResource(4, intID);
			PyOS.elementList4.add(createdResList.get(2));
			PyOS.createResource(5, intID);
			PyOS.elementList5.add(createdResList.get(3));
			PyOS.createResource(6, intID);
			PyOS.elementList6.add(createdResList.get(4));
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
			//Resource deletion
			while (createdResList.size()>0) {
				PyOS.deleteResource(createdResList.get(0).intID);
			}		
			PyOS.MOSEnd = 1;
		}
	}
	
}
