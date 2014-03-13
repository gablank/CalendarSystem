package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Model.*;
import fellesprosjekt.gruppe30.Server;
import fellesprosjekt.gruppe30.Utilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
	 * @param user the user to insert into the database
	 * @return true if success
	 */
	public boolean insertUser(User user) {
		String query;
		query = "INSERT INTO users ";
		query += "(email) ";
		query += "VALUES (?);";

		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch(SQLException e) {
			query = "UPDATE users ";
			query += "SET email = ? ";
			query += "WHERE email = ?;";

			try {
				PreparedStatement preparedStatement = this.connection.prepareStatement(query);
				preparedStatement.setString(1, user.getEmail());
				preparedStatement.setString(2, user.getEmail());
				preparedStatement.executeUpdate();

				preparedStatement.close();
			} catch(SQLException a) {
				a.printStackTrace();
				return false;
			}
		}

		if(user instanceof InternalUser) {
			return this.insertInternalUser((InternalUser) user);
		} else {
			return this.insertExternalUser((ExternalUser) user);
		}
	}

	private boolean insertInternalUser(InternalUser user) {
		String query;
		query = "INSERT INTO internal_users ";
		query += "(email, password, first_name, last_name) ";
		query += "VALUES (?, ?, ?, ?);";

		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getFirstName());
			preparedStatement.setString(4, user.getLastName());
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch(SQLException e) {
			return this.updateInternalUser(user);
		}

		return true;
	}

	private boolean updateInternalUser(InternalUser user) {
		String query;
		query = "UPDATE internal_users ";
		query += "SET email = ?, password = ?, first_name = ?, last_name = ? ";
		query += "WHERE email = ?;";

		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getPassword());
			preparedStatement.setString(3, user.getFirstName());
			preparedStatement.setString(4, user.getLastName());
			preparedStatement.setString(5, user.getEmail());
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private boolean insertExternalUser(ExternalUser user) {
		String query;
		query = "INSERT INTO external_users ";
		query += "(email) ";
		query += "VALUES (?);";

		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch(SQLException e) {
			return this.updateExternalUser(user);
		}

		return true;
	}

	private boolean updateExternalUser(ExternalUser user) {
		String query;
		query = "UPDATE external_users ";
		query += "SET email = ? ";
		query += "WHERE email = ?;";

		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * @param alarm object to insert into (or update) db
	 */
	private boolean insertAlarm(Alarm alarm) {
		this.insertUser(alarm.getUser());
		String query;
		query = "INSERT INTO alarms ";
		query += "(appointment_id, user_email, time) ";
		query += "VALUES (?, ?, ?);";

		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setInt(1, alarm.getAppointment().getId());
			preparedStatement.setString(2, alarm.getUser().getEmail());
			preparedStatement.setTimestamp(3, this.dateToSqlTimestamp(alarm.getDate()));
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch(SQLException e) {
			return this.updateAlarm(alarm);
		}
		return true;
	}

	private boolean updateAlarm(Alarm alarm) {
		String query;
		query = "UPDATE alarms ";
		query += "SET time = ? ";
		query += "WHERE appointment_id = ? AND user_email = ?;";

		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setTimestamp(1, this.dateToSqlTimestamp(alarm.getDate()));
			preparedStatement.setInt      (2, alarm.getAppointment().getId()          );
			preparedStatement.setString   (3, alarm.getUser().getEmail()              );
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param meetingRoom the MeetingRoom to insert into the database
	 * @param appointment Appointment to insert into the database
	 */
	private boolean insertMeetingRoomReservation(MeetingRoom meetingRoom, Appointment appointment) {
		String query;
		query = "INSERT INTO meeting_room_reservations ";
		query += "(meeting_room_id, appointment_id) ";
		query += "VALUES (?, ?);";

		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setInt(1, meetingRoom.getId());
			preparedStatement.setInt(2, appointment.getId());
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param attendant attendant to insert into db
	 * @return true if success
	 */
	private boolean insertAttendant(Attendant attendant) {
		this.insertUser(attendant.getUser());
		String query;
		query = "INSERT INTO attendants ";
		query += "(user_email, appointment_id, status) ";
		query += "VALUES (?, ?, ?);";

		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, attendant.getUser().getEmail());
			preparedStatement.setInt(2, attendant.getAppointment().getId());
			preparedStatement.setInt(3, attendant.getStatus());
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch(SQLException e) {
			query = "UPDATE attendants ";
			query += "SET status = ? ";
			query += "WHERE user_email = ? AND appointment_id = ?;";

			try {
				PreparedStatement preparedStatement = this.connection.prepareStatement(query);
				preparedStatement.setInt(1, attendant.getStatus());
				preparedStatement.setString(2, attendant.getUser().getEmail());
				preparedStatement.setInt(3, attendant.getAppointment().getId());
				preparedStatement.executeUpdate();

				preparedStatement.close();
			} catch(SQLException a) {
				a.printStackTrace();
				return false;
			}
		}

		if(attendant instanceof InternalAttendant) {
			return this.insertInternalAttendant((InternalAttendant) attendant);
		} else {
			return this.insertExternalAttendant((ExternalAttendant) attendant);
		}
	}

	/**
	 * @param attendant the InternalAttendant to insert into the database
	 * @return true if success
	 */
	private boolean insertInternalAttendant(InternalAttendant attendant) {
		String query;
		query = "INSERT INTO internal_attendants ";
		query += "(user_email, appointment_id, last_checked, is_visible) ";
		query += "VALUES (?, ?, ?, ?);";

		try {
			int isVisible = attendant.getVisibleOnCalendar() ? 1 : 0;

			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, attendant.getUser().getEmail());
			preparedStatement.setInt(2, attendant.getAppointment().getId());
			preparedStatement.setTimestamp(3, dateToSqlTimestamp(attendant.getLastChecked()));
			preparedStatement.setInt(4, isVisible);
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch(SQLException e) {
			return this.updateInternalAttendant(attendant);
		}
		return true;
	}

	private boolean updateInternalAttendant(InternalAttendant internalAttendant) {
		String query;
		query = "UPDATE internal_attendants ";
		query += "SET last_checked = ?, is_visible = ? ";
		query += "WHERE user_email = ? AND appointment_id = ?;";

		try {
			int isVisible = internalAttendant.getVisibleOnCalendar() ? 1 : 0;

			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setTimestamp(1, dateToSqlTimestamp(internalAttendant.getLastChecked()));
			preparedStatement.setInt(2, isVisible);
			preparedStatement.setString(3, internalAttendant.getUser().getEmail());
			preparedStatement.setInt(4, internalAttendant.getAppointment().getId());
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param attendant the ExternalAttendant to insert into the database
	 * @return true if success
	 */
	private boolean insertExternalAttendant(ExternalAttendant attendant) {
		String query;
		query = "INSERT INTO external_attendants ";
		query += "(user_email) ";
		query += "VALUES (?);";

		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, attendant.getUser().getEmail());
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch(SQLException e) {
			return this.updateExternalAttendant(attendant);
		}
		return true;
	}

	private boolean updateExternalAttendant(ExternalAttendant externalAttendant) {
		String query;
		query = "UPDATE external_attendants ";
		query += "SET user_email = ? ";
		query += "WHERE user_email = ?;";

		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, externalAttendant.getUser().getEmail());
			preparedStatement.setString(2, externalAttendant.getUser().getEmail());
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @param appointment the appointment to insert into the database
	 * @return the database id of the appointment; if an exception happened it returns -1
	 */
	public int insertAppointment(Appointment appointment) {
		if(appointment.getId() != -1) {
			this.updateAppointment(appointment);

			for(Attendant attendant : appointment.getAttendants()) {
				this.insertAttendant(attendant);
			}

			return appointment.getId();
		}
		int newAppointmentId = -1;

		String query;
		query = "INSERT INTO appointments ";
		query += "(title, description, start_date, end_date, place, last_updated, owner_email) ";
		query += "VALUES (?, ?, ?, ?, ?, ?, ?);";

		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, appointment.getTitle());
			preparedStatement.setString(2, appointment.getDescription());
			preparedStatement.setTimestamp(3, this.dateToSqlTimestamp(appointment.getStart()));
			preparedStatement.setTimestamp(4, this.dateToSqlTimestamp(appointment.getEnd()));
			preparedStatement.setString(5, appointment.getMeetingPlace());
			preparedStatement.setTimestamp(6, this.dateToSqlTimestamp(appointment.getLastUpdated()));
			preparedStatement.setString(7, appointment.getOwner().getEmail());
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

		appointment.setId(newAppointmentId);

		if(appointment.getMeetingRoom() != null) {
			this.insertMeetingRoomReservation(appointment.getMeetingRoom(), appointment);
		}

		for(Attendant attendant : appointment.getAttendants()) {
			this.insertAttendant(attendant);
		}

		return newAppointmentId;
	}

	private void updateAppointment(Appointment appointment) {
		String query;
		query = "UPDATE appointments ";
		query += "SET title = ?, description = ?, start_date = ?, end_date = ? ";
		query += "place = ?, last_updated = ?, owner_email = ? ";
		query += "WHERE id = ?;";

		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, appointment.getTitle());
			preparedStatement.setString(2, appointment.getDescription());
			preparedStatement.setTimestamp(3, this.dateToSqlTimestamp(appointment.getStart()));
			preparedStatement.setTimestamp(4, this.dateToSqlTimestamp(appointment.getEnd()));
			preparedStatement.setString(5, appointment.getMeetingPlace());
			preparedStatement.setTimestamp(6, this.dateToSqlTimestamp(appointment.getLastUpdated()));
			preparedStatement.setString(7, appointment.getOwner().getEmail());
			preparedStatement.setInt(8, appointment.getId());
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}

		if(appointment.getMeetingRoom() != null) {
			this.insertMeetingRoomReservation(appointment.getMeetingRoom(), appointment);
		}
	}

	/**
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
		query = "INSERT INTO meeting_rooms ";
		query += "(capacity) ";
		query += "VALUES (?);";

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
		query = "UPDATE meeting_rooms ";
		query += "SET capacity = ? ";
		query += "WHERE id = ?;";

		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setInt(1, meetingRoom.getRoomSize());
			preparedStatement.setInt(2, meetingRoom.getId());
			preparedStatement.executeUpdate();

			preparedStatement.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean deleteUser(User user) {
		String query = "DELETE FROM users WHERE email = ?;";
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.execute();
		} catch(SQLException e) {
			System.out.println("Could not delete user with email: " + user.getEmail());
			return false;
		}
		return true;
	}

	public boolean deleteMeetingRoom(MeetingRoom meetingRoom) {
		String query = "DELETE FROM meeting_rooms WHERE id = ?;";
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setInt(1, meetingRoom.getId());
			preparedStatement.execute();
		} catch(SQLException e) {
			System.out.println("Could not delete meeting room with id: " + meetingRoom.getId());
			return false;
		}
		return true;
	}

	public boolean deleteGroup(Group group) {
		String query = "DELETE FROM groups WHERE name = ?;";
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, group.getName());
			preparedStatement.execute();
		} catch(SQLException e) {
			System.out.println("Could not delete group with name: " + group.getName());
			return false;
		}
		return true;
	}

	public boolean deleteGroupMember(Group group, User user) {
		String query = "DELETE FROM group_members WHERE user_email = ? AND group_id = ?;";
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setInt   (2, group.getId()  );
			preparedStatement.execute();
		} catch(SQLException e) {
			System.out.println("Could not delete group member with email: " + user.getEmail());
			return false;
		}
		return true;
	}

	public boolean deleteAppointment(Appointment appointment) {
		String query = "DELETE FROM appointments WHERE id = ?;";
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setInt(1, appointment.getId());
			preparedStatement.execute();
		} catch(SQLException e) {
			System.out.println("Could not delete appointment with id: " + Integer.toString(appointment.getId()));
			return false;
		}
		return true;
	}

	private boolean deleteMeetingRoomReservation(Appointment appointment) {
		String query = "DELETE FROM meeting_room_reservations WHERE meeting_room_id = ? AND appointment_id = ?;";
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setInt(1, appointment.getMeetingRoom().getId());
			preparedStatement.setInt(2, appointment.getId()                 );
			preparedStatement.execute();
		} catch(SQLException e) {
			System.out.println("Could not delete meeting room reservation for appointment with id: " + Integer.toString(appointment.getId()));
			return false;
		}
		return true;
	}

	private boolean deleteAttendant(Attendant attendant) {
		String query = "DELETE FROM attendants WHERE user_email = ? AND appointment_id = ?;";
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, attendant.getUser().getEmail()    );
			preparedStatement.setInt   (2, attendant.getAppointment().getId());
			preparedStatement.execute();
		} catch(SQLException e) {
			System.out.println("Could not delete attendant with user_email: " + attendant.getUser().getEmail());
			return false;
		}
		return true;
	}

	public boolean deleteAlarm(Alarm alarm) {
		String query = "DELETE FROM alarms WHERE user_email = ? AND appointment_id = ?;";
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setString(1, alarm.getUser().getEmail()    );
			preparedStatement.setInt   (2, alarm.getAppointment().getId());
			preparedStatement.execute();
		} catch(SQLException e) {
			System.out.println("Could not delete alarm for user " + alarm.getUser().getEmail());
			return false;
		}
		return true;
	}

	private java.sql.Timestamp dateToSqlTimestamp(java.util.Date date) {
		return new java.sql.Timestamp(date.getTime());
	}

	public boolean loadDatabase(Server server) {
		List<User> users;
		List<MeetingRoom> meetingRooms;
		List<Appointment> appointments;
		List<Alarm> alarms;
		List<Group> groups;

		users = this.getUsers();
		meetingRooms = this.getMeetingRooms();
		appointments = this.getAppointments(users, meetingRooms);
		alarms = this.getAlarms(users, appointments);
		groups = this.getGroups(users);

		server.setUsers(users);
		server.setAppointments(appointments);
		server.setMeetingRooms(meetingRooms);
		server.setAlarms(alarms);
		server.setGroups(groups);

		return true;
	}

	private List<Group> getGroups(List<User> users) {
		List<Group> groups = new ArrayList<Group>();
		String query = "SELECT * FROM groups;";
		ResultSet results = this.query(query);
		if(results == null) {
			return groups;
		}
		try {
			while(results.next()) {
				try {
					int groupId = results.getInt("id");
					String name = results.getString("name");
					query = "SELECT * FROM group_members WHERE group_id = ?;";
					PreparedStatement preparedStatement = this.connection.prepareStatement(query);
					preparedStatement.setInt(1, groupId);
					ResultSet members = preparedStatement.executeQuery();

					Group newGroup = null;

					if(members != null) {
						newGroup = new Group(name);

						User newMember;
						String userEmail;
						while(members.next()) {
							userEmail = members.getString("user_email");
							newMember = Utilities.getUserByEmail(userEmail, users);
							newGroup.addMember(newMember);
						}
					}

					if(newGroup != null) {
						groups.add(newGroup);
					}

				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return groups;
	}

	public List<Alarm> getAlarms(List<User> users, List<Appointment> appointments) {
		List<Alarm> alarms = new ArrayList<Alarm>();
		String query = "SELECT * FROM alarms;";
		ResultSet results = this.query(query);
		if(results == null) {
			return alarms;
		}
		try {
			while(results.next()) {
				try {
					String userEmail = results.getString("user_email");
					int appointmentId = results.getInt("appointment_id");
					java.sql.Timestamp date = results.getTimestamp("time");
					java.util.Date date_ = new java.util.Date(date.getTime());

					InternalUser user = null;
					for(int i = 0; i < users.size(); i++) {
						if(users.get(i) instanceof InternalUser && users.get(i).getEmail().equals(userEmail)) {
							user = (InternalUser) users.get(i);
							break;
						}
					}
					Appointment appointment = null;
					for(int i = 0; i < appointments.size(); i++) {
						if(appointments.get(i).getId() == appointmentId) {
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

	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		String query;
		ResultSet results;

		// First get all internal users
		query = "SELECT * FROM internal_users;";
		results = this.query(query);
		if(results != null) {
			try {
				while(results.next()) {
					try {
						String email = results.getString("email");
						String password = results.getString("password");
						String firstName = results.getString("first_name");
						String lastName = results.getString("last_name");

						InternalUser user = new InternalUser(email, firstName, lastName);
						user.setPassword(password);
						users.add(user);
					} catch(SQLException e) {
						e.printStackTrace();
					}
				}
				results.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}

		// Then get all external users
		query = "SELECT * FROM external_users;";
		results = this.query(query);
		if(results != null) {
			try {
				while(results.next()) {
					try {
						String email = results.getString("email");

						ExternalUser user = new ExternalUser(email);
						users.add(user);
					} catch(SQLException e) {
						e.printStackTrace();
					}
				}
				results.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
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

	public List<Appointment> getAppointments(List<User> users, List<MeetingRoom> meetingRooms) {
		List<Appointment> appointments = new ArrayList<Appointment>();

		String query = "SELECT * FROM appointments;";
		ResultSet results = this.query(query);

		if(results == null) {
			return appointments;
		}
		try {
			while(results.next()) {
				try {
					int appointmentId = results.getInt("id");
					String title = results.getString("title");
					String description = results.getString("description");
					java.sql.Timestamp startTimestamp = results.getTimestamp("start_date");
					java.sql.Timestamp endTimestamp = results.getTimestamp("end_date");
					String place = results.getString("place");
					java.sql.Timestamp lastUpdatedTimestamp = results.getTimestamp("last_updated");
					java.util.Date startDate = new java.util.Date(startTimestamp.getTime());
					java.util.Date endDate = new java.util.Date(endTimestamp.getTime());
					java.util.Date lastUpdatedDate = new java.util.Date(lastUpdatedTimestamp.getTime());

					String ownerEmail = results.getString("owner_email");

					InternalUser owner = (InternalUser) Utilities.getUserByEmail(ownerEmail, users);

					if(owner == null) {
						System.out.println("Error: the user with id " + ownerEmail + " does not exist!");
						continue;
					}

					Appointment appointment = new Appointment(owner, title, description, startDate, endDate);
					appointment.setId(appointmentId);
					appointment.setLastUpdated(lastUpdatedDate);

					if(place.isEmpty()) {
						MeetingRoom meetingRoom = this.getMeetingRoomForAppointment(appointment, meetingRooms);

						if(meetingRoom == null) {
							System.out.println("Did not find meeting room for appointment " + Integer.toString(appointmentId));
						}
						appointment.setMeetingRoom(meetingRoom);
					} else {
						appointment.setMeetingPlace(place);
					}

					List<Attendant> attendants = this.getAttendants(appointment, users);
					appointment.setAttendants(attendants);
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

	private List<Attendant> getAttendants(Appointment appointment, List<User> users) {
		List<Attendant> attendants = new ArrayList<Attendant>();
		String query = "SELECT * FROM attendants WHERE appointment_id = ?;";
		try {
			PreparedStatement preparedStatement = this.connection.prepareStatement(query);
			preparedStatement.setInt(1, appointment.getId());
			ResultSet results = preparedStatement.executeQuery();

			if(results == null) {
				return null;
			}
			while(results.next()) {
				try {
					String userEmail = results.getString("user_email");
					User user = Utilities.getUserByEmail(userEmail, users);
					if(user instanceof InternalUser) {
						attendants.add(new InternalAttendant((InternalUser) user, appointment));
					} else {
						attendants.add(new ExternalAttendant((ExternalUser) user, appointment));
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return attendants;
	}

	private MeetingRoom getMeetingRoomForAppointment(Appointment appointment, List<MeetingRoom> meetingRooms) {
		String query = "SELECT * FROM meeting_room_reservations;";
		ResultSet results = this.query(query);
		if(results == null) {
			return null;
		}
		try {
			while(results.next()) {
				try {
					int meeting_room_id = results.getInt("meeting_room_id");
					if(results.getInt("appointment_id") == appointment.getId()) {
						return Utilities.getMeetingRoomById(meeting_room_id, meetingRooms);
					}
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
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

		/*
		Users
		 */
		InternalUser anders = new InternalUser("anders@wenhaug.no", "Anders", "Wenhaug");
		anders.setPassword("password");

		InternalUser easy = new InternalUser("", "Test", "User");
		easy.setPassword("");

		InternalUser emiljs = new InternalUser("emil.schroeder@gmail.com", "Emil Jakobus", "Schroeder");
		emiljs.setPassword("password");

		InternalUser emilh = new InternalUser("emil@heien.no", "Emil", "Heien");
		emilh.setPassword("password");

		ExternalUser espen = new ExternalUser("espstr@stud.ntnu.no");

		/*
		Meeting rooms
		 */
		MeetingRoom p15 = new MeetingRoom(88);

		/*
		Appointments
		 */
		Appointment studLan = new Appointment(anders, "StudLAN", "Gamings", new java.util.Date(), new java.util.Date(new java.util.Date().getTime() + 60 * 60 * 1000), p15);
		Appointment workWork = new Appointment(anders, "Work@rema1000", "Work work", new java.util.Date(), new java.util.Date(new java.util.Date().getTime() + 60 * 60 * 1000), "REMA 1000");

		/*
		Groups
		 */
		Group gamings = new Group("Gamings");
		gamings.addMember(anders);
		gamings.addMember(emiljs);

		Group espenGroup = new Group("Espen-group");
		espenGroup.addMember(espen);
		espenGroup.addMember(emilh);

		/*
		Add users to appointment
		 */
		studLan.addGroup(gamings);
		studLan.addUser(espen);

		workWork.addUser(emilh);

		/*
		Insert into database
		 */
		Database database = new Database();
		database.insertUser(anders);
		database.insertUser(emiljs);
		database.insertUser(emilh);
		database.insertUser(espen);
		database.insertUser(easy);
		p15.setId(database.insertMeetingRoom(p15));
		studLan.setId(database.insertAppointment(studLan));
		workWork.setId(database.insertAppointment(workWork));
		database.close();
	}
}