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
		} else if (cmd.matches("MOVE")) {
			RM.r2 = RM.r1;
			RM.ic++;
		} else if (cmd.matches("CM\\d\\d")) {
			int virtualAdr = Integer.parseInt(cmd.substring(2, 4));
			String strValMem = Memory.getFromVirtualAddress(virtualAdr);
			if (isNumeric(RM.r1.getString()) && isNumeric(strValMem)) {
				int valR1 = RM.r1.getInt();
				int valMem = Integer.parseInt(strValMem);

				if (valR1 > valMem) {
					RM.sf[0] = '0'; // ZF
					RM.sf[1] = '0'; // OF
				} else if (valR1 < valMem) {
					RM.sf[0] = '0';
					RM.sf[1] = '1';
				} else {
					RM.sf[0] = '1';
					RM.sf[1] = '0';
				}
			}
			RM.ic++;
		} else if (cmd.matches("JP\\d\\d")) {
			RM.ic = Integer.parseInt(cmd.substring(2, 4));
		} else if (cmd.matches("JE\\d\\d")) {
			if (RM.sf[0] == '1' && RM.sf[1] == '0') {
				RM.ic = Integer.parseInt(cmd.substring(2, 4));
			} else {
				RM.ic++;
			}
		} else if (cmd.matches("JL\\d\\d")) {
			if (RM.sf[0] == '0' && RM.sf[1] == '1') {
				RM.ic = Integer.parseInt(cmd.substring(2, 4));
			} else {
				RM.ic++;
			}
		} else if (cmd.matches("JG\\d\\d")) {
			if (RM.sf[0] == '0' && RM.sf[1] == '0') {
				RM.ic = Integer.parseInt(cmd.substring(2, 4));
			} else {
				RM.ic++;
			}
		} else if (cmd.matches("OU\\d\\d")) { // output to printer
			int counter = 0;
			RM.si[2] = '1';

			// if output is just starting
			if (RM.chstPrinter == 0) {
				RM.chstPrinter = 1;

				counter = Integer.parseInt(cmd.substring(2, 4));
				RM.r2.setString(cmd.substring(2, 4));

				// if (isNumeric(RM.r1.getString()) && counter != 0) {
				// int adrToWriteFrom = RM.r1.getInt();
				// String valToWrite;
				// try {
				// valToWrite = Memory.getFromVirtualAddress(adrToWriteFrom);
				// RM.printer.write(valToWrite + "\n");
				// RM.r1.setInt(RM.r1.getInt() + 1);
				// RM.r2.setInt(counter - 1); // one iteration complete
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
				// }
			}
			// if output continued
			else if (RM.chstPrinter == 1
					&& Integer.parseInt(RM.r2.getString()) != 0
					&& RM.mode == '1') {
				counter = RM.r2.getInt();

				if (isNumeric(RM.r1.getString()) && counter != 0) {
					int adrToWriteFrom = RM.r1.getInt();
					String valToWrite;
					try {
						valToWrite = Memory
								.getFromVirtualAddress(adrToWriteFrom);
						RM.printer.write(valToWrite + "\n");
						RM.r1.setInt(RM.r1.getInt() + 1);
						RM.r2.setInt(counter - 1); // one iteration complete
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				// if output is finished
				if (RM.chstPrinter == 1 && RM.r2.getInt() == 0) {
					RM.chstPrinter = 0;
					RM.ic++; // input complete
				}
			}
		} else if (cmd.matches("IN\\d\\d")) { // input from flash
			int counter = 0;

			// if input is just starting
			if (RM.chstFlash == 0) {
				RM.chstFlash = 1;
				RM.si[3] = '1';

				counter = Integer.parseInt(cmd.substring(2, 4));
				RM.r2.setString(cmd.substring(2, 4));

				// if (isNumeric(RM.r1.getString()) && counter != 0) {
				// int adrToWrite = Integer.parseInt(RM.r1.getString());
				// String valToWrite;
				// try {
				// valToWrite = RM.flash.readLine();
				// Memory.writeToVirtualAddress(adrToWrite, valToWrite);
				// RM.r1.setInt(RM.r1.getInt() + 1);
				// RM.r2.setInt(counter - 1); // one iteration complete
				// } catch (IOException e) {
				// e.printStackTrace();
				// }
				// }
			}
			// if input continued
			else if (RM.chstFlash == 1
					&& Integer.parseInt(RM.r2.getString()) != 0
					&& RM.mode == '1') {
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
				if (RM.chstFlash == 1 && RM.r2.getInt() == 0) {
					RM.chstFlash = 0;
					RM.ic++; // input complete
				}
			}
		} else if (cmd.matches("FOW\\d")) { // open file for writing
			int fileNum = Integer.parseInt(cmd.substring(3, 4));
			Hdd.openFileForWriting(fileNum);
			RM.ic++;
		} else if (cmd.matches("FOR\\d")) { // open file for reading
			int fileNum = Integer.parseInt(cmd.substring(3, 4));
			Hdd.openFileForReading(fileNum);
			RM.ic++;
		} else if (cmd.matches("FCL\\d")) { // close file
			int fileNum = Integer.parseInt(cmd.substring(3, 4));
			Hdd.closeFile(fileNum);
			RM.ic++;
		} else if (cmd.matches("FS\\d\\d")) { //File seek XY - X is file number, Y - line number
			int fileNum = Integer.parseInt(cmd.substring(2, 3));
			Hdd.seekCursor(fileNum, Integer.parseInt(cmd.substring(3, 4)));
			RM.ic++;
		} else if (cmd.matches("W\\d\\d\\d")) { // write to file n XY lines from
												// address R1, counter in R2
			int counter = 0;
			int fileNum = Integer.parseInt(cmd.substring(1, 2));
			// if input is just starting
			if (RM.chstHdd == 0) {
				RM.chstHdd = 1;
				RM.si[0] = '1';

				counter = Integer.parseInt(cmd.substring(2, 4));
				RM.r2.setString(cmd.substring(2, 4));

				// if (isNumeric(RM.r1.getString()) && counter != 0) {
				// int adrToReadFrom = Integer.parseInt(RM.r1.getString());
				// String valToWrite;
				// valToWrite = Memory.getFromVirtualAddress(adrToReadFrom);
				// Hdd.writeToFile(fileNum, valToWrite);
				// RM.r1.setInt(RM.r1.getInt() + 1);
				// RM.r2.setInt(counter - 1); // one iteration complete
				// }
			}
			// if input continued
			else if (RM.chstHdd == 1
					&& Integer.parseInt(RM.r2.getString()) != 0
					&& RM.mode == '1') {
				counter = RM.r2.getInt();

				if (isNumeric(RM.r1.getString()) && counter != 0) {
					int adrToReadFrom = Integer.parseInt(RM.r1.getString());
					String valToWrite;
					valToWrite = Memory.getFromVirtualAddress(adrToReadFrom);
					Hdd.writeToFile(fileNum, valToWrite);
					RM.r1.setInt(RM.r1.getInt() + 1);
					RM.r2.setInt(counter - 1); // one iteration complete
				}

				// if input is finished
				if (RM.chstHdd == 1 && RM.r2.getInt() == 0) {
					RM.chstHdd = 0;
					RM.ic++; // input complete
				}
			}

		} else if (cmd.matches("R\\d\\d\\d")) { // read from file n XY lines to
			int counter = 0;
			int fileNum = Integer.parseInt(cmd.substring(1, 2));
			// if input is just starting
			if (RM.chstHdd == 0) {
				RM.chstHdd = 1;
				RM.si[1] = '1';

				counter = Integer.parseInt(cmd.substring(2, 4));
				RM.r2.setString(cmd.substring(2, 4));

				// if (isNumeric(RM.r1.getString()) && counter != 0) {
				// int adrToWrite = Integer.parseInt(RM.r1.getString());
				// String valToWrite;
				// valToWrite = Hdd.readFromFile(fileNum);
				// Memory.writeToVirtualAddress(adrToWrite, valToWrite);
				// RM.r1.setInt(RM.r1.getInt() + 1);
				// RM.r2.setInt(counter - 1); // one iteration complete
				// }
			}
			// if input continued
			else if (RM.chstHdd == 1
					&& Integer.parseInt(RM.r2.getString()) != 0
					&& RM.mode == '1') {
				counter = RM.r2.getInt();

				if (isNumeric(RM.r1.getString()) && counter != 0) {
					int adrToWrite = Integer.parseInt(RM.r1.getString());
					String valToWrite;
					valToWrite = Hdd.readFromFile(fileNum);
					Memory.writeToVirtualAddress(adrToWrite, valToWrite);
					RM.r1.setInt(RM.r1.getInt() + 1);
					RM.r2.setInt(counter - 1); // one iteration complete
				}

				// if input is finished
				if (RM.chstHdd == 1 && RM.r2.getInt() == 0) {
					RM.chstHdd = 0;
					RM.ic++; // input complete
				}
			}

		} else if (cmd.matches("HALT")) {
			for (int i = 0; i < RM.si.length; i++) {
				RM.si[i] = '9';
			}
		}

	}

	public static boolean isNumeric(String str) {
		return str.matches("\\d+"); // match a number
	}
}
