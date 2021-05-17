package sample;

import sample.Models.PseudoApartmentTypes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public static ResultSet ApplyTargetFiltres(Connection conn, String type, Date b1, Date b2, int bp, int tp) throws SQLException //Зарефакторить SQL запросы в этом методе
    {
        String query = "";
        if (type != null)
        {
            //command = new SqlCommand($"SELECT a.number, a.type, a.price, l.settling, l.eviction FROM Apartments a, Living l, Booking b WHERE (a.type={type} AND b.settling>'{b2}' AND l.eviction<'{b1}' AND a.price>{bp} AND a.price<{tp})", connection);
            query = "SELECT a.number, a.\"type\", a.price FROM courseprojectschema.Apartments a WHERE a.\"type\" = \'" + type + "\' AND a.price > " + bp + " AND a.price < " + tp + " AND ((a.number IN (SELECT number FROM courseprojectschema.Living WHERE eviction < \'" + b1 + "\') AND NOT EXISTS(SELECT number FROM courseprojectschema.Booking WHERE a.number IN (SELECT number FROM courseprojectschema.Booking))) OR (a.number in (SELECT number FROM courseprojectschema.Booking WHERE settling > \'" + b2 + "\') OR (a.number in (SELECT number FROM courseprojectschema.Booking WHERE eviction < \'" + b1 + "\'))) AND NOT EXISTS(SELECT number FROM courseprojectschema.Living WHERE a.number IN (SELECT number FROM courseprojectschema.Living)) OR ((a.number in (SELECT number FROM courseprojectschema.Living WHERE eviction<\'" + b1 + "\')) AND (a.number in (SELECT number FROM courseprojectschema.Booking WHERE settling>\'" + b2 + "\') OR (a.number in (SELECT number FROM courseprojectschema.Booking WHERE eviction<\'" + b1 + "\')))) OR (a.number NOT IN (SELECT number FROM courseprojectschema.Living) AND a.number NOT IN (SELECT number FROM courseprojectschema.Booking)))";
        }
        else
        {
            //command = new SqlCommand($"SELECT a.number, a.type, a.price, l.settling, l.eviction FROM Apartments a, Living l, Booking b WHERE (b.settling>'{b2}' AND l.eviction<'{b1}' AND a.price>{bp} AND a.price<{tp})", connection);
            query = "SELECT a.number, a.\"type\", a.price FROM courseprojectschema.Apartments a WHERE a.price > " + bp + " AND a.price < " + tp + " AND ((a.number IN (SELECT number FROM courseprojectschema.Living WHERE eviction < \'" + b1 + "\') AND NOT EXISTS(SELECT number FROM courseprojectschema.Booking WHERE a.number IN (SELECT number FROM courseprojectschema.Booking))) OR (a.number in (SELECT number FROM courseprojectschema.Booking WHERE settling > \'" + b2 + "\') OR (a.number in (SELECT number FROM courseprojectschema.Booking WHERE eviction < \'" + b1 + "\'))) AND NOT EXISTS(SELECT number FROM courseprojectschema.Living WHERE a.number IN (SELECT number FROM courseprojectschema.Living)) OR ((a.number in (SELECT number FROM courseprojectschema.Living WHERE eviction<\'" + b1 + "\')) AND (a.number in (SELECT number FROM courseprojectschema.Booking WHERE settling>\'" + b2 + "\') OR (a.number in (SELECT number FROM courseprojectschema.Booking WHERE eviction<\'" + b1 + "\')))) OR (a.number NOT IN (SELECT number FROM courseprojectschema.Living) AND a.number NOT IN (SELECT number FROM courseprojectschema.Booking)))";
        }
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ArrayList<String> GetTypesOfApartments(Connection conn, String[] cE) throws SQLException // Получаем все реально имеющиеся типы номеров для данной гостинницы для последующего использоваия в ComboBox
    {
        ArrayList<String> currentTypesOfApartments = new ArrayList<String>();
        String query = "SELECT DISTINCT type FROM courseprojectschema.Apartments";
        ResultSet set = conn.createStatement().executeQuery(query);
        if (set != null)
        {
            while (set.next())
            {
                currentTypesOfApartments.add(set.getString(1));
            }
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
        if (set == null)
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
        for(int i = 0; i < PseudoApartmentTypes.getTypesArray().length; i++)
        {
            if(PseudoApartmentTypes.getTypesArray()[i].equals(cb))
            {
                return true;
            }
        }
        return false;
    }

    public static ResultSet SelectAllFromCustomer(Connection conn) throws SQLException {
        String query = "SELECT passport_series, passport_number, name, surname, patronymic, birthday, tel_number FROM courseprojectschema.Customer";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet SelectAllFromCustomerWithSetSurname(Connection conn, String surname) throws SQLException {
        String query = "SELECT passport_series, passport_number, name, surname, patronymic, birthday, tel_number FROM courseprojectschema.Customer WHERE surname LIKE \'" + surname + "%\'";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static int SelectNthIdFromCustomerWherePassportDataDefinedToString__ALTERNATIVE__(Connection conn, int ps, int pn) throws SQLException {
        String query = "SELECT DISTINCT customer_id FROM courseprojectschema.Customer WHERE passport_series = " + ps + " AND passport_number = " + pn;
        ResultSet set = conn.createStatement().executeQuery(query);
        if(set == null)
        {
            return -1;
        }
        else
        {
            return set.getInt(1);
        }
    }

    public static String SelectNthIdFromCustomerWherePassportDataDefinedToString(Connection conn, int ps, int pn) throws SQLException { //Очень странный метод, не знаю как я его вообще придумал. Не уверен, что смогу его заменить, поэтому выше напишу его альтернативу
        String query = "SELECT DISTINCT customer_id FROM courseprojectschema.Customer WHERE passport_series = " + ps + " AND passport_number = " + pn;
        ResultSet set = conn.createStatement().executeQuery(query);
        if(set == null)
        {
            return "null";
        }
        else
        {
            return set.getString(1);
        }
    }
    public static ResultSet SelectAllFromCustomerByCustomerId(Connection conn, int iOC) throws SQLException {
        String query = "SELECT passport_series, passport_number, name, surname, patronymic, birthday, tel_number FROM courseprojectschema.Customer WHERE customer_id = " + iOC;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet SelectAllFromLiving(Connection conn) throws SQLException {
        String query = "SELECT c.passport_series, c.passport_number, c.name, c.surname, c.patronymic, l.settling, l.eviction, l.number, l.value_of_guests, l.value_of_kids, l.as_id, l.living_id FROM courseprojectschema.Customer c, Living l WHERE c.customer_id = l.customer_id";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet SelectAllFromLivingByCustomerId(Connection conn, int iOC) throws SQLException {
        String query = "SELECT c.passport_series, c.passport_number, c.name, c.surname, c.patronymic, l.settling, l.eviction, l.number, l.value_of_guests, l.value_of_kids, l.as_id, l.living_id FROM courseprojectschema.Customer c, Living l WHERE l.customer_id = " + iOC + " AND c.customer_id = " + iOC;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet SelectAllFromBooking(Connection conn) throws SQLException {
        String query = "SELECT c.passport_series, c.passport_number, c.name, c.surname, c.patronymic, b.settling, b.eviction, b.number, b.value_of_guests, b.value_of_kids, b.booking_id, b.booking_id FROM courseprojectschema.Customer c, Booking b WHERE c.customer_id = b.customer_id";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet SelectAllFromBookingByCustomerId(Connection conn, int iOC) throws SQLException {
        String query = "SELECT c.passport_series, c.passport_number, c.name, c.surname, c.patronymic, b.settling, b.eviction, b.number, b.value_of_guests, b.value_of_kids, b.booking_id, b.booking_id FROM courseprojectschema.Customer c, Booking b WHERE b.customer_id = " + iOC + " AND c.customer_id = " + iOC;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet SelectAllFromApartments(Connection conn) throws SQLException {
        String query = "SELECT number, type, price FROM courseprojectschema.Apartments";
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static ResultSet SelectAllFromApartmentsWhereNumberIsSet(Connection conn, int n) throws SQLException {
        String query = "SELECT number, type, price FROM courseprojectschema.Apartments WHERE number = " + n;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static boolean IsNumberOfApartmentsFreeForToday(Connection conn, int nOA) throws SQLException {
        Date today = new Date();
        String query = "SELECT a.number FROM courseprojectschema.Apartments a WHERE (a.number = " + nOA + ") AND ((a.number IN (SELECT number FROM courseprojectschema.Living WHERE eviction < \'" + today + "\') AND NOT EXISTS(SELECT number FROM courseprojectschema.Booking WHERE a.number IN (SELECT number FROM courseprojectschema.Booking))) OR ((a.number in (SELECT number FROM courseprojectschema.Booking WHERE eviction < \'" + today + "\'))) AND NOT EXISTS(SELECT number FROM courseprojectschema.Living WHERE a.number IN (SELECT number FROM courseprojectschema.Living)) OR ((a.number in (SELECT number FROM courseprojectschema.Living WHERE eviction < \'" + today + "\')) AND ((a.number in (SELECT number FROM courseprojectschema.Booking WHERE eviction < \'" + today + "\')))) OR (a.number NOT IN (SELECT number FROM courseprojectschema.Living) AND a.number NOT IN (SELECT number FROM courseprojectschema.Booking)))";
        ResultSet set = conn.createStatement().executeQuery(query);
        if (set == null)
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
        if (set == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static int ValueOfCustomerLivingsAndBookingsForToday(Connection conn, int iOC) throws SQLException {
        Date today = new Date();
        String query1 = "SELECT COUNT(l.living_id) FROM courseprojectschema.Living l WHERE l.eviction > \'" + today + "\' AND l.customer_id IN (SELECT customer_id FROM courseprojectschema.Customer WHERE customer_id = " + iOC + ")";
        ResultSet set1 = conn.createStatement().executeQuery(query1);
        String query2 = "SELECT COUNT(b.booking_id) FROM courseprojectschema.Booking b WHERE b.customer_id IN (SELECT customer_id FROM courseprojectschema.Customer WHERE customer_id = " + iOC + ")";
        ResultSet set2 = conn.createStatement().executeQuery(query2);
        int v = set1.getInt(1) + set2.getInt(1);
        return v;
    }

    public static int GetCurrentDiscount(Connection conn) throws SQLException {
        String query = "SELECT courseprojectschema.DISTINCT discount FROM Discount";
        ResultSet set = conn.createStatement().executeQuery(query);
        if (set == null)
        {
            return 0;
        }
        else
        {
            return set.getInt(1);
        }
    }

    public static void SetNewDiscount(Connection conn, int disc) throws SQLException {
        String query1 = "SELECT DISTINCT discount FROM Discount";
        ResultSet set1 = conn.createStatement().executeQuery(query1);
        if (set1 == null)
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

    public static int[] SelectAllFromAdditionalServicesWhereIsSetLivingID(Connection conn, int iOL) throws SQLException {
        int[] v = new int[5];
        String query = "SELECT mini_bar, clothes_washing, telephone, intercity_telephone, food FROM courseprojectschema.Additional_services WHERE as_id = " + iOL;
        ResultSet set = conn.createStatement().executeQuery(query);
        if (set != null)
        {
            if (set.next()) //Возможно эту строку стоит убрать и у тебя в будущем ничего не будет работать именно из-за неё
            {
                for (int i = 0; i < v.length; i++)
                {
                    if (set.getObject(i) == null)
                    {
                        v[i] = 0;
                    }
                    else
                    {
                        v[i] = set.getInt(i);
                    }
                }
            }
        }
        return v;
    }

    public static boolean DoesCustomerHasNoLivingsAndBookings(Connection conn, int idOfClient) throws SQLException {
        String query1 = "SELECT living_id FROM courseprojectschema.Living WHERE customer_id = " + idOfClient;
        ResultSet set1 = conn.createStatement().executeQuery(query1);
        String query2 = "SELECT booking_id FROM courseprojectschema.Booking WHERE customer_id = " + idOfClient;
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
    public static void UpdateCustomerWithValues(Connection conn, int clientId, int passportSeries, int passportNumber, String name, String surname, String patronymic, Date birthday, String telephone) throws SQLException {
        String query = "UPDATE Customer SET passport_series = " + passportSeries + ", passport_number = " + passportNumber + ", name = \'" + name + "\', surname = \'" + surname + "\', patronymic = \'" + patronymic + "\', birthday = \'" + birthday + "\', tel_number = \'" + telephone + "\' WHERE customer_id = " + clientId;
        conn.createStatement().executeUpdate(query);
    }

    public static void DeleteNullableCustomer(Connection conn, int clientId) throws SQLException {
        String query = "DELETE FROM Customer WHERE living_id = " + clientId;
        conn.createStatement().executeUpdate(query);
    }

    public static void DeleteAllInfoAboutCustomer(Connection conn, int clientId) throws SQLException {
        String query1 = "DELETE FROM Customer WHERE customer_id = " + clientId;
        conn.createStatement().executeUpdate(query1);
        String query2 = "DELETE FROM Living WHERE customer_id = " + clientId;
        conn.createStatement().executeUpdate(query2);
        String query3 = "DELETE FROM Booking WHERE customer_id = " + clientId;
        conn.createStatement().executeUpdate(query3);
    }

    public static void InsertCustomerAndLivingAndAdditionalServicesEntry(Connection conn, int passportSeries, int passportNumber, String name, String surname, String patronymic, Date birthday, String telephone, int number, Date settling, Date eviction, int vog, int vok) throws SQLException {
        String query1 = "INSERT INTO Customer (passport_series, passport_number, name, surname, patronymic, birthday, tel_number) VALUES (\'" + passportSeries + "\', \'" + passportNumber + "\', \'" + name + "\', \'" + surname + "\', \'" + patronymic + "\', \'" + birthday + "\', \'" + telephone + "\')";
        conn.createStatement().executeUpdate(query1);
        String query2 = "select @@IDENTITY";
        ResultSet set1 = conn.createStatement().executeQuery(query2);
        int customer_id = set1.getInt(1);
        String query3 = "INSERT INTO Additional_services (mini_bar, clothes_washing, telephone, intercity_telephone, food) VALUES (0, 0, 0, 0, 0)";
        conn.createStatement().executeUpdate(query3);
        ResultSet set2 = conn.createStatement().executeQuery(query2);
        int as_id = set2.getInt(1);
        String query4 = "INSERT INTO Living (number, settling, eviction, value_of_guests, value_of_kids, customer_id, as_id) VALUES (\'" + number + "\', \'" + settling + "\', \'" + eviction + "\', \'" + vog + "\', \'" + vok + "\', \'" + customer_id + "\', \'" + as_id +"\')";
        conn.createStatement().executeUpdate(query4);
    }

    public static void InsertCustomerAndBookingAndEntry(Connection conn, int passportSeries, int passportNumber, String name, String surname, String patronymic, Date birthday, String telephone, int number, Date settling, Date eviction, int vog, int vok) throws SQLException {
        String query1 = "INSERT INTO Customer (passport_series, passport_number, name, surname, patronymic, birthday, tel_number) VALUES (\'" + passportSeries + "\', \'" + passportNumber + "\', \'" + name + "\', \'" + surname + "\', \'" + patronymic + "\', \'" + birthday + "\', \'" + telephone + "\')";
        conn.createStatement().executeUpdate(query1);
        String query2 = "select @@IDENTITY";
        ResultSet set1 = conn.createStatement().executeQuery(query2);
        int customer_id = set1.getInt(1);
        String query3 = "INSERT INTO Booking (number, settling, eviction, value_of_guests, value_of_kids, customer_id, as_id) VALUES (\'" + number + "\', \'" + settling + "\', \'" + eviction + "\', \'" + vog + "\', \'" + vok + "\', \'" + customer_id + "\', \')";
        conn.createStatement().executeUpdate(query3);
    }

    public static void ChangeLivingEntry(Connection conn, int livingId, int vog, int vok) throws SQLException {
        String query = "UPDATE Living SET value_of_guests = " + vog + ", value_of_kids = " + vok + " WHERE living_id = " + livingId;
        conn.createStatement().executeUpdate(query);
    }

    public static void DeleteLivingEntry(Connection conn, int livingId) throws SQLException {
        String query = "DELETE FROM Living WHERE living_id = " + livingId;
        conn.createStatement().executeUpdate(query);
    }

    public static void InsertLivingEntry(Connection conn, int number, Date settling, Date eviction, int vog, int vok, int customer_id) throws SQLException {
        String query1 = "INSERT INTO Additional_services (mini_bar, clothes_washing, telephone, intercity_telephone, food) VALUES (0, 0, 0, 0, 0)";
        conn.createStatement().executeUpdate(query1);
        String query2 = "select @@IDENTITY";
        ResultSet set = conn.createStatement().executeQuery(query2);
        int as_id = set.getInt(1);
        String query3 = "INSERT INTO Living (number, settling, eviction, value_of_guests, value_of_kids, customer_id, as_id) VALUES (\'" + number + "\', \'" + settling + "\', \'" + eviction + "\', \'" + vog + "\', \'" + vok + "\', \'" + customer_id + "\', \'" + as_id +"\')";
        conn.createStatement().executeUpdate(query3);
    }

    public static void ChangeBookingEntry(Connection conn, int bookingId, int vog, int vok) throws SQLException {
        String query = "UPDATE Living SET value_of_guests = " + vog + ", value_of_kids = " + vok + " WHERE living_id = " + bookingId;
        conn.createStatement().executeUpdate(query);
    }

    public static void DeleteBookingEntry(Connection conn, int bookingId) throws SQLException {
        String query = "DELETE FROM Booking WHERE booking_id = " + bookingId;
        conn.createStatement().executeUpdate(query);
    }

    public static void InsertBookingEntry(Connection conn, int number, Date settling, Date eviction, int vog, int vok, int customer_id) throws SQLException {
        String query = "INSERT INTO Booking (number, settling, eviction, value_of_guests, value_of_kids, customer_id) VALUES (\'" + number + "\', \'" + settling + "\', \'" + eviction + "\', \'" + vog + "\', \'" + vok + "\', \'" + customer_id + "\')";
        conn.createStatement().executeUpdate(query);
    }

    public static void ChangeApartmentsEntry(Connection conn, int apartmentId, int number, String type, int price) throws SQLException {
        String query = "UPDATE Apartments SET apartment_id = " + number + " , \"type\" = \'" + type + "\', price = " + price + " WHERE apartmentId = " + apartmentId;
        conn.createStatement().executeUpdate(query);
    }

    public static void DeleteApartmentsEntry(Connection conn, int apartmentId) throws SQLException {
        String query1 = "DELETE FROM Apartments WHERE apartment_id = " + apartmentId;
        conn.createStatement().executeUpdate(query1);
        String query2 = "DELETE FROM Photos WHERE apartment_id = " + apartmentId;
        conn.createStatement().executeUpdate(query2);
    }

    public static ResultSet SelectApartmentsIdWithNumber(Connection conn, int number) throws SQLException {
        String query = "SELECT living_id FROM courseprojectschema.Apartments WHERE number = " + number;
        ResultSet set = conn.createStatement().executeQuery(query);
        return set;
    }

    public static void ChangeAdditionalServices(Connection conn, int as_id, int mini_bar, int clothes_washing, int telephone, int intercity_telephone, int food) throws SQLException {
        String query = "UPDATE Additional_services SET mini_bar = " + mini_bar + ", clothes_washing = " + clothes_washing + ", telephone = " + telephone + ", intercity_telephone = " + intercity_telephone + ", food = " + food +" WHERE as_id = " + as_id;
        conn.createStatement().executeUpdate(query);
    }

    public static void InsertPhotoEntry(Connection conn, String path, int apartmentId) throws SQLException {
        String query = "INSERT INTO Photos (path, number) VALUES (\'" + path + "\', \'" + apartmentId + "\')";
        conn.createStatement().executeUpdate(query);
    }
    //Заменить все Customer на Client
}
