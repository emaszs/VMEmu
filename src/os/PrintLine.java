package os;

import java.io.IOException;

import machine.RM;
import res.LineInMemory;
import ui.OSUI;

//TODO
public class PrintLine extends Process {

	public PrintLine(int intID, String extID, int parentProcess, int priority,
			String startState) {
		super(intID, extID, parentProcess, priority, startState);
	}

	public void run() {

		// 1)ask for resource message for printLine
		if ((phase == 0) && (pState.equals("ru"))) {
			PyOS.askForResource(PyOS.waitingList15, 15);
			neededResource = 15;
			phase = 1;
		}

		// 2)ask for resource Line in memory
		if ((phase == 1) && (receivedResource == 15) && (pState.equals("ru"))) {
			PyOS.askForResource(PyOS.waitingList19, 19);
			neededResource = 19;
			phase = 2;
		}

		// 3)ask for resource Printer
		if ((phase == 2) && (receivedResource == 19) && (pState.equals("ru"))) {
			PyOS.askForResource(PyOS.waitingList5, 5);
			neededResource = 5;
			phase = 3;
		}

		// 4-5) Print line and free Printer
		// TODO
		if ((phase == 3) && (receivedResource == 5) && (pState.equals("ru"))) {
			neededResource = 0;

			LineInMemory line = (LineInMemory) ownedResList.get(ownedResList
					.size() - 1);

			try {
				OSUI.printer.write(line.line);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			RM.r1.setInt(RM.r1.getInt() + 1);
			RM.r2.setInt(RM.r2.getInt() - 1); // one iteration complete

			PyOS.freeResource(PyOS.waitingList5, 5,
					ownedResList.get(ownedResList.size() - 1));
			phase = 4;
		}

		// 6-7) free message from PrintLine
		if ((phase == 4) && (pState.equals("ru"))) {
			PyOS.freeResource(PyOS.waitingList11, 11, null);
			phase = 0;
		}
	}
}
