public class Memory {

	public Word[] block = new Word[RM.BLOCK_SIZE];
	public Block[] memory = new Block[RM.MEMORY_SIZE];

	// Stores information about which blocks are in use by VMs.
	public boolean[] allocatedBlocks = new boolean[RM.MEMORY_SIZE];

	public void initMemory() {

		for (int i = 0; i < RM.MEMORY_SIZE; i++) {
			memory[i] = new Block();
			memory[i].initBlock();
			for (int j = 0; j < RM.BLOCK_SIZE; j++) {
				memory[i].getWord(j).setString("0000");
			}
		}
	}

	public void printMemory() {
		this.printMemory(0, RM.MEMORY_SIZE);
	}

	public void printMemory(int startIdx, int endIdx) {
		for (int i = startIdx; i < endIdx; i++) {
			String line = new String();
			for (int j = 0; j < RM.BLOCK_SIZE; j++) {
				line += memory[i].getWord(j).getString() + " ";
			}
			System.out.println(line);
		}
	}

	public void initAllocationInfo() {
		for (int i = 0; i < RM.MEMORY_SIZE; i++) {
			allocatedBlocks[i] = false;
		}
	}

	public void initSupervisorAllocationInfo() {
		int startIdx = RM.MEMORY_SIZE - RM.SUPERVISOR_SIZE;
		int endIdx = RM.MEMORY_SIZE;
		for (int i = startIdx; i < endIdx; i++) {
			allocatedBlocks[i] = true;
		}
	}

	public int getNumFreeBlocks() {
		int num = 0;
		for (int i = 0; i < RM.MEMORY_SIZE; i++) {
			if (allocatedBlocks[i] == false) {
				num++;
			}
		}
		return num;
	}

	public void allocateBlock(final int idx) {
		allocatedBlocks[idx] = true;
	}

	public void allocateNumBlocksToVM(final int numBlocksToAllocate, VM vm) {

		int numSuccAllocated = 0;
		int pageTableCounter = 0;

		while (numSuccAllocated < numBlocksToAllocate) {

			int numFree = getNumFreeBlocks();

			// Generates a number between 1 and the number of free blocks
			int randomBlock = (int) Math
					.round((Math.random() * (numFree - 1)) + 1);
			// System.out.println("Random number between 1 and " + numFree +
			// " : "
			// + randomBlock);
			int freeBlockCounter = 0;
			int blockCounter = 0;

			/*
			 * Finds and allocates the nth free block of memory (randomBlock) by
			 * counting amount of free blocks found (freeBlockCounter).
			 * 
			 * If randomBlock == 1, allocates the 1st found free block. 2 - 2nd
			 * block, etc.
			 */
			while (freeBlockCounter < randomBlock) {
				if (allocatedBlocks[blockCounter] == false) {
					freeBlockCounter++;
				}

				// allocate block
				if (freeBlockCounter == randomBlock) {
					allocatedBlocks[blockCounter] = true;
					int pageTableBlockAddress = Character
							.getNumericValue(vm.ptp.getChar(1))
							* 10
							+ Character.getNumericValue(vm.ptp.getChar(2));

					// Sets value in page table for allocated block
					String word = new String();
					if (blockCounter < 10) {
						word += "0";
					}
					word += "0" + Integer.toString(blockCounter) + "0";
					this.memory[pageTableBlockAddress]
							.getWord(pageTableCounter).setString(word);

				}
				blockCounter++;
			}
			pageTableCounter++;
			numSuccAllocated++;
		}
	}

	public void allocatePageTableToVM(VM vm) {
		// Find free block in supervisor memory
		boolean blockFound = false;
		for (int i = RM.MEMORY_SIZE - RM.SUPERVISOR_SIZE; i < RM.MEMORY_SIZE; i++) {
			if (allocatedBlocks[i] == false && !blockFound) {
				blockFound = true;
				allocatedBlocks[i] = true;
				vm.ptp.setString("0" + Integer.toString(i) + "0");
				break;
			}
		}
		if (!blockFound) {
			System.out.println("Could not find free block in SM");
		}
	}
}
