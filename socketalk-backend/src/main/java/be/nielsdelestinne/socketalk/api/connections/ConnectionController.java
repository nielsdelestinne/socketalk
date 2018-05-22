package be.nielsdelestinne.socketalk.api.connections;

import be.nielsdelestinne.socketalk.domain.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import static be.nielsdelestinne.socketalk.api.connections.ConnectionInformationMessage.connectionInformationMessage;
import static java.util.stream.Collectors.toList;
import static org.springframework.web.util.HtmlUtils.htmlEscape;

@Controller
public class ConnectionController {

    private UserRepository userRepository;

    @Autowired
    public ConnectionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @MessageMapping("/initial-connection")
    @SendTo("/topic/initial-connection-information")
    public ConnectionInformationMessage initiallyConnected(@Payload ConnectionMessage connectionMessage, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        userRepository.addUnique(headerAccessor.getSessionId(), connectionMessage.getName());
        return connectionInformationMessage()
                .withAmountOfConnectedUsers(userRepository.getSize())
                .withNamesOfConnectedUsers(userRepository.getAll().stream()
                        .map(user -> htmlEscape(user.getName()))
                        .collect(toList()));
    }

}
