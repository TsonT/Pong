import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * The key listener for the pong game
 * @author Truong-son Tran
 */
public class PongKeyListener implements KeyListener
{
    /** an array that holds all of the keys that are currently pressed*/
    private ArrayList<Integer> keysPressed = new ArrayList<>();
    
    /** a variable that tells whether or not the listener is disabled*/
    private boolean isDisabled = false;

    /** the constructor of the listener*/
    public PongKeyListener()
    {
        
    }
    
    /**
     * when a key is typed
     * @param e the key that triggered the event
     */
    public void keyTyped(KeyEvent e)
    {
        
    }
    
    /**
     * when a key is pressed its key code is extracted and added
     * to the array list of currently pressed keys if it is not already there
     * @param e the key that triggered the event
     */
    public void keyPressed(KeyEvent e)
    {
        Integer keyCode = e.getKeyCode();
        
        if (keysPressed.indexOf(keyCode) < 0 && !isDisabled)
        {
            keysPressed.add(keyCode);
        }
    }
    
    /**
     * when a key is released the key code is extracted and if the array list
     * of the current pressed keys is empty then make sure the listener isnt disabled
     * if it is not empty then remove the key from the array list
     * @param e the key that triggered the event
     */
    public void keyReleased(KeyEvent e)
    {
        Integer keyCode = e.getKeyCode();
        
        if (keysPressed.isEmpty())
        {
            isDisabled = false;
        }
        else
        {
            keysPressed.remove(keysPressed.indexOf(keyCode));
        }
    }
    
    /**
     * getter for keysPressed
     * @return the arraylist of the current keys that are pressed down
     */
    public ArrayList<Integer> getKeysPressed()
    {
        return keysPressed;
    }
    
    /**
     * clears the keys pressed array list and makes the listener disabled until
     * a new key is pressed
     */
    public void reset()
    {
        keysPressed.clear();
        isDisabled = true;
    }
}