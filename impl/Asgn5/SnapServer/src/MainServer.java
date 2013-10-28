import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;


public class MainServer {

public static ArrayList<Client> myList = new ArrayList<Client>();
public static ArrayList<String> picList = new ArrayList<String>();

public static void main(String[] args) throws IOException {
    ServerSocket serverSocket = new ServerSocket(5555);
    while(true) {
    	System.out.println("Started");
    	Socket clientSocket = serverSocket.accept();
    	socketLogic logic = new socketLogic(clientSocket);
    	Thread handle = new Thread(logic);
    	handle.start();
    }
    
}

static class socketLogic implements Runnable {
    private Socket server;
    private String line,input;

    socketLogic(Socket server) {
      this.server=server;
    }

    public void run () {
      try {
        DataInputStream in = new DataInputStream (server.getInputStream());
        line = in.readLine();
        StringTokenizer tokens = new StringTokenizer(line);
        String command = tokens.nextToken(";");
        System.out.println("Connection!");
        if(command.equals("newUser")) {
        	  myList.add(new Client(server.getInetAddress(), tokens.nextToken(";")));
        	  System.out.println("Got new User:" + server.getInetAddress().toString());
        	  return;
          }
        else if(command.equals("newImage")){
        	System.out.println("Image Recieved");
        	InputStream fileStream = server.getInputStream();
            int bufferSize = server.getReceiveBufferSize();
            System.out.println("Buffer size: " + bufferSize);
            int val = new Random().nextInt();
			FileOutputStream tempFile = new FileOutputStream(val + "");
			picList.add(val + "");
            BufferedOutputStream bufStream = new BufferedOutputStream(tempFile);
            byte[] bytes = new byte[bufferSize];
            int count;
            while ((count = fileStream.read(bytes)) > 0) {
            	bufStream.write(bytes, 0, count);
            }
            bufStream.flush();
            bufStream.close();
            fileStream.close();
            
            //give number back
            DataOutputStream outStream = new DataOutputStream(server.getOutputStream());
            outStream.writeBytes(val +"");
            outStream.close();
        	}
        else if(command.equals("pull")){
        	System.out.println("pullRequest");
        	int lastImage = Integer.valueOf(tokens.nextToken(";"));
        	int imageLocation = picList.indexOf(lastImage);
        	if(imageLocation >= 0) {
        		
        		line = in.readLine();
        		
        		File file = new File(line);
        		long length = file.length();
        		
        		byte[] bytes = new byte[(int) length];
        		FileInputStream fileIn = new FileInputStream(file);
        		BufferedInputStream bufFile = new BufferedInputStream(fileIn);
        		BufferedOutputStream toClient = new BufferedOutputStream(server.getOutputStream());
        		
        		int count;
        		while ((count = bufFile.read(bytes)) > 0) {
        			toClient.write(bytes, 0, count);
        		}

        		toClient.flush();
        		toClient.close();
        		fileIn.close();
        		bufFile.close();
        		

                DataOutputStream outStream = new DataOutputStream(server.getOutputStream());
                if(imageLocation < picList.size()) {
                	outStream.writeBytes(picList.get(++imageLocation));
                }
                else {
                	outStream.writeBytes(picList.get(imageLocation));
                }
                outStream.close();
        	}
        	
    	  }
        server.close();
      } catch (IOException ioe) {
        System.out.println("IOException on socket listen: " + ioe);
        ioe.printStackTrace();
      }
    }
}
}