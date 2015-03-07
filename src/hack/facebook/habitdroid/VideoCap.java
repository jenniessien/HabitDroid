package hack.facebook.habitdroid;

import java.awt.image.BufferedImage;
import java.net.URLDecoder;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

public class VideoCap {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}

	VideoCapture videoCapture;
	Mat2Image mat2Img = new Mat2Image();
	CascadeClassifier faceDetectorClassifier;

	VideoCap() throws Exception {
		videoCapture = new VideoCapture();
		videoCapture.open(0);
		faceDetectorClassifier = newFaceDetectorClassifier();
	}

	BufferedImage getOneFrame() {
		final Mat webcamImage = mat2Img.getMat();
		videoCapture.retrieve(webcamImage);
		
		final MatOfRect faceDetections = new MatOfRect();
		faceDetectorClassifier.detectMultiScale(webcamImage, faceDetections); // detectMultiScale will perform the detection

		// Draw a bounding box around each face.
		for (Rect rect : faceDetections.toArray()) {
			Core.rectangle(webcamImage, // where to draw the box
					new Point(rect.x, rect.y), // bottom left
					new Point(rect.x + rect.width, rect.y + rect.height), // top right
					new Scalar(255, 0, 0)); // RGB colour
		}
		
		// videoCapture.read(mat2Img.mat);
		return mat2Img.getImage(mat2Img.mat);
	}
	
	public CascadeClassifier newFaceDetectorClassifier() throws Exception {
		String metaUrl = Face.class.getResource("haarcascade_frontalface_default.xml").getPath();
		if (metaUrl.startsWith("/", 0)) {
			metaUrl = metaUrl.replaceFirst("/", "");
		}
		metaUrl = URLDecoder.decode(metaUrl, "UTF-8");
		return new CascadeClassifier(metaUrl);
	}
}