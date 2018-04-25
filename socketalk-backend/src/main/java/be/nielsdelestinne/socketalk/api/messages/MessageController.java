package be.nielsdelestinne.socketalk.api.messages;

import be.nielsdelestinne.socketalk.domain.users.User;
import be.nielsdelestinne.socketalk.domain.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import java.util.Optional;

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
        Optional<User> userOptional = userRepository.get(headerAccessor.getSessionId());
        message.setText(userOptional.isPresent() ? userOptional.get().getName() + ": " + message.getText() : "UNKNOWN: " + message.getText());
        return message;
    }

}
