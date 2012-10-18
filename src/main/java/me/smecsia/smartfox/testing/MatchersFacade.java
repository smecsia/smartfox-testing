package me.smecsia.smartfox.testing;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import org.mockito.Matchers;

import static org.mockito.Matchers.argThat;

/**
 * @author Ilya Sadykov
 *         Date: 05.10.12
 *         Time: 16:48
 */
public class MatchersFacade {

    public static <T> T same(T value) {
        Matchers.same(value);
        return value;
    }

    public static User eq(User value) {
        argThat(new UserMatcher(value));
        return value;
    }

    public static ISFSObject eq(ISFSObject value) {
        argThat(new ISFSObjectMatcher(value));
        return value;
    }

    public static <T> T eq(T value) {
        Matchers.eq(value);
        return value;
    }

    public static <T> T any(Class<T> clazz, T value) {
        Matchers.any(clazz);
        return value;
    }
}
