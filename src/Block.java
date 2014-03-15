
public class Block {
    private final Word[] block = new Word[VM.BLOCK_SIZE];

    public Word getWord(final int idx) {
        return block[idx];
    }

    public void setWord(final int idx, final Word val) {
        block[idx] = val;
    }
}
