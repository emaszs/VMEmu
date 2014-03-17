public final class RM {
	public static final int WORD_SIZE = 4;
	public static final int BLOCK_SIZE = 10;
	public static final int MEMORY_SIZE = 50;
	public static final int SUPERVISOR_SIZE = 10;

	public static Memory memory = new Memory();

	public Word ptp = new Word();

	public static void main(final String[] args) {
		VM basicVM = new VM();
		VM newVM = new VM();

		memory.initMemory();

		memory.initAllocationInfo();
		memory.allocatePageTableToVM(basicVM);
		memory.allocatePageTableToVM(newVM);
		memory.initSupervisorAllocationInfo();
		System.out.println("Amount of free memory blocks left: "
				+ memory.getNumFreeBlocks());

		memory.allocateNumBlocksToVM(10, basicVM);
		memory.allocateNumBlocksToVM(10, newVM);

		System.out.println("Supervisor memory:");
		memory.printMemory(40, 50);

		System.out.println("Amount of free memory blocks left: "
				+ memory.getNumFreeBlocks());
		//
		// for (int i = 0; i < MEMORY_SIZE; i++) {
		// if (memory.allocatedBlocks[i] == true) {
		// System.out.println(i);
		// }
		// }
	}
}