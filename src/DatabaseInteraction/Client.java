package DatabaseInteraction;

import java.sql.*;
import java.util.Scanner;
import java.lang.Thread;

public class Client extends Thread {

    private final Connection connection;
    private Scanner scanner;

    public Client() throws SQLException {
        String connectionUrl =
                "jdbc:sqlserver://mysqlchatbot.database.windows.net:1433;"
                        + "database=ChatDatabase;"
                        + "user=apm@mysqlchatbot;"
                        + "password=Mysqlchat123;"
                        + "encrypt=true;"
                        + "trustServerCertificate=false;"
                        + "loginTimeout=30;";
        connection = DriverManager.getConnection(connectionUrl);
        scanner = new Scanner(System.in);
    }

    public void sendMessage(String message) throws SQLException {
        String selectSQL = "SELECT TOP 1 number FROM dbo.Messages ORDER BY number DESC";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(selectSQL);
        resultSet.next();
        long new_number = resultSet.getLong(1) + 1;
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO dbo.Messages VALUES('" + message + "', " + new_number + ")");
        preparedStatement.executeUpdate();
    }
}