package gameClient;

/**
 * Little interface for the Login screen - will transfer the information from the text field to the main function
 */
public interface ButtonListener {

    /**
     * when user click on login in the login screen - the info from the test field will transfer to the main function
     * and the game will begin with the choosen scenario
     *
     */
    public void update();
}
