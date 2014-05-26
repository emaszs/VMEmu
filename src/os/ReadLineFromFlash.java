package os;

import machine.Memory;
import machine.RM;
import res.LineInMemory;

//TODO
public class ReadLineFromFlash extends Process {

	public ReadLineFromFlash(int intID, String extID, int parentProcess,
			int priority, String startState) {
		super(intID, extID, parentProcess, priority, startState);
	}

	public void run() {
		// 1)ask for message for ReadLineFromFlash
		if ((phase == 0) && (pState.equals("ru"))) {
			PyOS.askForResource(PyOS.waitingList16, 16);
			neededResource = 16;
			phase = 1;
		}

		// 2)ask for Flash
		if ((phase == 1) && (receivedResource == 16) && (pState.equals("ru"))) {
			PyOS.askForResource(PyOS.waitingList6, 6);
			neededResource = 6;
			phase = 2;
		}

		// 3)ask for Supervisor Memory
		if ((phase == 2) && (receivedResource == 6) && (pState.equals("ru"))) {
			PyOS.askForResource(PyOS.waitingList2, 2);
			neededResource = 2;
			phase = 3;
		}

		// 4-5)Reads line, free Flash
		// TODO
		if ((phase == 3) && (receivedResource == 2) && (pState.equals("ru"))) {
			neededResource = 0;

			int adrToWrite = Integer.parseInt(RM.r1.getString());
			String valToWrite = ((LineInMemory) PyOS.findResource(ownedResList,
					"LineInMemory")).line;
			Memory.writeToVirtualAddress(adrToWrite, valToWrite);

			PyOS.freeResource(PyOS.waitingList6, 6, ownedResList.get(0));
			phase = 4;
		}

		// 6-7) free Message from ReadLineFromFlash
		if ((phase == 4) && (pState.equals("ru"))) {
			PyOS.freeResource(PyOS.waitingList12, 12, null);
			phase = 0;
		}
	}
}
