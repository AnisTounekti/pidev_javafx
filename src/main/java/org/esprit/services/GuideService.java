package org.esprit.services;

import org.esprit.entities.Guide;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.esprit.Utils.*;

public class GuideService implements IService<Guide> {

    @Override
    public void Ajouter(Guide guide) {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(INSERT_GUIDE,
                     Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, guide.getName());
            pstmt.setInt(2, guide.getAge());
            int affectedRows = pstmt.executeUpdate();
            // Extract data from result set
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Guide> Afficher() {
        List<Guide> guides = new ArrayList();
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_GUIDE);) {
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                guides.add(new Guide(rs.getInt("id"), rs.getString("name"), rs.getInt("age")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guides;
    }

    @Override
    public void Modifier(Guide guide) {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_GUIDE)) {
            pstmt.setString(1, guide.getName());
            pstmt.setInt(2, guide.getAge());
            pstmt.setInt(3, guide.getId());
            int affectedRows = pstmt.executeUpdate();
            // Extract data from result set
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void Supprimer(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(DELETE_GUIDE)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            // Extract data from result set
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Guide> getSearchGuidesByName(String name) {
        if (name == null || "".equals(name)) {
            return Afficher();
        }
        List<Guide> guides = new ArrayList();
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(SELECT_GUIDE_LIKE_NAME)) {
            pstmt.setString(1, name+"%");
            ResultSet rs = pstmt.executeQuery();
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                guides.add(new Guide(rs.getInt("id"), rs.getString("name"),
                        rs.getInt("age")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return guides;
    }
}
