package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Model.*;

import java.sql.*;
import java.util.Properties;


public class Database {
    private static Database instance = null;
    private Connection connection;
    private final String username = "csUser";
    private final String password = "csPassword";

    // Stop instantiation
    private Database() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch(ClassNotFoundException e) {
            e.printStackTrace();
            return;
        }

        Properties loginCredentials = new Properties();
        loginCredentials.put("user", this.username);
        loginCredentials.put("password", this.password);

        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost/calendarSystem", loginCredentials);
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
    public int insertUser(User user) {
        int newUserId = -1;

        String query;
        query =  "INSERT INTO users ";
        query += "(username, password, firstName, lastName, email) ";
        query += "VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername() );
            preparedStatement.setString(2, user.getPassword() );
            preparedStatement.setString(3, user.getFirstname());
            preparedStatement.setString(4, user.getLastname() );
            preparedStatement.setString(5, user.getEmail()    );
            preparedStatement.executeUpdate();

            ResultSet userId = preparedStatement.getGeneratedKeys();

            /**
             * Moves the cursor forward one row from its current position.
             * A <code>ResultSet</code> cursor is initially positioned
             * before the first row; the first call to the method
             * <code>next</code> makes the first row the current row; the
             * second call makes the second row the current row, and so on.
             */
            userId.next();

            newUserId = userId.getInt(1);

            preparedStatement.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return newUserId;
    }

    /**
     *
     * @param userId id of the owner of the alarm
     * @param appointmentId id of the appointment the alarm is for
     * @param date the date the alarm should go off (java.util.Date)
     */
    private void insertAlarm(int userId, int appointmentId, java.util.Date date) {
        String query;
        query =  "INSERT INTO alarms ";
        query += "(appointmentId, userId, time) ";
        query += "VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, appointmentId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setTimestamp(3, this.dateToSqlTimestamp(date));
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param meetingRoom the MeetingRoom to insert into the database
     * @param appointmentId if of the Appointment to insert into the database
     */
    private void insertMeetingRoomReservation(MeetingRoom meetingRoom, int appointmentId) {
        String query;
        query =  "INSERT INTO meetingRoomReservations ";
        query += "(meetingRoomId, appointmentId) ";
        query += "VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, meetingRoom.getId());
            preparedStatement.setInt(2, appointmentId);
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
    public void insertAttendant(Attendant attendant) {
        String query;
        query =  "INSERT INTO userAppointments ";
        query += "(userId, appointmentId, status, lastChecked, isVisible) ";
        query += "VALUES (?, ?, ?, ?, ?)";

        try {
            int isVisible = attendant.getVisibleOnCalendar() ? 1 : 0;

            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, attendant.getUser().getId());
            preparedStatement.setInt(2, attendant.getAppointment().getId());
            preparedStatement.setInt(3, attendant.getStatus());
            preparedStatement.setDate(4, new java.sql.Date(0));  // Default to last checked at UNIX timestamp start
            preparedStatement.setInt(5, isVisible);
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch(SQLException e) {
            e.printStackTrace();
        }

        if(attendant.getAlarmStatus()) {
            this.insertAlarm(
                    attendant.getUser().getId(),
                    attendant.getAppointment().getId(),
                    attendant.getAlarmClock()
            );
        }
    }

    /**
     *
     * @param appointment the appointment to insert into the database
     * @return the database id of the appointment; if an exception happened it returns -1
     */
    public int insertAppointment(Appointment appointment) {
        int newAppointmentId = -1;

        String query;
        query =  "INSERT INTO appointments ";
        query += "(name, description, startDate, endDate, place, lastUpdated, ownerId) ";
        query += "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, appointment.getTitle());
            preparedStatement.setString(2, appointment.getDescription());
            preparedStatement.setTimestamp(3, this.dateToSqlTimestamp(appointment.getStart()));
            preparedStatement.setTimestamp(4, this.dateToSqlTimestamp(appointment.getEnd()));
            preparedStatement.setString(5, appointment.getMeetingPlace());
            preparedStatement.setTimestamp(6, this.dateToSqlTimestamp(appointment.getLastUpdated()));
            preparedStatement.setInt(7, appointment.getOwner().getId());
            preparedStatement.executeUpdate();

            ResultSet appointmentId = preparedStatement.getGeneratedKeys();

            /**
             * Moves the cursor forward one row from its current position.
             * A <code>ResultSet</code> cursor is initially positioned
             * before the first row; the first call to the method
             * <code>next</code> makes the first row the current row; the
             * second call makes the second row the current row, and so on.
             */
            appointmentId.next();

            newAppointmentId = appointmentId.getInt(1);

            preparedStatement.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        if(appointment.getRoom() != null) {
            this.insertMeetingRoomReservation(appointment.getRoom(), newAppointmentId);
        }

        return newAppointmentId;
    }

    /**
     *
     * @param meetingRoom meeting room to insert into db
     * @return id of the meeting room in the database; if exception happens returns -1
     */
    public int insertMeetingRoom(MeetingRoom meetingRoom) {
        int newMeetingRoomId = -1;

        String query;
        query =  "INSERT INTO meetingRooms ";
        query += "(capacity) ";
        query += "VALUES (?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, meetingRoom.getRoomSize());
            preparedStatement.executeUpdate();

            ResultSet meetingRoomId = preparedStatement.getGeneratedKeys();

            /**
             * Moves the cursor forward one row from its current position.
             * A <code>ResultSet</code> cursor is initially positioned
             * before the first row; the first call to the method
             * <code>next</code> makes the first row the current row; the
             * second call makes the second row the current row, and so on.
             */
            meetingRoomId.next();

            newMeetingRoomId = meetingRoomId.getInt(1);

            preparedStatement.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        return newMeetingRoomId;
    }

    private java.sql.Timestamp dateToSqlTimestamp(java.util.Date date) {
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
        attendant.setAlarm(new java.util.Date(new java.util.Date().getTime() - 60 * 60 * 1000));

        Database database = new Database();
        anders.setId(database.insertUser(anders));
        meetingRoom.setId(database.insertMeetingRoom(meetingRoom));
        appointment.setId(database.insertAppointment(appointment));
        database.insertAttendant(attendant);
        database.close();
    }
}
