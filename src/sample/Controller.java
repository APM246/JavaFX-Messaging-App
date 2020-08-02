package sample;

import DatabaseInteraction.Client;
import DatabaseInteraction.SQLDatabaseConnection;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.sql.SQLException;

public class Controller {

    public TextField textbox;
    public TextArea chat;
    public Client client;
    public SQLDatabaseConnection database;

    public Controller()
    {
        try
        {
            client = new Client();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void setup()
    {
        chat.setWrapText(true);
    }

    public void sendMessage() throws Exception
    {
        client.sendMessage(textbox.getText());
    }

    public void displayMessages() throws Exception
    {
        database = new SQLDatabaseConnection(chat);
        database.start();
    }
}
