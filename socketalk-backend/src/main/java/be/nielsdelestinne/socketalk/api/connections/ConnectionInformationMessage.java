package be.nielsdelestinne.socketalk.api.connections;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public class ConnectionInformationMessage {

    private List<String> namesOfConnectedUsers;
    private int amountOfConnectedUsers;

    private ConnectionInformationMessage() {
    }

    public static ConnectionInformationMessage connectionInformationMessage() {
        return new ConnectionInformationMessage();
    }

    public ConnectionInformationMessage withNamesOfConnectedUsers(List<String> namesOfConnectedUsers) {
        this.namesOfConnectedUsers = namesOfConnectedUsers;
        return this;
    }

    public ConnectionInformationMessage withAmountOfConnectedUsers(int amountOfConnectedUsers) {
        this.amountOfConnectedUsers = amountOfConnectedUsers;
        return this;
    }
}
