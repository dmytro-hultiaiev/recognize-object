import nu.pattern.OpenCV;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
    private static final int windowWidth = 1280;
    private static final int windowHeight = 720;

    static {
        OpenCV.loadLocally();
    }

    public static void main( String[] args ){
        JFrame window = new JFrame();
        final JLabel videoLabel = new JLabel();

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
        window.add(videoLabel, gbc);

        //Start recognition after window become active
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                RecognizeFacesByHaar.recognizeFace(videoLabel);
            }
        });

        window.setVisible(true);
    }
}
