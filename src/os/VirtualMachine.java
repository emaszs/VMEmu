package os;

public class VirtualMachine extends Process {

	public VirtualMachine(int intID, String extID, int parentProcess, int priority, String startState) {
		super(intID, extID, parentProcess, priority, startState); 
	}
	
	public void run() {
		
	}
}
