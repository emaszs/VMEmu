package ui;

import os.PyOS;
import os.StartStop;

public class OSUI {
	public static void main(final String[] args) {	
		startOS();
	}

	public static void startOS() {
		PyOS.processList.add(new StartStop(1, "StartStop", 0, 100, "re"));
		PyOS.id = 1;
		
		PyOS.planner();
		
		while (PyOS.timer > 0) {
			PyOS.currentProcess.run();
		}
		
		PyOS.currentProcess.run();
	}
	
}
