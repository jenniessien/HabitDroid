package hack.facebook.habitdroid;

import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class BlinkDetector {
	
	private class ShapesRecord {
		private long time;
		private int nShapes;
		
		private ShapesRecord(MatOfRect eyes) {
			this.nShapes = eyes.cols();
			this.time = System.currentTimeMillis();
		}
	}

	/**
	 * Cascade classfier
	 */
	private CascadeClassifier cascade;
	
	
	
    // Create a constructor method  
	public BlinkDetector() throws IOException {
		String cascadeFile = "resources/frontalEyes35x16.xml";
		cascade = new CascadeClassifier(cascadeFile);
		if (cascade.empty()) {
			throw new IOException("Error loading cascade");
		} 
	}

	/**
	 * 
	 * @param inputframe
	 * @return image
	 */
	public Mat detect(Mat inputframe) {
		Mat mRgba = new Mat();
		Mat mGrey = new Mat();
		MatOfRect eyes = new MatOfRect();
		inputframe.copyTo(mRgba);
		inputframe.copyTo(mGrey);
		Imgproc.cvtColor(mRgba, mGrey, Imgproc.COLOR_BGR2GRAY);
		Imgproc.equalizeHist(mGrey, mGrey);
		cascade.detectMultiScale(mGrey, eyes);
		Rect[] eyesArray = eyes.toArray();				
		System.out.println(String.format("Detected %s faces", eyes.cols()));
		for (Rect rect : eyesArray) {
			Point center = new Point(rect.x + rect.width * 0.5, rect.y
					+ rect.height * 0.5);
			Core.ellipse(mRgba, center, new Size(rect.width * 0.5,
					rect.height * 0.5), 0, 0, 360, new Scalar(255, 0, 255), 4,
					8, 0);
		}
		return mRgba;
	}
}
