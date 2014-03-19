import java.io.BufferedReader;
import java.io.IOException;

public class Loader {
	public static void loadProgram(final RM RM, final BufferedReader flash) {
		int VMProgramIC = 0;
		String line = null;
		try {
			while ((line = flash.readLine()) != null) {

				/*
				 * DSXY - data segment allocation. XY - virtual adress to store
				 * data
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
				
				/*
				 * Checking once more if line isn't empty
				 * Required if Data Segment is at the end of the input flash drive.
				 */
				if (line != null) {
					Memory.writeToVirtualAddress(VMProgramIC, line);
					VMProgramIC++;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
