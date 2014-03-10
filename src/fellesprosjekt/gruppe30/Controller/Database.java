package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Model.*;

import java.io.DataOutputStream;
import java.sql.*;
import java.util.ArrayList;
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

        Properties loginCredentials = new Properties();
        loginCredentials.put("user", this.username);
        loginCredentials.put("password", this.password);

        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost/calendar_system", loginCredentials);
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
        if(user.getId() != -1) {
            this.updateUser(user);
            return user.getId();
        }
        int newUserId = -1;

        String query;
        query =  "INSERT INTO users ";
        query += "(username, password, first_name, last_name, email) ";
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

    public void updateUser(User user) {
        String query;
        query =  "UPDATE users ";
        query += "SET username = '?', password = '?', first_name = '?', last_name = '?', email = '?') ";
        query += "WHERE id = '?'";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername() );
            preparedStatement.setString(2, user.getPassword() );
            preparedStatement.setString(3, user.getFirstname());
            preparedStatement.setString(4, user.getLastname() );
            preparedStatement.setString(5, user.getEmail()    );
            preparedStatement.setInt(6, user.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
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
        query += "(appointment_id, user_id, time) ";
        query += "VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt      (1, appointmentId                );
            preparedStatement.setInt      (2, userId                       );
            preparedStatement.setTimestamp(3, this.dateToSqlTimestamp(date));
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Alarm (probably) already existed, updating instead of inserting...");
            this.updateAlarm(userId, appointmentId, date);
            return;
        }
    }

    private void updateAlarm(int userId, int appointmentId, java.util.Date date) {
        String query;
        query =  "UPDATE alarms ";
        query += "SET time = '?' ";
        query += "WHERE appointment_id = '?' AND user_id = '?'";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, this.dateToSqlTimestamp(date));
            preparedStatement.setInt      (2, appointmentId                );
            preparedStatement.setInt      (3, userId                       );
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
        query =  "INSERT INTO meeting_room_reservations ";
        query += "(meeting_room_id, appointment_id) ";
        query += "VALUES (?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, meetingRoom.getId());
            preparedStatement.setInt(2, appointmentId      );
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
        query =  "INSERT INTO user_appointments ";
        query += "(user_id, appointment_id, status, last_checked, is_visible) ";
        query += "VALUES (?, ?, ?, ?, ?)";

        try {
            int isVisible = attendant.getVisibleOnCalendar() ? 1 : 0;

            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt      (1, attendant.getUser().getId()                        );
            preparedStatement.setInt      (2, attendant.getAppointment().getId()                 );
            preparedStatement.setInt      (3, attendant.getStatus()                              );
            preparedStatement.setTimestamp(4, this.dateToSqlTimestamp(attendant.getLastChecked()));
            preparedStatement.setInt      (5, isVisible                                          );
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Attendant (probably) already existed, updating instead of inserting...");
            this.updateAttendant(attendant);
            return;
        }

        if(attendant.getAlarmStatus()) {
            this.insertAlarm(
                    attendant.getUser().getId(),
                    attendant.getAppointment().getId(),
                    attendant.getAlarmClock()
            );
        }
    }

    public void updateAttendant(Attendant attendant) {
        String query;
        query =  "UPDATE user_appointments ";
        query += "SET status = '?', last_checked = '?', is_visible = '?') ";
        query += "WHERE user_id = '?' AND appointment_id = '?'";

        try {
            int isVisible = attendant.getVisibleOnCalendar() ? 1 : 0;

            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt      (1, attendant.getStatus()                              );
            preparedStatement.setTimestamp(2, this.dateToSqlTimestamp(attendant.getLastChecked()));  // Default to last checked at UNIX timestamp start
            preparedStatement.setInt      (3, isVisible                                          );
            preparedStatement.setInt      (4, attendant.getUser().getId()                        );
            preparedStatement.setInt      (5, attendant.getAppointment().getId()                 );
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
        if(appointment.getId() != -1) {
            this.updateAppointment(appointment);
            return appointment.getId();
        }
        int newAppointmentId = -1;

        String query;
        query =  "INSERT INTO appointments ";
        query += "(name, description, start_date, end_date, place, last_updated, owner_id) ";
        query += "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString   (1, appointment.getTitle()                               );
            preparedStatement.setString   (2, appointment.getDescription()                         );
            preparedStatement.setTimestamp(3, this.dateToSqlTimestamp(appointment.getStart())      );
            preparedStatement.setTimestamp(4, this.dateToSqlTimestamp(appointment.getEnd())        );
            preparedStatement.setString   (5, appointment.getMeetingPlace()                        );
            preparedStatement.setTimestamp(6, this.dateToSqlTimestamp(appointment.getLastUpdated()));
            preparedStatement.setInt      (7, appointment.getOwner().getId()                       );
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


    private void updateAppointment(Appointment appointment) {
        String query;
        query =  "UPDATE appointments ";
        query += "SET name = '?', description = '?', start_date = '?', end_date = '?', ";
        query += "place = '?', last_updated = '?', owner_id = '?') ";
        query += "WHERE id = '?'";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString   (1, appointment.getTitle()                               );
            preparedStatement.setString   (2, appointment.getDescription()                         );
            preparedStatement.setTimestamp(3, this.dateToSqlTimestamp(appointment.getStart())      );
            preparedStatement.setTimestamp(4, this.dateToSqlTimestamp(appointment.getEnd())        );
            preparedStatement.setString   (5, appointment.getMeetingPlace()                        );
            preparedStatement.setTimestamp(6, this.dateToSqlTimestamp(appointment.getLastUpdated()));
            preparedStatement.setInt      (7, appointment.getOwner().getId()                       );
            preparedStatement.setInt      (8, appointment.getId()                                  );
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }

        if(appointment.getRoom() != null) {
            this.insertMeetingRoomReservation(appointment.getRoom(), appointment.getId());
        }
    }

    /**
     *
     * @param meetingRoom meeting room to insert into db
     * @return id of the meeting room in the database; if exception happens returns -1
     */
    public int insertMeetingRoom(MeetingRoom meetingRoom) {
        if(meetingRoom.getId() != -1) {
            this.updateMeetingRoom(meetingRoom);
            return meetingRoom.getId();
        }
        int newMeetingRoomId = -1;

        String query;
        query =  "INSERT INTO meeting_rooms ";
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

    private void updateMeetingRoom(MeetingRoom meetingRoom) {
        String query;
        query =  "UPDATE meeting_rooms ";
        query += "SET capacity = '?' ";
        query += "WHERE id = '?'";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, meetingRoom.getRoomSize());
            preparedStatement.setInt(2, meetingRoom.getId()      );
            preparedStatement.executeUpdate();

            preparedStatement.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    private java.sql.Timestamp dateToSqlTimestamp(java.util.Date date) {
        return new java.sql.Timestamp(date.getTime());
    }

    public ArrayList<Alarm> getAlarms() {
        ArrayList<Alarm> alarms = new ArrayList<Alarm>();
        String query = "SELECT * FROM alarms;";
        ResultSet results = this.query(query);
        if(results == null) {
            return null;
        }
        try {
            while(results.next()) {
                try {
                    int userid = results.getInt("user_id");
                    int appointmentid = results.getInt("appointment_id");
                    java.sql.Timestamp date = results.getTimestamp("time");
                    java.util.Date date_ = new java.util.Date(date.getTime());

                    alarms.add(new Alarm(userid, appointmentid, date_));

                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return alarms;
    }

    public ResultSet query(String query) {
        try {
            Statement statement = this.connection.createStatement();

            return statement.executeQuery(query);
        } catch(SQLException e) {
            e.printStackTrace();
            return null;
        }
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
        /*anders.setId(database.insertUser(anders));
        meetingRoom.setId(database.insertMeetingRoom(meetingRoom));
        appointment.setId(database.insertAppointment(appointment));
        database.insertAttendant(attendant);           */
        ArrayList<Alarm> alarms = database.getAlarms();
        for(int i = 0; i < alarms.size(); i++) {
            Alarm alarm = alarms.get(i);
            System.out.println(alarm.getDate().toString() + Integer.toString(alarm.getUserid()) + Integer.toString(alarm.getAppointmentId()));
        }

        database.close();
    }
}
