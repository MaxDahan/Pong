import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameFrame extends JFrame {
    
    public GameFrame(int x, int y, int GWIDTH, int GHEIGHT, Game g) {
        requestFocus();
        setUndecorated(true);
        setAlwaysOnTop(true);
        setSize(GWIDTH, GHEIGHT);
        setLocation(x, y);
        addMouseListener((MouseListener)new GameFrame.MouseInput());
        
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
        add(g);
        setVisible(true);
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
}