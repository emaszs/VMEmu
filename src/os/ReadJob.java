package os;

//TODO ***** Papildyti, atlaisvinti supervizorine atminti
import java.io.IOException;
import res.TaskInHardDrive;
import memory.Hdd;
import ui.OSUI;

//TODO
public class ReadJob extends Process {
	public int filesInUse = 0;
	
	public ReadJob(int intID, String extID, int parentProcess, int priority, String startState) {
		super(intID, extID, parentProcess, priority, startState); 
	}
	
	public void run() {
		//System.out.println("Current phase " + phase);
		//System.out.println("Current state " + pState);
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
					if (line != null) {
						Hdd.openFileForWriting(filesInUse);
						Hdd.writeToFile(filesInUse, line);
						Hdd.closeFile(filesInUse);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			} while (line != null && !line.equals("$END"));
			
			if (line == null) {
				PyOS.askForResource(PyOS.waitingList1, 1);
				PyOS.freeResource(PyOS.waitingList2, 2, ownedResList.get(1));
				PyOS.freeResource(PyOS.waitingList4, 4, ownedResList.get(1));
			} else {
				filesInUse++;
			}
				
			
			
			PyOS.freeResource(PyOS.waitingList6, 6, ownedResList.get(0));
			phase = 4;
		}
		
		//5 TODO free flash, hard drive resources
		if ((phase == 4) && (pState.equals("ru"))){
			PyOS.freeResource(PyOS.waitingList2, 2, ownedResList.get(0));
			PyOS.freeResource(PyOS.waitingList4, 4, ownedResList.get(0));
			phase = 5;
		}
		
		//7) creates resource task in hard drive
		//TODO resource should contain fileId
		if ((phase == 5) && (pState.equals("ru"))) {
			
			
			PyOS.createResource(7, intID);
			ownedResList.add(createdResList.get(createdResList.size()-1));
			ownedResList.get(ownedResList.size()-1).user = PyOS.findProcessByIntId(intID);
			((TaskInHardDrive)ownedResList.get(ownedResList.size()-1)).computingTime = 1;
			PyOS.freeResource(PyOS.waitingList7, 7, ownedResList.get(ownedResList.size()-1));
			phase = 0;
		}
	}
}
