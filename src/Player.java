import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Rectangle;

// 
// Decompiled by Procyon v0.5.36
// 

public class Player
{
    private int GWIDTH;
    private int GHEIGHT;
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected int dfb;
    protected int speed;
    protected int score;
    protected float velX;
    protected float velY;
    protected boolean up;
    protected boolean down;
    protected boolean left;
    protected boolean right;
    protected boolean pColor;
    protected Rectangle ub;
    protected Rectangle db;
    protected Rectangle rb;
    protected Rectangle lb;
    protected Rectangle topBound;
    protected Rectangle bottomBound;
    private Color p1Color;
    private Color p2Color;
    
    public Player(final int GWIDTH, final int GHEIGHT, final Rectangle topBound, final Rectangle bottomBound, final Color p1Color, final Color p2Color, final boolean pColor) {
        this.width = 25;
        this.height = 150;
        this.dfb = 20;
        this.speed = 9;
        this.score = 0;
        if (pColor) {
            this.x = this.dfb;
        }
        else {
            this.x = GWIDTH - this.dfb - this.width;
        }
        this.y = GHEIGHT / 2 - this.height / 2;
        this.GWIDTH = GWIDTH;
        this.GHEIGHT = GHEIGHT;
        this.pColor = pColor;
        this.topBound = topBound;
        this.bottomBound = bottomBound;
        this.p1Color = p1Color;
        this.p2Color = p2Color;
    }
    
    public void setUpBounds() {
        if (this.pColor) {
            this.x = this.dfb;
        }
        else {
            this.x = this.GWIDTH - this.dfb - this.width;
        }
        this.y = this.GHEIGHT / 2 - this.height / 2;
    }
    
    public void update() {
        this.x += (int)this.velX;
        this.y += (int)this.velY;
        if (this.up) {
            this.velY = (float)(-this.speed);
        }
        else if (this.down) {
            this.velY = (float)this.speed;
        }
        else {
            this.velY = 0.0f;
        }
        if (this.down) {
            this.velY = (float)this.speed;
        }
        else if (this.up) {
            this.velY = (float)(-this.speed);
        }
        else {
            this.velY = 0.0f;
        }
        this.collision();
    }
    
    public void collision() {
        if (this.getBounds().intersects(this.topBound)) {
            this.velY = 0.0f;
            this.y = (int)(this.topBound.getY() + this.topBound.getHeight());
        }
        else if (this.getBounds().intersects(this.bottomBound)) {
            this.velY = 0.0f;
            this.y = (int)this.bottomBound.getY() - this.height;
        }
    }
    
    public void draw(final Graphics2D g) {
        if (this.pColor) {
            g.setColor(this.p1Color);
        }
        else {
            g.setColor(this.p2Color);
        }
        g.fillRect(this.x, this.y, this.width, this.height);
    }
    
    public Rectangle getBounds() {
        return new Rectangle(this.x, this.y, this.width, this.height);
    }
}