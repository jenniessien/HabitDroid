package hack.facebook.habitdroid.desktop;

import hack.facebook.habitdroid.BlinkDetector;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;



/**
 * Desktop App launcher
 * Creates all the required classes set up the dependencies
 * @author maesierra
 *
 */
public class DesktopLauncher {

	
	public static void main(String[] args) throws Exception {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		//Create the capture
		final VideoCapture capture =new VideoCapture(0);
		final MainDesktopApp mainPanel = new MainDesktopApp(capture);
		final BlinkDetector detector = new BlinkDetector();
		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
		executor.scheduleAtFixedRate(new Runnable() {
			
			public void run() {
				Mat webcam_image=new Mat();  
				if(capture.isOpened()) {  
		           capture.read(webcam_image);  
		           if( !webcam_image.empty() ) {   
		        	   mainPanel.setSize(webcam_image.width()+40,webcam_image.height()+60);  
		               //-- 3. Apply the classifier to the captured image  
		               webcam_image=detector.detect(webcam_image);  
		               //-- 4. Display the image  
		               mainPanel.updateImage(webcam_image);  
		           }
				}
			}
		}, 0, 100, TimeUnit.MILLISECONDS);		
		mainPanel.start();
	}
}
