import java.io.IOException;

public class Processing {
	public static void processCommand(String cmd) {
		// String cmd = wordCmd.getString();
		if (cmd.length() > RM.WORD_SIZE) {
			cmd = cmd.substring(0, RM.WORD_SIZE);
		}

		if (cmd.matches("LR\\d\\d")) {
			int adr = Integer.parseInt(cmd.substring(2, 4));
			String val = new String();
			val = Memory.getFromVirtualAddress(adr);
			RM.r1.setString(val);
			RM.ic++;
		} else if (cmd.matches("SR\\d\\d")) {
			int adr = Integer.parseInt(cmd.substring(2, 4));
			String val = RM.r1.getString();
			Memory.writeToVirtualAddress(adr, val);
			RM.ic++;
		} else if (cmd.matches("PUSR")) {
			RM.sp = RM.sp - 1;
			Memory.writeToVirtualAddress(RM.sp, RM.r1.toString());
			RM.ic++;
		} else if (cmd.matches("POPR")) {
			RM.r1.setString(Memory.getFromVirtualAddress(RM.sp));
			RM.sp = RM.sp + 1;
			RM.ic++;
		} else if (cmd.matches("A1\\d\\d")) {
			System.out.println("Using addition to R1");
			int adr = Integer.parseInt(cmd.substring(2, 4));
			String valToAdd = Memory.getFromVirtualAddress(adr);
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
				RM.ic++;
			}
		} else if (cmd.matches("A2\\d\\d")) {
			int adr = Integer.parseInt(cmd.substring(2, 4));
			String valToAdd = Memory.getFromVirtualAddress(adr);
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
				RM.ic++;
			}
		} else if (cmd.matches("S1\\d\\d")) {
			int adr = Integer.parseInt(cmd.substring(2, 4));
			String valToSub = Memory.getFromVirtualAddress(adr);
			String valR1 = RM.r1.getString();

			if (isNumeric(valToSub) && isNumeric(valR1)) {
				int result = Integer.parseInt(valR1)
						- Integer.parseInt(valToSub);

				if (result < 0) {
					RM.sf[1] = '1'; // Setting OF
					result = result * (-1);
				}
				if (result == 0) {
					RM.sf[0] = '1'; // Setting ZF
				}

				RM.r1.setString(Integer.toString(result));
				RM.ic++;
			}
		} else if (cmd.matches("S2\\d\\d")) {
			int adr = Integer.parseInt(cmd.substring(2, 4));
			String valToSub = Memory.getFromVirtualAddress(adr);
			String valR2 = RM.r2.getString();

			if (isNumeric(valToSub) && isNumeric(valR2)) {
				int result = Integer.parseInt(valR2)
						- Integer.parseInt(valToSub);

				if (result < 0) {
					RM.sf[1] = '1'; // Setting OF
					result = result * (-1);
				}
				if (result == 0) {
					RM.sf[0] = '1'; // Setting ZF
				}

				RM.r1.setString(Integer.toString(result));
				RM.ic++;
			}
		} else if (cmd.matches("ML\\d\\d")) {
			int adr = Integer.parseInt(cmd.substring(2, 4));
			String valMemory = Memory.getFromVirtualAddress(adr);
			String valR1 = RM.r1.getString();

			if (isNumeric(valMemory) && isNumeric(valR1)) {
				long result = Integer.parseInt(valR1)
						* Integer.parseInt(valMemory);

				if (result > 9999) {
					RM.sf[1] = '1'; // Setting OF
					result = result % 10000;
				}
				if (result == 0) {
					RM.sf[0] = '1'; // Setting ZF
				}
				RM.r1.setString(Long.toString(result));
				RM.ic++;
			}
		} else if (cmd.matches("DV\\d\\d")) {
			int adr = Integer.parseInt(cmd.substring(2, 4));
			String valMemory = Memory.getFromVirtualAddress(adr);
			String valR1 = RM.r1.getString();

			// Set interrupt for div by 0
			if (isNumeric(valMemory) && Integer.parseInt(valMemory) == 0) {
				RM.pi = '1';
			} else if (isNumeric(valMemory) && isNumeric(valR1)) {
				int resultDiv = Integer.parseInt(valR1)
						/ Integer.parseInt(valMemory);
				int resultMod = Integer.parseInt(valR1)
						% Integer.parseInt(valMemory);

				RM.r1.setString(Integer.toString(resultDiv));
				RM.r2.setString(Integer.toString(resultMod));
				RM.ic++;
			}
		} else if (cmd.matches("OU\\d\\d")) { // output to printer
			// TODO
		} else if (cmd.matches("IN\\d\\d")) { // input from flash
			int counter = 0;

			// if input is just starting
			if (RM.si[3] == '0') {
				RM.si[3] = '1';

				counter = Integer.parseInt(cmd.substring(2, 4));
				System.out.println("Counter: " + counter);
				RM.r2.setString(cmd.substring(2, 4));

				if (isNumeric(RM.r1.getString()) && counter != 0) {
					int adrToWrite = Integer.parseInt(RM.r1.getString());
					String valToWrite;
					try {
						valToWrite = RM.flash.readLine();
						Memory.writeToVirtualAddress(adrToWrite, valToWrite);
						RM.r1.setInt(RM.r1.getInt() + 1);
						RM.r2.setInt(counter - 1); // one iteration complete
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			// if input continued
			else if (RM.si[3] == '1'
					&& Integer.parseInt(RM.r2.getString()) != 0) {
				counter = RM.r2.getInt();

				if (isNumeric(RM.r1.getString()) && counter != 0) {
					int adrToWrite = Integer.parseInt(RM.r1.getString());
					String valToWrite;
					try {
						valToWrite = RM.flash.readLine();
						Memory.writeToVirtualAddress(adrToWrite, valToWrite);
						RM.r1.setInt(RM.r1.getInt() + 1);
						RM.r2.setInt(counter - 1); // one iteration complete
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
				
				// if input is finished
				if (RM.si[3] == '1' && RM.r2.getInt() == 0) {
					RM.si[3] = '0';
					// RM.r2.setInt(RM.r2.getInt() - 1);
					RM.ic++; // input complete
				}
			}
			// TODO
		} else if (cmd.matches("FOW\\d")) { // open file for writing
			// TODO
		} else if (cmd.matches("FOR\\d")) { // open file for reading
			// TODO
		} else if (cmd.matches("FCL\\d")) { // close file
			// TODO
		} else if (cmd.matches("FW\\d\\d")) { // write to file XY lines from
												// address R1, counter in R2

		} else if (cmd.matches("FR\\d\\d")) { // read from file XY lines to
												// address R1, counter in R2
			// TODO
		}

	}

	public static boolean isNumeric(String str) {
		return str.matches("\\d+"); // match a number
	}
}
