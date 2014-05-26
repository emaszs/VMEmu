package memory;
import java.io.BufferedReader;
import java.io.IOException;

import machine.Memory;

public class LoaderHelper {
	public static String[] cmdRegexes = { "LR\\d{2}", "SR\\d{2}", "PUSR",
			"POPR", "A1\\d{2}", "A2\\d{2}", "S1\\d{2}", "S2\\d{2}", "ML\\d{2}",
			"DV\\d{2}", "MOVE", "CM\\d{2}", "JP\\d{2}", "JE\\d{2}", "JL\\d{2}",
			"JG\\d{2}", "OU\\d{2}", "IN\\d{2}", "FOW\\d", "FOR\\d", "FCL\\d",
			"FS\\d{2}", "W\\d{3}", "R\\d{3}", "HALT", "DS\\d{2}", "\\$STR",
			"\\$END" };

	public static boolean isLegal(String cmd) {
		boolean matches = false;
		for (int i = 0; i < cmdRegexes.length; i++) {
			if (cmd.matches(cmdRegexes[i])) {
				matches = true;
			}
		}
		return matches;
	}

	public static boolean loadProgram(final BufferedReader flash) {
		boolean loadSuccessful = false;
		int VMProgramIC = 0;
		String line = null;
		try {
			while ((line = flash.readLine()) != null) {

				if (isLegal(line)) {

					/*
					 * DSXY - data segment allocation. XY - virtual adress to
					 * store data
					 */
					if (line.matches("DS\\d\\d")) {
						int dataSegIC = 0;
						int adr = Integer.parseInt(line.substring(2, 4));
						line = flash.readLine(); // Skip DSXY line
						while (!line.matches("DSOV")) {
							Memory.writeToVirtualAddress(adr + dataSegIC, line);
							dataSegIC++;
							line = flash.readLine();
						}
						line = flash.readLine(); // Skip DSOV line
					}

					if (line != null && line.matches("\\$STR")) {
						line = flash.readLine();
					}

					if (line != null && line.matches("\\$END")) {
						loadSuccessful = true;
						break;
					}
					/*
					 * Checking once more if line isn't empty Required if Data
					 * Segment is at the end of the input flash drive.
					 */
					if (line != null) {
						Memory.writeToVirtualAddress(VMProgramIC, line);
						VMProgramIC++;
					}
				} else {
					System.out
							.println("Illegal syntax found! stopping loading.");
					System.out.println(line);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loadSuccessful;
	}
}
