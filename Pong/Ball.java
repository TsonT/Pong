import java.util.*;
import java.awt.*;

/**
 * the ball object
 * @author Truong-son
 */
public class Ball
{
    /** the width of the ball*/
    public static final Integer WIDTH = 20;
    
    /** the height of the ball*/
    public static final Integer HEIGHT = 20;
    
    /** the x position of the ball when the round is reset*/
    public Integer restingPosX;
    
    /** the y position of the ball when the round is reset*/
    public Integer restingPosY;
    
    /** the resting x speed of the ball*/
    public Integer restingDeltaX = 0;
    
    /** the resting y speed of the ball*/
    public Integer restingDeltaY = 0;
    
    /** the starting x speed of the ball (this is constant if the ball does not speed up)*/
    private Integer startingDeltaX;
    
    /** the starting y speed of the ball (this is constant if the ball does not speed up)*/
    private Integer startingDeltaY;
    
    /** the current x speed of the ball*/
    private Integer deltaX;
    
    /** the current y speed of the ball*/
    private Integer deltaY;
    
    /** the current x position of the ball*/
    private Integer posX;
    
    /** the current y position of the ball*/
    private Integer posY;
    
    /** the color of the ball*/
    private Color color;
    
    /** the direction that the ball is going to go when it is served*/
    private Directions serveDirection = Directions.RIGHT;

    /**
     * the constructor for the ball object
     * @param restingPosX the x position of the ball when the round is reset
     * @param restingPosY the y position of the ball when the round is reset
     * @param startingDeltaX the starting x speed of the ball
     * @param startingDeltaY the starting y speed of the ball
     * @param color the color of the ball
     */
    public Ball(Integer restingPosX, Integer restingPosY, Integer startingDeltaX,
               Integer startingDeltaY, Color color)
    {
        this.restingPosX = restingPosX;
        this.restingPosY = restingPosY;
        this.startingDeltaX = startingDeltaX;
        this.startingDeltaY = startingDeltaY;
        this.color = color;
        reset();
    }
    
    /**
     * getter for posX
     * @return posX the current x position of the ball
     */
    public Integer getPosX()
    {
        return posX;
    }
    
    /**
     * setter for posX
     * @param posX the new x position of the ball
     */
    public void setPosX(Integer posX)
    {
        this.posX = posX;
    }
    
    /**
     * getter for posY
     * @return posY the current y position of the ball
     */
    public Integer getPosY()
    {
        return posY;
    }
    
    /**
     * setter for posY
     * @param posY the new y position of the ball
     */
    public void setPosY(Integer posY)
    {
        this.posY = posY;
    }
    
    /**
     * getter for deltaX
     * @return deltaX the current x speed of the ball
     */
    public Integer getDeltaX()
    {
        return deltaX;
    }
    
    /**
     * setter for deltaX
     * @param deltaX the x speed of the ball that is to be set
     */
    public void setDeltaX(Integer deltaX)
    {
        this.deltaX = deltaX;
    }
    
    /**
     * getter for deltaY
     * @return deltaY the current y speed of the ball
     */
    public Integer getDeltaY()
    {
        return deltaY;
    }
    
    /**
     * setter for deltaY
     * @param deltaY the y speed of the ball that is to be set
     */
    public void setDeltaY(Integer deltaY)
    {
        this.deltaY = deltaY;
    }
    
    /**
     * getter for serve direction
     * @return serveDirection the direction in which the ball is to be served
     */
    public Directions getServeDirection()
    {
        return serveDirection;
    }
    
    /**
     * setter for serve direction
     * @param serveDirection the direction to which to set the serve direction to
     */
    public void setServeDirection(Directions serveDirection)
    {
        this.serveDirection = serveDirection;
    }
    
    /**
     * getter for color of the ball
     * @return color the color of the ball
     */
    public Color getColor()
    { 
        return color;
    }
    
    /**
     * setter for color of the ball
     * @param color the color to set the color of the ball
     */
    public void setColor(Color color)
    {
        this.color = color;
    }
    
    /**
     * reverses the x direction of the ball
     */
    public void deflectX()
    {
        deltaX = -(deltaX);
    }
    
    /**
     * reverses the y direction of the ball
     */
    public void deflectY()
    {
        deltaY = -(deltaY);
    }
    
    /**
     * changes the current postion of the ball based on its deltaX and deltaY
     */
    public void move()
    {
        posX += deltaX;
        posY += deltaY;
    }
    
    /**
     * draws the ball object
     * @param g the graphics element that is used to draw the ball
     */
    public void draw(Graphics g)
    {
        g.setColor(color);
        g.fillOval(posX, posY, WIDTH, HEIGHT);
    }
    
    /**
     * increases the deltaX and deltaY of the ball by 1
     */
    public void accelerate()
    {
        if (deltaX < 0)
        {
            deltaX--;
        }
        else
        {
            deltaX++;
        }
        if (deltaY < 0)
        {
            deltaY--;
        }
        else
        {
            deltaY++;
        }
    }
    
    /**
     * sets the current postion of the ball to its resting position
     * sets the current speed of the ball to its resting speed
     */
    public void reset()
    {
        try
        {
            posX = restingPosX;
            posY = restingPosY;
            deltaX = restingDeltaX;
            deltaY = restingDeltaY;
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    /**
     * checks if the ball has collided with another object based on the parameters
     * @param otherX the x position of the object being checked
     * @param otherY the y position of the object being checked
     * @param otherWidth the width of the object being checked
     * @param otherHeight the height of the object being checked
     * @return true if the ball overlaps with the object given, otherwise
     *         return false
     */
    public boolean objectCollides(Integer otherX, Integer otherY,
                               Integer otherWidth, Integer otherHeight)
    {
        if (posX <= (otherX + otherWidth) && (posX + WIDTH) >= (otherX))
        {
            if ((posY) <= (otherY + otherHeight) && (posY + HEIGHT) >= (otherY))
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * sets the current speed of the ball to the original speed of the ball
     * based on what the serving direction is the ball will be sent that direction
     */
    public void serve()
    {
        deltaX = startingDeltaX;
        deltaY = startingDeltaY;
        
        if (serveDirection == Directions.LEFT)
        {
            deflectX();
        }
    }

}