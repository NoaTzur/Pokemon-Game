package gameClient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class myLogin extends JFrame implements ActionListener {

    private static JLabel username;
    private static JLabel scenario;
    private static JTextField usernameT;
    private static JTextField scenarioT;
    private static JButton login;
    private static JLabel background = new JLabel();

    public static void main(String[] args) {

        JPanel myPanel = new JPanel();
        JFrame myFrame = new JFrame("Pokemon GAME login");
        //screen size
        myFrame.setSize(750,400);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //add panel and background
        myPanel.setLayout(new BorderLayout());
        myFrame.add(myPanel);

        JLabel background=new JLabel(new ImageIcon("resources\\loginScreen.jpg"));
        myFrame.add(background);
        background.setLayout(new FlowLayout());



        //username label
        username = new JLabel("User-Name: ");
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



        myFrame.setVisible(true); //in the end

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = usernameT.getText();
        String scenarioNum = scenarioT.getText();
        int scenario = Integer.parseInt(scenarioNum);
        System.out.println(scenario);

        /*
        if(scenario >=0 && scenario <=23)
            pass is to the MyFrame and start the game
         */
    }
}
