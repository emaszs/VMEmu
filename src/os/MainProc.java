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
				int jobGovToBeDeletedId = ownedResList
						.get(ownedResList.size() - 1).creatorProcess;

				int jobGovChildIndex = -1;

				for (int i = 0; i < this.childrenList.size(); i++) {
					if (this.childrenList.get(i).intID == jobGovToBeDeletedId) {
						jobGovChildIndex = i;
					}
				}

				if (jobGovChildIndex == -1) {
					System.out.println("did not find a jobGovernor to delete");
				} else {
					PyOS.deleteProcess(jobGovToBeDeletedId, jobGovChildIndex);
				}
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
