package os;
import memory.Word;

public class RMState {

	public Word ptp;
	public Word r1;
	public Word r2;
	public int ic;
	public char[] sf;
	public int sp = 0;
	public int t = 0;
	
	public RMState() {
		
	}
	
	public void setState(Word ptp, Word r1, Word r2, int ic, char[] sf, int sp, int t, int ti) {
		this.ptp = ptp;
		this.r1 = r1;
		this.r2 = r2;
		this.ic = ic;
		this.sf = sf;
		this.sp = sp;
		this.t = t;
	}
	
}
