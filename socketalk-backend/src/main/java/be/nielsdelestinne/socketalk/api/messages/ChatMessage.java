package be.nielsdelestinne.socketalk.api.messages;

public class ChatMessage {

    private String text;

    public ChatMessage() {
    }

    public ChatMessage(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public ChatMessage withText(String text) {
        this.text = text;
        return this;
    }
}
