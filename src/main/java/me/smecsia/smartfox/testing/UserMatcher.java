package me.smecsia.smartfox.testing;

import com.smartfoxserver.v2.entities.User;
import org.mockito.internal.matchers.Equals;

/**
 * @author Ilya Sadykov
 *         Date: 05.10.12
 *         Time: 16:28
 */
public class UserMatcher extends Equals {
    public UserMatcher(Object wanted) {
        super(wanted);
    }

    public boolean matches(Object actual) {
        if (!(actual instanceof User)) {
            return false;
        }
        User actualUser = (User) actual;
        User wanted = (User) getWanted();

        return Integer.valueOf(actualUser.getId()).equals(wanted.getId());
    }

}
