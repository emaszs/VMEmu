package memory;

public class FileInfo {
	int fileLocation;
	
	boolean fileIsOpenForWriting;
	boolean fileIsOpenForReading;
	boolean fileIsBeingWrittenTo;
	boolean fileIsBeingReadFrom;
	
	int cursorPosition;
}
