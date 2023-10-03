package org.example.dao;

import org.example.model.Response;

import java.sql.*;

public class ResponseDAO {

    private Connection connection;

    public ResponseDAO(Connection connection) {
        this.connection = connection;
    }

    public void saveResponse(Response response) {
        String sql = "INSERT INTO responses (question_id, text, is_correct) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, response.getQuestionId());
            preparedStatement.setString(2, response.getText());
            preparedStatement.setBoolean(3, response.isCorrect());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Response saving failed, no lines were changed.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    response.setResponseId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Response creation failed, cannot get the identification");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateResponse(Response response) {
        String sql = "UPDATE responses SET question_id = ?, text = ?, is_correct = ? WHERE response_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, response.getQuestionId());
            preparedStatement.setString(2, response.getText());
            preparedStatement.setBoolean(3, response.isCorrect());
            preparedStatement.setInt(4, response.getResponseId());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Response update failed, no lines were changed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteResponse(int responseId) {
        String sql = "DELETE FROM responses WHERE response_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, responseId);

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Response deleting failed, no lines were changed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Response getResponseById(int responseId) {
        String sql = "SELECT * FROM responses WHERE response_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, responseId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Response response = new Response();
                    response.setResponseId(resultSet.getInt("response_id"));
                    response.setQuestionId(resultSet.getInt("question_id"));
                    response.setText(resultSet.getString("text"));
                    response.setCorrect(resultSet.getBoolean("is_correct"));
                    return response;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
