package hack.facebook.habitdroid;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

public class Face extends JPanel {

	public Face() {
		super();
	}

	
	public static void main(String[] args) throws InterruptedException, UnsupportedEncodingException{
		
		JFrame frame = new JFrame("Detector");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
	     String metaUrl = Face.class.getResource("haarcascade_frontalface_alt.xml").getPath();
	        if (metaUrl.startsWith("/", 0)) {
	        	metaUrl = metaUrl.replaceFirst("/", "");
	        }
	        metaUrl = URLDecoder.decode(metaUrl, "UTF-8"); //this will replace %20 with spaces

		CascadeClassifier faceDetector = new CascadeClassifier(metaUrl);
		//CascadeClassifier eyeDetector = new CascadeClassifier("D:\\CS\\opencv\\sources\\data\\haarcascades\\haarcascade_eye.xml");
		
		Face facePanel = new Face();
		frame.setContentPane(facePanel);

		frame.setVisible(true);

		Mat webcam = new Mat();
		//MatToBufImg img = new MatToBufImg();

		VideoCapture capture = new VideoCapture(0);
		
		//VideoPanel vidPanel = new VideoPanel();

		if (capture.isOpened()) {
			Thread.sleep(500);
			while (true) {
				capture.read(webcam);

				if (!webcam.empty()) {
					frame.setSize(400, 400);

					MatOfRect faceDetections = new MatOfRect();
					faceDetector.detectMultiScale(webcam, faceDetections);
					
					// Draw a bounding box around each face.
				    for (Rect rect : faceDetections.toArray()) {
				        Core.rectangle(webcam, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0));
				    }
				    
				   // facePanel.);
				    facePanel.repaint();
				}
			}
		}
	}
	


}
