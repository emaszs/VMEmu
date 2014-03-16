
public class Word {
    private char[] word = new char[RM.WORD_SIZE];

    public char getChar(final int idx) {
        return word[idx];
    }

    public void setChar(final int idx, final char val) {
        word[idx] = val;
    }
    
    public String getString() {
    	StringBuilder stringBuilder = new StringBuilder(RM.WORD_SIZE);
    	for (int i = 0; i < RM.WORD_SIZE; i++) {
    		stringBuilder.append(word[i]);
    	}
    	return stringBuilder.toString();
    }
    
    public void setString(String val) {
    	char[] charArray = new char[RM.WORD_SIZE];
    	charArray = val.substring(0, RM.WORD_SIZE).toCharArray();
    	word = charArray.clone();
    }
}
