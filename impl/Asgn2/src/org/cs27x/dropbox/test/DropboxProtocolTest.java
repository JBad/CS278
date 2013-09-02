package org.cs27x.dropbox.test;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.cs27x.dropbox.Dropbox;
import org.cs27x.dropbox.DropboxCmd;
import org.cs27x.dropbox.DropboxCmdProcessor;
import org.cs27x.dropbox.DropboxProtocol;
import org.cs27x.dropbox.DropboxTransport;
import org.cs27x.dropbox.HazelcastTransport;
import org.cs27x.dropbox.DropboxCmd.OpCode;
import org.cs27x.filewatcher.FileReactor;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DropboxProtocolTest {
	@Mock DropboxTransport mTransport;
	@Mock DropboxCmdProcessor mProtocol;
	@Mock DropboxCmd dbCmd;
	DropboxProtocol dbProtocol;
	Path testPath;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		dbProtocol = new DropboxProtocol(mTransport, mProtocol);
		testPath = FileSystems.getDefault().getPath("test-data");
	}
	
	@Test
	public void testConnect() {
		dbProtocol.connect("");
		verify(mTransport, times(1)).connect("");
	}
	
	@Test
	public void testPublish() {
		dbProtocol.publish(null);
		verify(mTransport, times(1)).publish(null);
	}
	
	@Test
	public void testAddFile() { 
		dbProtocol.addFile(testPath);
		verify(mTransport, times(1)).publish((DropboxCmd)anyObject());
		
	}
	
	@Test
	public void testRemoveFile(){
		dbProtocol.removeFile(testPath);
		verify(mTransport, times(1)).publish((DropboxCmd)anyObject());
	}	
	
	@Test
	public void testUpdateFile(){
		dbProtocol.updateFile(testPath);
		verify(mTransport, times(1)).publish((DropboxCmd)anyObject());
	}
}
