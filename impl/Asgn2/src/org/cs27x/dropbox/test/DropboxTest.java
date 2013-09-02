package org.cs27x.dropbox.test;

import org.cs27x.dropbox.Dropbox;
import org.cs27x.dropbox.DropboxProtocol;
import org.cs27x.dropbox.HazelcastTransport;
import org.cs27x.filewatcher.FileReactor;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DropboxTest {

	@Mock HazelcastTransport mTransport;
	@Mock DropboxProtocol mProtocol;
	@Mock FileReactor mReactor;
	Dropbox dropbox;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		dropbox = new Dropbox(mTransport, mProtocol, mReactor);
	}

	@Test
	public void testConnect() throws Exception{
		dropbox.connect("");
		verify(mTransport, times(1)).connect("");
		verify(mReactor, times(1)).start();
	}
	
	@Test
	public void testConnected() throws Exception{
		dropbox.connected();
		verify(mTransport, times(1)).isConnected();
	}
	
	@Test
	public void testawaitConnection() throws Exception{
		dropbox.awaitConnect(0);
		verify(mTransport, times(1)).awaitConnect(0);
	}
}
