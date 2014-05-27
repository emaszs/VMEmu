package ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import memory.Hdd;
import os.PyOS;
import os.StartStop;

public class OSUI {
	public static BufferedReader flash;
	public static BufferedWriter printer;

	public static void main(final String[] args) {
		Hdd.initFiles();
		startOS();
		//System.out.println(Hdd.readProgramFromFile(0));
	}

	public static void startOS() {
		try {
			flash = new BufferedReader(new FileReader(
					"C:/Users/Tomas/Desktop/gggg.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Scanner input = new Scanner(System.in);
		String key = new String();
		boolean isModeChosen = false;
		int modeChosen = 0;

		do {
			System.out
					.println("Execute (w)hole package or in (s)tep-by-step mode?");
			key = input.nextLine();

			switch (key) {
			case "w":
				System.out.println("Executing whole program");
				isModeChosen = true;
				modeChosen = 1;
				break;

			case "s":
				System.out.println("Using step-by-step mode");
				isModeChosen = true;
				modeChosen = 2;
				break;

			default:
				System.out.println("Unknown command, try again...");
			}
		} while (isModeChosen != true);

		if (modeChosen == 1) {
			PyOS.processList.add(new StartStop(1, "StartStop", 0, 100, "re"));
			PyOS.id = 1;
			PyOS.currentProcess = PyOS.processList.get(0);
			// main OS loop
			while (PyOS.MOSEnd != 1) {
				if (PyOS.timer > 0) {
					PyOS.currentProcess.run();
					PyOS.timer--;
				} else {
					PyOS.planner();
				}
			}
		} else if (modeChosen == 2) {
			do {
				// TODO beatiful & elegant UI
				// key = input.nextLine();
			} while (PyOS.MOSEnd != 1);
		}
		input.close();
	}
}
