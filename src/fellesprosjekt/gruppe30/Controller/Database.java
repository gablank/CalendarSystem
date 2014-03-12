package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Model.*;
import fellesprosjekt.gruppe30.Server;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import java.util.Calendar;


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
    public int insertUser(InternalUser user) {
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

    public void updateUser(InternalUser user) {
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
     * @param alarm object to insert into (or update) db
     */
    private void insertAlarm(Alarm alarm) {
        String query;
        query =  "INSERT INTO alarms ";
        query += "(appointment_id, user_id, time) ";
        query += "VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt      (1, alarm.getAppointment().getId()          );
            preparedStatement.setInt      (2, alarm.getUser().getId()                 );
            preparedStatement.setTimestamp(3, this.dateToSqlTimestamp(alarm.getDate()));
            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch(SQLException e) {
            e.printStackTrace();
            System.out.println("Alarm (probably) already existed, updating instead of inserting...");
            this.updateAlarm(alarm.getUser().getId(), alarm.getAppointment().getId(), alarm.getDate());
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
            this.insertAlarm(new Alarm(
                    attendant.getUser(),
                    attendant.getAppointment(),
                    attendant.getAlarmClock())
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
            this.insertAlarm(new Alarm(
                    attendant.getUser(),
                    attendant.getAppointment(),
                    attendant.getAlarmClock())
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

    public boolean loadDatabase(Server server) {
        List<InternalUser> users;
        List<MeetingRoom> meetingRooms;
        List<Appointment> appointments;
        List<Alarm> alarms;
        List<Attendant> attendants;

        users = this.getUsers();
        meetingRooms = this.getMeetingRooms();
        appointments = this.getAppointments(users, meetingRooms);
        alarms = this.getAlarms(users, appointments);


        server.setUsers(users);
        server.setAppointments(appointments);
        server.setMeetingRooms(meetingRooms);
        server.setAlarms(alarms);

        return true;
    }

    public List<Alarm> getAlarms(List<InternalUser> users, List<Appointment> appointments) {
        List<Alarm> alarms = new ArrayList<Alarm>();
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

					InternalUser user = null;
                    for(int i = 0; i < users.size(); i++) {
                        if(users.get(i).getId() == userid) {
                            user = users.get(i);
                            break;
                        }
                    }
                    Appointment appointment = null;
                    for(int i = 0; i < appointments.size(); i++) {
                        if(appointments.get(i).getId() == appointmentid) {
                            appointment = appointments.get(i);
                            break;
                        }
                    }
                    if(user == null || appointment == null) {
                        System.out.println("Couldn't find user or appointment for alarm!");
                        return null;
                    }
                    alarms.add(new Alarm(user, appointment, date_));

                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return alarms;
    }

    public List<InternalUser> getUsers() {
        List<InternalUser> users = new ArrayList<InternalUser>();
        String query = "SELECT * FROM users;";
        ResultSet results = this.query(query);
        if(results == null) {
            return null;
        }
        try {
            while(results.next()) {
                try {
                    int userid = results.getInt("id");
                    String username = results.getString("username");
                    String password = results.getString("password");
                    String firstName = results.getString("first_name");
                    String lastName = results.getString("last_name");
                    String email = results.getString("email");

					InternalUser user = new InternalUser(firstName, lastName, username, email);
					user.setPassword(password);
                    user.setId(userid);
                    users.add(user);

                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<MeetingRoom> getMeetingRooms() {
        List<MeetingRoom> meetingRooms = new ArrayList<MeetingRoom>();
        String query = "SELECT * FROM meeting_rooms;";
        ResultSet results = this.query(query);
        if(results == null) {
            return null;
        }
        try {
            while(results.next()) {
                try {
                    int meeting_room_id = results.getInt("id");
                    int capacity = results.getInt("capacity");

                    MeetingRoom meetingRoom = new MeetingRoom(capacity);
                    meetingRoom.setId(meeting_room_id);

                    meetingRooms.add(meetingRoom);

                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return meetingRooms;
    }

    public List<Appointment> getAppointments(List<InternalUser> users,
                                                  List<MeetingRoom> meetingRooms) {
        List<Appointment> appointments = new ArrayList<Appointment>();
        String query = "SELECT * FROM appointments;";
        ResultSet results = this.query(query);
        if(results == null) {
            return null;
        }
        try {
            while(results.next()) {
                try {
                    int appointment_id = results.getInt("id");
                    String name = results.getString("name");
                    String description = results.getString("description");
                    java.sql.Timestamp startTimestamp = results.getTimestamp("start_date");
                    java.sql.Timestamp endTimestamp = results.getTimestamp("end_date");
                    String place = results.getString("place");
                    java.sql.Timestamp lastUpdatedTimestamp = results.getTimestamp("last_updated");
                    java.util.Date startDate = new java.util.Date(startTimestamp.getTime());
                    java.util.Date endDate = new java.util.Date(endTimestamp.getTime());
                    java.util.Date lastUpdatedDate = new java.util.Date(lastUpdatedTimestamp.getTime());

                    int ownerId = results.getInt("owner_id");

					InternalUser owner = null;
                    for(int i = 0; i < users.size(); i++) {
                        if(users.get(i).getId() == ownerId) {
                            owner = users.get(i);
                            break;
                        }
                    }

                    if(owner == null) {
                        System.out.println("Error: the user with id " + Integer.toString(ownerId) + " does not exist!");
                    }

                    Appointment appointment = null;
                    if(place == null) {
                        int meetingRoomId = this.getMeetingRoomForAppointment(appointment_id);
                        for(int i = 0; i < meetingRooms.size(); i++) {
                            if(meetingRooms.get(i).getId() == meetingRoomId) {
                                appointment = new Appointment(owner, name, description, startDate, endDate, meetingRooms.get(i));
                                break;
                            }
                        }
                        if(appointment == null) {
                            System.out.println("Did not find meeting room for appointment " + Integer.toString(appointment_id));
                            appointment = new Appointment(owner, name, description, startDate, endDate, null, null);
                        }
                    } else {
                        appointment = new Appointment(owner, name, description, startDate, endDate, place);
                    }

                    List<Integer> attendantIds = this.getAttendantIds(appointment_id);
                    for(int i = 0; i < attendantIds.size(); i++) {
                        for(int j = 0; j < users.size(); j++) {
                            if(users.get(j).getId() == attendantIds.get(i)) {
                                Attendant newAttendant = new Attendant(users.get(j), appointment);
                                appointment.addAttendant(newAttendant);
                            }
                        }
                    }

                    appointment.setLastUpdated(lastUpdatedDate);
                    appointment.setId(appointment_id);
                    appointments.add(appointment);
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    private List<Integer> getAttendantIds(int appointment_id) {
        List<Integer> attendantIds = new ArrayList<Integer>();
        String query = "SELECT * FROM user_appointments WHERE appointment_id = ?;";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, appointment_id);
            ResultSet results = preparedStatement.executeQuery();

            if(results == null) {
                return null;
            }
            while(results.next()) {
                try {
                    int userId = results.getInt("user_id");
                    attendantIds.add(new Integer(userId));
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return attendantIds;
    }

    private int getMeetingRoomForAppointment(int appointment_id) {
        String query = "SELECT * FROM meeting_room_reservations;";
        ResultSet results = this.query(query);
        if(results == null) {
            return -1;
        }
        try {
            while(results.next()) {
                try {
                    int meeting_room_id = results.getInt("meeting_room_id");
                    if(results.getInt("appointment_id") == appointment_id) {
                        return meeting_room_id;
                    }
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return -1;
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
		InternalUser anders = new InternalUser("Anders", "Wenhaug", "andersw", "anders@wenhaug.no");
		anders.setPassword("password");
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
        database.close();
    }
}
