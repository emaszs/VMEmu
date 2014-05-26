package os;

import res.TaskInHardDrive;

//TODO
public class MainProc extends Process {

	public MainProc(int intID, String extID, int parentProcess, int priority,
			String startState) {
		super(intID, extID, parentProcess, priority, startState);
	}

	public void run() {

		// 1) asks for task in hard drive memory
		if ((phase == 0) && (pState.equals("ru"))) {
			PyOS.askForResource(PyOS.waitingList7, 7);
			neededResource = 7;
			phase = 1;
		}

		// 2-4) deleting or creating jobGovernor
		if (phase == 1) {
			if (((TaskInHardDrive) PyOS.findResource(ownedResList,
					"TaskInHardDrive")).computingTime == 0) {
				// TODO getting latest received TaskInHardDrive resource?
				int jobGovToDeleteId = ownedResList
						.get(ownedResList.size() - 1).intID;
				// TODO childrenListIndex???
				PyOS.deleteProcess(jobGovToDeleteId, childrenListIndex);
			} else {
				PyOS.createProcess(10, intID, 50,
						ownedResList.get(ownedResList.size() - 1));
			}
			phase = 2;
		}

		// TODO
		if ((phase == 2) && (receivedResource == 7) && (pState.equals("ru"))) {
			neededResource = 0;

			phase = 0;
		}
	}

}
