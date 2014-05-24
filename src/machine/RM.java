package machine;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import ui.Ui;
import memory.Word;

public final class RM {
	public static final int WORD_SIZE = 4;
	public static final int BLOCK_SIZE = 10;
	public static final int MEMORY_SIZE = 50;
	public static final int SUPERVISOR_SIZE = 10;

	public static Memory memory = new Memory();

	public static Word ptp = new Word(); // page table pointer
	public static Word r1 = new Word(), r2 = new Word();
	public static int ic; // 2 bytes instruction counter
	public static char[] sf = new char[2];// 2 bytes, OF and SF
	public static int sp = 0; // stack pointer
	public static char mode = '0'; // 0 - user, 1 - supervisor
	public static char pi = '0'; // program interrupts
	public static char[] si = { '0', '0', '0', '0' }; // supervisor interrupts
	public static int t = 0; // timer
	public static int sm = 0; // semaphore register
	public static int ti = 0; // timer interrupt
	public static int chstPrinter = 0, chstFlash = 0, chstHdd = 0; // channel
																	// status. 1
																	// - in use

	public static BufferedReader flash;
	public static BufferedWriter printer;

	/*public static void main(final String[] args) {

		memory.initMemory();

		memory.initAllocationInfo();

		memory.allocatePageTableToVM();

		memory.initSupervisorAllocationInfo();
		//System.out.println("Amount of free memory blocks left: "
		//		+ memory.getNumFreeBlocks());

		memory.allocateNumBlocksToVM(10);

		boolean loaded = false;
		try {
			flash = new BufferedReader(new FileReader(
					"C:/Users/Tomas/Desktop/gggg.txt"));
			loaded = Loader.loadProgram(flash);
			printer = new BufferedWriter(new FileWriter(
					"C:/Users/Tomas/Desktop/print.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// RM.r1.setString(Integer.toString(15));

		if (loaded) {

			Hdd.initFiles();
			Hdd.openFileForReading(0);

			
			Ui.workingProgram();
			
			
			
			
			
			
			
			
			// load
			//Processing.processCommand(Memory.getFromVirtualAddress(0));
			// cmp
			//Processing.processCommand(Memory.getFromVirtualAddress(RM.ic));
			//System.out.println("ZF: " + RM.sf[0] + " OF: " + RM.sf[1]);
			// jump
			//Processing.processCommand(Memory.getFromVirtualAddress(RM.ic));
			// out
			//RM.mode = '1';
			//Processing.processCommand(Memory.getFromVirtualAddress(RM.ic));
			//Processing.processCommand(Memory.getFromVirtualAddress(RM.ic));
			//Processing.processCommand(Memory.getFromVirtualAddress(RM.ic));
			//RM.mode = '0'; // input done
			//System.out.println("IC after output, should be 4: " + RM.ic);
			// halt
			//Processing.processCommand(Memory.getFromVirtualAddress(RM.ic));
			
			// clearing interrupts after IO
			//RM.si[0] = RM.si[1] = RM.si[2] = RM.si[3] = '0';

			//System.out.println("r1 value: " + RM.r1.getString());
			//System.out.println("r2 value: " + RM.r2.getString());
			//System.out.println("ic value: " + RM.ic);

			//System.out.println("Basic memory:");
			//memory.printMemory(0, 40);

			//System.out.println("Supervisor memory:");
			//memory.printMemory(40, 50);

			//System.out.println("Amount of free memory blocks left: "
			//		+ memory.getNumFreeBlocks());

			//System.out.println("0001 virtual value: "
			//		+ Memory.getFromVirtualAddress(1));

			try {
				flash.close();
				printer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/
	
	//TODO perdaryti
	public static void doStep() {
		
		
		Processing.processCommand(Memory.getFromVirtualAddress(RM.ic));
		
		t = t-1;
		if (t == 0) {
			ti = 1;
		}
		
		if (si[0] > '0' || si[1] > '0' || si[2] > '0' || si[3] > '0' || pi > '0' || ti > 0) {
			if (si[0] > '0' || si[1] > '0' || si[2] > '0' || si[3] > '0') {
				if (mode == '0') {
					mode = '1';
				}
				
				if (si[0] == '9' || si[1] == '9' || si[2] == '9' || si[3] == '9') {
					si[0] = '0';
					si[1] = '0';
					si[2] = '0';
					si[3] = '0';
					Ui.haltDetected();
				}
			}
			if (ti > 0) {
				Ui.outOfComputingTime();
				t = 10;
				ti = 0;
			}
			
			if (pi > '0') {
				Ui.divisionByZero();
			}
			
			
		}
	}
	
}
