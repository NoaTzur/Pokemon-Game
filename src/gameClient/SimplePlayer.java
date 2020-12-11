package gameClient;

import javazoom.jl.player.*;
import java.io.FileInputStream;

/**
 * Simple player i took from my tutor (yael), it will play the pokemon theme in english, while the game is running
 */

public class SimplePlayer implements Runnable
{
    private String path;

    public SimplePlayer(String path)
    {
        this.path = path;
    }

    public void play()
    {
        try
        {
            FileInputStream fis = new FileInputStream(path);
            Player playMP3 = new Player(fis);
            playMP3.play();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }

    @Override
    public void run()
    {
        play();
    }



}