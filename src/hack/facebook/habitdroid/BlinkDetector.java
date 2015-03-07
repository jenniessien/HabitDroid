package hack.facebook.habitdroid;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class BlinkDetector {
	
	
	private CircularFifoQueue<Integer> heightHistory = new CircularFifoQueue<Integer>(10);

	/**
	 * Cascade classfier
	 */
	private CascadeClassifier cascade;
	
	
	
    // Create a constructor method  
	public BlinkDetector() throws IOException {
		String cascadeFile = "resources/haarcascade_mcs_eyepair_big.xml";		
		cascade = new CascadeClassifier(cascadeFile);
		if (cascade.empty()) {
			throw new IOException("Error loading cascade");
		} 
	}
	
	@Override
	protected void finalize() throws Throwable {
		
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
		if (eyesArray.length == 1) {
			Rect rect = eyesArray[0];
			int height = rect.height;
			System.out.println("Height: " + height);
			System.out.println("Size: " + heightHistory.size());
			if (heightHistory.size() > 3) {
				int avg = 0;
				for (Integer value:heightHistory) {
					avg += value;
				}
				avg /= heightHistory.size();
				if (height * 1.05 < avg) {
					System.out.println("Blink!!");
				}
				
			}
			heightHistory.add(height);		
			Point center = new Point(rect.x + rect.width * 0.5, rect.y
					+ height * 0.5);
			Core.ellipse(mRgba, center, new Size(rect.width * 0.5,
					height * 0.5), 0, 0, 360, new Scalar(255, 0, 255), 4,
					8, 0);
			
			
			
		}
		
		return mRgba;
	}
}
