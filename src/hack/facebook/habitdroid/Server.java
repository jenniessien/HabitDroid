package hack.facebook.habitdroid;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Server {

	// fdsihslj
	  
	public static void main (String[] args) throws Exception {
		Server server = new Server();
		
		for (int i = 0; i < 1; i++) {
			server.sendData(System.currentTimeMillis()/1000, "blink");		
		}

	}
	        
	public boolean sendData(long timestamp, String event) throws Exception{		
		URL url = new URL("http://jenni.gift/log_event.php?type=" + event + "&time=" + timestamp);
	    String result = "";
	    String data = "fName=" + URLEncoder.encode("Atli", "UTF-8");
	    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	    try {

	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	        connection.setUseCaches(false);
	        connection.setRequestMethod("POST");
	        connection.setRequestProperty("Content-Type",
	                "application/x-www-form-urlencoded");

	        // Send the POST data
	        DataOutputStream dataOut = new DataOutputStream(
	                connection.getOutputStream());
	        dataOut.writeBytes(data);
	        dataOut.flush();
	        dataOut.close();
	        
	        BufferedReader in = null;
	        try {
	            String line;
	            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } finally {
	            if (in != null) {
	                in.close();
	            }
	        }

	    } finally {
	        connection.disconnect();
	        System.out.println(result);
	    }
	    
	    return true;
	}
}
