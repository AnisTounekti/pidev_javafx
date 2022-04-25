package org.esprit.services;

import org.esprit.entities.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.esprit.Utils.*;

public class ReservationService implements IService<Reservation> {

    @Override
    public void Ajouter(Reservation reservation) {

        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(INSERT_RESERVATION,
                     Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, reservation.getTrip());
            pstmt.setString(2, reservation.getUser());
            pstmt.setDate(3, Date.valueOf(reservation.getDate().toLocalDate()));
            pstmt.setString(4, reservation.getStatus());
            int affectedRows = pstmt.executeUpdate();
            // Extract data from result set
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<Reservation> Afficher() {
        List<Reservation> reservations = new ArrayList();
        // Open a connection
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_RESERVATION);) {
            // Extract data from result set
            while (rs.next()) {
                // Retrieve by column name
                reservations.add(new Reservation(rs.getInt("id"),
                        rs.getString("trip"),
                        rs.getString("user"),
                        rs.getDate("date").toLocalDate().atStartOfDay(),
                        rs.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public void Modifier(Reservation reservation) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void Supprimer(int id) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(DELETE_RESERVATION)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            // Extract data from result set
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
