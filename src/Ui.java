import java.util.Scanner;

public class Ui {
	private static String key;
	private static boolean end = false;
	
	public static void workingProgram() {
		Scanner input = new Scanner(System.in);
		
		RM.t = 10;
		
		do {
			System.out.println("/n/n/n/n/n/n/n/n");
			System.out.println("**************************************************");
			
			System.out.println("Basic memory:");
			RM.memory.printMemory(0, 40);

			System.out.println("Supervisor memory:");
			RM.memory.printMemory(40, 50);
		
			System.out.println("Amount of free memory blocks left: "
				+ RM.memory.getNumFreeBlocks());
		
			System.out.println("Registers:");
			System.out.println("IC: " + RM.ic);
			System.out.println("PTP: " + RM.ptp);
			System.out.println("R1: " + RM.r1);
			System.out.println("R2: " + RM.r2);
			System.out.println("T: " + RM.t);
			System.out.println("ZF: " + RM.sf[0] + " OF: " + RM.sf[1]);
			System.out.println("MODE: " + RM.mode);
			System.out.println("CHST1: " + RM.chstFlash);
			System.out.println("CHST2: " + RM.chstPrinter);
			System.out.println("CHST3: " + RM.chstHdd);
			System.out.println("PI: " + RM.pi);
			System.out.println("SI " + RM.si[0] + RM.si[1] + RM.si[2] + RM.si[3]);
			System.out.println("TI: " + RM.ti);
		
			System.out.println("Commands: n - next step, q - quit");
			key = input.nextLine();
			switch(key) {
			
			case "n": {
				RM.doStep();
			}
			break;
			
			case "q": {
				end = true;
			}
			break;
			
			
			}
		
		
		} while(end != true);
		
		System.out.println("Ending processor work.");
		
		try {
			input.close();
		} catch(IllegalStateException e) {
			e.printStackTrace();
		}
	}
	
	public static void outOfComputingTime() {
		System.out.println("Program is out of computing time, allocating more.");
	}
	
	public static void divisionByZero() {
		System.out.println("Program error: encountered division by zero!");
		end = true;
	}
	
	public static void haltDetected() {
		System.out.println("Halt instruction detected, ending virtual machine work.");
		end = true;
	}
	
}
