package hack.facebook.habitdroid;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Main {

	// a commentsfisfhkwj
	public static void main (String[] args) {
		
		// the main frame with three buttons
	    JFrame MyFrame=new JFrame("My New Frame");
	    MyFrame.setLayout(new BoxLayout(MyFrame, BoxLayout.LINE_AXIS));
	    
	    MyFrame.setSize(400, 400);
	    MyFrame.setLayout(new FlowLayout());
	    MyFrame.setVisible(true) ;
	    
	    
	    // On click of any of these take the person to one of the detection units.     
	    // Create a new instance of something.
	   //ActionListener =  n
	    
	    JButton faceButton =new JButton("Face Detection");
	    MyFrame.add(faceButton);
	    
	    JButton eyeButton =new JButton("Eye Detection");
	    MyFrame.add(eyeButton);
	   
	    
	    JButton slouchButton=new JButton("Slouch Detection");
	    MyFrame.add(slouchButton);
	    
	    
	    faceButton.addActionListener(new ActionListener()
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	           Face faceDetection = new Face();
	           try {
				faceDetection.run();
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        }
	    });
	    
	    
	    eyeButton.addActionListener(new ActionListener()
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	           // Launch new frame for eye detector.
	        }
	    });
	    
	    slouchButton.addActionListener(new ActionListener()
	    {
	        public void actionPerformed(ActionEvent e)
	        {
	           // Launch new frame for eye detector.
	        }
	    });
	}
}
