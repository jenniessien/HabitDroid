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
		final JFrame mainFrame = new JFrame("HabitDroid");
		mainFrame.setLayout(new BoxLayout(mainFrame, BoxLayout.LINE_AXIS));
		mainFrame.setSize(400, 400);
		mainFrame.setLayout(new FlowLayout());
		mainFrame.setVisible(true);

		final JButton blickDetectionBtn = new JButton("Blink Detection");
		mainFrame.add(blickDetectionBtn);

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
