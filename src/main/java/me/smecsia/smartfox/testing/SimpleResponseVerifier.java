package me.smecsia.smartfox.testing;

import com.smartfoxserver.v2.exceptions.IErrorCode;
import com.smartfoxserver.v2.exceptions.SFSException;
import com.smartfoxserver.v2.extensions.BaseClientRequestHandler;
import com.smartfoxserver.v2.extensions.BaseServerEventHandler;
import junit.framework.AssertionFailedError;

import java.util.Arrays;
import java.util.List;

import static me.smecsia.smartfox.testing.MatchersFacade.eq;
import static me.smecsia.smartfox.tools.util.ExceptionUtil.formatStackTrace;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;

/**
 * @author Ilya Sadykov
 *         Date: 05.10.12
 *         Time: 16:47
 */
public class SimpleResponseVerifier implements ResponseVerifier {
    protected Response response;

    public SimpleResponseVerifier(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    @Override
    public void doVerifyException(Throwable e) throws AssertionError {
        if (response.isMustRaiseException()) {
            boolean isGoalAchieved = true;
            IErrorCode errorCode = getResponse().getRaisedExceptionCode();
            List<String> errorParams = getResponse().getErrorParams();
            if (e != null && e.getClass().isAssignableFrom(getResponse().getRaisesException())) {
                if (e instanceof SFSException) {
                    SFSException sfsE = (SFSException) e;
                    IErrorCode gotCode = sfsE.getErrorData().getCode();
                    if (!errorCode.equals(gotCode) ||
                            (!errorParams.isEmpty() && !errorParams.equals(sfsE.getErrorData().getParams()))) {
                        isGoalAchieved = false;
                    }
                } else {
                    isGoalAchieved = false;
                }
            } else {
                isGoalAchieved = false;
            }
            if (!isGoalAchieved) {
                throw new AssertionFailedError(getResponse().getRaisesException() +
                        ("( errorCode = " + errorCode + ", errorParams = " + errorParams + ")") + " is " +
                        "expected  to be thrown, but was not. The following was thrown: " + e + ": \n" +
                        formatStackTrace(e));
            }
        } else if (response.isMustNotRaiseException() && e != null) {
            throw new AssertionFailedError("It is expected that there's no exception should be thrown, but there's" +
                    " one:" + (e.getMessage()) + ": \n" + formatStackTrace(e));
        }
    }

    @Override
    public void doVerifyAfter(Object handler) throws Exception {
        if (!BaseServerEventHandler.class.isAssignableFrom(handler.getClass()) &&
                !BaseClientRequestHandler.class.isAssignableFrom(handler.getClass())) {
            throw new RuntimeException("Cannot verify object of a class: " + handler.getClass() + ". It is not an " +
                    "instance of a handler!");
        }

        if (response.getWithUdp() == null) {
            if (response.getUsers() != null) {
                verifyPrivate(handler).
                        invoke("send", eq(response.getAction()), eq(response.get()), eq(Arrays.asList(response.getUsers())));
            } else if (response.getUser() != null) {
                verifyPrivate(handler).
                        invoke("send", eq(response.getAction()), eq(response.get()), eq(response.getUser()));
            }

        } else {
            if (response.getUsers() != null) {
                verifyPrivate(handler).
                        invoke("send", eq(response.getAction()), eq(response.get()), eq(Arrays.asList(response.getUsers())),
                                eq(response.getWithUdp()));
            } else if (response.getUser() != null) {
                verifyPrivate(handler).
                        invoke("send", eq(response.getAction()), eq(response.get()), eq(response.getUser()),
                                eq(response.getWithUdp()));
            }
        }
    }
}
