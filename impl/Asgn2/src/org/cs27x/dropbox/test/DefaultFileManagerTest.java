package org.cs27x.dropbox.test;
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

public class DefaultFileManagerTest {

	Path testPath;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		testPath = FileSystems.getDefault().getPath("test-data");
	}

}
