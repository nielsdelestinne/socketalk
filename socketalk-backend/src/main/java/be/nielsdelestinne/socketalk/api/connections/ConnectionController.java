package be.nielsdelestinne.socketalk.api.connections;

import be.nielsdelestinne.socketalk.domain.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class ConnectionController {

    private UserRepository userRepository;

    @Autowired
    public ConnectionController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @MessageMapping("/initial-connection")
    @SendTo("/topic/initial-connection-greeting")
    public ConnectionSuccessMessage initiallyConnected(@Payload ConnectionMessage connectionMessage, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        userRepository.addUnique(headerAccessor.getSessionId(), connectionMessage.getName());
        return new ConnectionSuccessMessage("Connected: "
                + HtmlUtils.htmlEscape(connectionMessage.getName())
                + " (" + userRepository.getSize() + " users connected)");
    }

}
