import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RecognizeFacesByHaar {
    private static final CascadeClassifier faceCascade = new CascadeClassifier("src/main/resources/haarcascade_frontalface_default.xml");

    public static void recognizeFace(final JLabel videoLabel){
        //Initialization video thread
        final VideoCapture videoCapture = new VideoCapture(0);

        //Matrix storage of rectangular areas
        final MatOfRect faces = new MatOfRect();

        //Video processing and output (30 FPS)
        Timer timer = new Timer(11, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mat frame = new Mat();
                MatOfByte matOfByte = new MatOfByte();

                //Capture a frame from a video stream
                videoCapture.read(frame);

                //RGB image in black and white
                //Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2GRAY);

                //Compress the size of the image
                Imgproc.resize(frame, frame, new Size(640, 480));

                //Add recognized faces in the container
                faceCascade.detectMultiScale(frame, faces);

                //Draw rectangle around recognized faces
                for(Rect element : faces.toArray()){
                    Imgproc.rectangle(frame, element.tl(), element.br(), new Scalar(0, 255, 0), 2);
                }

                //Display the processed frame
                Imgcodecs.imencode(".jpg", frame, matOfByte);
                ImageIcon imageIcon = new ImageIcon(matOfByte.toArray());
                videoLabel.setIcon(imageIcon);
                videoLabel.repaint();
            }
        });

        timer.start();
    }
}
