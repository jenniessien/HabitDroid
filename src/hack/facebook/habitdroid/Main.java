package hack.facebook.habitdroid;

import hack.facebook.habitdroid.desktop.MainDesktopApp;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

public class Main {
	
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	public static void main(String[] args) throws Exception {
		// the main frame with three buttons
		JFrame MyFrame = new JFrame("My New Frame");
		MyFrame.setLayout(new BoxLayout(MyFrame, BoxLayout.LINE_AXIS));

		MyFrame.setSize(400, 400);
		MyFrame.setLayout(new FlowLayout());
		MyFrame.setVisible(true);

		JButton blickDetectionBtn = new JButton("Blink Detection");
		MyFrame.add(blickDetectionBtn);

		blickDetectionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
		});
	}
}
