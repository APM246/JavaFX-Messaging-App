package sample;

import DatabaseInteraction.Client;
import DatabaseInteraction.SQLDatabaseConnection;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
        String message = textbox.getText();
        textbox.clear();
        textbox.requestFocus();
        client.sendMessage(message);
    }

    public void enterPressed() throws Exception
    {
        textbox.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            @Override
            public void handle(KeyEvent ke)
            {
                if (ke.getCode().equals(KeyCode.ENTER))
                {
                    try {
                        sendMessage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void displayMessages() throws Exception
    {
        database = new SQLDatabaseConnection(chat);
        database.start();
    }
}
