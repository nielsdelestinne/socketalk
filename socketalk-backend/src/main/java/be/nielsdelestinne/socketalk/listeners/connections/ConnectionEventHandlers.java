package be.nielsdelestinne.socketalk.listeners.connections;

import be.nielsdelestinne.socketalk.domain.users.User;
import be.nielsdelestinne.socketalk.domain.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import static be.nielsdelestinne.socketalk.domain.users.User.UserBuilder.user;

@Component
public class ConnectionEventHandlers {

    private UserRepository userRepository;

    @Autowired
    public ConnectionEventHandlers(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener
    private void onSessionConnectedEvent(SessionConnectedEvent event) {
        StompHeaderAccessor stomHeaderAccessor = StompHeaderAccessor.wrap(event.getMessage());
        userRepository.add(createUserFromStompMessage(stomHeaderAccessor));
    }

    private User createUserFromStompMessage(StompHeaderAccessor sha) {
        return user()
                .withSessionId(sha.getSessionId())
                .build();
    }

}
