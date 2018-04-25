package be.nielsdelestinne.socketalk.domain.users;

import javax.annotation.Generated;
import java.util.Objects;

public class User {

    private String sessionId;
    private String name;

    private User(UserBuilder userBuilder) {
        sessionId = userBuilder.sessionId;
        name = userBuilder.name;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getName() {
        return name;
    }

    @Override
    @Generated(value = "java.utils.Object.equals")
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(sessionId, user.sessionId);
    }

    @Override
    @Generated(value = "java.utils.Object.hashCode")
    public int hashCode() {
        return Objects.hash(sessionId);
    }

    public static class UserBuilder {
        private String sessionId;
        private String name;

        public static UserBuilder user() {
            return new UserBuilder();
        }

        public User build() {
            return new User(this);
        }

        public UserBuilder withSessionId(String sessionId) {
            this.sessionId = sessionId;
            return this;
        }

        public UserBuilder withName(String name) {
            this.name = name;
            return this;
        }
    }

}
