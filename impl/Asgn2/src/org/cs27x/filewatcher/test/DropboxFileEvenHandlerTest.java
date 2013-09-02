package org.cs27x.filewatcher.test;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.attribute.FileTime;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import org.cs27x.dropbox.DropboxCmd;
import org.cs27x.dropbox.DropboxProtocol;
import org.cs27x.dropbox.FileManager;
import org.cs27x.filewatcher.DropboxFileEventHandler;
import org.cs27x.filewatcher.FileEvent;
import org.cs27x.filewatcher.FileState;
import org.cs27x.filewatcher.FileStates;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DropboxFileEvenHandlerTest {
	@Mock DropboxProtocol mTransport;
	@Mock FileStates mFileStates;
	@Mock FileManager mFileHandler;
	DropboxFileEventHandler dbFileEventHandler_;
	Path testPath;
	FileEvent event;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		testPath = FileSystems.getDefault().getPath("test-data");
		dbFileEventHandler_ = new DropboxFileEventHandler(mFileHandler, mFileStates, mTransport);
	}
	
	public void setUpHandler(Kind<?> kind) throws IOException {
		event = new FileEvent(kind, testPath);
		when(mFileStates.filter(any(FileEvent.class))).thenReturn(event);
	}
	@Test
	public void testHandleCreate() throws IOException{
		setUpHandler(ENTRY_CREATE);
		dbFileEventHandler_.handle(event);
		verify(mTransport, times(1)).addFile(testPath);
		verifyNoMoreInteractions(mTransport);
	}
	
	@Test
	public void testHandleModify() throws IOException{
		setUpHandler(ENTRY_MODIFY);
		dbFileEventHandler_.handle(event);
		verify(mTransport, times(1)).updateFile(testPath);
		verifyNoMoreInteractions(mTransport);
	}

	@Test
	public void testHandleDelete() throws IOException{
		setUpHandler(ENTRY_DELETE);
		dbFileEventHandler_.handle(event);
		verify(mTransport, times(1)).removeFile(testPath);
		verifyNoMoreInteractions(mTransport);
	}
	
	@Test
	public void testHandleNull() throws IOException{
		setUpHandler(ENTRY_MODIFY);
		when(mFileStates.filter(any(FileEvent.class))).thenReturn(null);
		dbFileEventHandler_.handle(event);
		verifyZeroInteractions(mTransport);
	}
}
