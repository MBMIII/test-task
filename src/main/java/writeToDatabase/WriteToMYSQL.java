package writeToDatabase;

import abstarction.WriteToDatabase;
import exception.IncorrectExpressionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WriteToMYSQL extends WriteToDatabase {
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private String url;
    private String username;
    private String password;
    private String selectAll = "select * from expressions order by id";
    private String selectByValue = "select * from expressions where result ";
    private String selectById = "select expression, result from expressions where id = ? order by id";
    private String insertInto = "insert into expressions (expression, result) values (?, ?)";
    private String update = "update expressions set expression = ?, result = ? WHERE id = ?";

    public WriteToMYSQL(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public void write(String expression) throws IncorrectExpressionException {
        double result = evaluate(expression);
        try {
            connection = DriverManager.getConnection(url, username, password);
            preparedStatement = connection.prepareStatement(insertInto);
            preparedStatement.setString(1, expression.replaceAll("\\s+", ""));
            preparedStatement.setDouble(2, result);
            preparedStatement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void updateExpressionByID(int id, String newExpression) throws IncorrectExpressionException {
        double result = evaluate(newExpression);
        try {
            connection = DriverManager.getConnection(url, username, password);
            preparedStatement = connection.prepareStatement(update);
            preparedStatement.setString(1, newExpression.replaceAll("\\s+", ""));
            preparedStatement.setDouble(2, result);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<List<String>> getAll() {
        List<String> expression;
        List<List<String>> expressions = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            try (ResultSet rs = statement.executeQuery(selectAll)) {
                while (rs.next()) {
                    expression = new ArrayList<>();
                    expression.add(rs.getString(2));
                    expression.add(rs.getString(3));
                    expressions.add(expression);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return expressions;
    }

    @Override
    public String getExpressionByID(int id) {
        String result = "";
        try {
            connection = DriverManager.getConnection(url, username, password);
            preparedStatement = connection.prepareStatement(selectById);
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    result = rs.getString(1) + " = " + rs.getString(2);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    @Override
    public List<String> getExpressionByValue(String mathSymbol, double value) {
        List<String> expressions = new ArrayList<>();
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            try (ResultSet rs = statement.executeQuery(selectByValue + mathSymbol + value)) {
                while (rs.next()) {
                    expressions.add(rs.getString("expression"));
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return expressions;
    }

    @Override
    public void clearTable(String tableName) {
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
            statement.execute("truncate " + tableName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
