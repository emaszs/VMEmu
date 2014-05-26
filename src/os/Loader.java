package os;

//TODO
public class Loader extends Process {
	
	public Loader(int intID, String extID, int parentProcess, int priority, String startState) {
		super(intID, extID, parentProcess, priority, startState); 
	}
	
	public void run(){
		
		//1) asks for message from job governor
		if ((phase == 0) && (pState.equals("ru"))) {	
			PyOS.askForResource(PyOS.waitingList8, 8);
			neededResource = 8;
			phase = 1;
		}
		
		
		//2) asks for hard drive memory
		if ((phase == 1) && (receivedResource == 8) && (pState.equals("ru"))) {	
			PyOS.askForResource(PyOS.waitingList4, 4);
			neededResource = 4;
			phase = 2;
		}
		
		
		//3) asks for user memory
		if ((phase == 2) && (receivedResource == 4) && (pState.equals("ru"))) {	
			PyOS.askForResource(PyOS.waitingList3, 3);
			neededResource = 3;
			phase = 3;
		}
		
		//4-5) loads program and frees Task in user memory resource
		//TODO
		if ((phase == 3) && (receivedResource == 3) && (pState.equals("ru"))) {	

			
			//Nuspresk, ar Task in user memory tures svarios info
			//PyOS.freeResource(PyOS.waitingList9, 9, ownedResList.get(0));
			neededResource = 0;
			phase = 4;
		}
		
		//6-7) free Hard drive memory resource
		//TODO
		if ((phase == 4) && (pState.equals("ru"))) {	
			
			
			
			//Nuspresk, ar Hard drive memory tures svarios info
			//PyOS.freeResource(PyOS.waitingList9, 9, ownedResList.get(0));
			phase = 0;
		}
	}
}
