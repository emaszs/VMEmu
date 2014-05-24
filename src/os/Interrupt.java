package os;


//TODO
public class Interrupt extends Process {

	public Interrupt(int intID, String extID, int parentProcess, int priority, String startState) {
		super(intID, extID, parentProcess, priority, startState); 
	}
	
	public void run() {
		
		//1) ask for Message about interrupt
		if ((phase == 0) && (pState.equals("ru"))) {	
			PyOS.askForResource(PyOS.waitingList20, 20);
			neededResource = 20;
			phase = 1;
		}
		
		
		//2-5) identify interrupt, identify JobGovernor, free Message about identified interrupt
		//TODO
		if ((phase == 1) && (receivedResource == 20) && (pState.equals("ru"))) {	
			neededResource = 0;
			
			
			
			
			phase = 0;
		}
		
		
		
	}
	

}
