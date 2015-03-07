package hack.facebook.habitdroid;

import java.awt.image.BufferedImage;

import org.opencv.core.Core;
import org.opencv.core.Mat;

public class Mat2Image {
	Mat mat = new Mat();
	BufferedImage img;
	byte[] dat;

	public Mat2Image() {
	}

	public Mat2Image(Mat mat) {
		getSpace(mat);
	}

	public void getSpace(Mat mat) {
		this.mat = mat;
		int w = mat.cols(); 
		int h = mat.rows();
		if (dat == null || dat.length != w * h * 3) {
			dat = new byte[w * h * 3];
		}
		if (img == null || img.getWidth() != w || img.getHeight() != h || img.getType() != BufferedImage.TYPE_3BYTE_BGR) {
			img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		}
	}

	BufferedImage getImage(Mat mat) {
		getSpace(mat);
		mat.get(0, 0, dat);
		img.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), dat);
		return img;
	}
	
	public Mat getMat() {
		return mat;
	}

	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
}
