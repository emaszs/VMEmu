package os;

import java.util.ArrayList;

import machine.Memory;
import res.Flash;
import res.HardDriveMemory;
import res.LineInMemory;
import res.MessageAboutIdentifiedInterrupt;
import res.MessageAboutInterrupt;
import res.Printer;
import res.Resource;
import res.SupervisorMemory;
import res.TaskInHardDrive;
import res.TaskInUserMemory;
import res.UserMemory;

public class PyOS {
	public static ArrayList<Process> processList = new ArrayList<Process>();
	public static ArrayList<Resource> resourceList = new ArrayList<Resource>();
	public static int MOSEnd = 0;
	public static ArrayList<Process> readyProcesses = new ArrayList<Process>();
	public static Process currentProcess = null;
	public static int id = 0; // process inner number
	public static int id2 = 0; // resource inner number

	public static int timer = 10;

	// Waiting process lists
	public static ArrayList<Process> waitingList1 = new ArrayList<Process>(); // MOS
																				// pabaiga
	public static ArrayList<Process> waitingList2 = new ArrayList<Process>(); // Supervizorine
																				// atmintis
	public static ArrayList<Process> waitingList3 = new ArrayList<Process>(); // Vartotojo
																				// atmintis
	public static ArrayList<Process> waitingList4 = new ArrayList<Process>(); // Kietojo
																				// disko
																				// atmintis
	public static ArrayList<Process> waitingList5 = new ArrayList<Process>(); // Spausdintuvas
	public static ArrayList<Process> waitingList6 = new ArrayList<Process>(); // Flash
																				// irenginys
	public static ArrayList<Process> waitingList7 = new ArrayList<Process>(); // Uzduotis
																				// kietajame
																				// diske
	public static ArrayList<Process> waitingList8 = new ArrayList<Process>(); // Pranesimas
																				// is
																				// JobGovernor
	public static ArrayList<Process> waitingList9 = new ArrayList<Process>(); // Uzduotis
																				// vartotojo
																				// atmintyje
	public static ArrayList<Process> waitingList10 = new ArrayList<Process>(); // Pranesimas
																				// apie
																				// identifikuota
																				// pertraukima
	public static ArrayList<Process> waitingList11 = new ArrayList<Process>(); // Pranesimas
																				// is
																				// PrintLine
	public static ArrayList<Process> waitingList12 = new ArrayList<Process>(); // Pranesimas
																				// is
																				// ReadLineFromFlash
	public static ArrayList<Process> waitingList13 = new ArrayList<Process>(); // Pranesimas
																				// is
																				// ReadLineFromHardDrive
	public static ArrayList<Process> waitingList14 = new ArrayList<Process>(); // Pranesimas
																				// is
																				// WriteLineToHardDrive
	public static ArrayList<Process> waitingList15 = new ArrayList<Process>(); // Pranesimas
																				// procesui
																				// PrintLine
	public static ArrayList<Process> waitingList16 = new ArrayList<Process>(); // Pranesimas
																				// procesui
																				// ReadLineFromFlash
	public static ArrayList<Process> waitingList17 = new ArrayList<Process>(); // Pranesimas
																				// procesui
																				// ReadLineFromHardDrive
	public static ArrayList<Process> waitingList18 = new ArrayList<Process>(); // Pranesimas
																				// procesui
																				// WriteLineToHardDrive
	public static ArrayList<Process> waitingList19 = new ArrayList<Process>(); // Eilute
																				// atmintyje
	public static ArrayList<Process> waitingList20 = new ArrayList<Process>(); // Pranesimas
																				// apie
																				// pertraukima

	public static int[] resourceAmounts = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0 };// 0 index unused

	public static ArrayList<Resource> elementList2 = new ArrayList<Resource>();
	public static ArrayList<Resource> elementList3 = new ArrayList<Resource>();
	public static ArrayList<Resource> elementList4 = new ArrayList<Resource>();
	public static ArrayList<Resource> elementList5 = new ArrayList<Resource>();
	public static ArrayList<Resource> elementList6 = new ArrayList<Resource>();
	public static ArrayList<Resource> elementList7 = new ArrayList<Resource>();
	public static ArrayList<Resource> elementList9 = new ArrayList<Resource>();
	public static ArrayList<Resource> elementList10 = new ArrayList<Resource>();
	public static ArrayList<Resource> elementList19 = new ArrayList<Resource>();
	public static ArrayList<Resource> elementList20 = new ArrayList<Resource>();

	// public static void main(final String[] args) {
	//
	// processList.add(new StartStop(1, "StartStop", 0, 100, "re"));
	// id = 1;
	// }

	// Process primitives
	public static void createProcess(int processNo, int parentProcess,
			int priority, Resource startingResource) {
		Process newProcess = null;
		id++;
		System.out.println("Create process" + processNo);
		// creating ReadJob
		if (processNo == 2) {
			newProcess = new ReadJob(id, "ReadJob", parentProcess, priority,
					"re");
		}

		// creating MainProc
		if (processNo == 3) {
			newProcess = new MainProc(id, "MainProc", parentProcess, priority,
					"re");
		}

		// creating Loader
		if (processNo == 4) {
			newProcess = new Loader(id, "Loader", parentProcess, priority, "re");
		}

		// creating Interrupt
		if (processNo == 5) {
			newProcess = new Interrupt(id, "Interrupt", parentProcess,
					priority, "re");
		}

		// creating PrintLine
		if (processNo == 6) {
			newProcess = new PrintLine(id, "PrintLine", parentProcess,
					priority, "re");
		}

		// creating ReadLineFromFlash
		if (processNo == 7) {
			newProcess = new ReadLineFromFlash(id, "ReadLineFromFlash",
					parentProcess, priority, "re");
		}

		// creating WriteLineToHardDrive
		if (processNo == 8) {
			newProcess = new WriteLineToHardDrive(id, "WriteLineToHardDrive",
					parentProcess, priority, "re");
		}

		// creating ReadLineFromHardDrive
		if (processNo == 9) {
			newProcess = new ReadLineFromHardDrive(id, "ReadLineFromHardDrive",
					parentProcess, priority, "re");
		}

		// creating JobGovernor
		if (processNo == 10) {
			newProcess = new JobGovernor(id, "JobGovernor", parentProcess,
					priority, "re", startingResource);
		}

		// creating VirtuamMachine
		if (processNo == 11) {
			newProcess = new VirtualMachine(id, "VirtualMachine",
					parentProcess, priority, "re");
		}

		processList.add(newProcess);

		// find parent
		int listIndex = 0;
		int listSize = processList.size();
		for (int i = 1; i <= listSize; i++) {
			if (processList.get(i - 1).intID == parentProcess) {
				listIndex = i - 1;
			}
		}
		// Add new process to its parent children list
		processList.get(listIndex).childrenList.add(newProcess);

		readyProcesses.add(newProcess);
	}

	public static void deleteProcess(int intID, int childrenListIndex) {
		// find in list
		int listIndex = 0;
		int listSize = processList.size();
		for (int i = 1; i <= listSize; i++) {
			if (processList.get(i - 1).intID == intID) {
				listIndex = i - 1;
			}
		}

		Process processToDelete = processList.get(listIndex);

		// deleting process children
		int children = processToDelete.childrenList.size();
		if (children != 0) {
			for (int i = 1; i <= children; i++) {
				deleteProcess(processToDelete.childrenList.get(0).intID, 0);
			}

		}

		// deleting created
		int resListSize = processToDelete.createdResList.size();
		for (int i = 1; i <= resListSize; i++) {
			deleteResource(processToDelete.createdResList.get(0).intID);
		}
		// resListSize = processToDelete.ownedResList.size();
		// for (int i = 1; i <= resListSize; i++) {
		// deleteResource(processToDelete.ownedResList.get(0).intID);
		// }

		// remove from parent children list
		// find parent
		int listIndex2 = 0;
		listSize = processList.size();
		for (int i = 1; i <= listSize; i++) {
			if (processList.get(i - 1).intID == processToDelete.parentProcess) {
				listIndex2 = i - 1;
			}
		}
		// remove
		processList.get(listIndex2).childrenList.remove(childrenListIndex);

		// remove from main procesList
		processList.remove(listIndex);

		// remove from ready process list
		if (processToDelete.pState.equals("re") == true) {
			int listIndex3 = 0;
			int listSize2 = readyProcesses.size();
			for (int i = 1; i <= listSize2; i++) {
				if (readyProcesses.get(i - 1).intID == processToDelete.intID) {
					listIndex3 = i - 1;
				}
			}
			readyProcesses.remove(listIndex3);
		}

	}

	public static void stopProcess(int intID) {
		int listIndex = 0;
		int listSize = processList.size();
		for (int i = 1; i <= listSize; i++) {
			if (processList.get(i - 1).intID == intID) {
				listIndex = i - 1;
			}
		}

		if (processList.get(listIndex).pState.equals("re")) {
			processList.get(listIndex).pState = "rs";
		}

		if (processList.get(listIndex).pState.equals("bl")) {
			processList.get(listIndex).pState = "bs";
		}

	}

	public static void activateProcess(int intID) {
		int listIndex = 0;
		int listSize = processList.size();
		for (int i = 1; i <= listSize; i++) {
			if (processList.get(i - 1).intID == intID) {
				listIndex = i - 1;
			}
		}

		if (processList.get(listIndex).pState.equals("rs")) {
			processList.get(listIndex).pState = "re";
		}

		if (processList.get(listIndex).pState.equals("bs")) {
			processList.get(listIndex).pState = "bl";
		}
	}

	// Resource primitives
	public static void createResource(int resourceNo, int creatorID) {
		Resource newResource = null;
		id2++;
		System.out.println("Create resource" + resourceNo);
		// create Supervizorine atmintis
		if (resourceNo == 2) {
			newResource = new SupervisorMemory(id2, "SupervisorMemory",
					creatorID);
		}

		// create Vartotojo atmintis
		if (resourceNo == 3) {
			newResource = new UserMemory(id2, "UserMemory", creatorID);
			resourceAmounts[resourceNo] = 50;
		}

		// create Kietojo disko atmintis
		if (resourceNo == 4) {
			newResource = new HardDriveMemory(id2, "HardDriveMemory",
					creatorID);
		}

		// create Spausdintuvas
		if (resourceNo == 5) {
			newResource = new Printer(id2, "Printer", creatorID);
		}

		// create Flash irenginys
		if (resourceNo == 6) {
			newResource = new Flash(id2, "Flash", creatorID);
		}

		// create Uzduotis kietajame diske
		if (resourceNo == 7) {
			newResource = new TaskInHardDrive(id2, "TaskInHardDrive", creatorID);
		}

		// create Uzduotis vartotojo atmintyje
		if (resourceNo == 9) {
			newResource = new TaskInUserMemory(id2, "TaskInUserMemory",
					creatorID);
		}

		// create Pranesimas apie identifikuota pertraukima
		// if (resourceNo == 10) {
		// newResource = new MessageAboutIdentifiedInterrupt(id,
		// "MessageAboutIdentifiedInterrupt", creatorID);
		// }

		// create Eilute atmintyje
		if (resourceNo == 19) {
			newResource = new LineInMemory(id, "LineInMemory", creatorID);
		}

		// create Pranesimas apie pertraukima
		if (resourceNo == 20) {
			newResource = new MessageAboutInterrupt(id,
					"MessageAboutInterrupt", creatorID);
		}

		// add to creators list
		int listIndex = 0;
		int listSize = processList.size();
		for (int i = 1; i <= listSize; i++) {
			if (processList.get(i - 1).intID == creatorID) {
				listIndex = i - 1;
			}
		}
		processList.get(listIndex).createdResList.add(newResource);
		// resourceAmounts[resourceNo] = 1;

		resourceList.add(newResource);

	}

	public static void deleteResource(int resourceID) {
		// Find in list
		int listIndex = 0;
		int listSize = resourceList.size();
		for (int i = 1; i <= listSize; i++) {
			if (resourceList.get(i - 1).intID == resourceID) {
				listIndex = i - 1;
			}
		}

		Resource resourceToDelete = resourceList.get(listIndex);

		// Find creator
		int creator = resourceToDelete.creatorProcess;
		int listIndex2 = 0;
		listSize = processList.size();
		for (int i = 1; i <= listSize; i++) {
			if (processList.get(i - 1).intID == creator) {
				listIndex2 = i - 1;
			}
		}
		Process creatorProcess = processList.get(listIndex2);

		// remove from creator's list
		int listIndex3 = 0;
		listSize = creatorProcess.createdResList.size();
		for (int i = 1; i <= listSize; i++) {
			if (creatorProcess.createdResList.get(i - 1).intID == resourceID) {
				listIndex3 = i - 1;
			}
		}
		creatorProcess.createdResList.remove(listIndex3);

		// Remove resource from all resource list
		resourceList.remove(listIndex);
		// Remove resource from current owners list
		Process owner = findProcessByIntIdInList(processList,resourceToDelete.user.intID);
		if (owner != null) {
			
			int listIndex4 = 0;
			listSize = owner.ownedResList.size();
			for (int i = 1; i <= listSize; i++) {
				if (owner.ownedResList.get(i - 1).intID == resourceToDelete.intID) {
					listIndex4 = i - 1;
				}
			}
			owner.ownedResList.remove(listIndex4);
		}
	
	
	
	}

	public static void freeResource(ArrayList<Process> list, int resourceNo,
			Resource freedResource) {

		resourceAmounts[resourceNo]++;
		if (freedResource != null) {
			// Find last user
			int listIndex = 0;
			int listSize = freedResource.user.ownedResList.size();
			for (int i = 1; i <= listSize; i++) {
				if (freedResource.user.ownedResList.get(i - 1).intID == freedResource.intID) {
					listIndex = i - 1;
				}
			}
			freedResource.user.ownedResList.remove(listIndex);
			freedResource.user = null;
			ArrayList<Resource> resList = getResourceList(resourceNo);
			resList.add(freedResource);
		}

		distributor(list, resourceNo);

		// resourceAmounts[resourceNo]++;
		// if (freedResource != null) {
		// Find last user
		// int listIndex = 0;
		// int listSize = freedResource.user.ownedResList.size();
		// for (int i = 1; i <= listSize; i++) {
		// if (freedResource.user.ownedResList.get(i-1).intID ==
		// freedResource.intID) {
		// listIndex = i-1;
		// }
		// }
		// freedResource.user.ownedResList.remove(listIndex);
		// }

		// distributor(list, resourceNo);
	}

	public static void askForResource(ArrayList<Process> list, int resourceNo) {
		currentProcess.pState = "bl";
		list.add(currentProcess);

		distributor(list, resourceNo);
	}

	// Planner
	public static void planner() {

		System.out.println("Planner");
		if (currentProcess.pState.equals("ru")) {
			readyProcesses.add(currentProcess);
			currentProcess.pState = "re";
		}

		int numberOfReadyProcesses = readyProcesses.size();
		if (numberOfReadyProcesses == 0) {
			freeResource(waitingList1, 1, null);
		} else {
			int index = 0;
			int maxPriority = 0;
			for (int i = 1; i <= numberOfReadyProcesses; i++) {
				if ((readyProcesses.get(i - 1).priority > maxPriority)
						&& (readyProcesses.get(i - 1).pState.equals("re"))) {
					index = i - 1;
					maxPriority = readyProcesses.get(i - 1).priority;
				}
			}

			currentProcess = readyProcesses.get(index);
			readyProcesses.remove(index);
			currentProcess.pState = "ru";
			PyOS.timer = 10;
		}
	}

	// Resource distributor
	public static void distributor(ArrayList<Process> list, int resourceNumber) {

		System.out.println("Distributor");
		if ((list.size() != 0) && (resourceAmounts[resourceNumber] != 0)) {

			Process processToGetResource;
			ArrayList<Resource> ResList;

			if (resourceNumber == 10) {
				ResList = getResourceList(resourceNumber);
				Resource distributableResource = ResList.get(0);
				ResList.remove(0);
				// Find job gov
				int listIndex = 0;
				int listSize = list.size();
				MessageAboutIdentifiedInterrupt message = (MessageAboutIdentifiedInterrupt) distributableResource;
				for (int i = 1; i <= listSize; i++) {
					if (list.get(i - 1).intID == message.jobGovernorResponsibleForInt) {
						listIndex = i - 1;
					}
				}

				processToGetResource = list.get(listIndex);
				processToGetResource.receivedResource = resourceNumber;
				processToGetResource.neededResource = 0;
				processToGetResource.ownedResList.add(message);
				message.user = processToGetResource;
				list.remove(listIndex);
				readyProcesses.add(processToGetResource);
				processToGetResource.pState = "re";

			} else {

				processToGetResource = list.get(0);
				processToGetResource.receivedResource = resourceNumber;
				processToGetResource.neededResource = 0;
				if (processToGetResource.pState.equals("bs")) {
					processToGetResource.pState = "rs";
				} else {
					processToGetResource.pState = "re";
				}

				list.remove(0);
				readyProcesses.add(processToGetResource);

				// if not a message
				if (((resourceNumber >= 2) && (resourceNumber <= 10) && (resourceNumber != 8))
						|| (resourceNumber == 19) || (resourceNumber == 20)) {
					ResList = getResourceList(resourceNumber);
					Resource distributableResource = ResList.get(0);
					ResList.remove(0);
					processToGetResource.ownedResList
							.add(distributableResource);
					distributableResource.user = processToGetResource;
				}
			}
			resourceAmounts[resourceNumber]--;
		}
		planner();
	}

	// is this the correct usage of extId? Yes
	public static Resource findResource(ArrayList<Resource> resList,
			String resourceExtId) {
		for (int i = 0; i < resList.size(); i++) {
			Resource res = resList.get(i);
			if (res.extID.equals(resourceExtId)) {
				return res;
			}
		}
		return null;
	}

	public static Process findProcessByIntId(int intId) {
		for (int i = 0; i < processList.size(); i++) {
			Process proc = processList.get(i);
			if (proc.intID == intId) {
				return proc;
			}
		}
		return null;
	}

	public static Process findProcessByIntIdInList(ArrayList<Process> procList,
			int intId) {
		for (int i = 0; i < procList.size(); i++) {
			Process proc = procList.get(i);
			if (proc.intID == intId) {
				return proc;
			}
		}
		return null;
	}

	// Returns resource element list
	public static ArrayList<Resource> getResourceList(int resourceNo) {

		ArrayList<Resource> list = null;

		switch (resourceNo) {
		case 2:
			list = elementList2;
			break;
		case 3:
			list = elementList3;
			break;
		case 4:
			list = elementList4;
			break;
		case 5:
			list = elementList5;
			break;
		case 6:
			list = elementList6;
			break;
		case 7:
			list = elementList7;
			break;
		case 9:
			list = elementList9;
			break;
		case 10:
			list = elementList10;
			break;
		case 19:
			list = elementList19;
			break;
		case 20:
			list = elementList20;
			break;
		}

		return list;
	}

	public static int findChildrenListIndex(int intID) {
		// find in list
		int listIndex = 0;
		int listSize = processList.size();
		for (int i = 1; i <= listSize; i++) {
			if (processList.get(i - 1).intID == intID) {
				listIndex = i - 1;
			}
		}

		Process process = processList.get(listIndex);

		// find parent
		int listIndex2 = 0;
		listSize = processList.size();
		for (int i = 1; i <= listSize; i++) {
			if (processList.get(i - 1).intID == process.parentProcess) {
				listIndex2 = i - 1;
			}
		}
		// remove
		Process parent = processList.get(listIndex2);

		// find child index
		int listIndex3 = 0;
		listSize = parent.childrenList.size();
		for (int i = 1; i <= listSize; i++) {
			if (parent.childrenList.get(i - 1).intID == intID) {
				listIndex3 = i - 1;
			}
		}

		return listIndex3;
	}
}
