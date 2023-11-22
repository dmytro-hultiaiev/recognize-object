import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    private static final int windowWidth = 1280;
    private static final int windowHeight = 720;

    static {
        OpenCV.loadLocally();
    }

    private static void startVideoProcessing(final JLabel screen){
        //Initialization video thread
        final VideoCapture videoCapture = new VideoCapture(0);

        //Video processing and output (30 FPS)
        Timer timer = new Timer(33, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Mat frame = new Mat();
                MatOfByte matOfByte = new MatOfByte();

                //Capture a frame from a video stream
                videoCapture.read(frame);

                //Display the processed frame
                Imgcodecs.imencode(".jpg", frame, matOfByte);
                ImageIcon imageIcon = new ImageIcon(matOfByte.toArray());
                screen.setIcon(imageIcon);
                screen.repaint();
            }
        });
        timer.start();
    }

    public static void main( String[] args ){
        JFrame window = new JFrame();
        final JLabel screen = new JLabel();

        //Initialization layout manager
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;

        //Window properties
        window.setTitle("Recognize");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(windowWidth, windowHeight);
        window.setLocationRelativeTo(null);
        window.setLayout(new GridBagLayout());
        window.add(screen, gbc);

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                startVideoProcessing(screen);
            }
        });

        window.setVisible(true);
    }
}
