package be.nielsdelestinne.socketalk.api.connections;

public class ConnectionSuccessMessage {

    private String content;

    public ConnectionSuccessMessage() {
    }

    public ConnectionSuccessMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
