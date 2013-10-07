package IntegrationTests;

import java.io.File;
import java.io.IOException;
import java.nio.file.WatchEvent;

public class DropBoxIntegrationTests {
	public static void main(String[] args) throws IOException, InterruptedException{
		String directoryA = "DirectoryA";
		String directoryB = "DirectoryB";
		String fileA = "TestA.txt";
		String message = "message";
		(new File("../" + directoryA)).mkdirs();
		(new File("../" + directoryB)).mkdirs();
		DropboxInstance dropboxA = new DropboxInstance(directoryA);
		Thread.sleep(1000);
		DropboxInstance dropboxB = new DropboxInstance(directoryB);
		dropboxA.addFile(fileA, message);
		Thread.sleep(100);
		if(dropboxB.popEvent() && dropboxB.fileExists(fileA)) {
			System.out.println("Successful Copy from A to B");
		}
		
	}
	
}


