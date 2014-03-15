public class Memory {

	public Word[] block = new Word[VM.BLOCK_SIZE];
	public Block[] memory = new Block[VM.MEMORY_SIZE];

	// Stores information about which blocks are in use by VMs.
	public boolean[] allocatedBlocks = new boolean[VM.MEMORY_SIZE];

	public void initAllocationInfo() {
		for (int i = 0; i < VM.MEMORY_SIZE; i++) {
			allocatedBlocks[i] = false;
		}
	}
	
	public void initSupervisorAllocationInfo() {
		int startIdx = VM.MEMORY_SIZE - VM.SUPERVISOR_SIZE;
		int endIdx = VM.MEMORY_SIZE;
		for (int i = startIdx; i < endIdx; i++) {
			allocatedBlocks[i] = true;
		}
	}

	public int getNumFreeBlocks() {
		int num = 0;
		for (int i = 0; i < VM.MEMORY_SIZE; i++) {
			if (allocatedBlocks[i] == false) {
				num++;
			}
		}
		return num;
	}

	public void allocateBlock(final int idx) {
		allocatedBlocks[idx] = true;
	}

	public void allocateNumOfRandomBlocks(final int numBlocksToAllocate) {

		int numSuccAllocated = 0;
		while (numSuccAllocated < numBlocksToAllocate) {

			int numFree = getNumFreeBlocks();

			// Generates a number between 1 and the number of free blocks
			int randomBlock = (int) Math
					.round((Math.random() * (numFree - 1)) + 1);
			System.out.println("Random number between 1 and " + numFree + " : "
					+ randomBlock);
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

				if (freeBlockCounter == randomBlock) {
					allocatedBlocks[blockCounter] = true;
				}
				blockCounter++;
			}
			numSuccAllocated++;
		}
	}
}
