import java.awt.Graphics2D;
import java.awt.Color;
import java.util.Random;
import java.awt.Rectangle;

// 
// Decompiled by Procyon v0.5.36
// 

public class Ball
{
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int maxSpeed;
    protected int GWIDTH;
    protected int GHEIGHT;
    private float velX;
    private float velY;
    private Player player1;
    private Player player2;
    private Rectangle topBound;
    private Rectangle bottomBound;
    private Random rand;
    private Color ballColor;
    
    public Ball(final int GWIDTH, final int GHEIGHT, final Player player1, final Player player2, final Rectangle topBound, final Rectangle bottomBound, final Color ballColor) {
        this.maxSpeed = 14;
        this.rand = new Random();
        this.GWIDTH = GWIDTH;
        this.GHEIGHT = GHEIGHT;
        this.player1 = player1;
        this.player2 = player2;
        this.topBound = topBound;
        this.bottomBound = bottomBound;
        this.ballColor = ballColor;
        this.setUpBounds();
        this.setUpVel();
    }
    
    public void setUpBounds() {
        this.x = this.GWIDTH / 2 - 15;
        this.y = this.GHEIGHT / 2 - 15;
        this.width = 30;
        this.height = 30;
    }
    
    public void setUpVel() {
        this.velX = (float)(this.rand.nextInt(this.maxSpeed + 1) - this.maxSpeed / 2);
        this.velY = (float)(this.rand.nextInt(11) - 5);
        while ((this.velX < this.maxSpeed / 2 && this.velX > -(this.maxSpeed / 2)) || (this.velY < 1.0f && this.velY > -1.0f)) {
            this.velX = (float)(this.rand.nextInt(this.maxSpeed + 1) - this.maxSpeed / 2);
            this.velY = (float)(this.rand.nextInt(11) - this.maxSpeed / 2);
        }
    }
    
    public void update() {
        this.x += (int)this.velX;
        this.y += (int)this.velY;
        this.collision();
    }
    
    public void collision() {
        if (this.getBounds().intersects(this.topBound)) {
            this.velY *= -1.0f;
            this.y = (int)(this.topBound.getY() + this.topBound.getHeight());
        }
        else if (this.getBounds().intersects(this.bottomBound)) {
            this.velY *= -1.0f;
            this.y = (int)this.bottomBound.getY() - this.height;
        }
        if (this.getBounds().intersects(this.player1.getBounds())) {
            this.velX = this.velX * -1.0f + 1.0f;
            this.x = this.player1.x + this.player1.width;
            if (Math.abs(this.velX + Math.abs(this.player1.velY / 4.0f)) <= this.maxSpeed) {
                if (this.velY < 0.0f && this.player1.velY < 0.0f) {
                    this.velX += Math.abs(this.player1.velY / 2.0f);
                }
                else if (this.velY > 0.0f && this.player1.velY > 0.0f) {
                    this.velX += this.player1.velY / 4.0f;
                }
                else if (this.velY > 0.0f && this.player1.velY < 0.0f && this.velX - Math.abs(this.player1.velY / 4.0f) > 1.0f) {
                    this.velX -= Math.abs(this.player1.velY / 4.0f);
                    this.velY -= Math.abs(this.player1.velY / 4.0f);
                }
                else if (this.velY < 0.0f && this.player1.velY > 0.0f && this.velX - this.player1.velY / 4.0f > 1.0f) {
                    this.velX -= this.player1.velY / 4.0f;
                    this.velY -= Math.abs(this.player1.velY / 4.0f);
                }
            }
            else if (this.velY > 0.0f && this.player1.velY < 0.0f && this.velX - Math.abs(this.player1.velY / 4.0f) > 1.0f) {
                this.velX -= Math.abs(this.player1.velY / 4.0f);
                this.velY -= Math.abs(this.player1.velY / 4.0f);
            }
            else if (this.velY < 0.0f && this.player1.velY > 0.0f && this.velX - this.player1.velY / 4.0f > 1.0f) {
                this.velX -= this.player1.velY / 4.0f;
                this.velY -= this.player1.velY / 4.0f;
            }
        }
        else if (this.getBounds().intersects(this.player2.getBounds())) {
            this.velX = this.velX * -1.0f - 1.0f;
            this.x = this.player2.x - this.width;
            if (Math.abs(this.velX - this.player2.velY / 4.0f) <= this.maxSpeed) {
                if (this.velY < 0.0f && this.player2.velY < 0.0f) {
                    this.velX -= Math.abs(this.player2.velY / 2.0f);
                }
                else if (this.velY > 0.0f && this.player2.velY > 0.0f) {
                    this.velX -= this.player2.velY / 4.0f;
                }
                else if (this.velY > 0.0f && this.player2.velY < 0.0f && this.velX + Math.abs(this.player2.velY / 4.0f) < -1.0f) {
                    this.velX += Math.abs(this.player2.velY / 4.0f);
                    this.velY += Math.abs(this.player2.velY / 4.0f);
                }
                else if (this.velY < 0.0f && this.player2.velY > 0.0f && this.velX + this.player2.velY / 4.0f < -1.0f) {
                    this.velX += this.player2.velY / 4.0f;
                    this.velY += this.player2.velY / 4.0f;
                }
            }
            else if (this.velY > 0.0f && this.player2.velY < 0.0f && this.velX + Math.abs(this.player2.velY / 4.0f) < -1.0f) {
                this.velX += Math.abs(this.player2.velY / 4.0f);
                this.velY += Math.abs(this.player2.velY / 4.0f);
            }
            else if (this.velY < 0.0f && this.player2.velY > 0.0f && this.velX + this.player2.velY / 4.0f < -1.0f) {
                this.velX += this.player2.velY / 4.0f;
                this.velY += this.player2.velY / 4.0f;
            }
        }
    }
    
    public void draw(final Graphics2D g) {
        g.setColor(this.ballColor);
        g.fillOval(this.x, this.y, this.width, this.height);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
}