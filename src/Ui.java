import java.util.Scanner;

public class Ui {
	private static String key;
	private static boolean end = false;
	private static boolean isModeChosen = false;
	private static int modeChosen = 0;
	
	public static void workingProgram() {
		Scanner input = new Scanner(System.in);
		
		RM.t = 10;
		//  program execution as a whole or by steps
		//  Showing VM memory, OA memory by block number
		//  Showing current command
		System.out.println("*****************************");
		System.out.println("*******Programa loaded*******");
		System.out.println("*****************************");
		
		do {
			System.out.println("Execute (w)hole program or in (s)tep-by-step mode?");
			key = input.nextLine();
			
			switch(key) {
			case "w" : {
				System.out.println("Executing whole program");
				isModeChosen = true;
				modeChosen = 1;
			}
			break;
			
			case "s" : {
				System.out.println("Using step-by-step mode");
				isModeChosen = true;
				modeChosen = 2;
			}
			break;
			
			default: {
				System.out.println("Unknown command, try again...");
			}
			}
		} while(isModeChosen != true);
		
		if (modeChosen == 1) {
			wholeExecution();
		}
		
		
		//do {
			//System.out.println("Amount of free memory blocks left: "
			//	+ RM.memory.getNumFreeBlocks());
			//printRegisters() ;
			//System.out.println("Commands: n - next step, q - quit");
			//key = input.nextLine();
			//switch(key) {
			//case "n": {
			//	RM.doStep();
			//}
			//break;
			//case "q": {
			//	end = true;
			//}
			//break;
			//}
		//} while(end != true);
		
		System.out.println("Ending processor work.");
		
		try {
			input.close();
		} catch(IllegalStateException e) {
			e.printStackTrace();
		}
	}
	
	public static void outOfComputingTime() {
		System.out.println("Program is out of computing time, allocating more.");
		System.out.println(RM.ic);
		System.out.println(Memory.getFromVirtualAddress(RM.ic));
	}
	
	public static void divisionByZero() {
		System.out.println("Program error: encountered division by zero!");
		end = true;
	}
	
	public static void haltDetected() {
		System.out.println("Halt instruction detected, ending virtual machine work.");
		end = true;
	}
	
	public static void printRegisters() {
		System.out.println("Registers:");
		System.out.println("IC: " + RM.ic);
		System.out.println("PTP: " + RM.ptp);
		if (RM.r1.getString().equals("")) {
			System.out.print("R1: " + "0000");
		}
		else {
			System.out.print("R1: " + RM.r1);
		}
		
		if (RM.r2.getString().equals("")) {
			System.out.println(" R2: " + "0000");
		}
		else {
			System.out.println(" R2: " + RM.r2);
		}

		System.out.println("T: " + RM.t);
		
		if (RM.sf[0] == '\u0000') {
			System.out.print("ZF: " + '0');
		}
		else {
			System.out.print("ZF: " + RM.sf[0]);
		}
		
		if (RM.sf[1] == '\u0000') {
			System.out.println(" OF: " + '0');
		}
		else {
			System.out.println(" OF: " + RM.sf[1]);
		}
		System.out.println("MODE: " + RM.mode);
		System.out.print("CHST1: " + RM.chstFlash);
		System.out.print(" CHST2: " + RM.chstPrinter);
		System.out.println(" CHST3: " + RM.chstHdd);
		System.out.println("PI: " + RM.pi);
		System.out.println("SI " + RM.si[0] + RM.si[1] + RM.si[2] + RM.si[3]);
		System.out.println("TI: " + RM.ti);
		System.out.println("SM: " + RM.sm);
	}
	
	public static void wholeExecution() {
		String enter;
		Scanner scan = new Scanner(System.in);
		System.out.println("*************************");
		System.out.println("*Processor and memory before execution*");
		System.out.println("Basic memory:");
		printAll();
		System.out.println("Press enter to start execution...");
		enter = scan.nextLine();
		
		System.out.println("Executing program...");
		do {
			RM.doStep();
		} while (end != true);
		
		System.out.println("Execution finished. Press enter to continue...");
		enter = scan.nextLine();
		System.out.println("*************************");
		System.out.println("*Processor and memory after execution*");
		printAll();
		
		try {
			scan.close();
		} catch(IllegalStateException e) {
			e.printStackTrace();
		}
	}
	
	public static void printAll() {
		System.out.println("Basic memory:");
		RM.memory.printMemory(0, 40);
		System.out.println("Supervisor memory:");
		RM.memory.printMemory(40, 50);
		printRegisters();
	}
	
}
