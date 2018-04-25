package be.nielsdelestinne.socketalk.api.connections;

public class ConnectionMessage {

    private String name;

    public ConnectionMessage() {
    }

    public ConnectionMessage(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
