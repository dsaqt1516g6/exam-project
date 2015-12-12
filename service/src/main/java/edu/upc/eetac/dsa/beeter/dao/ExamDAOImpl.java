package edu.upc.eetac.dsa.beeter.dao;

import edu.upc.eetac.dsa.beeter.db.Database;
import edu.upc.eetac.dsa.beeter.entity.Exam;

import javax.imageio.ImageIO;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ExamDAOImpl implements ExamDAO
{
    @Context
    private Application app;
    @Override
    public Exam createExam(String userid, String subject, String text, InputStream image) throws SQLException
    {
        UUID uuid = writeAndConvertImage(image);
        Connection        connection = null;
        PreparedStatement stmt       = null;
        String            id         = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getString(1);
            } else {
                throw new SQLException();
            }

            stmt = connection.prepareStatement(ExamDAOQuery.CREATE_EXAM);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, subject);
            stmt.setString(4, text);
            stmt.setString(5, uuid.toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }

        return getExamById(id);
    }

    @Override
    public Exam getExamById(String id) throws SQLException
    {
        Exam exam = null;

        Connection        connection = null;
        PreparedStatement stmt       = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ExamDAOQuery.GET_EXAM_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                exam = new Exam();
                exam.setId(rs.getString("id"));
                exam.setUserid(rs.getString("user_id"));
                exam.setSubject(rs.getString("subject"));
                exam.setText(rs.getString("text"));
                exam.setimage(rs.getString("image"));
                exam.setCreated_at(rs.getTimestamp("created_at").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return exam;
    }
    private UUID writeAndConvertImage(InputStream file) {

        BufferedImage image = null;
        try {
            image = ImageIO.read(file);

        } catch (IOException e) {
            throw new InternalServerErrorException(
                    "Something has been wrong when reading the file.");
        }
        UUID uuid = UUID.randomUUID();
        String filename = uuid.toString() + ".png";
        try {
            ImageIO.write(
                    image,
                    "png",
                    new File(app.getProperties().get("uploadFolder") + filename));
        } catch (IOException e) {
            throw new InternalServerErrorException(
                    "Something has been wrong when converting the file.");
        }

        return uuid;
    }
}
