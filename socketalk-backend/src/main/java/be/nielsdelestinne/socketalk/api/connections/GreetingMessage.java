package be.nielsdelestinne.socketalk.api.connections;

public class GreetingMessage {

    private String content;

    public GreetingMessage() {
    }

    public GreetingMessage(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
