package me.smecsia.smartfox.testing;

import com.smartfoxserver.bitswarm.sessions.Session;
import com.smartfoxserver.v2.api.ISFSApi;
import com.smartfoxserver.v2.entities.SFSUser;
import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;
import com.smartfoxserver.v2.exceptions.IErrorCode;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import org.junit.Before;
import org.powermock.api.mockito.PowerMockito;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static me.smecsia.smartfox.testing.MatchersFacade.any;
import static me.smecsia.smartfox.tools.util.ExceptionUtil.formatStackTrace;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyString;
import static org.powermock.api.mockito.PowerMockito.*;

/**
 * @author Ilya Sadykov
 *         Date: 09.10.12
 *         Time: 19:44
 */
public abstract class AbstractHandlerTest extends AbstractRoomTest {
    @Before
    public void prepareBasicMocks() {
        prepareBasicMocksIfRequired();
    }

    protected Response users(User[] users) {
        return new Response(users);
    }

    protected Response user(User user) {
        return new Response(user);
    }

    protected Response noExceptionRaised() {
        return new Response(false);
    }

    protected Response exceptionIsRaised(Class<? extends Throwable> exc) {
        return new Response(exc);
    }

    protected Response exceptionIsRaised(Class<? extends Throwable> exc, IErrorCode errorCode) {
        return new Response(exc, errorCode);
    }

    protected Response custom(ResponseVerifier verifier) {
        Response response = new Response(false);
        return response;
    }

    protected Params doRequest() {
        return new Params();
    }

    protected Params response() {
        return new Params();
    }

    /**
     * Verify output against input
     */
    public ResponseVerifier then(Response response) {
        return new SimpleResponseVerifier(response);
    }

    public ResponseVerifier then(ResponseVerifier verifier) {
        return verifier;
    }

    protected static synchronized <T> T initHandler(Class<T> clazz) {
        try {
            T spy = PowerMockito.spy(clazz.newInstance());
            // stubbing the "send" method
            doReturn(initApi()).when(spy, "getApi");
            doNothing().when(spy, "send", anyString(), any(ISFSObject.class, new SFSObject()),
                    any(User.class, new SFSUser(new Session())));
            doNothing().when(spy, "send", anyString(), any(ISFSObject.class, new SFSObject()),
                    any(List.class, new ArrayList<User>()));
            doNothing().when(spy, "send", anyString(), any(ISFSObject.class, new SFSObject()),
                    any(User.class, new SFSUser(new Session())), any(Boolean.TYPE, false));
            doNothing().when(spy, "send", anyString(), any(ISFSObject.class, new SFSObject()),
                    any(List.class, new ArrayList<User>()), any(Boolean.TYPE, false));
            return spy;
        } catch (Exception e) {
            fail("Cannot initialize test for handler: " + e.getMessage());
        }
        return null;
    }

    private static ISFSApi initApi() {
        return mock(ISFSApi.class);
    }

    @SuppressWarnings("unchecked")
    protected <T> T createHandler() {
        try {
            String className = getClass().getName();
            String handlerClass = className.substring(0, className.length() - 4);
            return createHandler((Class<T>) Class.forName(handlerClass));
        } catch (Exception e) {
            fail("Cannot create current request handler class:  " + e.getMessage() + ": \n " + formatStackTrace(e));
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    protected <T> T createHandler(Class<T> clazz) {
        try {
            if (clazz != null && (
                    BaseServerEventHandler.class.isAssignableFrom(clazz) ||
                            BaseClientRequestHandler.class.isAssignableFrom(clazz))
                    ) {
                T handler = initHandler(clazz);
                if (handler != null) {
                    prepareBasicMocksIfRequired();
                    doReturn(getParentExtension()).when(handler, "getParentExtension");
                    if (handler instanceof BaseServerEventHandler) {
                        Field field = BaseServerEventHandler.class.getDeclaredField("parentExtension");
                        field.setAccessible(true);
                        field.set(handler, getParentExtension());
                    }
                }
                return handler;
            }
            fail("Cannot find current client request handler class for:  " + getClass() +
                    " ( Tried to check class " + clazz + " ) ");
            return null;
        } catch (Exception e) {
            fail("Cannot create current request handler class:  " + e.getMessage() + ": \n " + formatStackTrace(e));
            return null;
        }
    }

    protected void checkExceptionThrown(Exception e, ResponseVerifier[] verifiers) {
        try {
            for (ResponseVerifier verifier : verifiers) {
                verifier.doVerifyException(e);
            }
        } catch (AssertionError error) {
            fail("Could not verify request and getResponse of a handler: " + ((e != null) ? e.getMessage() : e) + ": " +
                    "\n" + formatStackTrace(error));
        }
    }

    protected void checkVerifiers(Object handler, ResponseVerifier[] verifiers) throws Exception {
        for (ResponseVerifier verifier : verifiers) {
            verifier.doVerifyAfter(handler);
        }
    }
}
