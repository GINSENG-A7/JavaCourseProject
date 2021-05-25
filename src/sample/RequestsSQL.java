package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Models.PseudoApartmentTypes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class RequestsSQL {
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

    public static ResultSet ApplyTargetFilters(Connection conn, String type, Date b1, Date b2, int bp, int tp) throws SQLException //Зарефакторить SQL запросы в этом методе
    {
        String query = "";
        if (type != null)
        {
            //command = new SqlCommand($"SELECT a.number, a.type, a.price, l.settling, l.eviction FROM Apartments a, Living l, Booking b WHERE (a.type={type} AND b.settling>'{b2}' AND l.eviction<'{b1}' AND a.price>{bp} AND a.price<{tp})", connection);
            query = "SELECT a.apartment_id, a.number, a.`type`, a.price FROM courseprojectschema.Apartment a WHERE a.`type` = \'" + type + "\' AND a.price > " + bp + " AND a.apartment_id < " + tp + " AND ((a.apartment_id IN (SELECT number FROM courseprojectschema.Living WHERE eviction < \'" + b1 + "\') AND NOT EXISTS(SELECT apartment_id FROM courseprojectschema.Booking WHERE a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Booking))) OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE settling > \'" + b2 + "\') OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE eviction < \'" + b1 + "\'))) AND NOT EXISTS(SELECT apartment_id FROM courseprojectschema.Living WHERE a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Living)) OR ((a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Living WHERE eviction<\'" + b1 + "\')) AND (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE settling>\'" + b2 + "\') OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE eviction<\'" + b1 + "\')))) OR (a.apartment_id NOT IN (SELECT apartment_id FROM courseprojectschema.Living) AND a.apartment_id NOT IN (SELECT apartment_id FROM courseprojectschema.Booking)))";
        }
        else
        {
            //command = new SqlCommand($"SELECT a.number, a.type, a.price, l.settling, l.eviction FROM Apartments a, Living l, Booking b WHERE (b.settling>'{b2}' AND l.eviction<'{b1}' AND a.price>{bp} AND a.price<{tp})", connection);
            query = "SELECT a.apartment_id, a.number, a.`type`, a.price FROM courseprojectschema.Apartment a WHERE a.price > " + bp + " AND a.price < " + tp + " AND ((a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Living WHERE eviction < \'" + b1 + "\') AND NOT EXISTS(SELECT number FROM courseprojectschema.Booking WHERE a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Booking))) OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE settling > \'" + b2 + "\') OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE eviction < \'" + b1 + "\'))) AND NOT EXISTS(SELECT apartment_id FROM courseprojectschema.Living WHERE a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Living)) OR ((a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Living WHERE eviction<\'" + b1 + "\')) AND (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE settling>\'" + b2 + "\') OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE eviction<\'" + b1 + "\')))) OR (a.apartment_id NOT IN (SELECT apartment_id FROM courseprojectschema.Living) AND a.apartment_id NOT IN (SELECT apartment_id FROM courseprojectschema.Booking)))";
        }
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static Boolean IsNumberFreeForSetDate(Connection conn, Date b1, Date b2, int numberId) throws SQLException {
        String query = "SELECT a.apartment_id FROM courseprojectschema.Apartment a WHERE ((a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Living WHERE eviction < \'" + b1 + "\') AND NOT EXISTS(SELECT number FROM courseprojectschema.Booking WHERE a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Booking))) OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE settling > \'" + b2 + "\') OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE eviction < \'" + b1 + "\'))) AND NOT EXISTS(SELECT apartment_id FROM courseprojectschema.Living WHERE a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Living)) OR ((a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Living WHERE eviction<\'" + b1 + "\')) AND (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE settling>\'" + b2 + "\') OR (a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE eviction<\'" + b1 + "\')))) OR (a.apartment_id NOT IN (SELECT apartment_id FROM courseprojectschema.Living) AND a.apartment_id NOT IN (SELECT apartment_id FROM courseprojectschema.Booking)))";
        ResultSet set = conn.createStatement().executeQuery(query);
        while (set.next())
        {
            if(numberId == set.getInt(1)) {
                return true;
            }
        }
        return false;
    }

    public static ObservableList<String> GetTypesOfApartments(Connection conn) throws SQLException // Получаем все реально имеющиеся типы номеров для данной гостинницы для последующего использоваия в ComboBox
    {
        ObservableList<String> currentTypesOfApartments = FXCollections.observableArrayList();
        String query = "SELECT DISTINCT type FROM courseprojectschema.Apartment";
        ResultSet set = conn.createStatement().executeQuery(query);
        while (set.next())
        {
            currentTypesOfApartments.add(set.getString(1));
        }
        return currentTypesOfApartments;
    }

    //Тут получение листа картинок заданного номера. Пока не знаю как его правильно переписать =(
/*    public static List<PictureData> CollectImagesOfNthNumber(SqlConnection connection, int nOA, List<PictureData> pList)
    {
        pList.Clear();
        SqlCommand command = new SqlCommand($"SELECT photos_id, path FROM Photos WHERE number = {nOA}", connection);
        command.CommandType = CommandType.Text;
        SqlDataReader reader = command.ExecuteReader();
        if (reader.HasRows)
        {
            while (reader.Read())
            {
                pList.Add(new PictureData() { id = Convert.ToInt32(reader.GetValue(0)), path = reader.GetValue(1).ToString()});
            }
        }
        reader.Close();
        return pList;
    }*/

    public static boolean SelectSetValueOfNumberFromApartments(Connection conn, int n) throws SQLException {
        String query = "SELECT DISTINCT number FROM courseprojectschema.Apartments WHERE number = " + n;
        ResultSet set = conn.createStatement().executeQuery(query);
        if (set.next() == false)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public static boolean TypeOfApartmentsIsCorrect(Connection conn, String cb)
    {
        PseudoApartmentTypes pAT = PseudoApartmentTypes.getApartmentsTypes();
        for(int i = 0; i < pAT.getTypesArray().length; i++)
        {
            if(pAT.getTypesArray()[i].equals(cb))
            {
                return true;
            }
        }
        return false;
    }

    public static ResultSet SelectAllFromClient(Connection conn) throws SQLException {
        String query = "SELECT client_id, passport_series, passport_number, name, surname, patronymic, birthday, tel_number FROM courseprojectschema.Client";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet SelectAllFromClientWithSetSurname(Connection conn, String surname) throws SQLException {
        String query = "SELECT passport_series, passport_number, name, surname, patronymic, birthday, tel_number FROM courseprojectschema.Client WHERE surname LIKE \'" + surname + "%\'";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static int SelectNthIdFromClientWherePassportDataDefinedToString__ALTERNATIVE__(Connection conn, int ps, int pn) throws SQLException {
        String query = "SELECT DISTINCT client_id FROM courseprojectschema.Client WHERE passport_series = " + ps + " AND passport_number = " + pn;
        ResultSet set = conn.createStatement().executeQuery(query);
        if(set.next() == false)
        {
            return -1;
        }
        else
        {
            return set.getInt(1);
        }
    }

    public static String SelectNthIdFromClientWherePassportDataDefinedToString(Connection conn, int ps, int pn) throws SQLException { //Очень странный метод, не знаю как я его вообще придумал. Не уверен, что смогу его заменить, поэтому выше напишу его альтернативу
        String query = "SELECT DISTINCT client_id FROM courseprojectschema.Client WHERE passport_series = " + ps + " AND passport_number = " + pn;
        ResultSet set = conn.createStatement().executeQuery(query);
        if(set.next() == false)
        {
            return "null";
        }
        else
        {
            return set.getString(1);
        }
    }
    public static ResultSet SelectAllFromClientByClientId(Connection conn, int iOC) throws SQLException {
        String query = "SELECT client_id, passport_series, passport_number, name, surname, patronymic, birthday, tel_number FROM courseprojectschema.Client WHERE client_id = " + iOC;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet SelectAllFromLiving(Connection conn) throws SQLException {
        String query = "SELECT l.living_id, c.client_id, c.name, c.surname, c.patronymic, l.settling, l.eviction, a.number, l.value_of_guests, l.value_of_kids, a.apartment_id, l.as_id FROM courseprojectschema.Client c, Living l, Apartment a WHERE c.client_id = l.client_id AND l.apartment_id = a.apartment_id";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet SelectAllFromLivingByClientId(Connection conn, int iOC) throws SQLException {
        String query = "SELECT l.living_id, c.client_id, c.name, c.surname, c.patronymic, l.settling, l.eviction, a.number, l.value_of_guests, l.value_of_kids, a.apartment_id, l.as_id FROM courseprojectschema.Client c, courseprojectschema.Living l, courseprojectschema.Apartment a WHERE l.client_id = " + iOC + " AND c.client_id = " + iOC + " AND l.apartment_id = a.apartment_id";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet SelectAllFromBooking(Connection conn) throws SQLException {
        String query = "SELECT b.booking_id, b.client_id, c.name, c.surname, c.patronymic, b.settling, b.eviction, a.number, b.value_of_guests, b.value_of_kids, a.apartment_id FROM courseprojectschema.Client c, courseprojectschema.Booking b, courseprojectschema.Apartment a WHERE c.client_id = b.client_id AND b.apartment_id = a.apartment_id";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet SelectRelatedDataFromBookingById(Connection conn, Integer bookingId) throws SQLException {
        String query = "SELECT settling, eviction, value_of_guests, value_of_kids, apartment_id FROM courseprojectschema.Booking WHERE booking_id = " + bookingId;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet SelectAllFromBookingByClientId(Connection conn, int iOC) throws SQLException {
        String query = "SELECT b.booking_id, b.client_id, c.name, c.surname, c.patronymic, b.settling, b.eviction, a.number, b.value_of_guests, b.value_of_kids, a.apartment_id FROM courseprojectschema.Client c, courseprojectschema.Booking b, courseprojectschema.Apartment a WHERE b.client_id = " + iOC + " AND c.client_id = " + iOC + " AND b.apartment_id = a.apartment_id";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet SelectAllFromApartments(Connection conn) throws SQLException {
        String query = "SELECT apartment_id, number, type, price FROM courseprojectschema.Apartment";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet SelectAllFromApartmentsWhereNumberIsSet(Connection conn, int n) throws SQLException {
        String query = "SELECT number, type, price FROM courseprojectschema.Apartments WHERE number = " + n;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet SelectAllFromApartmentsWhereApartmentIdIsSet(Connection conn, int apartmentId) throws SQLException {
        String query = "SELECT apartment_id, number, type, price FROM courseprojectschema.Apartment WHERE apartment_id = " + apartmentId;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static boolean IsNumberOfApartmentsFreeForToday(Connection conn, int nOA) throws SQLException {
        String query = "SELECT a.apartment_id FROM courseprojectschema.Apartment a WHERE (a.apartment_id = " + nOA + ") AND ((a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Living WHERE eviction < \'" + LocalDate.now() + "\') AND NOT EXISTS(SELECT apartment_id FROM courseprojectschema.Booking WHERE a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Booking))) OR ((a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE eviction < \'" + LocalDate.now() + "\'))) AND NOT EXISTS(SELECT apartment_id FROM courseprojectschema.Living WHERE a.apartment_id IN (SELECT apartment_id FROM courseprojectschema.Living)) OR ((a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Living WHERE eviction < \'" + LocalDate.now() + "\')) AND ((a.apartment_id in (SELECT apartment_id FROM courseprojectschema.Booking WHERE eviction < \'" + LocalDate.now() + "\')))) OR (a.apartment_id NOT IN (SELECT apartment_id FROM courseprojectschema.Living) AND a.apartment_id NOT IN (SELECT apartment_id FROM courseprojectschema.Booking)))";
        ResultSet set = conn.createStatement().executeQuery(query);
        if (set.next() == false)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    public static boolean IsNumberOfApartmentsUnique(Connection conn, int nOA) throws SQLException {
        String query = "SELECT DISTINCT number FROM courseprojectschema.Apartments WHERE number = " + nOA;
        ResultSet set = conn.createStatement().executeQuery(query);
        if (set.next() == false)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static int ValueOfClientLivingsAndBookingsForToday(Connection conn, int iOC) throws SQLException {
        LocalDate.now();
        String query1 = "SELECT COUNT(l.living_id) FROM courseprojectschema.Living l WHERE l.eviction > \'" + LocalDate.now() + "\' AND l.client_id IN (SELECT client_id FROM courseprojectschema.client WHERE client_id = " + iOC + ")";
        ResultSet set1 = conn.createStatement().executeQuery(query1);
        String query2 = "SELECT COUNT(b.booking_id) FROM courseprojectschema.Booking b WHERE b.client_id IN (SELECT client_id FROM courseprojectschema.client WHERE client_id = " + iOC + ")";
        ResultSet set2 = conn.createStatement().executeQuery(query2);
        int l = 0;
        int b = 0;
        if(set1.next()) {
            l = set1.getInt(1);
        }
        if(set2.next()) {
            b = set2.getInt(1);
        }
        int v = l + b;
        return v;
    }

    public static int GetCurrentDiscount(Connection conn) throws SQLException {
        String query = "SELECT DISTINCT discount FROM courseprojectschema.Discount";
        ResultSet set = conn.createStatement().executeQuery(query);
        if (set.next() == false)
        {
            return 0;
        }
        else
        {
            return set.getInt(1);
        }
    }

    public static void SetNewDiscount(Connection conn, int disc) throws SQLException {
        String query1 = "SELECT DISTINCT discount FROM courseprojectschema.Discount";
        ResultSet set1 = conn.createStatement().executeQuery(query1);
        if (set1.next() == false)
        {
            String query2 = "INSERT INTO courseprojectschema.Discount (discount) VALUES (" + disc + ")";
            conn.createStatement().executeUpdate(query2);
        }
        else
        {
            String query3 = "UPDATE courseprojectschema.Discount SET discount = " + disc;
            conn.createStatement().executeUpdate(query3);
        }
    }

    public static Integer[] SelectAllFromAdditionalServicesWhereIsSetLivingID(Connection conn, int iOAS) throws SQLException {
        Integer[] v = new Integer[6];
        String query = "SELECT as_id, mini_bar, clothes_washing, telephone, intercity_telephone, food FROM courseprojectschema.Additional_services WHERE as_id = " + iOAS;
        ResultSet set = conn.createStatement().executeQuery(query);
        if (set.next())
        {
            for (int i = 0; i < v.length; i++) {
                v[i] = set.getInt(i + 1);
            }
        }
        else {
            for (int i = 0; i < v.length; i++) {
                v[i] = 0;
            }
        }
        return v;
    }

    public static boolean DoesClientHasNoLivingsAndBookings(Connection conn, int idOfClient) throws SQLException {
        String query1 = "SELECT living_id FROM courseprojectschema.Living WHERE client_id = " + idOfClient;
        ResultSet set1 = conn.createStatement().executeQuery(query1);
        String query2 = "SELECT booking_id FROM courseprojectschema.Booking WHERE client_id = " + idOfClient;
        ResultSet set2 = conn.createStatement().executeQuery(query2);
        if (set1 == null && set2 == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    //Элементарные запросы на удаление и изменение сущностей!!!

    public static void UpdateClientWithValues(Connection conn, int clientId, int passportSeries, int passportNumber, String name, String surname, String patronymic, Date birthday, String telephone) throws SQLException {
        String query = "UPDATE courseprojectschema.Client SET passport_series = " + passportSeries + ", passport_number = " + passportNumber + ", name = \'" + name + "\', surname = \'" + surname + "\', patronymic = \'" + patronymic + "\', birthday = \'" + birthday + "\', tel_number = \'" + telephone + "\' WHERE client_id = " + clientId;
        conn.createStatement().executeUpdate(query);
    }

    public static void DeleteNullableClient(Connection conn, int clientId) throws SQLException {
        String query = "DELETE FROM courseprojectschema.Client WHERE living_id = " + clientId;
        conn.createStatement().executeUpdate(query);
    }

    public static void DeleteAllInfoAboutclient(Connection conn, int clientId) throws SQLException {
        String query1 = "DELETE FROM courseprojectschema.Client WHERE client_id = " + clientId;
        conn.createStatement().executeUpdate(query1);
        String query2 = "DELETE FROM courseprojectschema.Living WHERE client_id = " + clientId;
        conn.createStatement().executeUpdate(query2);
        String query3 = "DELETE FROM courseprojectschema.Booking WHERE client_id = " + clientId;
        conn.createStatement().executeUpdate(query3);
    }

    public static void InsertClientAndLivingAndAdditionalServicesEntry(Connection conn, int passportSeries, int passportNumber, String name, String surname, String patronymic, Date birthday, String telephone, int apartment_id, Date settling, Date eviction, int vog, int vok) throws SQLException {
        String query1 = "INSERT INTO courseprojectschema.Client (passport_series, passport_number, name, surname, patronymic, birthday, tel_number) VALUES (" + passportSeries + ", " + passportNumber + ", \'" + name + "\', \'" + surname + "\', \'" + patronymic + "\', \'" + birthday + "\', \'" + telephone + "\')";
        conn.createStatement().executeUpdate(query1);
        String query2 = "select @@IDENTITY";
        ResultSet set1 = conn.createStatement().executeQuery(query2);
        set1.next();
        int client_id = set1.getInt(1);
        String query3 = "INSERT INTO courseprojectschema.Additional_services (mini_bar, clothes_washing, telephone, intercity_telephone, food) VALUES (0, 0, 0, 0, 0)";
        conn.createStatement().executeUpdate(query3);
        ResultSet set2 = conn.createStatement().executeQuery(query2);
        set2.next();
        int as_id = set2.getInt(1);
        String query4 = "INSERT INTO courseprojectschema.Living (apartment_id, settling, eviction, value_of_guests, value_of_kids, client_id, as_id) VALUES (" + apartment_id + ", \'" + settling + "\', \'" + eviction + "\', " + vog + ", " + vok + ", " + client_id + ", " + as_id + ")";
        conn.createStatement().executeUpdate(query4);
    }

    public static void InsertClientAndBookingAndEntry(Connection conn, int passportSeries, int passportNumber, String name, String surname, String patronymic, Date birthday, String telephone, int apartment_id, Date settling, Date eviction, int vog, int vok) throws SQLException {
        String query1 = "INSERT INTO courseprojectschema.Client (passport_series, passport_number, name, surname, patronymic, birthday, tel_number) VALUES (" + passportSeries + ", " + passportNumber + ", \'" + name + "\', \'" + surname + "\', \'" + patronymic + "\', \'" + birthday + "\', \'" + telephone + "\')";
        conn.createStatement().executeUpdate(query1);
        String query2 = "select @@IDENTITY";
        ResultSet set1 = conn.createStatement().executeQuery(query2);
        set1.next();
        int client_id = set1.getInt(1);
        String query3 = "INSERT INTO courseprojectschema.Booking (apartment_id, settling, eviction, value_of_guests, value_of_kids, client_id) VALUES ('" + apartment_id + "', \'" + settling + "\', \'" + eviction + "\', " + vog + ", " + vok + ", " + client_id + ")";
        conn.createStatement().executeUpdate(query3);
    }

    public static void ChangeLivingEntry(Connection conn, int livingId, int vog, int vok) throws SQLException {
        String query = "UPDATE courseprojectschema.Living SET value_of_guests = " + vog + ", value_of_kids = " + vok + " WHERE living_id = " + livingId;
        conn.createStatement().executeUpdate(query);
    }

    public static void DeleteLivingEntry(Connection conn, int livingId, int asId) throws SQLException {
        String query1 = "DELETE FROM courseprojectschema.Additional_services WHERE as_id = " + asId;
        conn.createStatement().executeUpdate(query1);
        String query2 = "DELETE FROM courseprojectschema.Living WHERE living_id = " + livingId;
        conn.createStatement().executeUpdate(query2);
    }

    public static void InsertLivingEntry(Connection conn, int client_id, int apartment_id, Date settling, Date eviction, int vog, int vok) throws SQLException {
        String query1 = "INSERT INTO courseprojectschema.Additional_services (mini_bar, clothes_washing, telephone, intercity_telephone, food) VALUES (0, 0, 0, 0, 0)";
        conn.createStatement().executeUpdate(query1);
        String query2 = "select @@IDENTITY";
        ResultSet set = conn.createStatement().executeQuery(query2);
        set.next();
        int as_id = set.getInt(1);
        String query3 = "INSERT INTO courseprojectschema.Living (number, settling, eviction, value_of_guests, value_of_kids, client_id, as_id) VALUES ('" + apartment_id + "', \'" + settling + "\', \'" + eviction + "\', '" + vog + "', '" + vok + "', '" + client_id + "', '" + as_id +"')";
        conn.createStatement().executeUpdate(query3);
    }

    public static void ChangeBookingEntry(Connection conn, int bookingId, int vog, int vok) throws SQLException {
        String query = "UPDATE courseprojectschema.Living SET value_of_guests = " + vog + ", value_of_kids = " + vok + " WHERE living_id = " + bookingId;
        conn.createStatement().executeUpdate(query);
    }

    public static void DeleteBookingEntry(Connection conn, int bookingId) throws SQLException {
        String query = "DELETE FROM courseprojectschema.Booking WHERE booking_id = " + bookingId;
        conn.createStatement().executeUpdate(query);
    }

    public static void InsertBookingEntry(Connection conn, int client_id, int apartment_id, Date settling, Date eviction, int vog, int vok) throws SQLException {
        String query = "INSERT INTO courseprojectschema.Booking (apartment_id, settling, eviction, value_of_guests, value_of_kids, client_id) VALUES ('" + apartment_id + "', \'" + settling + "\', \'" + eviction + "\', '" + vog + "', '" + vok + "', '" + client_id + "')";
        conn.createStatement().executeUpdate(query);
    }

    public static void ChangeApartmentsEntry(Connection conn, int apartmentId, int number, String type, int price) throws SQLException {
        String query = "UPDATE courseprojectschema.Apartments SET apartment_id = " + number + " , \"type\" = \'" + type + "\', price = " + price + " WHERE apartmentId = " + apartmentId;
        conn.createStatement().executeUpdate(query);
    }

    public static void DeleteApartmentsEntry(Connection conn, int apartmentId) throws SQLException {
        String query1 = "DELETE FROM courseprojectschema.Apartments WHERE apartment_id = " + apartmentId;
        conn.createStatement().executeUpdate(query1);
        String query2 = "DELETE FROM courseprojectschema.Photos WHERE apartment_id = " + apartmentId;
        conn.createStatement().executeUpdate(query2);
    }

    public static ResultSet SelectApartmentsIdWithNumber(Connection conn, int number) throws SQLException {
        String query = "SELECT living_id FROM courseprojectschema.Apartments WHERE number = " + number;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static void ChangeAdditionalServices(Connection conn, int as_id, int mini_bar, int clothes_washing, int telephone, int intercity_telephone, int food) throws SQLException {
        String query = "UPDATE courseprojectschema.Additional_services SET mini_bar = " + mini_bar + ", clothes_washing = " + clothes_washing + ", telephone = " + telephone + ", intercity_telephone = " + intercity_telephone + ", food = " + food +" WHERE as_id = " + as_id;
        conn.createStatement().executeUpdate(query);
    }

    public static void InsertPhotoEntry(Connection conn, String path, int apartmentId) throws SQLException {
        String query = "INSERT INTO courseprojectschema.Photos (path, number) VALUES (\'" + path + "\', " + apartmentId + ")";
        conn.createStatement().executeUpdate(query);
    }
}
