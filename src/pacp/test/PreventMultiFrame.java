package pacp.test;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PreventMultiFrame {
    public void createAndShowUI() {
        JFrame frame = new JFrame("DEMO");

        JButton display = new JButton("Display a frame");
        display.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrameDisplay frm = JFrameDisplay.getInstance();
                frm.createAndShowUI();
            }
        });

        JButton exit = new JButton("Exit");    
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());
        frame.add(display);
        frame.add(exit);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PreventMultiFrame().createAndShowUI();
            }
        });
    }
}

// The singleton class
class JFrameDisplay extends JFrame {
	private static final long serialVersionUID = 1L;
	private static JFrameDisplay _instance;
    
    private JFrameDisplay() {
        
    }
    
    public static JFrameDisplay getInstance() {
        if(_instance == null) {
            _instance = new JFrameDisplay();
        }
        
        return _instance;
    }
    
    public void createAndShowUI() {
        setTitle("JFrameDisplay");
        setSize(new Dimension(400,400));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }
}