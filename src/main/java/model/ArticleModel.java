package model;

import entity.Article;
import util.ConnectHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ArticleModel {
    public boolean insert(Article article) {
        try {
            Connection cnn = ConnectHelper.getConnection();
            if (cnn == null) {
                System.err.println("Can't open connection to database.");
                return false;
            }
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("INSERT INTO articles");
            sqlBuilder.append("(url, tittle, description, content, thumbnail, createdAt, updatedAt, status)");
            sqlBuilder.append(" ");
            sqlBuilder.append("VALUES");
            sqlBuilder.append(" ");
            sqlBuilder.append("(?, ?, ?, ?, ?, ?, ?, ?)");
            PreparedStatement preparedStatement = cnn.prepareStatement(sqlBuilder.toString());
            preparedStatement.setString(1, article.getUrl());
            preparedStatement.setString(2, article.getTitle());
            preparedStatement.setString(3, article.getDescription());
            preparedStatement.setString(4, article.getContent());
            preparedStatement.setString(5, article.getThumbnail());
            preparedStatement.setString(6, article.getCreatedAtString());
            preparedStatement.setString(7, article.getUpdatedAtString());
            preparedStatement.setInt(8, article.getStatus());
            preparedStatement.execute();
            return true;
        } catch (SQLException sqlException) {
            System.err.println(sqlException.getMessage());
        }
        return false;
    }
}