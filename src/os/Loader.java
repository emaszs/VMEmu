package os;
//TODO ****  Perduoti vartotojo atminti JobGov or smth
import machine.Memory;
import memory.Hdd;
import memory.LoaderHelper;
import res.HardDriveMemory;

//TODO
public class Loader extends Process {

	public Loader(int intID, String extID, int parentProcess, int priority,
			String startState) {
		super(intID, extID, parentProcess, priority, startState);
	}

	public void run() {

		// 1) asks for message from job governor
		if ((phase == 0) && (pState.equals("ru"))) {
			PyOS.askForResource(PyOS.waitingList8, 8);
			neededResource = 8;
			phase = 1;
		}

		// 2) asks for hard drive memory
		if ((phase == 1) && (receivedResource == 8) && (pState.equals("ru"))) {
			PyOS.askForResource(PyOS.waitingList4, 4);
			neededResource = 4;
			phase = 2;
		}

		// 3) asks for user memory
		if ((phase == 2) && (receivedResource == 4) && (pState.equals("ru"))) {
			PyOS.askForResource(PyOS.waitingList3, 3);
			neededResource = 3;
			phase = 3;
		}

		// 4-5) loads program and frees Task in user memory resource
		// TODO
		if ((phase == 3) && (receivedResource == 3) && (pState.equals("ru"))) {

			int VMProgramIC = 0;
			int fileNum = ((HardDriveMemory) PyOS.findResource(ownedResList,
					"HardDriveMemory")).fileId;
			String line = new String();
			while ((line = Hdd.readFromFile(fileNum)) != null) {

				if (LoaderHelper.isLegal(line)) {

					/*
					 * DSXY - data segment allocation. XY - virtual adress to
					 * store data
					 */
					if (line.matches("DS\\d\\d")) {
						int dataSegIC = 0;
						int adr = Integer.parseInt(line.substring(2, 4));
						line = Hdd.readFromFile(fileNum); // Skip DSXY line
						while (!line.matches("DSOV")) {
							Memory.writeToVirtualAddress(adr + dataSegIC, line);
							dataSegIC++;
							line = Hdd.readFromFile(fileNum);
						}
						line = Hdd.readFromFile(fileNum); // Skip DSOV line
					}

					if (line != null && line.matches("\\$STR")) {
						line = Hdd.readFromFile(fileNum);
					}

					if (line != null && line.matches("\\$END")) {
						break;
					}
					/*
					 * Checking once more if line isn't empty Required if Data
					 * Segment is at the end of the input flash drive.
					 */
					if (line != null) {
						Memory.writeToVirtualAddress(VMProgramIC, line);
						VMProgramIC++;
					}
				} else {
					System.out
							.println("Illegal syntax found! stopping loading.");
					System.out.println(line);
					break;
				}
			}
			// Nuspresk, ar Task in user memory tures svarbios info
			// Netures
			PyOS.freeResource(PyOS.waitingList9, 9, ownedResList.get(0));
			neededResource = 0;
			phase = 4;
		}

		// 6-7) free Hard drive memory resource
		// TODO
		if ((phase == 4) && (pState.equals("ru"))) {

			// Nuspresk, ar Hard drive memory tures svarios info
			// PyOS.freeResource(PyOS.waitingList9, 9, ownedResList.get(0));
			phase = 0;
		}
	}
}
