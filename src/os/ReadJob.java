package os;

import java.io.IOException;

import memory.Hdd;
import ui.OSUI;

//TODO
public class ReadJob extends Process {
	public int filesInUse = 0;
	
	public ReadJob(int intID, String extID, int parentProcess, int priority, String startState) {
		super(intID, extID, parentProcess, priority, startState); 
	}
	
	public void run() {
		
		//1) asks for Flash
		if ((phase == 0) && (pState.equals("ru"))) {	
			PyOS.askForResource(PyOS.waitingList6, 6);
			neededResource = 6;
			phase = 1;
		}
		//2) asks for supervisor memory
		if ((phase == 1) && (receivedResource == 6) && (pState.equals("ru"))) {	
			PyOS.askForResource(PyOS.waitingList2, 2);
			neededResource = 2;
			phase = 2;
		}
		
		//3)asks for hard drive memory
		//TODO
		if ((phase == 2) && (receivedResource == 2) && (pState.equals("ru"))) {	
		
			PyOS.askForResource(PyOS.waitingList4, 4);
			neededResource = 4;
			phase = 3;
		}
		
		//4) copies task to hard drive memmory
		//TODO
		if ((phase == 3) && (receivedResource == 4) && (pState.equals("ru"))) {	
			neededResource = 0;
			
			String line = new String();
			
			do {
				try {
					line = OSUI.flash.readLine();
					Hdd.writeToFile(filesInUse, line);
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} while (!line.equals("$END"));
			filesInUse++;
			Hdd.initFiles();
			
			
			PyOS.freeResource(PyOS.waitingList6, 6, ownedResList.get(0));
			phase = 4;
		}
		
		//5 TODO free flash, hard drive resources
		if (phase == 4) {
			
		}
		
		//7) creates resource task in hard drive
		//TODO resource should contain fileId
		if ((phase == 4) && (pState.equals("ru"))) {
			
			
			PyOS.createResource(7, intID);
			PyOS.freeResource(PyOS.waitingList7, 7, createdResList.get(createdResList.size()-1));
			phase = 0;
		}
	}
}
