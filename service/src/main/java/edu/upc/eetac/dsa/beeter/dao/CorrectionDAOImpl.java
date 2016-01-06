package edu.upc.eetac.dsa.beeter.dao;

import edu.upc.eetac.dsa.beeter.db.Database;
import edu.upc.eetac.dsa.beeter.entity.Correction;
import edu.upc.eetac.dsa.beeter.entity.CorrectionCollection;

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

public class CorrectionDAOImpl implements CorrectionDAO
{
    @Context
    private Application app;
    @Override
    public Correction createCorrection(String userid, String examid, String text, InputStream image) throws SQLException
    {
        UUID uuid = writeAndConvertImage(image);
        Connection connection = null;
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

            stmt = connection.prepareStatement(CorrectionDAOQuery.CREATE_CORRECTION);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, examid);
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

        return getCorrectionById(id);
    }

    @Override
    public Correction getCorrectionById(String id) throws SQLException
    {
        Correction correction = null;

        Connection        connection = null;
        PreparedStatement stmt       = null;
        try {
            connection = Database.getConnection();
            PropertyResourceBundle prb = (PropertyResourceBundle) ResourceBundle.getBundle("beeter");

            stmt = connection.prepareStatement(CorrectionDAOQuery.GET_CORRECTION_BY_ID);
            stmt.setString(1, id);


            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                correction = new Correction();
                correction.setId(rs.getString("id"));
                correction.setUser_id(rs.getString("user_id"));
                correction.setExam_id(rs.getString("exam_id"));
                correction.setText(rs.getString("text"));
                correction.setRating(rs.getString("rating"));
                correction.setCreator(rs.getString("name_creator"));
                correction.setImage_correction(prb.getString("image_corrections_base_url") + rs.getString("image_correction") + ".png");
                correction.setCreated_at(rs.getTimestamp("created_at").getTime());
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

        return correction;
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
                    new File(prb.getString("upload_folder_correction") + filename));
        } catch (IOException e) {
            throw new InternalServerErrorException(
                    "Something has been wrong when converting the file.");
        }

        return uuid;
    }

    @Override
    public CorrectionCollection getCorrections(String exam_id,long timestamp, boolean before) throws SQLException {
        CorrectionCollection correctionCollection = new CorrectionCollection();

        Connection connection = null;
        PropertyResourceBundle prb = (PropertyResourceBundle) ResourceBundle.getBundle("beeter");
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            if (before)
                stmt = connection.prepareStatement(CorrectionDAOQuery.GET_CORRECTIONS);
            else
                stmt = connection.prepareStatement(CorrectionDAOQuery.GET_CORRECTIONS_AFTER);
            stmt.setTimestamp(1, new Timestamp(timestamp));
            stmt.setString(2, exam_id);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Correction correction = new Correction();
                correction.setId(rs.getString("id"));
                correction.setUser_id(rs.getString("user_id"));
                correction.setExam_id(rs.getString("exam_id"));
                correction.setText(rs.getString("text"));
                correction.setRating(rs.getString("rating"));
                correction.setCreator(rs.getString("name_creator"));
                correction.setImage_correction(prb.getString("image_corrections_base_url") + rs.getString("image_correction") + ".png");
                correction.setCreated_at(rs.getTimestamp("created_at").getTime());
                if (first) {
                    correctionCollection.setNewestTimestamp(correction.getCreated_at());
                    first = false;
                }
                correctionCollection.setOldestTimestamp(correction.getCreated_at());
                correctionCollection.getCorrections().add(correction);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return correctionCollection;
    }

    @Override
    public boolean deleteCorrection(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(CorrectionDAOQuery.DELETE_CORRECTION);
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
