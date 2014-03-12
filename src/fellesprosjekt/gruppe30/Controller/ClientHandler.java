package fellesprosjekt.gruppe30.Controller;

import fellesprosjekt.gruppe30.Model.Appointment;
import fellesprosjekt.gruppe30.Model.InternalUser;
import fellesprosjekt.gruppe30.Model.MeetingRoom;
import fellesprosjekt.gruppe30.Model.User;
import fellesprosjekt.gruppe30.Server;
import org.json.JSONObject;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;


class ClientHandler extends Network {
    Server server;
    String username = null;

    public ClientHandler(Socket connectionSocket, Server server) {
        this.connectionSocket = connectionSocket;
        this.server = server;
        System.out.println("ClientHandler created.");
    }

    @Override
    protected void handleMessage(JSONObject message) throws IOException {
        if (message.has("type")) {
            String type = message.getString("type");
            String action = "";

            if(message.has("action"))
                action = message.getString("action");

            if(username == null && !type.equals("login")){
            	System.out.println("Recieved message to do something while not logged in, ignoring it. message: " + message.toString());
            	return;
            }

            switch (type) {
                case "login":
                    if(message.has("username") && message.has("password")){
                        String usernameReceived = message.getString("username");
                        // password is a sha256 hash (64 chars)
                        String password = message.getString("password");

                        JSONObject response = new JSONObject();
                        response.put("type", "login");
                        
                        if(this.server.verifyLogin(usernameReceived, password)) {
                            response.put("status", "success");
                            response.put("statusMessage", "OK");
                            response.put("username", usernameReceived);
                            username = usernameReceived;
                            System.out.println(username + " has logged in!");
                        } else {
                            response.put("status", "wrongCombination");
                            response.put("statusMessage", "The username and password combination was wrong!");
                            System.out.println("Someone tried logging in as " + username + ", but the password was wrong!");
                        }
                        
                        send(response);

                    }else{
                        System.out.println("a login message did not have the required fields: " + message.toString());
                    }

                    break;

                case "appointment":
                    if ((action == "new" || action == "change")
                            && message.has("id")
                            && message.has("title")
                            && message.has("description")
                            && message.has("start")
                            && message.has("end")
                            && message.has("meetingPlace")
                            && message.has("attendants")
                            && message.has("owner")
                            && message.has("meetingRoom")) {

                        String title         = message.getString("title");
                        String description   = message.getString("description");
                        long   start         = message.getLong("start");
                        long   end           = message.getLong("end");
                        String meetingPlace  = message.getString("meetingPlace");
                        String attendants    = message.getString("attendants");
                        int    ownerId       = message.getInt("owner");
                        int    meetingRoomId = message.getInt("meetingRoom");

                        

						InternalUser owner = server.getUserById(ownerId);

                        if(owner == null){
                            System.out.println("failed handling an appointment message, owner was not found. Message: " + message.toString());
                            return;
                        }

                        Date startDate = new Date(start);
                        Date endDate = new Date(end);

                        MeetingRoom meetingRoom = server.getMeetingRoomById(meetingRoomId);

                        //
                        // todo: parse, look up and add attendants
                        //

                        Appointment appointment = null;

                        if(meetingRoom == null && meetingPlace.isEmpty()){
                            System.out.println("failed handling an appointment message, no meetingPlace or valid meetingRoom was set. Message: " + message.toString());
                            return;

                        }else if(meetingRoom != null && !meetingPlace.isEmpty()){
                            System.out.println("failed handling an appointment message, both meetingPlace and meetingRoom were set. Message: " + message.toString());
                            return;

                        }else if(meetingRoom != null){
                            appointment = new Appointment(owner, title, description, startDate, endDate, meetingRoom);

                        }else{
                            appointment = new Appointment(owner, title, description, startDate, endDate, meetingPlace);

                        }

                        if(action == "change"){
                            int id = message.getInt("id");
                            appointment.setId(id);
                            if(server.getAppointmentById(id) == null){
                                System.out.println("failed handling an change appointment message, the appointment with specified id could not be found.");
                            }
                        }

                        server.insertAppointment(appointment);


                    } else if(action == "remove" && message.has("id")  ){
                        int    id           = message.getInt("id");


                        server.removeAppointment(id);

                    }else{
                        System.out.println("an appointment message did not have the required fields: " + message.toString());
                    }

                    break;

                case "meetingRoom":
                    if ((action == "new" || action == "change") && message.has("id") && message.has("roomSize")) {

                        int    id       = message.getInt("id");
                        String roomSize = message.getString("title");

                        //
                        // modify serverside model
                        //

                    } else if(action == "remove" && message.has("id")) {

                        //
                        // modify serverside model
                        //

                    } else {
                        System.out.println("a meetingRoom message did not have the required fields: " + message.toString());
                    }


                    break;

                case "logout":
                	
                	username = null;

                    break;

                default:
                    System.out.println("got a message of invalid type: " + message.toString());
            }

        } else {
            System.out.println("a message had no type field: " + message.toString());
        }
    }
}
