package os;
//TODO
public class JobGovernor extends Process {

	public JobGovernor(int intID, String extID, int parentProcess, int priority, String startState, Resource startingResource) {
		super(intID, extID, parentProcess, priority, startState);
		ownedResList.add(startingResource);
	}
	
	public void run() {
		
	}
	
}
