package IntegrationTests;

import static java.nio.file.StandardWatchEventKinds.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Queue;

public class DropboxInstance {
	//creates dropbox instance watching the specified directory	
	//also registers a watcher for this directory
	Path directory;
	WatchService watcher;
	Queue<WatchEvent<?>> eventQueue;
	String stringDirectory;
	public DropboxInstance(String directory) throws IOException {
		stringDirectory = directory;
		watcher = FileSystems.getDefault().newWatchService();
		this.directory = FileSystems.getDefault().getPath(directory);
	    this.directory.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
	}
	//adds adds specified file
	public void addFile(String File){
		
	}
	//adds adds specified file, with the contents
	public void addFile(String File, String content){
	}
	//removes specified file
	public void removeFile(String File){
	}
	//appends the contents to the file triggering an update flag
	public void updateFile(String File, String content){
	}
	//pops the most recent event
	public WatchEvent<?> popEvent() {
		return eventQueue.poll();
	}
	//places new file events into our queue
	private void pollSystem() throws InterruptedException{
		WatchKey key;
        key = watcher.take();
	    for (WatchEvent<?> event: key.pollEvents()) {
	        eventQueue.add(event);
	    }
	}
	//empties the queue, and notifies the user of 
	private int clearSystem() {
		int count = eventQueue.size();
		eventQueue.clear();
		return count;
	}
	public boolean fileExists(String fileA) {
		File f = new File(fileA);
		return f.exists();
	}
		
}
