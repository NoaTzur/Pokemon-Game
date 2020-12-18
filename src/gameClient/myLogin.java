package gameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * This class build the Login screen, user will have to register his ID and the scenario he want to play with.
 * after this - the game field will display on the screen, and the game will automatically begin.
 */

public class myLogin extends JFrame implements ActionListener {

    private static JLabel username;
    private static JLabel scenario;
    private static JTextField usernameT;
    private static JTextField scenarioT;
    private static JButton login;
    private static JLabel background = new JLabel();
    private Ex2 ex2;

    /**
     * this function will connect between the login screen to the main game
     * @param ex2 - the main game
     */
    public void register(Ex2 ex2){
        this.ex2 = ex2;
    }

    /**
     * function for calling in the main game to display the login screen
     */
    public static void action(){
        main(null);
    }

    public static void main(String[] args) {

        JPanel myPanel = new JPanel();
        JFrame myFrame = new JFrame("Pokemon GAME login");

        //set up screen size
        myFrame.setSize(750,400);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myPanel.setSize(750,400);

        myPanel.setLayout(null);

        //user ID label
        username = new JLabel("user-ID: ");
        username.setBounds(150, 100,300,50);
        Font usernameFont = new Font(Font.SERIF, Font.BOLD, 30);
        username.setFont(usernameFont);
        myPanel.add(username);

        //scenario label
        scenario = new JLabel("Scenario [0,23]: ");
        scenario.setBounds(150, 170,300,50);
        Font scenarioFont = new Font(Font.SERIF, Font.BOLD, 30);
        scenario.setFont(scenarioFont);
        myPanel.add(scenario);

        //text field - username
        usernameT = new JTextField(20);
        usernameT.setBounds(350, 115,200,30);
        myPanel.add(usernameT);

        //text field - scenario
        scenarioT = new JTextField(20);
        scenarioT.setBounds(350, 185,200,30);
        myPanel.add(scenarioT);

        //login button
        login = new JButton("Login");
        login.setBounds(250,270,200,30);
        login.setBackground(new Color(131, 246, 160));
        login.setForeground(Color.BLACK);
        login.setFocusPainted(false);
        login.setFont(new Font("Tahoma", Font.BOLD, 20));
        login.addActionListener(new myLogin());
        myPanel.add(login);

        //add panel and background
        JLabel background=new JLabel(new ImageIcon("data\\loginScreen.jpg"));
        background.setBounds(0,0, 750,400);
        myPanel.add(background);

        //background.setLayout(new FlowLayout());
        myFrame.add(myPanel);

        myFrame.setVisible(true); //in the end

    }

    @Override
    //when user click on "login" button, text field info sent to the Ex2 main function
    public void actionPerformed(ActionEvent e) {
        String userid = usernameT.getText();
        int id = Integer.parseInt(userid);
        String scenarioTemp = scenarioT.getText();
        int scenario = Integer.parseInt(scenarioTemp);
        //if(scenario>-1 && scenario <24){
            Ex2.setScenario(scenario);
            Ex2.setId(id);
        //}

    }
}
