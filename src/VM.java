public final class VM {
    public static final int WORD_SIZE = 4;
    public static final int BLOCK_SIZE = 10;
    public static final int MEMORY_SIZE = 50;
    public static final int SUPERVISOR_SIZE = 10;

    public static Memory memory = new Memory();

    public static void main(final String[] args) {
        memory.initAllocationInfo();
        System.out.println(memory.getNumFreeBlocks());
        memory.allocateNumOfRandomBlocks(10);
        System.out.println(memory.getNumFreeBlocks());
        //
        // for (int i = 0; i < MEMORY_SIZE; i++) {
        // if (memory.allocatedBlocks[i] == true) {
        // System.out.println(i);
        // }
        // }

    }
}
