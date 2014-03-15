
public class Word {
    private final char[] word = new char[VM.WORD_SIZE];

    public char getChar(final int idx) {
        return word[idx];
    }

    public void setChar(final int idx, final char val) {
        word[idx] = val;
    }
}
