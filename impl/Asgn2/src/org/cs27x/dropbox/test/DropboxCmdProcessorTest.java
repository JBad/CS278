package org.cs27x.dropbox.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;

import org.cs27x.dropbox.DropboxCmd;
import org.cs27x.dropbox.DropboxCmdProcessor;
import org.cs27x.dropbox.FileManager;
import org.cs27x.dropbox.DropboxCmd.OpCode;
import org.cs27x.filewatcher.FileState;
import org.cs27x.filewatcher.FileStates;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DropboxCmdProcessorTest {
	FileStates mFileStates;
	FileState mState;
	FileManager mMgr;
	DropboxCmd mCmd;
	Path testPath;
	DropboxCmdProcessor dcp;

	@Before
	public void setUp() throws Exception {
		mFileStates = mock(FileStates.class);
		mState = mock(FileState.class);
		testPath = FileSystems.getDefault().getPath("test-data");
		mCmd = mock(DropboxCmd.class);
		mMgr = mock(FileManager.class);
		when(mFileStates.getState(testPath)).thenReturn(mState);
		when(mFileStates.getOrCreateState(testPath)).thenReturn(mState);
	}
	
	public void setupMocks(OpCode op){
		when(mCmd.getOpCode()).thenReturn(op);
		when(mCmd.getData()).thenReturn(new byte[]{1,2,3,4});
		when(mMgr.resolve(anyString())).thenReturn(testPath);
		dcp = new DropboxCmdProcessor(mFileStates, mMgr);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUpdateFileStateRemove() {
		setupMocks(OpCode.REMOVE);
		dcp.updateFileState(mCmd, testPath);
		verify(mState,never()).setLastModificationDate((FileTime)anyObject());
	}
	
	@Test
	public void testUpdateFileStateRemoveStateNull() {
		setupMocks(OpCode.REMOVE);
		when(mFileStates.getState(testPath)).thenReturn(null);
		dcp.updateFileState(mCmd, testPath);
		verifyZeroInteractions(mState);
	}

	@Test
	public void testUpdateFileStateAdd(){
		setupMocks(OpCode.ADD);
		dcp.updateFileState(mCmd, testPath);
		verify(mState,times(1)).setLastModificationDate((FileTime)anyObject());
		verify(mState,times(1)).setSize(4);
	}

	@Test
	public void testUpdateFileStateUpdate(){
		setupMocks(OpCode.UPDATE);
		dcp.updateFileState(mCmd, testPath);
		verify(mState,times(1)).setLastModificationDate((FileTime)anyObject());
		verify(mState,times(1)).setSize(4);
	}
	
	@Test
	public void testUpdateFileStateSync(){
		setupMocks(OpCode.SYNC);
		dcp.updateFileState(mCmd, testPath);
		verifyZeroInteractions(mState);
	}	
	
	@Test
	public void testUpdateFileStateGet(){
		setupMocks(OpCode.GET);
		dcp.updateFileState(mCmd, testPath);
		verifyZeroInteractions(mState);
	}	

	@Test
	public void testcmdReceivedRemove() throws IOException {
		setupMocks(OpCode.REMOVE);
		dcp.cmdReceived(mCmd);
		verify(mMgr,times(1)).delete(testPath);
	}

	@Test
	public void testcmdReceivedAdd() throws IOException{
		setupMocks(OpCode.ADD);
		dcp.cmdReceived(mCmd);
		verify(mMgr,times(1)).write(testPath, new byte[]{1,2,3,4}, false);
	}

	@Test
	public void testcmdReceivedUpdate() throws IOException{
		setupMocks(OpCode.UPDATE);
		dcp.cmdReceived(mCmd);
		verify(mMgr,times(1)).write(testPath, new byte[]{1,2,3,4}, true);
	}
	
	@Test
	public void testcmdReceivedSync() throws IOException{
		setupMocks(OpCode.SYNC);
		dcp.cmdReceived(mCmd);
		verify(mMgr,never()).delete(testPath);
		verify(mMgr,never()).write(testPath, new byte[]{1,2,3,4}, true);
	}	
	
	@Test
	public void testcmdReceivedGet() throws IOException{
		setupMocks(OpCode.GET);
		dcp.cmdReceived(mCmd);
		verify(mMgr,never()).delete(testPath);
		verify(mMgr,never()).write(testPath, new byte[]{1,2,3,4}, true);
	}
}