package edu.upc.eetac.dsa.beeter.dao;

import edu.upc.eetac.dsa.beeter.db.Database;
import edu.upc.eetac.dsa.beeter.entity.Exam;
import edu.upc.eetac.dsa.beeter.entity.ExamCollection;

import javax.imageio.ImageIO;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
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
            PropertyResourceBundle prb = (PropertyResourceBundle) ResourceBundle.getBundle("beeter");

            stmt = connection.prepareStatement(ExamDAOQuery.GET_EXAM_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                exam = new Exam();
                exam.setId(rs.getString("id"));
                exam.setUserid(rs.getString("user_id"));
                exam.setSubject(rs.getString("subject"));
                exam.setText(rs.getString("text"));
                exam.setImage(prb.getString("image_base_url")+ rs.getString("image")+".png");
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
            PropertyResourceBundle prb = (PropertyResourceBundle) ResourceBundle.getBundle("beeter");

            ImageIO.write(
                    image,
                    "png",
                    new File(prb.getString("upload_folder") + filename));
        } catch (IOException e) {
            throw new InternalServerErrorException(
                    "Something has been wrong when converting the file.");
        }

        return uuid;
    }

    @Override
    public ExamCollection getExams(long timestamp, boolean before) throws SQLException {
        ExamCollection examCollection = new ExamCollection();

        Connection connection = null;
        PropertyResourceBundle prb = (PropertyResourceBundle) ResourceBundle.getBundle("beeter");
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            if (before)
                stmt = connection.prepareStatement(ExamDAOQuery.GET_EXAMS);
            else
                stmt = connection.prepareStatement(ExamDAOQuery.GET_EXAMS_AFTER);
            stmt.setTimestamp(1, new Timestamp(timestamp));

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Exam exam = new Exam();
                exam.setId(rs.getString("id"));
                exam.setUserid(rs.getString("user_id"));
                exam.setSubject(rs.getString("subject"));
                exam.setText(rs.getString("text"));
                exam.setImage(prb.getString("image_base_url") + rs.getString("image")+".png");
                exam.setCreated_at(rs.getTimestamp("created_at").getTime());
                if (first) {
                    examCollection.setNewestTimestamp(exam.getCreated_at());
                    first = false;
                }
                examCollection.setOldestTimestamp(exam.getCreated_at());
                examCollection.getExams().add(exam);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return examCollection;
    }

    @Override
    public boolean deleteExam(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(ExamDAOQuery.DELETE_EXAM);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

}
