package DatabaseInteraction;

import java.sql.*;

public class Client {

    private final Connection connection;

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
    }

    public void sendMessage(String message) throws Exception {
        long new_number = 0;

        try
        {
            String selectSQL = "SELECT TOP 1 number FROM dbo.Messages ORDER BY number DESC";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(selectSQL);
            resultSet.next();
            new_number = resultSet.getLong(1) + 1;
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO dbo.Messages VALUES('" + message + "', " + new_number + ")");
            preparedStatement.executeUpdate();
        }

        catch (Exception e)
        {
            new_number += 1;
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO dbo.Messages VALUES('" + message + "', " + new_number + ")");
            preparedStatement.executeUpdate();
        }
    }
}