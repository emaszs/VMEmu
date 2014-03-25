package ui;
import java.util.InputMismatchException;
import java.util.Scanner;

import machine.Memory;
import machine.RM;

public class Ui {
	private static String key;
	private static boolean end = false;
	private static boolean isModeChosen = false;
	private static int modeChosen = 0;

	public static void workingProgram() {
		Scanner input = new Scanner(System.in);

		RM.sf[0] = '0';
		RM.sf[1] = '0';
		RM.t = 10;
		// program execution as a whole or by steps
		// Showing VM memory, OA memory by block number
		// Showing current command
		System.out.println("*****************************");
		System.out.println("*******Programa loaded*******");
		System.out.println("*****************************");

		do {
			System.out
					.println("Execute (w)hole program or in (s)tep-by-step mode?");
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
			wholeExecution();
		} else if (modeChosen == 2) {
			do {
				System.out.println("*****************************");
				System.out.println("*Executing program in step-by-step mode*");
				printRegisters();
				System.out.println("Current command: "
						+ Memory.getFromVirtualAddress(RM.ic));
				if (Memory.getFromVirtualAddress(RM.ic).matches("HALT") != true) {
					System.out.println("Next command: "
							+ Memory.getFromVirtualAddress(RM.ic + 1));
				}
				System.out.println("Choose command:");
				System.out.println("Print (a)ll memory ");
				System.out.println("Print memory (b)lock");
				System.out.println("Print (v)irtual memory");
				System.out.println("(E)xecute step");
				System.out.println("Print the amount of (f)ree memory");
				key = input.nextLine();
				switch (key) {
				case "a":
					System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					printAllMemory();
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					break;

				case "b":
					int blockNumber = 0;
					boolean good = false;

					do {
						System.out
								.println("Number of memory block you need printed:");
						try {
							blockNumber = input.nextInt();
							if (blockNumber > 49 || blockNumber < 0) {
								System.out
										.println("Block number out of range, must be between 0 and 49.");
							} else {
								good = true;
							}
						} catch (InputMismatchException e) {
							System.out
									.println("Wrong number format, please try again...");
						}
					} while (good != true);
					System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					RM.memory.printMemory(blockNumber, blockNumber + 1);
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					break;

				case "v":
					System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					printVirtualMemory();
					System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
					break;

				case "e":
					RM.doStep();
					break;

				case "f":
					System.out.println("Amount of free memory blocks left: "
							+ RM.memory.getNumFreeBlocks());
					break;

				default:
					System.out.println("Unknown command, please try again!");
				}

			} while (end != true);
		}

		System.out.println("Ending processor work.");

		try {
			input.close();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	public static void outOfComputingTime() {
		System.out
				.println("Program is out of computing time, allocating more.");
		// System.out.println(RM.ic);
		// System.out.println(Memory.getFromVirtualAddress(RM.ic));
	}

	public static void divisionByZero() {
		System.out.println("Program error: encountered division by zero!");
		end = true;
	}

	public static void haltDetected() {
		System.out
				.println("Halt instruction detected, ending virtual machine work.");
		end = true;
	}

	public static void printRegisters() {
		System.out.println("Registers:");
		System.out.print("IC: " + RM.ic);
		System.out.println(" PTP: " + RM.ptp);
		if (RM.r1.getString().equals("")) {
			System.out.print("R1: " + "0000");
		} else {
			System.out.print("R1: " + RM.r1);
		}

		if (RM.r2.getString().equals("")) {
			System.out.println(" R2: " + "0000");
		} else {
			System.out.println(" R2: " + RM.r2);
		}

		System.out.println("T: " + RM.t);

		System.out.print("ZF: " + RM.sf[0]);
		System.out.println(" OF: " + RM.sf[1]);

		System.out.println("MODE: " + RM.mode);
		System.out.print("CHST1: " + RM.chstFlash);
		System.out.print(" CHST2: " + RM.chstPrinter);
		System.out.println(" CHST3: " + RM.chstHdd);
		System.out.print("PI: " + RM.pi);
		System.out.print(" SI " + RM.si[0] + RM.si[1] + RM.si[2] + RM.si[3]);
		System.out.print(" TI: " + RM.ti);
		System.out.println(" SM: " + RM.sm);

	}

	public static void printAllMemory() {
		System.out.println("Basic memory:");
		RM.memory.printMemory(0, 40);
		System.out.println("Supervisor memory:");
		RM.memory.printMemory(40, 50);
	}

	public static void wholeExecution() {
		Scanner scan = new Scanner(System.in);
		System.out.println("*************************");
		System.out.println("*Processor and memory before execution*");
		printAll();
		System.out.println("Press enter to start execution...");
		scan.nextLine();
		System.out.println("*************************");
		System.out.println("Executing program...");
		do {
			RM.doStep();
		} while (end != true);

		System.out.println("Execution finished. Press enter to continue...");
		scan.nextLine();
		System.out.println("*************************");
		System.out.println("*Processor and memory after execution*");
		printAll();

		try {
			scan.close();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	public static void printAll() {
		printAllMemory();
		printRegisters();
	}

	public static void printVirtualMemory() {
		for (int i = 0; i < 100; i++) {
			if (i % 10 == 0 && i != 0) {
				System.out.println();
			}
			System.out.print(" " + Memory.getFromVirtualAddress(i));
		}
		System.out.println();
	}

}
