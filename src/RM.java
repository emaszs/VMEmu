import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public final class RM {
	public static final int WORD_SIZE = 4;
	public static final int BLOCK_SIZE = 10;
	public static final int MEMORY_SIZE = 50;
	public static final int SUPERVISOR_SIZE = 10;

	public static Memory memory = new Memory();

	public Word ptp = new Word(); // page table pointer
	public Word r1, r2 = new Word();
	public int ic; // 2 bytes instruction ocunter
	public Word sf = new Word(); // 2 bytes, OF and SF
	public int sp = 0; // stack pointer 
	public char mode = '0'; // 0 - user, 1 - supervisor
	public char pi = '0'; // program interrupts
	public char si = '0'; // supervisor interrupts
	public int t = 0; // timer
	public int chst1, chst2, chst3 = 0; //	

	
	public static void main(final String[] args) {
		RM basicRM = new RM();
		//RM newVM = new RM();

		memory.initMemory();

		memory.initAllocationInfo();
		
		memory.allocatePageTableToVM(basicRM);		
		Word basicRMptp = new Word();
		basicRMptp.setString(basicRM.ptp.getString());
		
		memory.allocatePageTableToVM(basicRM);
		Word newRMptp = new Word();
		newRMptp.setString(basicRM.ptp.getString());
		
		memory.initSupervisorAllocationInfo();
		System.out.println("Amount of free memory blocks left: "
				+ memory.getNumFreeBlocks());

		basicRM.ptp.setString(basicRMptp.getString());
		memory.allocateNumBlocksToVM(10, basicRM);
		
		basicRM.ptp.setString(newRMptp.getString());
		memory.allocateNumBlocksToVM(10, basicRM);
		
		BufferedReader flash;
		try {
			flash = new BufferedReader(new FileReader("C:/Users/user/Desktop/prog.txt"));
			memory.loadProgram(basicRM, flash);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Basic memory:");
		memory.printMemory(0, 40);
		
		
		System.out.println("Supervisor memory:");
		memory.printMemory(40, 50);

		System.out.println("Amount of free memory blocks left: "
				+ memory.getNumFreeBlocks());
			
			System.out.println("0011 virtual value: " + memory.getFromVirtualAddress(basicRM, 11));
		//
		// for (int i = 0; i < MEMORY_SIZE; i++) {
		// if (memory.allocatedBlocks[i] == true) {
		// System.out.println(i);
		// }
		// }
	}
}
