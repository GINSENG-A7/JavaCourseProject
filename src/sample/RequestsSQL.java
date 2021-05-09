package sample;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestsSQL {

    public static ResultSet SelectAllFromHuman(Connection conn) throws SQLException {
        String query = "SELECT * FROM institut.HUMANS";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
/*

    public static void AddEntryInHuman(Connection conn, Human hmn) throws SQLException {
        String query = "INSERT INTO institut.HUMANS (NAME, SURNAME, PATRONYMIC, GENDER, HEIGHT, WEIGHT, AGE, TELEPHONE) VALUES (\'" + hmn.getName() + "\', \'" + hmn.getSurname() + "\', \'" + hmn.getPatronymic() + "\', \'" + hmn.getGender() + "\', " + hmn.getHeight() + ", " + hmn.getWeight() + ", " + hmn.getAge() + ", \'" + hmn.getTelephone() + "\')";
        conn.createStatement().executeUpdate(query);
    }

    public static void EditEntryInHumanWithId(Connection conn, Human hmn) throws SQLException {
        String query = "UPDATE institut.HUMANS SET NAME = \'" + hmn.getName() + "\', SURNAME = \'" + hmn.getSurname() + "\', PATRONYMIC = \'" + hmn.getPatronymic() + "\', GENDER = \'" + hmn.getGender() + "\', HEIGHT = " + hmn.getHeight() + ", WEIGHT = " + hmn.getWeight() + ", AGE = " + hmn.getAge() + ", TELEPHONE = \'" + hmn.getTelephone() + "\' WHERE ID = " + hmn.getId();
        conn.createStatement().executeUpdate(query);
    }
*/

    public static void DeleteEntryInHumanWithId(Connection conn, int id) throws SQLException {
        String query = "DELETE FROM institut.HUMANS WHERE ID = " + id;
        conn.createStatement().executeUpdate(query);
    }

    public static ResultSet SelectEldestFromHuman(Connection conn) throws SQLException {
        String query = "SELECT * FROM institut.humans WHERE AGE = (SELECT MAX(AGE) FROM institut.humans)";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }
}
