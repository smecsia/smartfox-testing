package me.smecsia.smartfox.testing.util;

import com.smartfoxserver.v2.exceptions.IErrorCode;
import com.smartfoxserver.v2.exceptions.SFSErrorCode;
import com.smartfoxserver.v2.exceptions.SFSErrorData;
import com.smartfoxserver.v2.exceptions.SFSLoginException;

/**
 * @author Ilya Sadykov
 * @version $Date$ $Revision$
 */
public class ExceptionUtil {

    public static String formatStackTrace(Throwable exc) {
        Throwable cause = exc;
        StringBuilder builder = new StringBuilder();
        while (cause != null) {
            builder.append("Caused by ").append(cause.toString()).append(": ").append(cause.getMessage()).
                    append("\n");
            builder.append(formatStackTrace(cause.getStackTrace()));
            cause = cause.getCause();
        }
        return builder.toString();
    }


    public static String formatStackTrace(StackTraceElement[] stackTraceElements) {
        StringBuilder builder = new StringBuilder();
        for (StackTraceElement element : stackTraceElements) {
            builder.append("at ").append(element.getClassName()).
                    append("<").append(element.getMethodName()).append(">");
            builder.append("(").append(element.getFileName());
            if (element.getLineNumber() > 0) {
                builder.append(":").append(element.getLineNumber());
            }
            builder.append(")");
            builder.append("\n");
        }
        return builder.toString();
    }

    public static void throwLoginError(IErrorCode code) throws SFSLoginException {
        // TODO: hack to support custom login errors
        // we're always sending LOGIN_BAD_USERNAME and passing the first param equals to our own error code
        SFSErrorData data = new SFSErrorData(SFSErrorCode.LOGIN_BAD_USERNAME);
        data.addParameter(code.toString());
        throw new SFSLoginException("{0}", data);
    }

    public static String describeErrorCode(int errorCode) {
        switch (errorCode) {
            case 0:
                return "NICKNAME_NOT_VALID";
            case 1:
                return "";
            case 2:
                return "JOIN_ROOM_ERROR";
            case 3:
                return "NICKNAME_NOT_AVAILABLE";
            case 4:
                return "NICKNAME_SET_ERROR";
            case 5:
                return "MESSAGE_CANNOT_BE_EMPTY";
            case 6:
                return "USER_NOT_FOUND";
            default:
                return "UNKNOWN_ERROR (code=" + errorCode + ")";
        }
    }

    public static String describeLoginError(IErrorCode errorCode) {
        switch (errorCode.getId()) {
            case 2:
                return "LOGIN_BAD_USERNAME";
            case 32001:
                return "LOGIN_TOKEN_INVALID";
            case 32002:
                return "LOGIN_TOKEN_EXPIRED";
            case 32003:
                return "LOGIN_TYPE_EMPTY";
            case 32004:
                return "LOGIN_TYPE_UNKNOWN";
            case 32005:
                return "LOGIN_USER_NOT_FOUND";
            case 32006:
                return "LOGIN_BAD_PASSWORD";
            case 32007:
                return "LOGIN_BAD_USERNAME";
            case 32008:
                return "FB_TOKEN_INVALID";
            case 32009:
                return "INTERNAL_ERROR";
            default:
                return "UNKNOWN_ERROR (code=" + errorCode.getId() + ")";
        }
    }
}
