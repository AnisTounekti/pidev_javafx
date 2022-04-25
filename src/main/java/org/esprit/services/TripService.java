package org.esprit.services;

import org.esprit.entities.Guide;
import org.esprit.entities.Trip;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.esprit.Utils.*;

public class TripService implements IService<Trip> {

    @Override
    public void Ajouter(Trip trip) {
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(INSERT_TRIP,
                     Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, trip.getName());
            pstmt.setDate(2, trip.getDate() != null ? Date.valueOf(trip.getDate()) : new Date(2121212));
            pstmt.setInt(3, trip.getNbSejour());
            pstmt.setFloat(4, trip.getPrix());
            pstmt.setInt(5, trip.getGuide());
            int affectedRows = pstmt.executeUpdate();
            // Extract data from result set
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Trip> Afficher() {

        List<Trip> trips = new ArrayList();
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_TRIP)) {
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                trips.add(new Trip(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        rs.getInt("nb_sejour"),
                        rs.getFloat("prix"),
                        rs.getInt("guide_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trips;
    }

    @Override
    public void Modifier(Trip trip) {

        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(UPDATE_TRIP)) {
            pstmt.setString(1, trip.getName());
            pstmt.setInt(2, trip.getNbSejour());
            pstmt.setFloat(3, trip.getPrix());
            pstmt.setInt(4, trip.getId());
            int affectedRows = pstmt.executeUpdate();
            // Extract data from result set
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void Supprimer(int id) {

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(DELETE_TRIP)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            // Extract data from result set
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<Trip> AfficherByGuide(Guide guideSelected) {
        List<Trip> trips = new ArrayList();
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement stmt = conn.prepareStatement(SELECT_TRIP_BY_GUIDE)) {
            stmt.setInt(1,guideSelected.getId());
            ResultSet rs = stmt.executeQuery();
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                trips.add(new Trip(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDate("date").toLocalDate(),
                        rs.getInt("nb_sejour"),
                        rs.getFloat("prix"),
                        rs.getInt("guide_id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trips;
    }
}
