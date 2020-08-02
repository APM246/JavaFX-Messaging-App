package DatabaseInteraction;

import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.lang.Thread;

public class SQLDatabaseConnection extends Thread {
    private final Connection connection;
    private long latest_time;
    private TextArea textArea;

    public SQLDatabaseConnection(TextArea textArea) throws Exception {
            String connectionUrl =
                "jdbc:sqlserver://mysqlchatbot.database.windows.net:1433;"
                        + "database=ChatDatabase;"
                        + "user=apm@mysqlchatbot;"
                        + "password=Mysqlchat123;"
                        + "encrypt=true;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
            connection = DriverManager.getConnection(connectionUrl);
            latest_time = System.currentTimeMillis();
            this.textArea = textArea;
    }

    public void displayMessage() {
        ResultSet resultSet = null;
        String selectSql = "SELECT * FROM dbo.Messages WHERE time > " + latest_time;
        try {
            Statement statement = connection.createStatement(); {
                resultSet = statement.executeQuery(selectSql);
                while (resultSet.next()) {
                    textArea.appendText(resultSet.getString(1) + "\n");
                    latest_time = resultSet.getLong(2);
                }
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) displayMessage();
    }

    public static void main(String[] args) throws Exception {
        SQLDatabaseConnection database = new SQLDatabaseConnection(null);
        database.start();
        Client client = new Client();
        client.start();
    }
}