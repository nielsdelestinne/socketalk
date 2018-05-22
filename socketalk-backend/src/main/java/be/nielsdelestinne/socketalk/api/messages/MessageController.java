package be.nielsdelestinne.socketalk.api.messages;

import be.nielsdelestinne.socketalk.domain.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    private UserRepository userRepository;

    @Autowired
    public MessageController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @MessageMapping("/message")
    @SendTo("/topic/all-messages")
    public ChatMessage message(@Payload ChatMessage message, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        return message
                .withText(userRepository.get(headerAccessor.getSessionId())
                        .map(user -> user.getName() + ": " + message.getText())
                        .orElseGet(() -> "UNKNOWN: " + message.getText()));
    }

}
