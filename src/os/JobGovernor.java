package os;

import machine.RM;
import res.MessageAboutIdentifiedInterrupt;
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
			PyOS.createProcess(11, this.intID, 60, null);
			
			
			
			phase = 4;
		}
		
		
		
		//5) asks for Message about identified interrupt 
		if ((phase == 4) && (pState.equals("ru"))) {	
			PyOS.askForResource(PyOS.waitingList10, 10);
			neededResource = 9;
			phase = 5;
		}
		
		//6) stop VM process
		if ((phase == 5) && (pState.equals("ru"))) {
			//TODO jobGov should contain only one child VM process (?) yes, cause every task has its own JobGovernor which has its own VM
			PyOS.stopProcess(this.childrenList.get(0).intID);
			phase = 6;
		}
		
		//7 process interrupt by calling other procs or halting VM
		if ((phase == 6) && (pState.equals("ru"))) {	
			MessageAboutIdentifiedInterrupt msg = (MessageAboutIdentifiedInterrupt) 
					PyOS.findResource(this.ownedResList, "MessageAboutIdentifiedInterrupt");
			if (msg.interruptType.equals("WriteLineToHardDrive")) {
				// TODO create proc WriteLineToHardDrive with lineInMemory res
				// TODO jobGov is blocked until done
			} else if (msg.interruptType.equals("ReadLineFromHardDrive")) {
				// TODO create proc ReadLineFromHardDrive
				// TODO jobGov is blocked until done
			} else if (msg.interruptType.equals("PrintLine")) {
				// TODO create proc PrintLine with res lineInMemory
				// TODO jobGov is blocked until done
			} else if (msg.interruptType.equals("ReadLineFromFlash")) {
				// TODO create proc ReadLineFromFlash
				// TODO jobGov is blocked until done
			} else if (msg.interruptType.equals("Program") || msg.interruptType.equals("Halt")) {
				// TODO stop VM process
				// TODO block JobGov process with fictional resource TaskInHardDrive
			}
			
			
			phase = 6;
		}
		
	
		//TODO
		if ((phase == 6) && (receivedResource == 10) && (pState.equals("ru"))) {	

			

			
			phase = 4;
		}
	}
	
	public void saveState() {
		savedState.ic = RM.ic;
		savedState.pi = RM.pi;
		savedState.ptp = RM.ptp;
		savedState.r1 = RM.r1;
		savedState.r2 = RM.r2;
		savedState.sf = RM.sf;
		savedState.si = RM.si;
		savedState.sp = RM.sp;
		savedState.t = RM.t;
	}
	
	public void loadState() {
		RM.ic =savedState.ic;
		RM.pi = savedState.pi;
		RM.ptp = savedState.ptp;
		RM.r1 = savedState.r1;
		RM.r2 = savedState.r2;
		RM.sf = savedState.sf;
		RM.si = savedState.si;
		RM.sp = savedState.sp;
		RM.t = savedState.t;
	}
}
