package be.nielsdelestinne.socketalk.api.connections;

import be.nielsdelestinne.socketalk.domain.users.User;
import be.nielsdelestinne.socketalk.domain.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import java.util.Optional;

import static be.nielsdelestinne.socketalk.domain.users.User.UserBuilder.user;

@Controller
public class ConnectionController {

    private UserRepository userRepository;

    @Autowired
    public ConnectionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @MessageMapping("/initial-connection")
    @SendTo("/topic/initial-connection-greeting")
    public GreetingMessage initiallyConnected(@Payload ConnectionMessage connectionMessage, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        updateUserWithName(connectionMessage, headerAccessor);
        Optional<User> userOptional = userRepository.get(headerAccessor.getSessionId());
        return new GreetingMessage("Connected: "
                + HtmlUtils.htmlEscape(userOptional.isPresent() ? userOptional.get().getName() : "UNKNOWN")
                + " (" + userRepository.getSize() + " users connected)");
    }

    private void updateUserWithName(@Payload ConnectionMessage connectionMessage, SimpMessageHeaderAccessor headerAccessor) {
        userRepository.get(headerAccessor.getSessionId())
                .ifPresent(user -> {
                    userRepository.remove(user);
                    userRepository.add(user().withSessionId(user.getSessionId()).withName(connectionMessage.getName()).build());
                });
    }

}
