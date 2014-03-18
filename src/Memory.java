import java.io.BufferedReader;
import java.io.IOException;

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

	public void printMemory(final int startIdx, final int endIdx) {
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

	/*
	 * Finds a number of free random blocks in memory, allocates them to the VM
	 * and fills out it's page table accordingly.
	 */
	public void allocateNumBlocksToVM(final int numBlocksToAllocate) {

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
							.getNumericValue(RM.ptp.getChar(1))
							* 10
							+ Character.getNumericValue(RM.ptp.getChar(2));

					// Constructing string of Word format
					String allocatedBlockAdr = new String();
					if (blockCounter < 10) {
						allocatedBlockAdr += "0";
					}
					allocatedBlockAdr += "0" + Integer.toString(blockCounter)
							+ "0";

					// Sets value in page table for allocated block
					int realAddress = pageTableBlockAddress * 10
							+ pageTableCounter;
					writeToRealAddress(realAddress, allocatedBlockAdr);
				}
				blockCounter++;
			}
			pageTableCounter++;
			numSuccAllocated++;
			
			// TODO init stackpointer
			RM.sp = 99;
		}
	}

	public void allocatePageTableToVM() {
		// Find free block in supervisor memory
		boolean blockFound = false;
		for (int i = RM.MEMORY_SIZE - RM.SUPERVISOR_SIZE; i < RM.MEMORY_SIZE; i++) {
			if (allocatedBlocks[i] == false && !blockFound) {
				blockFound = true;
				allocatedBlocks[i] = true;
				RM.ptp.setString("0" + Integer.toString(i) + "0");
				break;
			}
		}
		if (!blockFound) {
			System.out.println("Could not find free block in SM");
		}
	}

	public String getFromRealAddress(int realAdr) {
		return this.memory[realAdr / 10].getWord(realAdr % 10).getString();
	}
	
	public String getFromVirtualAddress(int virtualAdr) {

		int realAdr = this.translateFromVirtualToReal(virtualAdr);
		return this.getFromRealAddress(realAdr);
	}

	public int translateFromVirtualToReal(int virtualAdr) {
		int realAdr = 0;
		int virtualBlock = 0;
		int pageAdr = 0;
		int pageTableAdr = Integer.parseInt(RM.ptp.getString());

		virtualBlock = virtualAdr / 10;
		pageAdr = Integer.parseInt(this.memory[pageTableAdr / 10].getWord(
				virtualBlock).getString());
		realAdr = pageAdr + virtualAdr % 10;

		// TODO
		return realAdr;
	}

	public void writeToRealAddress(final int adr, final String val) {
		this.memory[adr / 10].getWord(adr % 10).setString(val);
	}

	public void writeToVirtualAddress(final int virtualAdr,
			final String val) {
		int realAdr = this.translateFromVirtualToReal(virtualAdr);
		this.writeToRealAddress(realAdr, val);
	}

	public void loadProgram(final RM RM, final BufferedReader flash) {
		int VMProgramIC = 0;
		String line = null;
		try {
			while ((line = flash.readLine()) != null) {
				this.writeToVirtualAddress(VMProgramIC, line);
				VMProgramIC++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
