public class Memory {

    public Word[] block = new Word[VM.BLOCK_SIZE];
    public Block[] memory = new Block[VM.MEMORY_SIZE];

    public boolean[] allocatedBlocks = new boolean[VM.MEMORY_SIZE];

    public void initAllocationInfo() {
        for (int i = 0; i < VM.MEMORY_SIZE; i++) {
            allocatedBlocks[i] = false;
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
            int randomBlock = (int) Math.round((Math.random() * (numFree - 1)) + 1);
            System.out.println("Random number between 0 and " + numFree + " : " + randomBlock);
            int freeBlockCounter = 0;
            int blockCounter = 0;

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
