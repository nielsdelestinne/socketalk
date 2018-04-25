package be.nielsdelestinne.socketalk.domain.users;

import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.newSetFromMap;

/**
 * Dummy Repository (stateful)
 */

@Repository
public class UserRepository {

    Set<User> connectedUsers;

    public UserRepository() {
        connectedUsers = newSetFromMap(new ConcurrentHashMap<User, Boolean>());
    }

    public void add(User user) {
        connectedUsers.add(user);
    }

    public void remove(User user) {
        connectedUsers.remove(user);
    }

    public int getSize() {
        return connectedUsers.size();
    }

    public Set<User> getAll() {
        return Collections.unmodifiableSet(connectedUsers);
    }

    public Optional<User> get(String sessionId) {
        return connectedUsers.stream()
                .filter(user -> user.getSessionId().equals(sessionId))
                .findFirst();
    }

}
