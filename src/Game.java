import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Game extends Canvas implements KeyListener, Runnable {
    protected static int GWIDTH = 900, GHEIGHT = 600;
    private GameFrame gameFrame;
    private Thread thread;
    private boolean running, updateRun, drawRun, update, paint;
    private Player player1, player2;
    private TitleFrame titleFrame;
    BufferedImage pauseImg;
    private Ball ball;
    private Rectangle topBound, bottomBound;
    private Color topRectColor, botRectColor, bgColor, dashedColor, score1Color, score2Color, p1Color, p2Color, ballColor;
    
    public Game(int x, int y, TitleFrame titleFrame) {
        pauseImg = null;
        update = true;
        paint = true;
        
        topBound = new Rectangle(10, 0, 880, 5);
        bottomBound = new Rectangle(10, 595, 880, 5);
        
        topRectColor = Color.BLACK;
        botRectColor = Color.BLACK;
        bgColor = Color.WHITE;
        dashedColor = Color.BLACK;
        score1Color = Color.BLACK;
        score2Color = Color.BLACK;
        p1Color = Color.BLACK;
        p2Color = Color.BLACK;
        ballColor = Color.BLACK;
        
        this.titleFrame = titleFrame;
        gameFrame = new GameFrame(x, y, 900, 600, this);
        player1 = new Player(900, 600, topBound, bottomBound, p1Color, p2Color, true);
        player2 = new Player(900, 600, topBound, bottomBound, p1Color, p2Color, false);
        ball = new Ball(900, 600, player1, player2, topBound, bottomBound, ballColor);
        
        start();
        addKeyListener(this);
        addMouseListener(new Game.MouseInput());
        try {
            pauseImg = ImageIO.read(new File("data/pause.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start() {
        running = true;
        updateRun = true;
        drawRun = true;
        thread = new Thread(this);
        thread.start();
    }
    
    public void pointCheck() {
        if (ball.x + ball.width <= 0) {
            Player player2 = this.player2;
            ++player2.score;
            reset();
        }
        else if (ball.x >= 900) {
            Player player3 = player1;
            ++player3.score;
            reset();
        }
    }
    
    public void reset() {
        ball.setUpBounds();
        ball.setUpVel();
        player1.setUpBounds();
        player2.setUpBounds();
    }
    public void startBackUp(TitleFrame titleFrame) {
        this.titleFrame = titleFrame;
        gameFrame.setVisible(true);
        thread.start();
    }
    
    public void update() {
        if (updateRun) {
            player1.update();
            player2.update();
            ball.update();
            pointCheck();
        }
    }
    public void paint() {
        if (drawRun) {
            BufferStrategy bs = getBufferStrategy();
            if (bs == null) {
                createBufferStrategy(3);
                return;
            }
            Graphics2D g = (Graphics2D)bs.getDrawGraphics();
            g.setColor(bgColor);
            g.fillRect(0, 0, 900, 600);
            g.setColor(dashedColor);
            for (int i = 0; i < 600; i += 31) {
                g.fillRect(445, i, 10, 16);
            }
            ball.draw(g);
            player1.draw(g);
            player2.draw(g);
            g.setColor(topRectColor);
            g.fill(topBound);
            g.setColor(botRectColor);
            g.fill(bottomBound);
            g.setFont(new Font("Arial", 1, 110));
            g.setColor(score1Color);
            g.drawString(new StringBuilder(String.valueOf(player1.score)).toString(), 300, 100);
            g.setColor(score2Color);
            g.drawString(new StringBuilder(String.valueOf(player2.score)).toString(), 534, 100);
            if (!updateRun) {
                g.drawImage(pauseImg, 450 - pauseImg.getWidth() / 2, 300 - pauseImg.getHeight() / 2, null);
            }
            g.dispose();
            bs.show();
        }
    }
    
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            player1.up = true;
        } else if (key == KeyEvent.VK_S) {
            player1.down = true;
        } else if (key == KeyEvent.VK_UP) {
            player2.up = true;
        } else if (key == KeyEvent.VK_DOWN) {
            player2.down = true;
        } else if (key == KeyEvent.VK_C) {
            System.exit(0);
        } else if (key == KeyEvent.VK_ESCAPE) {
            gameFrame.setVisible(false);
            titleFrame.setLocation(gameFrame.getX(), gameFrame.getY());
            titleFrame.setVisible(true);
            thread.stop();
        } else if (key == 82) {
            player1.score = 0;
            player2.score = 0;
        } else if (key == 80) {
            if (updateRun) {
                updateRun = false;
            } else if (!updateRun) {
                updateRun = true;
            }
        }
    }
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_W) {
            player1.up = false;
        } else if (key == KeyEvent.VK_S) {
            player1.down = false;
        } else if (key == KeyEvent.VK_UP) {
            player2.up = false;
        } else if (key == KeyEvent.VK_DOWN) {
            player2.down = false;
        }
    }
    
    public void run() {
        requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1.0E9 / amountOfTicks;
        double delta = 0.0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1.0) {
                if (update) {
                    update();
                }
                --delta;
            }
            if (paint) {
                paint();
            }
            ++frames;
            if (System.currentTimeMillis() - timer > 1000L) {
                timer += 1000L;
                frames = 0;
            }
        }
        stop();
    }
    public void stop() {
        System.exit(0);
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
    
    public static void main(String[] args) {
        new Game(510, 240, new TitleFrame());
    }
    public void keyTyped(KeyEvent e) { }
}