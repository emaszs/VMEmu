package os;

import memory.Hdd;
import res.HardDriveMemory;
import res.LineInMemory;

//TODO
public class WriteLineToHardDrive extends Process {

	String line;
	String fileId;

	public WriteLineToHardDrive(int intID, String extID, int parentProcess,
			int priority, String startState) {
		super(intID, extID, parentProcess, priority, startState);
	}

	public void run() {
		// 1)ask for resource message for WriteLineToHardDrive
		if ((phase == 0) && (pState.equals("ru"))) {
			PyOS.askForResource(PyOS.waitingList18, 18);
			neededResource = 18;
			phase = 1;
		}

		// 2)ask for resource Line in memory
		if ((phase == 1) && (receivedResource == 18) && (pState.equals("ru"))) {
			PyOS.askForResource(PyOS.waitingList19, 19);
			neededResource = 19;
			phase = 2;
		}

		// 3)ask for resource Hard drive memory
		if ((phase == 2) && (receivedResource == 19) && (pState.equals("ru"))) {
			PyOS.askForResource(PyOS.waitingList4, 4);
			neededResource = 4;
			phase = 3;
		}

		// 4-5) Print line and free Hard drive memory
		// TODO
		if ((phase == 3) && (receivedResource == 5) && (pState.equals("ru"))) {
			neededResource = 0;

			LineInMemory lineMem = (LineInMemory) PyOS.findResource(
					ownedResList, "LineInMemory");
			HardDriveMemory hdMem = (HardDriveMemory) PyOS.findResource(
					ownedResList, "HardDriveMemory");

			Hdd.writeToFile(hdMem.fileId, lineMem.line);

			PyOS.freeResource(PyOS.waitingList4, 4,
					ownedResList.get(ownedResList.size() - 1));
			phase = 4;
		}

		// 6-7) free message from WriteLineToHardDrive
		if ((phase == 4) && (pState.equals("ru"))) {
			PyOS.freeResource(PyOS.waitingList14, 14, null);
			phase = 0;
		}
	}
}
