package os;
//TODO
public class StartStop extends Process {

	public StartStop(int intID, String extID, int parentProcess, int priority, String startState) {
		super(intID, extID, parentProcess, priority, startState); 
	}
	
	public void run() {
		//Initialization
		if (phase == 0) {
			//System resource initialization
			
			
			
			//System process initialization
			
			
		}
		
		//End
		if ((phase == 1) && (receivedResource == 1) && (pState.equals("ru"))) {
			
			
		}
	}
	
}
