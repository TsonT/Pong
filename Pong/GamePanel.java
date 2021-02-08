import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.text.*;

/**
 * an object that extends the JPanel object
 * it holds the main game play of the game
 * @author Truong-son Tran
 */
public class GamePanel extends JPanel
{
    /** the paddle object for player1*/
    public Paddle paddle1;
    
    /** the paddle object for player2*/
    public Paddle paddle2;
    
    /** the ball object*/
    public Ball ball;
    
    /** the key listener for the game panel*/
    private PongKeyListener pongKeyListener;
    
    /** the arraylist that holds all the paddles in play*/
    private ArrayList<Paddle> paddles = new ArrayList<>();
    
    /** the starting x position of paddle1*/
    public static final Integer PADDLE1_STARTING_POSX = 0;
    
    /** the starting x position of paddle2*/
    public static final Integer PADDLE2_STARTING_POSX = (Pong.GAME_PANEL_WIDTH - Paddle.WIDTH);
    
    /** the starting y position of paddle1*/
    public static final Integer PADDLE1_STARTING_POSY = (Pong.GAME_PANEL_HEIGHT / 2) - 
                                                    (Paddle.HEIGHT / 2);
    
    /** the starting y position of paddle2*/
    public static final Integer PADDLE2_STARTING_POSY = (Pong.GAME_PANEL_HEIGHT / 2) - 
                                                    (Paddle.HEIGHT / 2);
    
    /** the starting x position of the ball*/
    public static final Integer BALL_STARTING_POSX = (Pong.GAME_PANEL_WIDTH / 2) - 
                                                 (Ball.WIDTH / 2);
    
    /** the starting y position of the ball*/
    public static final Integer BALL_STARTING_POSY = (Pong.GAME_PANEL_HEIGHT / 2) - 
                                                 (Ball.HEIGHT / 2);
    
    /** the starting x speed of the ball*/
    public static final Integer BALL_STARTING_DELTAX = Pong.getDeltaX();
    
    /** the starting y speed of the ball*/
    public static final Integer BALL_STARTING_DELTAY = Pong.getDeltaY();
    
    /** the name of the file that outputs the game logs*/
    public static final String OUTPUT_FILE_NAME = "GameLog.txt";
    
    /** the width of the line down the middle*/
    public static final Integer DASH_LINE_WIDTH = 4;
    
    /** the length of the array representing the dashing pattern*/
    public static final Integer DASH_ARRAY_LENGTH = 9;
    
    /** a boolean that tells whether a round is currently active*/
    private boolean roundStarted = false;
    
    /** the number of milliseconds to update the game*/
    private static final Integer REFRESH_TIME = 10;
    
    /**
     * the constructor for game panel
     * sets the background color based on the settings
     * initializes all the paddles and ball
     * also initializes the key listener
     */
    public GamePanel()
    {
        this.setBackground(Pong.getGameBackgroundColor());
        
        paddle1 = new Paddle(PADDLE1_STARTING_POSX, PADDLE1_STARTING_POSY, KeyEvent.VK_W, 
                            KeyEvent.VK_S, Pong.getPaddleColor(), Pong.getPlayer1Name());
                            
        paddle2 = new Paddle(PADDLE2_STARTING_POSX, PADDLE2_STARTING_POSY, KeyEvent.VK_UP,
                            KeyEvent.VK_DOWN, Pong.getPaddleColor(), Pong.getPlayer2Name());
        
        paddles.add(paddle1);
        paddles.add(paddle2);

        ball = new Ball(BALL_STARTING_POSX, BALL_STARTING_POSY, BALL_STARTING_DELTAX,
                       BALL_STARTING_DELTAY, Pong.getBallColor());
        
        pongKeyListener = new PongKeyListener();
        addKeyListener(pongKeyListener);
    }
    
    /**
     * renders the game based on the REFRESH_TIME
     * runs in a constant loop until the game is exited
     * @throws InterruptedException when another thread attempts to interrupt
     */
    public void run() throws InterruptedException
    {
        while(true)
        {
            update();
            
            Thread.sleep(REFRESH_TIME);
            
            repaint();
        }
    }
    
    /**
     * draws all the objects including the paddles, the ball, and the dashed line
     * @param g the graphics element that creates the shapes needed
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        drawDashedLine(g, Pong.GAME_PANEL_WIDTH / 2, 0, Pong.GAME_PANEL_WIDTH / 2, 
                     Pong.GAME_PANEL_HEIGHT);

        for (Paddle paddle : paddles)
        {
            paddle.draw(g);
        }
        
        ball.draw(g);
    }
    
    /**
     * draws a dashed line based on the parameters
     * @param g the graphics element that draws the shapes
     * @param x1 the starting x position of the line
     * @param y1 the starting y position of the line
     * @param x2 the ending x position of the line
     * @param y2 the ending y position of the line
     */
    public void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2)
    {

        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setColor(Pong.getPaddleColor());

        Stroke dashed = new BasicStroke(DASH_LINE_WIDTH, BasicStroke.CAP_BUTT, 
                                     BasicStroke.JOIN_BEVEL,
                                     0, new float[]{DASH_ARRAY_LENGTH}, 0);
        g2d.setStroke(dashed);
        g2d.drawLine(x1, y1, x2, y2);

        g2d.dispose();
    }
    
    /**
     * updates the positions of the paddle and the ball
     */
    public void update()
    {
        movePaddles(pongKeyListener.getKeysPressed());
        moveBall();
    }
    
    /**
     * moves the paddles based on the keys within the array parameter
     * also starts the round if the round has been ended
     * @param keysPressed an arraylist holding all the keys that have been pressed
     */
    public void movePaddles(ArrayList<Integer> keysPressed)
    {
        for (Integer keyCode : keysPressed)
        {
            if (!roundStarted)
            {
                startRound();
            }
            
            for (Paddle paddle : paddles)
            {
                if (keyCode == paddle.getUpKey())
                {
                    paddle.moveY(-Paddle.PADDLE_SPEED);
                }
                
                if (keyCode == paddle.getDownKey())
                {
                    paddle.moveY(Paddle.PADDLE_SPEED);
                }
                
                checkPaddleCollisionBorders(paddle);
            }
        }
    }
    
    /**
     * checks if the paddle has reached the edge of the game screen
     * if it has then the paddle is not able to move in that direction anymore
     * @param paddle the paddle that is being tracked
     */
    public void checkPaddleCollisionBorders(Paddle paddle)
    {
        //Top border
        if (paddle.objectCollides(0, 0, Pong.GAME_PANEL_WIDTH, 0))
        {
            paddle.setPosY(0);
        }
        //Bottom border
        else if (paddle.objectCollides(0, Pong.GAME_PANEL_HEIGHT, Pong.GAME_PANEL_WIDTH, 0))
        {
            paddle.setPosY(Pong.GAME_PANEL_HEIGHT - Paddle.HEIGHT);
        }
    }
    
    /**
     * moves the ball based on its speed
     * also checks if the ball reaches the left or right end
     */
    public void moveBall()
    {
        ball.move();
        ballCollidesPaddles();
        ballCollidesBorders();
        if (ballScored())
        {
            Pong.updateScores(paddle1.getScore(), paddle2.getScore());
            ball.reset();
            resetRound();
            checkIfWon();
        }
    }
    
    /**
     * checks if the ball collides with one of the paddles
     * if it does then reflect the x speed of the ball
     * @return true if the ball has hit a paddle, otherwise 
     *         returns false
     */
    public boolean ballCollidesPaddles()
    {
        for (Paddle paddle : paddles)
        {
            if (ball.objectCollides(paddle.getPosX(), paddle.getPosY(), 
                Paddle.WIDTH, Paddle.HEIGHT))
            {
                ball.deflectX();
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * checks if the ball collides with one of the upper or lower borders
     * if it does the reflect the y speed of the ball
     * @return true if the ball has hit one of the borders, otherwise
     *         returns false
     */
    public boolean ballCollidesBorders()
    {
        //Top and bottom borders only
        if (ball.objectCollides(0, 0, Pong.GAME_PANEL_WIDTH, 0) || 
            ball.objectCollides(0, Pong.GAME_PANEL_HEIGHT, Pong.GAME_PANEL_WIDTH, 0))
        {
            ball.deflectY();
            return true;
        }
        
        return false;
    }
    
    /**
     * checks if the ball collides with one of the left or right borders
     * if it does then the score of the paddle that is opposite of the border
     * gains a point and the ball is served in the direction of the paddle that 
     * got scored on
     * @return true if the ball hit one of the borders, otherwise
     *         returns false
     */
    public boolean ballScored()
    {
        //Left border
        if (ball.objectCollides(0, 0, 0, Pong.GAME_PANEL_HEIGHT))
        {
            paddle2.scored();
            ball.setServeDirection(Directions.LEFT);
            return true;
        }
        //Right border
        else if (ball.objectCollides(Pong.GAME_PANEL_WIDTH, 0, 0, Pong.GAME_PANEL_HEIGHT))
        {
            paddle1.scored();
            ball.setServeDirection(Directions.RIGHT);
            return true;
        }
        
        return false;
    }
    
    /**
     * resets the paddles and ball to their starting positions 
     * disables the keyListener until a key is released and pressed again
     * sets the round started variable to false
     */
    public void resetRound()
    {
        for (Paddle paddle : paddles)
        {
            paddle.reset();
        }
        
        roundStarted = false;
        ball.reset();
        pongKeyListener.reset();
    }
    
    /**
     * starts the round by setting the round started variable to true
     * and serving the ball
     */
    public void startRound()
    {
        roundStarted = true;
        ball.serve();
    }
    
    /**
     * checks if the points required to win the game has been reached by
     * one of the players if it has then the game over method is called
     */
    public void checkIfWon()
    {
        for (Paddle paddle : paddles)
        {
            if (paddle.getScore() == Pong.getPointsToWin())
            {
                gameOver(paddle);
            }
        }
    }
    
    /**
     * removes the key listener from the panel to stop anymore movement
     * creates the winner label with the name of the paddle
     * outputs the results to the game logs
     * @param paddle the paddle that won
     */
    public void gameOver(Paddle paddle)
    {
        removeKeyListener(pongKeyListener);
        Pong.createWinnerLabel(paddle.getName());
        outputToGameLog(paddle);
    }

    /**
     * outputs the winner of the game to the game log text file
     * with the current date
     * @param paddle the paddle that won
     */
    public void outputToGameLog(Paddle paddle)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(OUTPUT_FILE_NAME, true);
            PrintWriter out = new PrintWriter(fos);
            
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
        
            out.println(currentDate + ": WINNER - " + paddle.getName());
            
            out.flush();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        
    }
}