package hack.facebook.habitdroid;

import java.net.URLDecoder;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

class WebCamFaceDetect {
	
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	public void run() throws Exception {
		System.out.println("\nRunning DetectFaceDemo");

		String metaUrl = Face.class.getResource(
				"haarcascade_frontalface_default.xml").getPath();
		if (metaUrl.startsWith("/", 0)) {
			metaUrl = metaUrl.replaceFirst("/", "");
		}
		metaUrl = URLDecoder.decode(metaUrl, "UTF-8"); // this will replace %20
														// with spaces

		CascadeClassifier faceDetectorClassifier = new CascadeClassifier(
				metaUrl);

		// Get a frame from the webcam
		Mat webcamImage = new Mat();
		VideoCapture webcam = new VideoCapture(0); // Assuming that webcam will
													// be device '0'

		// Pause
		Thread.sleep(500); // 0.5 -> 1 sec of a delay. This is not obvious but
							// its necessary
		// as the camera simply needs time to initialize itself

		if (!webcam.isOpened()) {
			System.out.println("Did not connect to camera");
		} else
			System.out.println("found webcam: " + webcam.toString());

		// grab a frame from the web-cam
		webcam.retrieve(webcamImage);
		System.out.println("Captured image with " + webcamImage.width()
				+ " pixels wide by " + webcamImage.height() + " pixels tall.");
		Highgui.imwrite("webcam_face.jpg", webcamImage);
		// Now I'm finished with the webcam

		// Now detect the face in the image.
		// MatOfRect is a special container class for Rect.
		MatOfRect faceDetections = new MatOfRect();
		faceDetectorClassifier.detectMultiScale(webcamImage, faceDetections); // detectMultiScale
																		// will
																		// perform
																		// the
																		// detection
		System.out.println(String.format("Detected %s faces",
				faceDetections.toArray().length));

		// Draw a bounding box around each face.
		for (Rect rect : faceDetections.toArray()) {
			Core.rectangle(webcamImage, // where to draw the box
					new Point(rect.x, rect.y), // bottom left
					new Point(rect.x + rect.width, rect.y + rect.height), // top
																			// right
					new Scalar(255, 0, 0)); // RGB colour
		}
		System.out.println("Bounding boxes drawn");

		// Save the visualized detection.
		String filename = "haar_detected_webcam_face.png";
		Highgui.imwrite(filename, webcamImage);
		System.out.println("Writing: " + filename);
		webcam.release();
	}
}

//public class WebCamFaceDetection {
//	public static void main(String[] args) throws Exception {
//		System.out
//				.println("Web Cam Face Detection with OpenCV and Harr classifier ");
//		// Load the native library.
//		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
//		new WebCamFaceDetect().run();
//	}
//}
