package os;
//TODO
public class MainProc extends Process {
	
	public MainProc(int intID, String extID, int parentProcess, int priority, String startState) {
		super(intID, extID, parentProcess, priority, startState); 
	}
	
	public void run() {
		
		//1) asks for task in hard drive memory
		if ((phase == 0) && (pState.equals("ru"))) {
			PyOS.askForResource(PyOS.waitingList7, 7);
			neededResource = 7;
			phase = 1;	
		}
		
		//2-4) deleting or creating jobGovernor
		//TODO
		if ((phase == 0) && (receivedResource == 7) && (pState.equals("ru"))) {
			neededResource = 0;

			
			
			
			
			phase = 0;
		}
	}
	

}
