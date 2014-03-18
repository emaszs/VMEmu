
public class Processing {
	public static void processCommand(String cmd) {
//		String cmd = wordCmd.getString();
		if (cmd.length() > RM.WORD_SIZE) {
			cmd = cmd.substring(0, RM.WORD_SIZE);
		}
		
		if (cmd.matches("LR\\d\\d")) {
			int adr = Integer.parseInt(cmd.substring(2, 4));
			String val = new String();
			val = RM.memory.getFromVirtualAddress(adr);
			RM.r1.setString(val);
		} else if (cmd.matches("SR\\d\\d")) {
			int adr = Integer.parseInt(cmd.substring(2, 4));
			String val = RM.r1.getString();
			RM.memory.writeToVirtualAddress(adr, val);	
		}
			
	}
}
