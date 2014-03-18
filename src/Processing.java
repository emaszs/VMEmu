public class Processing {
	public static void processCommand(String cmd) {
		// String cmd = wordCmd.getString();
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
		} else if (cmd.matches("AW\\d\\d")) {
			// TODO allocate word
		} else if (cmd.matches("PUSR")) {
			RM.sp = RM.sp - 1;
			RM.memory.writeToVirtualAddress(RM.sp, RM.r1.toString());
		} else if (cmd.matches("POPR")) {
			RM.r1.setString(RM.memory.getFromVirtualAddress(RM.sp));
			RM.sp = RM.sp + 1;
		} else if (cmd.matches("A1\\d\\d")) {
			System.out.println("Using addition to R1");
			int adr = Integer.parseInt(cmd.substring(2, 4));
			String valToAdd = RM.memory.getFromVirtualAddress(adr);
			String valR1 = RM.r1.getString();

			if (isNumeric(valToAdd) && isNumeric(valR1)) {
				int result = Integer.parseInt(valR1)
						+ Integer.parseInt(valToAdd);

				if (result > 9999) {
					RM.sf[1] = '1'; // Setting OF
					result = result % 10000;
				}
				if (result == 0) {
					RM.sf[0] = '1'; // Setting ZF
				}

				RM.r1.setString(Integer.toString(result));
			}
		} else if (cmd.matches("A2\\d\\d")) {
			int adr = Integer.parseInt(cmd.substring(2, 4));
			String valToAdd = RM.memory.getFromVirtualAddress(adr);
			String valR2 = RM.r2.getString();

			if (isNumeric(valToAdd) && isNumeric(valR2)) {
				int result = Integer.parseInt(valR2)
						+ Integer.parseInt(valToAdd);

				if (result > 9999) {
					RM.sf[1] = '1'; // Setting OF
					result = result % 10000;
				}
				if (result == 0) {
					RM.sf[0] = '1'; // Setting ZF
				}

				RM.r2.setString(Integer.toString(result));
			}
		} else if (cmd.matches("S1\\d\\d")) {
			int adr = Integer.parseInt(cmd.substring(2, 4));
			String valToSub = RM.memory.getFromVirtualAddress(adr);
			String valR1 = RM.r1.getString();

			if (isNumeric(valToSub) && isNumeric(valR1)) {
				int result = Integer.parseInt(valR1) - Integer.parseInt(valToSub);
				
				if (result < 0) {
					RM.sf[1] = '1'; // Setting OF
					result = result * (-1);
				}
				if (result == 0) {
					RM.sf[0] = '1'; // Setting ZF
				}
				
				RM.r1.setString(Integer.toString(result));
			}
		} else if (cmd.matches("S2\\d\\d")) {
			int adr = Integer.parseInt(cmd.substring(2, 4));
			String valToSub = RM.memory.getFromVirtualAddress(adr);
			String valR2 = RM.r2.getString();

			if (isNumeric(valToSub) && isNumeric(valR2)) {
				int result = Integer.parseInt(valR2) - Integer.parseInt(valToSub);
				
				if (result < 0) {
					RM.sf[1] = '1'; // Setting OF
					result = result * (-1);
				}
				if (result == 0) {
					RM.sf[0] = '1'; // Setting ZF
				}
				
				RM.r1.setString(Integer.toString(result));
			}
		} else if (cmd.matches("ML\\d\\d")) {
			int adr = Integer.parseInt(cmd.substring(2,4));
			String valMemory = RM.memory.getFromVirtualAddress(adr);
			String valR1 = RM.r1.getString();
			
			if (isNumeric(valMemory) && isNumeric(valR1)) {
				long result = Integer.parseInt(valR1) * Integer.parseInt(valMemory);
				
				if (result > 9999) {
					RM.sf[1] = '1'; // Setting OF
					result = result % 10000;
				}
				if (result == 0) {
					RM.sf[0] = '1'; // Setting ZF
				}
				RM.r1.setString(Long.toString(result));
			}
		} else if (cmd.matches("DV\\d\\d")) {
			int adr = Integer.parseInt(cmd.substring(2,4));
			String valMemory = RM.memory.getFromVirtualAddress(adr);
			String valR1 = RM.r1.getString();
			
			// Set interrupt for div by 0
			if (isNumeric(valMemory) && Integer.parseInt(valMemory) == 0) {
				RM.pi = '1';				
			} else if (isNumeric(valMemory) && isNumeric(valR1)) {
				int resultDiv = Integer.parseInt(valR1) / Integer.parseInt(valMemory);
				int resultMod = Integer.parseInt(valR1) % Integer.parseInt(valMemory);
				
				RM.r1.setString(Integer.toString(resultDiv));
				RM.r2.setString(Integer.toString(resultMod));
			}
		}
	}

	public static boolean isNumeric(String str) {
		return str.matches("\\d+"); // match a number
	}
}
