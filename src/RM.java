import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public final class RM {
	public static final int WORD_SIZE = 4;
	public static final int BLOCK_SIZE = 10;
	public static final int MEMORY_SIZE = 50;
	public static final int SUPERVISOR_SIZE = 10;

	public static Memory memory = new Memory();

	public static Word ptp = new Word(); // page table pointer
	public static Word r1 = new Word(), r2 = new Word();
	public static int ic; // 2 bytes instruction ocunter
	public static char[] sf = new char[2];// 2 bytes, OF and SF
	public static int sp = 0; // stack pointer
	public static char mode = '0'; // 0 - user, 1 - supervisor
	public static char pi = '0'; // program interrupts
	public static char si = '0'; // supervisor interrupts
	public static int t = 0; // timer
	public static int chst1, chst2, chst3 = 0; //

	public static void main(final String[] args) {
		RM basicRM = new RM();
		// RM newVM = new RM();

		memory.initMemory();

		memory.initAllocationInfo();

		memory.allocatePageTableToVM();
		Word basicRMptp = new Word();
		basicRMptp.setString(RM.ptp.getString());

//		memory.allocatePageTableToVM();
//		Word newRMptp = new Word();
//		newRMptp.setString(RM.ptp.getString());

		memory.initSupervisorAllocationInfo();
		System.out.println("Amount of free memory blocks left: "
				+ memory.getNumFreeBlocks());

		RM.ptp.setString(basicRMptp.getString());
		memory.allocateNumBlocksToVM(10);

//		RM.ptp.setString(newRMptp.getString());
//		memory.allocateNumBlocksToVM(10);

		BufferedReader flash;
		try {
			flash = new BufferedReader(new FileReader(
					"C:/Users/user/Desktop/prog.txt"));
			RM.ptp.setString(basicRMptp.getString());
			memory.loadProgram(basicRM, flash);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Processing.processCommand(memory.getFromVirtualAddress(1));
		Processing.processCommand(memory.getFromVirtualAddress(2));

		System.out.println("sp value: " + RM.sp);
		System.out.println("r1 value: " + RM.r1.getString());
		
		System.out.println("Basic memory:");
		memory.printMemory(0, 40);

		System.out.println("Supervisor memory:");
		memory.printMemory(40, 50);

		System.out.println("Amount of free memory blocks left: "
				+ memory.getNumFreeBlocks());

		System.out.println("0011 virtual value: "
				+ memory.getFromVirtualAddress(11));
		

		//
		// for (int i = 0; i < MEMORY_SIZE; i++) {
		// if (memory.allocatedBlocks[i] == true) {
		// System.out.println(i);
		// }
		// }
	}
}
