package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Model.*;

import java.sql.*;
import java.util.Properties;


public class Database {
    private static Database instance = null;
    private Connection connection;
    private final String username = "cs_user";
    private final String password = "cs_password";

    // Stop instantiation
    private Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Properties login_credentials = new Properties();
        login_credentials.put("user", this.username);
        login_credentials.put("password", this.password);

        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost/calendar_system", login_credentials);
            System.out.println("Connected to database!");

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Could not get database connection.");
        }
    }

    public static Database getInstance() {
        if(instance == null) {
            instance = new Database();
        }
        return instance;
    }

    /**
     *
     * @param user the user to insert into the database
     * @return the database id of the user; if an exception happened it returns -1
     */
    public int insert_user(User user) {
        int new_user_id = -1;

        String query;
        query =  "INSERT INTO users ";
        query += "(username, password, first_name, last_name, email) ";
        query += "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.get_username() );
            preparedStatement.setString(2, user.get_password() );
            preparedStatement.setString(3, user.get_firstname());
            preparedStatement.setString(4, user.get_lastname() );
            preparedStatement.setString(5, user.get_email()    );
            preparedStatement.executeUpdate();

            ResultSet user_id = preparedStatement.getGeneratedKeys();

            /**
             * Moves the cursor forward one row from its current position.
             * A <code>ResultSet</code> cursor is initially positioned
             * before the first row; the first call to the method
             * <code>next</code> makes the first row the current row; the
             * second call makes the second row the current row, and so on.
             */
            user_id.next();

            new_user_id = user_id.getInt(1);

            preparedStatement.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return new_user_id;
    }

    /**
     *
     * @param user_id id of the owner of the alarm
     * @param appointment_id id of the appointment the alarm is for
     * @param date the date the alarm should go off (java.util.Date)
     */
    private void insert_alarm(int user_id, int appointment_id, java.util.Date date) {
        String query;
        query =  "INSERT INTO alarms ";
        query += "(appointment_id, user_id, time) ";
        query += "VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, appointment_id);
            preparedStatement.setInt(2, user_id);
            preparedStatement.setTimestamp(3, this.date_to_sql_timestamp(date));
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param meetingRoom the MeetingRoom to insert into the database
     * @param appointment_id if of the Appointment to insert into the database
     */
    private void insert_meeting_room_reservation(MeetingRoom meetingRoom, int appointment_id) {
        String query;
        query =  "INSERT INTO meeting_room_reservations ";
        query += "(meeting_room_id, appointment_id) ";
        query += "VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, meetingRoom.get_id());
            preparedStatement.setInt(2, appointment_id);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param attendant attendant to insert into db
     */
    public void insert_attendant(Attendant attendant) {
        String query;
        query =  "INSERT INTO user_appointments ";
        query += "(user_id, appointment_id, status, last_checked, is_visible) ";
        query += "VALUES (?, ?, ?, ?, ?)";

        try {
            int is_visible = attendant.get_visible_on_calendar() ? 1 : 0;

            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, attendant.get_user().get_id());
            preparedStatement.setInt(2, attendant.get_appointment().get_id());
            preparedStatement.setInt(3, attendant.get_status());
            preparedStatement.setDate(4, new java.sql.Date(0));  // Default to last checked at UNIX timestamp start
            preparedStatement.setInt(5, is_visible);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        if(attendant.get_alarm_status()) {
            this.insert_alarm(
                    attendant.get_user().get_id(),
                    attendant.get_appointment().get_id(),
                    attendant.get_alarm_clock()
            );
        }
    }

    /**
     *
     * @param appointment the appointment to insert into the database
     * @return the database id of the appointment; if an exception happened it returns -1
     */
    public int insert_appointment(Appointment appointment) {
        int new_appointment_id = -1;

        String query;
        query =  "INSERT INTO appointments ";
        query += "(name, description, start_date, end_date, place, last_updated, owner_id) ";
        query += "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, appointment.get_title());
            preparedStatement.setString(2, appointment.get_description());
            preparedStatement.setTimestamp(3, this.date_to_sql_timestamp(appointment.get_start()));
            preparedStatement.setTimestamp(4, this.date_to_sql_timestamp(appointment.get_end()));
            preparedStatement.setString(5, appointment.get_meeting_place());
            preparedStatement.setTimestamp(6, this.date_to_sql_timestamp(appointment.get_last_updated()));
            preparedStatement.setInt(7, appointment.get_owner().get_id());
            preparedStatement.executeUpdate();

            ResultSet appointment_id = preparedStatement.getGeneratedKeys();

            /**
             * Moves the cursor forward one row from its current position.
             * A <code>ResultSet</code> cursor is initially positioned
             * before the first row; the first call to the method
             * <code>next</code> makes the first row the current row; the
             * second call makes the second row the current row, and so on.
             */
            appointment_id.next();

            new_appointment_id = appointment_id.getInt(1);

            preparedStatement.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        if(appointment.get_room() != null) {
            this.insert_meeting_room_reservation(appointment.get_room(), new_appointment_id);
        }

        return new_appointment_id;
    }

    /**
     *
     * @param meetingRoom meeting room to insert into db
     * @return id of the meeting room in the database; if exception happens returns -1
     */
    public int insert_meeting_room(MeetingRoom meetingRoom) {
        int new_meeting_room_id = -1;

        String query;
        query =  "INSERT INTO meeting_rooms ";
        query += "(capacity) ";
        query += "VALUES (?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, meetingRoom.get_room_size());
            preparedStatement.executeUpdate();

            ResultSet meeting_room_id = preparedStatement.getGeneratedKeys();

            /**
             * Moves the cursor forward one row from its current position.
             * A <code>ResultSet</code> cursor is initially positioned
             * before the first row; the first call to the method
             * <code>next</code> makes the first row the current row; the
             * second call makes the second row the current row, and so on.
             */
            meeting_room_id.next();

            new_meeting_room_id = meeting_room_id.getInt(1);

            preparedStatement.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return new_meeting_room_id;
    }

    private java.sql.Timestamp date_to_sql_timestamp(java.util.Date date) {
        return new java.sql.Timestamp(date.getTime());
    }

    public ResultSet query(String query) {
        ResultSet result = null;

        try {
            Statement statement = this.connection.createStatement();

            result = statement.executeQuery(query);

            statement.close();
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }

    public void close() {
        try {
            this.connection.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /**
         * Test data
         */
        User anders = new User("Anders", "Wenhaug", "andersw", "password", "anders@wenhaug.no");
        MeetingRoom meetingRoom = new MeetingRoom(8);
        Appointment appointment = new Appointment(
                anders,
                "Test-appointment",
                "Test-description",
                new java.util.Date(),
                new java.util.Date(new java.util.Date().getTime() + 60 * 60 * 1000),
                meetingRoom
        );
        Attendant attendant = new Attendant(anders, appointment);
        attendant.set_alarm(new java.util.Date(new java.util.Date().getTime() - 60 * 60 * 1000));

        Database database = new Database();
        anders.set_id(database.insert_user(anders));
        meetingRoom.set_id(database.insert_meeting_room(meetingRoom));
        appointment.set_id(database.insert_appointment(appointment));
        database.insert_attendant(attendant);
        database.close();
    }
}
