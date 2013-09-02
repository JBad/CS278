package org.cs27x.filewatcher.test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent.Kind;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import org.cs27x.filewatcher.FileEvent;
import org.cs27x.filewatcher.FileState;
import org.cs27x.filewatcher.FileStates;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Mockito.*;

public class FileStatesTest {
	FileStates fileStates;
	@Mock Map<String, FileState> mStates;
	Path testPath;
	String p;
	FileState fState;
	FileEvent fEvent;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		testPath = FileSystems.getDefault().getPath("test-data");
		p = testPath.toAbsolutePath().toString();
		fileStates = new FileStates(mStates);
		fState = new FileState(0, null);
	}

	public void setupFileEvent(Kind<?> kind) { 
		fEvent = new FileEvent(kind, testPath);
	}
	
	@Test
	public void testGetState(){
		when(mStates.get(p)).thenReturn(fState);
		fileStates.getState(testPath);
		assertEquals(fileStates.getState(testPath),fState);
	}

	@Test
	public void testGetOrCreateState(){
		when(mStates.get(p)).thenReturn(fState);
		fileStates.getOrCreateState(testPath);
		verify(mStates, never()).put((String)anyObject(), (FileState)anyObject());
	}
	
	@Test
	public void testGetOrCreateStateNull(){
		when(mStates.get(p)).thenReturn(null);
		fileStates.getOrCreateState(testPath);
		verify(mStates, times(1)).put((String)anyObject(), (FileState)anyObject());
	}
	
	@After
	public void tearDown() throws Exception {
	}


}
