package sample;

import DatabaseInteraction.Client;
import DatabaseInteraction.SQLDatabaseConnection;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

import java.sql.SQLException;

public class Controller {

    @FXML
    private TextField textbox;
    @FXML
    private TextArea chat;
    @FXML
    private TextField username;
    @FXML
    private BorderPane borderPane;
    @FXML
    private Button button;
    private Client client;
    private SQLDatabaseConnection database;

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
        button.setStyle("-fx-background-color:dodgerblue");
        borderPane.setStyle("-fx-border-color:grey");
    }

    public void setUsername()
    {
        String name = username.getText();

        if (!name.equals("") && !name.equals(" "))
        {
            System.out.println(name);
            client.setUserName(name);
        }
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
