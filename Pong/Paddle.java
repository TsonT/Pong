import java.awt.*;
import javax.swing.*;

/**
 * the paddle object
 * @author Truong-son Tran
 */
public class Paddle
{
    /** the current x position of the paddle*/
    private Integer posX;
    
    /** the current y position of the paddle*/
    private Integer posY;
    
    /** the integer value of the upkey for the paddle*/
    private Integer upKey;
    
    /** the integer value of the downkey for the paddle*/
    private Integer downKey;
    
    /** the height of the paddle*/
    public static final Integer HEIGHT = 200;
    
    /** the width of the paddle*/
    public static final Integer WIDTH = 20;
    
    /** the speed at which the paddle moves*/
    public static final Integer PADDLE_SPEED = 10;
    
    /** the starting x position of the paddle*/
    public Integer startingPosX;
    
    /** the starting y position of the paddle*/
    public Integer startingPosY;
    
    /** the color of the paddle*/
    private Color color = Color.white;
    
    /** the score of the paddle*/
    private Integer score = 0;
    
    /** the player name of the paddle*/
    private String name = "NONE";

    /**
     * the constructor for the paddle object
     * @param startingPosX the starting x postion of the paddle
     * @param startingPosY the starting y postion of the paddle
     * @param upKey the control for making the paddle go up
     * @param downKey the control for making the paddle go down
     * @param color the color of the paddle
     * @param name the name of the player that controls the paddle
     */
    public Paddle(Integer startingPosX, Integer startingPosY, Integer upKey,
                 Integer downKey, Color color, String name)
    {
        this.startingPosX = startingPosX;
        this.startingPosY = startingPosY;
        this.upKey = upKey;
        this.downKey = downKey;
        this.color = color;
        this.name = name;
        reset();
    }
    
    /**
     * getter for posX
     * @return posX the current x position of the paddle
     */
    public Integer getPosX()
    {
        return posX;
    }
    
    /**
     * setter for posX
     * @param posX the new x position of the paddle
     */
    public void setPosX(Integer posX)
    {
        this.posX = posX;
    }
    
    /**
     * getter for posY
     * @return posY the current y position of the paddle
     */
    public Integer getPosY()
    {
        return posY;
    }
    
    /**
     * setter for posY
     * @param posY the new y position of the paddle
     */
    public void setPosY(Integer posY)
    {
        this.posY = posY;
    }

    /**
     * getter for up key
     * @return upKey the integer value of the key that moves the paddle up
     */
    public Integer getUpKey()
    {
        return upKey;
    }
    
    /**
     * setter for up key
     * @param upKey the key to set to move the paddle up
     */
    public void setUpKey(Integer upKey)
    {
        this.upKey = upKey;
    }
    
    /**
     * getter for down key
     * @return downKey the integer value of the key that moves the paddle down
     */
    public Integer getDownKey()
    { 
        return downKey;
    }
    
    /**
     * setter for down key
     * @param downKey the key to set to move the paddle down 
     */
    public void setDownKey(Integer downKey)
    {
        this.downKey = downKey;
    }
    
    /**
     * getter for score
     * @return score the current score of the paddle
     */
    public Integer getScore()
    { 
        return score;
    }
    
    /**
     * setter for score
     * @param score the score to set to the score of the paddle
     */
    public void setScore(Integer score)
    {
        this.score = score;
    }
    
    /**
     * getter for name
     * @return name the name of the paddle
     */
    public String getName()
    { 
        return name;
    }
    
    /**
     * setter for name
     * @param name the name to set to the name of the paddle
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * increases the current score of the paddle by 1
     */
    public void scored()
    {
        score++;
    }
    
    /**
     * getter for color
     * @return color the color of the paddle
     */
    public Color getColor()
    { 
        return color;
    }
    
    /**
     * setter for color
     * @param color the color to set the paddle
     */
    public void setColor(Color color)
    {
        this.color = color;
    }
    
    /**
     * moves the paddle up or down based on the parameter
     * @param deltaY the amount to move the paddle
     */
    public void moveY(Integer deltaY)
    {
        posY += deltaY;
    }
    
    /**
     * sets the paddle to its original positions
     */
    public void reset()
    {
        posX = startingPosX;
        posY = startingPosY;
    }
    
    /**
     * checks if the paddle has collided with another object based on the parameters
     * @param otherX the x position of the object being checked
     * @param otherY the y position of the object being checked
     * @param otherWidth the width of the object being checked
     * @param otherHeight the height of the object being checked
     * @return true if the paddle overlaps with the object given, otherwise
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
     * draws the paddle using the graphic element provided
     * @param g the graphics element that draws the paddle
     */
    public void draw(Graphics g)
    {
        g.setColor(color);
        g.fillRect(posX, posY, WIDTH, HEIGHT);
    }
}