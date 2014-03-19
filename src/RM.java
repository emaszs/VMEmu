import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
	public static int chst1, chst2, chst3 = 0; //

	public static BufferedReader flash;
	public static BufferedWriter printer;

	public static void main(final String[] args) {
		RM basicRM = new RM();

		memory.initMemory();

		memory.initAllocationInfo();

		memory.allocatePageTableToVM();

		memory.initSupervisorAllocationInfo();
		System.out.println("Amount of free memory blocks left: "
				+ memory.getNumFreeBlocks());

		memory.allocateNumBlocksToVM(10);

		try {
			flash = new BufferedReader(new FileReader(
					"C:/Users/Emilis/Desktop/prog.txt"));
			Loader.loadProgram(basicRM, flash);
			printer = new BufferedWriter(new FileWriter("C:/Users/Emilis/Desktop/print.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// RM.r1.setString(Integer.toString(15));

		Processing.processCommand(Memory.getFromVirtualAddress(0));
		Processing.processCommand(Memory.getFromVirtualAddress(1));
		Processing.processCommand(Memory.getFromVirtualAddress(1));
		Processing.processCommand(Memory.getFromVirtualAddress(1));
		
		Processing.processCommand(Memory.getFromVirtualAddress(2));
		Processing.processCommand(Memory.getFromVirtualAddress(3));
		Processing.processCommand(Memory.getFromVirtualAddress(3));

		System.out.println("r1 value: " + RM.r1.getString());
		System.out.println("r2 value: " + RM.r2.getString());
		System.out.println("ic value: " + RM.ic);

		System.out.println("Basic memory:");
		memory.printMemory(0, 40);

		System.out.println("Supervisor memory:");
		memory.printMemory(40, 50);

		System.out.println("Amount of free memory blocks left: "
				+ memory.getNumFreeBlocks());

		System.out.println("0001 virtual value: "
				+ Memory.getFromVirtualAddress(1));

		try {
			flash.close();
			printer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Hdd.initFiles();
		// Hdd.openFileForWriting(0);
		// Hdd.writeToFile(0, "WATT");
		// Hdd.writeToFile(0, "WATA");
		// Hdd.writeToFile(0, "WATA");
		//
		// Hdd.seekCursor(0, 5);
		// Hdd.writeToFile(0, "WOOT");
		// Hdd.seekCursor(0, 5);
		// Hdd.closeFile(0);
		//
		// Hdd.openFileForReading(0);
		// System.out.println(Hdd.readFromFile(0));
		//

	}
}
