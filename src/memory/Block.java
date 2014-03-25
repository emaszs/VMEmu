package memory;
import machine.RM;


public class Block {
    private final Word[] block = new Word[RM.BLOCK_SIZE];

    public Word getWord(final int idx) {
        return block[idx];
    }

    public void setWord(final int idx, final Word val) {
        block[idx] = val;
    }
    
    public void initBlock() {
    	for (int i = 0; i < RM.BLOCK_SIZE; i++) {
    		this.block[i] = new Word();
    	}
    }
}
