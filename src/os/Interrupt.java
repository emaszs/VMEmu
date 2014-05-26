package os;

import res.MessageAboutIdentifiedInterrupt;
import res.Resource;

//TODO
public class Interrupt extends Process {
	public String interruptType = new String();
	public int jobGovernorResponsibleForInt = 0;

	public Interrupt(int intID, String extID, int parentProcess, int priority,
			String startState) {
		super(intID, extID, parentProcess, priority, startState);
	}

	public void run() {
		
		//1) ask for Message about interrupt
		if ((phase == 0) && (pState.equals("ru"))) {	
			PyOS.askForResource(PyOS.waitingList20, 20);
			neededResource = 20;
			phase = 1;
		}
		
		//2) identify interrupt type and jobgov
		if ((phase == 1) && (pState.equals("ru"))) {	
			
			Resource messageAboutInterrupt = PyOS.findResource(this.ownedResList, "MessageAboutInterrupt");
			
			// Finding parent process of VM that created interrupt
			Process vm = PyOS.findProcessByIntId(messageAboutInterrupt.creatorProcess);
			Process jobGov = PyOS.findProcessByIntId(vm.parentProcess);
			
			jobGovernorResponsibleForInt = jobGov.intID;
			char[] si = jobGov.savedState.si;
			
			if(si[0] == '1') {
				interruptType = "WriteLineToHardDrive";
			} else if (si[1] == '1') {
				interruptType = "ReadLineFromHardDrive";
			} else if (si[2] == '1') {
				interruptType = "PrintLine";
			} else if (si[3] == '1') {
				interruptType = "ReadLineFromFlash";
			} else if (si[0] == '9' && si[1] == '9' && si[2] == '9' && si[3] == '9') {
				interruptType = "Halt";
			} else if (jobGov.savedState.pi != '0') {
				interruptType = "Program";
			}
			phase = 2;
		}
		
		//3) create MessageAboutIdentifiedInterrupt(10) resource
		if ((phase == 2) && (pState.equals("ru"))) {	
			MessageAboutIdentifiedInterrupt newResource = new MessageAboutIdentifiedInterrupt(PyOS.id2++, 
					"MessageAboutIdentifiedInterrupt", this.intID, interruptType, jobGovernorResponsibleForInt);

			this.createdResList.add(newResource);
			PyOS.resourceAmounts[10]++;		
			PyOS.resourceList.add(newResource);
		}
		
		
		//2-5) identify interrupt, identify JobGovernor, free Message about identified interrupt
		//TODO
		if ((phase == 1) && (receivedResource == 20) && (pState.equals("ru"))) {	
			neededResource = 0;
			
			
			
			
			phase = 0;
		}	
	}
}
