package res;

//TODO
public class MessageAboutIdentifiedInterrupt extends Resource {
	public String interruptType = new String();
	public int jobGovernorResponsibleForInt = 0;

	public MessageAboutIdentifiedInterrupt(int intID, String extID, int creatorProcess, String interruptType, int jobGovernorResponsibleForInt) {
		super(intID, extID, creatorProcess);
		this.interruptType = interruptType;
		this.jobGovernorResponsibleForInt = jobGovernorResponsibleForInt;
		
	}
}
