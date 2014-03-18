public class Word {
	private String word = new String();

	public char getChar(final int idx) {
		return word.charAt(idx);
	}

	// Unfinished - TODO if needed
	// public void setChar(final int idx, final char val) {
	// String temp = new String();
	// temp = this.word;
	// word[idx] = val;
	// }

	public String getString() {
		return this.word;
	}

	public void setString(String val) {
		if (val.length() > RM.WORD_SIZE) {
			this.word = val.substring(0, RM.WORD_SIZE);
		} else if (val.length() < RM.WORD_SIZE) { 
			String zeroString = new String();
			for (int i = 0; i < RM.WORD_SIZE - val.length(); i++) {
				zeroString += "0";
			}
			this.word = zeroString + val;
		}
		else {
			this.word = val;
		}
	}
	
	public String toString() {
		return this.word;
	}
}
