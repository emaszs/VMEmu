package os;

import machine.Memory;
import machine.RM;
import memory.Hdd;
import res.HardDriveMemory;

//TODO *** atlaisvinti arba sunaikinti eilute
public class ReadLineFromHardDrive extends Process {

	public ReadLineFromHardDrive(int intID, String extID, int parentProcess,
			int priority, String startState) {
		super(intID, extID, parentProcess, priority, startState);
	}

	public void run() {
		// 1)ask for message for ReadLineFromHardDrive
		if ((phase == 0) && (pState.equals("ru"))) {
			PyOS.askForResource(PyOS.waitingList17, 17);
			neededResource = 17;
			phase = 1;
		}

		// 2)ask for Hard drive memory
		if ((phase == 1) && (receivedResource == 17) && (pState.equals("ru"))) {
			PyOS.askForResource(PyOS.waitingList4, 4);
			neededResource = 4;
			phase = 2;
		}

		// 3)ask for Supervisor Memory
		if ((phase == 2) && (receivedResource == 4) && (pState.equals("ru"))) {
			PyOS.askForResource(PyOS.waitingList2, 2);
			neededResource = 2;
			phase = 3;
		}

		// 4-5)read line, free Hard drive memory
		// TODO
		if ((phase == 3) && (receivedResource == 2) && (pState.equals("ru"))) {
			neededResource = 0;

			int fileId = ((HardDriveMemory) PyOS.findResource(ownedResList,
					"HardDriveMemory")).fileId;
			String line = Hdd.readFromFile(fileId);
			int adrToWrite = Integer.parseInt(RM.r1.getString());

			Memory.writeToVirtualAddress(adrToWrite, line);

			RM.r1.setInt(RM.r1.getInt() + 1);
			RM.r2.setInt(RM.r2.getInt() - 1); // one iteration complete

			PyOS.freeResource(PyOS.waitingList4, 4, ownedResList.get(0));
			phase = 4;
		}

		// 6-7) free Message from ReadLineFromHardDrive
		if ((phase == 4) && (pState.equals("ru"))) {
			PyOS.freeResource(PyOS.waitingList13, 13, null);
			phase = 0;
		}
	}
}
