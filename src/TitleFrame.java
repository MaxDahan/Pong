import java.awt.Color;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TitleFrame extends JFrame implements KeyListener {
    private JLayeredPane pane;
    private Game game;
    private Color nothingColor;
    private ArrayList<JComponent> titlePane, skinPane;
    private boolean firstPlay;
    
    public TitleFrame() {
        nothingColor = new Color(0, 0, 0, 0);
        titlePane = new ArrayList<JComponent>();
        skinPane = new ArrayList<JComponent>();
        firstPlay = true;
        
        requestFocus();
        setAlwaysOnTop(true);
        setUndecorated(true);
        setLocation(520, 250);
        getContentPane().setBackground(Color.WHITE);
        getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
        setSize(880, 580);
        addMouseListener(new TitleFrame.MouseInput());
        addKeyListener(this);
        
        BufferedImage image = null;
        InputStream input = getClass().getResourceAsStream("icon.png");
        if (input == null) {
            input = getClass().getResourceAsStream("/icon.png");
        }
        try {
            image = ImageIO.read(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        setIconImage(image);
        pane = new JLayeredPane();
        pane.setLayout(null);
        add(pane);
        setUpTitlePane();
        setUpSkinPane();
        pane.setVisible(true);
        
        setVisible(true);
    }
    public void setUpTitlePane() {
        JLabel title = new JLabel("Pong");
        title.setFont(new Font("Arial", 0, 120));
        title.setForeground(Color.BLACK);
        int width = title.getFontMetrics(title.getFont()).stringWidth("Pong");
        title.setBounds(440 - width / 2, 30, width, title.getFontMetrics(title.getFont()).getHeight());
        pane.setLayer(title, 2);
        pane.add(title);
        titlePane.add(title);
        
        MyButton play = new MyButton("play");
        play.setFont(new Font("Arial", 0, 60));
        play.setForeground(Color.BLACK);
        play.setBackground(nothingColor);
        play.setHoverBackgroundColor(nothingColor);
        play.setPressedBackgroundColor(nothingColor);
        play.setHoverTextColor(Color.GREEN);
        play.setPressedTextColor(new Color(0, 200, 0));
        play.setBorder(null);
        play.setFocusable(false);
        width = play.getFontMetrics(play.getFont()).stringWidth("play");
        play.setBounds(440 - width / 2, 200, width, play.getFontMetrics(play.getFont()).getHeight());
        play.addActionListener(new TitleFrame.PlayEvent(this));
        pane.setLayer(play, 2);
        pane.add(play);
        titlePane.add(play);
        
        MyButton skinButton = new MyButton("skins");
        skinButton.setFont(new Font("Arial", 0, 60));
        skinButton.setForeground(Color.BLACK);
        skinButton.setBackground(nothingColor);
        skinButton.setHoverBackgroundColor(nothingColor);
        skinButton.setPressedBackgroundColor(nothingColor);
        skinButton.setHoverTextColor(Color.GREEN);
        skinButton.setPressedTextColor(new Color(0, 200, 0));
        skinButton.setBorder(null);
        skinButton.setFocusable(false);
        width = skinButton.getFontMetrics(skinButton.getFont()).stringWidth("skins");
        skinButton.setBounds(440 - width / 2, play.getY() + play.getHeight() + 22, width, skinButton.getFontMetrics(skinButton.getFont()).getHeight());
        skinButton.addActionListener(new SkinEvent());
        pane.setLayer(skinButton, 2);
        pane.add(skinButton);
        titlePane.add(skinButton);
    }
    public void setUpSkinPane() {
        JLabel skinTitle = new JLabel("Change the Colors!");
        skinTitle.setFont(new Font("Arial", 0, 70));
        int titleWidth = skinTitle.getFontMetrics(skinTitle.getFont()).stringWidth("Change the Colors!");
        skinTitle.setForeground(Color.BLACK);
        skinTitle.setBounds(440 - titleWidth / 2, 30, titleWidth, skinTitle.getFontMetrics(skinTitle.getFont()).getHeight());
        skinTitle.setVisible(false);
        pane.setLayer(skinTitle, 2);
        pane.add(skinTitle);
        skinPane.add(skinTitle);
        
        JTextArea outline = new JTextArea();
        outline.setBackground(Color.BLACK);
        outline.setEditable(false);
        outline.setBounds(20, skinTitle.getY() + skinTitle.getHeight() + 30, 530, 400);
        outline.setVisible(false);
        pane.setLayer(outline, 0);
        pane.add(outline);
        skinPane.add(outline);
        
        JTextArea colorOutline = new JTextArea();
        colorOutline.setBackground(Color.BLACK);
        colorOutline.setEditable(false);
        colorOutline.setBounds(outline.getX() + outline.getWidth() + 20, skinTitle.getY() + skinTitle.getHeight() + 30, 270, 300);
        colorOutline.setVisible(false);
        pane.setLayer(colorOutline, 0);
        pane.add(colorOutline);
        skinPane.add(colorOutline);
        
        JTextArea rgboutline = new JTextArea();
        rgboutline.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        rgboutline.setBackground(Color.WHITE);
        rgboutline.setEditable(false);
        rgboutline.setBounds(outline.getX() + outline.getWidth() + 20, colorOutline.getY() + colorOutline.getHeight() + 20, 270, 80);
        rgboutline.setVisible(false);
        pane.setLayer(rgboutline, 0);
        pane.add(rgboutline);
        skinPane.add(rgboutline);
        
        JTextField rField = new JTextField();
        rField.setFont(new Font("Arial", 0, 24));
        rField.setBounds(rgboutline.getX() + 10, rgboutline.getY() + rgboutline.getHeight() - 50, 75, 40);
        rField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        rField.setVisible(false);
        pane.setLayer(rField, 2);
        pane.add(rField);
        skinPane.add(rField);
        JLabel rName = new JLabel("R");
        rName.setFont(new Font("Arial", 0, 28));
        rName.setBounds(rField.getX() + 25, rField.getY() - 30, rName.getFontMetrics(rName.getFont()).stringWidth(rName.getText()), rName.getFontMetrics(rName.getFont()).getHeight());
        rName.setForeground(Color.RED);
        rName.setVisible(false);
        pane.setLayer(rName, 2);
        pane.add(rName);
        skinPane.add(rName);
        
        JTextField gField = new JTextField();
        gField.setFont(new Font("Arial", 0, 24));
        gField.setBounds(10 + rField.getX() + rField.getWidth(), rgboutline.getY() + rgboutline.getHeight() - 50, 75, 40);
        gField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        gField.setVisible(false);
        pane.setLayer(gField, 2);
        pane.add(gField);
        skinPane.add(gField);
        
        JLabel gName = new JLabel("G");
        gName.setFont(new Font("Arial", 0, 28));
        gName.setBounds(gField.getX() + 25, gField.getY() - 30, gName.getFontMetrics(gName.getFont()).stringWidth(gName.getText()), gName.getFontMetrics(gName.getFont()).getHeight());
        gName.setForeground(Color.GREEN);
        gName.setVisible(false);
        pane.setLayer(gName, 2);
        pane.add(gName);
        skinPane.add(gName);
        
        JTextField bField = new JTextField();
        bField.setFont(new Font("Arial", 0, 24));
        bField.setBounds(10 + gField.getX() + gField.getWidth(), rgboutline.getY() + rgboutline.getHeight() - 50, 75, 40);
        bField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        bField.setVisible(false);
        pane.setLayer(bField, 2);
        pane.add(bField);
        skinPane.add(bField);
        
        JLabel bName = new JLabel("B");
        bName.setFont(new Font("Arial", 0, 30));
        bName.setBounds(bField.getX() + 25, bField.getY() - 30, bName.getFontMetrics(bName.getFont()).stringWidth(bName.getText()), bName.getFontMetrics(gName.getFont()).getHeight());
        bName.setForeground(Color.BLUE);
        bName.setVisible(false);
        pane.setLayer(bName, 2);
        pane.add(bName);
        skinPane.add(bName);
    }
    
    public void titleFrameChange() {
        for (int i = 0; i < titlePane.size(); ++i) {
            titlePane.get(i).setVisible(true);
        }
        for (int i = 0; i < skinPane.size(); ++i) {
            skinPane.get(i).setVisible(false);
        }
    }
    public void skinFrameChange() {
        for (int i = 0; i < skinPane.size(); ++i) {
            skinPane.get(i).setVisible(true);
        }
        for (int i = 0; i < titlePane.size(); ++i) {
            titlePane.get(i).setVisible(false);
        }
    }
    
    public void keyPressed(final KeyEvent e) {
        final int key = e.getKeyCode();
        if (key == KeyEvent.VK_ESCAPE) {
            if (titlePane.get(0).isVisible()) {
                System.exit(0);
            } else if (skinPane.get(0).isVisible()) {
                titleFrameChange();
            }
        } else if (key == KeyEvent.VK_C) {
            System.exit(0);
        }
    }
   
    private class PlayEvent implements ActionListener {
        private TitleFrame frame;
        
        public PlayEvent(TitleFrame frame) {
            this.frame = frame;
        }
        
        public void actionPerformed(final ActionEvent e) {
            if (firstPlay) {
                new Game(TitleFrame.this.getX(), TitleFrame.this.getY(), this.frame);
            } else {
                game.startBackUp(frame);
            }
        }
    }
    private class SkinEvent implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            TitleFrame.this.skinFrameChange();
        }
    }
    
    private class MouseFollow extends Thread {
        private boolean first;
        private int mouseLastx, mouseLasty;
        
        private MouseFollow() {
            first = true;
            mouseLastx = 0;
            mouseLasty = 0;
        }
        
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Point mousePos = MouseInfo.getPointerInfo().getLocation();
                
                if (!first) {
                	mouseLastx = (int)mousePos.getX() - mouseLastx;
					mouseLasty = (int)mousePos.getY() - mouseLasty;
					setLocation(getX() + mouseLastx, getY() + mouseLasty);
                }
                first = false;
				mouseLastx = (int)mousePos.getX();
				mouseLasty = (int)mousePos.getY();
            }
        }
    }
    public class MouseInput extends MouseAdapter {
        private int lastX, lastY;
        private Thread mouseFollow;
        
        public MouseInput() {
            lastX = 0;
            lastY = 0;
            mouseFollow = new Thread();
        }

        public void mousePressed(MouseEvent e) {
            lastX = getX();
            lastY = getY();
            mouseFollow = new MouseFollow();
            mouseFollow.start();
        }
        public void mouseReleased(MouseEvent e) {
            mouseFollow.stop();
            lastX = Math.abs(lastX - getX());
            lastY = Math.abs(lastY - getY());
        }
    }
    
    public void keyReleased(final KeyEvent e) {}
    public void keyTyped(final KeyEvent e) {}
    public static void main(final String[] args) {
        new TitleFrame();
    }
}