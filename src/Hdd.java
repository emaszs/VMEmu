import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Hdd {
	public static final int FILE_LENGTH = 10;
	public static final int FILE_NUM = 3;
	public static final String REAL_FILE_LOC = "C:/Users/Tomas/Desktop/hdd.txt";
	public static FileInfo[] fileList = new FileInfo[3];

	public static void seekCursor(int fileNum, int lineNum) {
		fileList[fileNum].cursorPosition = lineNum * 6;
	}

	public static void initFiles() {
		for (int i = 0; i < FILE_NUM; i++) {
			fileList[i] = new FileInfo();
			
			seekCursor(i, i * 10);

			fileList[i].fileIsOpenForWriting = false;
			fileList[i].fileIsOpenForReading = false;
			fileList[i].fileIsBeingWrittenTo = false;
			fileList[i].fileIsBeingReadFrom = false;
		}
	}

	public static void openFileForWriting(int fileNum) {
		fileList[fileNum].fileIsOpenForWriting = true;
	}

	public static void openFileForReading(int fileNum) {
		fileList[fileNum].fileIsOpenForReading = true;
	}

	public static void closeFile(int fileNum) {
		if (!fileList[fileNum].fileIsBeingWrittenTo
				&& !fileList[fileNum].fileIsBeingWrittenTo) {
			fileList[fileNum].fileIsOpenForReading = false;
			fileList[fileNum].fileIsOpenForWriting = false;
		}
	}

	public static void writeToFile(int fileNum, String val) {
		if (fileList[fileNum].fileIsOpenForWriting
				&& !fileList[fileNum].fileIsBeingWrittenTo) {

			File file = new File(REAL_FILE_LOC);
			RandomAccessFile access;
			try {
				access = new RandomAccessFile(file, "rw");
				int cursor = fileList[fileNum].cursorPosition;
				access.seek(cursor);
				access.writeBytes(val);
				fileList[fileNum].cursorPosition += RM.WORD_SIZE + 2; // newline
																		// symbols
																		// - +2
				access.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String readFromFile(int fileNum) {
		if (fileList[fileNum].fileIsOpenForReading
				&& !fileList[fileNum].fileIsBeingReadFrom) {

			File file = new File(REAL_FILE_LOC);
			String result;
			RandomAccessFile access;
			try {
				access = new RandomAccessFile(file, "rw");
				int cursor = fileList[fileNum].cursorPosition;
				access.seek(cursor);
				result = access.readLine();
				fileList[fileNum].cursorPosition += RM.WORD_SIZE + 2; // newline
																		// symbols
																		// - +2
				access.close();

				return result;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
