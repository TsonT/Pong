import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.*;
import java.util.*;


/**
 * This is a game that stimulates table tennis
 * two players each control a paddle on the left or right
 * a person scores when the ball reaches one of the borders
 * the points to win can be changed in the settings
 * @author Truong-son Tran
 *
 */
public class Pong
{
    
    /** the width of the entire screen*/
    public static final Integer FRAME_WIDTH = 1000;
    
    /** the height of the entire screen*/
    public static final Integer FRAME_HEIGHT = 1000;
    
    /** the width of the actual game*/
    public static final Integer GAME_PANEL_WIDTH = 1000;
    
    /** the height of the actual game*/
    public static final Integer GAME_PANEL_HEIGHT = 1000;
    
    /** the font size for the score*/
    public static final float SCORE_SIZE = 100f;
    
    /** holds the paddle 1 score*/
    private static Integer paddle1Score = 0;
    
    /** holds the paddle 2 score*/
    private static Integer paddle2Score = 0;
    
    /** the label for player1 name*/
    private static JLabel labelPlayer1Name;
    
    /** the label for player1 score*/
    private static JLabel labelPaddle1Score;
    
    /** the label for player2 name*/
    private static JLabel labelPlayer2Name;
    
    /** the label for player2 score*/
    private static JLabel labelPaddle2Score;
    
    /** the total number of settings*/
    private static final Integer NUMBER_SETTINGS = 8; 
    
    /** the index where the color of the paddles is located*/
    public static final Integer PADDLE_COLOR_INDEX = 0;
    
    /** the index where the color of the ball is located*/
    public static final Integer BALL_COLOR_INDEX = 1;
    
    /** the index where the color of the background is located*/
    public static final Integer BACKGROUND_COLOR_INDEX = 2;
    
    /** the index where the player1 name is located*/
    public static final Integer PLAYER1_NAME_INDEX = 3;

    /** the index where the player2 name is located*/
    public static final Integer PLAYER2_NAME_INDEX = 4;
    
    /** the index where the x speed of the ball is located*/
    public static final Integer DELTAX_INDEX = 5;
    
    /** the index where the y speed of the ball is located*/
    public static final Integer DELTAY_INDEX = 6;
    
    /** the index where the number of points needed to win is located*/
    public static final Integer POINTS_TO_WIN_INDEX = 7;
    
    /** the width of the space between the two players labels*/
    public static final Integer SPACE_WIDTH = 80;
    
    /** an array holding all the settings options*/
    private static String[] settings = new String[NUMBER_SETTINGS]; 
    
    /** an array holding the default settings options*/
    private static final String[] DEFAULT_SETTINGS = {"white", "white", "black", 
                                                      "p1", "p2", "4", "3", "3"};
    
    /** the font of the score labels*/
    private static Font scoreFont = null;
    
    /** the panel where the game is being played*/
    private static GamePanel gamePanel = null;
    
    /** 
     * creates the GUI for the game
     * and starts the game from gamepanel
     * @param args holds the file name for the settings
     * @throws InterruptedException if the run method gets interrupted
     */
    public static void main(String[] args) throws InterruptedException
    {
        if (args.length != 0)
        {
            getSettings(args[0]);
        }
        else
        {
            settings = DEFAULT_SETTINGS;
        }
        

        JFrame frame = new JFrame("Pong");
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  
        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        
        gamePanel = new GamePanel();
        gamePanel.setPreferredSize(new Dimension(GAME_PANEL_WIDTH, GAME_PANEL_HEIGHT));
        gamePanel.setFocusable(true);
        frame.add(gamePanel);
        
        scoreFont = getScoreFont();
        
        labelPlayer1Name = new JLabel(getPlayer1Name().toUpperCase() + ": ");
        labelPlayer1Name.setFont(scoreFont);
        labelPlayer1Name.setForeground(Color.white);
        labelPlayer1Name.setAlignmentX(Component.CENTER_ALIGNMENT);
        gamePanel.add(labelPlayer1Name);
        
        labelPaddle1Score = new JLabel(Integer.toString(paddle1Score));
        labelPaddle1Score.setFont(scoreFont);
        labelPaddle1Score.setForeground(Color.white);
        labelPaddle1Score.setAlignmentX(Component.CENTER_ALIGNMENT);
        gamePanel.add(labelPaddle1Score);
        
        gamePanel.add(Box.createHorizontalStrut(SPACE_WIDTH));
        
        labelPlayer2Name = new JLabel(getPlayer2Name().toUpperCase() + ": ");
        labelPlayer2Name.setFont(scoreFont);
        labelPlayer2Name.setForeground(Color.white);
        labelPlayer2Name.setAlignmentX(Component.CENTER_ALIGNMENT);
        gamePanel.add(labelPlayer2Name);
        
        labelPaddle2Score = new JLabel(Integer.toString(paddle2Score));
        labelPaddle2Score.setFont(scoreFont);
        labelPaddle2Score.setForeground(Color.white);
        labelPaddle2Score.setAlignmentY(Component.CENTER_ALIGNMENT);
        gamePanel.add(labelPaddle2Score);
        
        frame.setVisible(true);
        frame.pack();
        gamePanel.run();
    }
    
    /**
     * gets the font file and returns it to be used
     * @return font the font which is used for the labels
     */
    public static Font getScoreFont()
    {
        Font font = null;
        try
        {
            font = Font.createFont(Font.TRUETYPE_FONT, 
                       new File("BitFont.ttf")).deriveFont(SCORE_SIZE);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("BitFont.ttf")));
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return font;
    }
    
    /**
     * updates the scores that are displayed
     * @param paddle1Score the score of the left paddle
     * @param paddle2Score the score of the right paddle
     */
    public static void updateScores(Integer paddle1Score, Integer paddle2Score)
    {
        paddle1Score = paddle1Score;
        labelPaddle1Score.setText(Integer.toString(paddle1Score));
        
        paddle2Score = paddle2Score;
        labelPaddle2Score.setText(Integer.toString(paddle2Score));
    }
    
    /**
     * gets the settings file and turns it into an array of strings
     * that are all lowercase
     * @param fileName the name of the settings file
     */
    private static void getSettings(String fileName)
    {
        Scanner in = null;
        
        try
        {
            FileInputStream fis = new FileInputStream(fileName);
            in = new Scanner(fis);
        }
        catch (Exception e)
        {
            System.out.println("Could not find the file, using default settings instead");
        }
            
        for (int i = 0; i < settings.length; i++)
        {
            try
            {
                String settingsString = in.nextLine();
                
                if (settingsString.indexOf(":") >= 0)
                {
                    settings[i] = settingsString;
                    settings[i] = settings[i].substring(settings[i].indexOf(":") + 1, 
                                                    settings[i].length());
                    settings[i] = settings[i].replaceAll("\\s+", "");
                    settings[i] = settings[i].toLowerCase();
                }
                else
                {
                    i--;
                }
            }
            catch (Exception e)
            {
                settings[i] = DEFAULT_SETTINGS[i];
            }
        }
    }
    
    /**
     * gets the color value of the paddles
     * @return the color value of the paddles from settings
     */
    public static Color getPaddleColor()
    {
        return getColor(settings[PADDLE_COLOR_INDEX]);
    }
    
    /**
     * gets the color value of the ball
     * @return the color value of the ball from settings
     */
    public static Color getBallColor()
    {
        return getColor(settings[BALL_COLOR_INDEX]);
    }
    
    /**
     * gets the color value of the background
     * @return the color value of the background from settings
     */
    public static Color getGameBackgroundColor()
    {
        return getColor(settings[BACKGROUND_COLOR_INDEX]);
    }
    
    /**
     * gets player1 name from the settings as a string
     * @return the string value of player1 name from settings
     */
    public static String getPlayer1Name()
    {
        return settings[PLAYER1_NAME_INDEX];
    }
    
    /**
     * gets player2 name from the settings as a string
     * @return the string value of player2 name from settings
     */
    public static String getPlayer2Name()
    {
        return settings[PLAYER2_NAME_INDEX];
    }
    
    /**
     * gets the integer value of the x speed of the ball
     * @return the integer value of the x speed of the ball from settings
     */
    public static Integer getDeltaX()
    {
        return Integer.parseInt(settings[DELTAX_INDEX]);
    }
    
    /**
     * gets the integer value of the y speed of the ball
     * @return the integer value of the y speed of the ball from settings
     */
    public static Integer getDeltaY()
    {
        return Integer.parseInt(settings[DELTAY_INDEX]);
    }
    
    /**
     * gets the integer value of the needed points to win
     * @return the integer value of the needed points to win from the settings
     */
    public static Integer getPointsToWin()
    {
        return Integer.parseInt(settings[POINTS_TO_WIN_INDEX]);
    }
    
    /**
     * gets the color value based on the name of a color
     * @param strColor the string name of the color that needs to be returned
     * @return color the color value of the string given
     */
    public static Color getColor(String strColor)
    {
        Color color = null;
        
        switch (strColor)
        {
            case "black":
                color = Color.black;
                break;
                
            case "red":
                color = Color.red;
                break;
            
            case "green":
                color = Color.green;
                break;
            
            case "blue":
                color = Color.blue;
                break;
                
            default:
                color = Color.white;
                break;
        }
        
        return color;
    }
    
    /**
     * clears the game panel of all other labels and creates a
     * label with the winners name
     * @param name the name of the winner
     */
    public static void createWinnerLabel(String name)
    {
        gamePanel.removeAll();
        gamePanel.revalidate();
        gamePanel.repaint();
        
        JLabel labelWinner = new JLabel("WINNER: " + name);
        labelWinner.setFont(scoreFont);
        labelWinner.setForeground(Color.white);
        labelWinner.setAlignmentY(Component.CENTER_ALIGNMENT);
        gamePanel.add(labelWinner);
    }
}