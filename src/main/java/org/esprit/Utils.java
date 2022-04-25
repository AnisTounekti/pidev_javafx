/**
 * Copyright (c) ${YEAR} , All rights reserved.
 * <p>
 * 9fbef606107a605d69c0edbcd8029e5d
 */
package org.esprit;

public final class Utils {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/guide";
    public static final String USER = "root";
    public static final String PASS = "";
    public static final String SELECT_GUIDE = "SELECT id, name, age FROM guide";
    public static final String INSERT_GUIDE = "INSERT INTO guide(name,age) VALUES(?,?)";
    public static final String UPDATE_GUIDE = "UPDATE guide set name=? , age=? where id=?";
    public static final String DELETE_GUIDE = "DELETE FROM guide where id = ?";

    public static final String SELECT_TRIP = "SELECT id, name, date, nb_sejour,prix,guide_id FROM Trip";
    public static final String SELECT_TRIP_BY_GUIDE = "SELECT id, name, date, nb_sejour,prix,guide_id FROM Trip where guide_id = ?";

    public static final String INSERT_TRIP = "INSERT INTO trip(name,date,nb_sejour,prix,guide_id) VALUES(?,?,?,?,?)";
    public static final String DELETE_TRIP = "DELETE FROM trip where id = ?";
    public static final String UPDATE_TRIP = "UPDATE trip set name=?, nb_sejour=?, prix=? where id=?";

    public static final String SELECT_RESERVATION = "SELECT * FROM reservation";
    public static final String INSERT_RESERVATION = "INSERT INTO reservation(name,date,nbsejour,prix,guide) VALUES(?,?,?,?,?)";
    public static final String DELETE_RESERVATION = "DELETE FROM reservation where id = ?";
    public static final String UPDATE_RESERVATION = "UPDATE reservation set name=?, status=? where id=?";
}
