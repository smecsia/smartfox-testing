package me.smecsia.smartfox.testing;

import com.smartfoxserver.v2.entities.User;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.exceptions.IErrorCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ilya Sadykov
 *         Date: 05.10.12
 *         Time: 17:18
 */
public class Response extends Params {

    private String action = null;
    private Boolean withUdp = null;
    private User user = null;
    private User[] users = null;

    private Boolean shouldRaiseException = null;
    private Class<? extends Throwable> raisesException = null;
    private IErrorCode raisedExceptionCode = null;
    private List<String> errorParams = new ArrayList<String>();

    public Response(String action) {
        this.action = action;
    }

    public Response(Boolean shouldRaiseException) {
        this.shouldRaiseException = shouldRaiseException;
    }

    public Response(User[] users) {
        this.users = users;
    }

    public Response(User user) {
        this.user = user;
    }

    public Response(Class<? extends Throwable> raisesException, IErrorCode errorCode, String ... errorParams) {
        this.raisesException = raisesException;
        this.raisedExceptionCode = errorCode;
        this.errorParams.addAll(Arrays.asList(errorParams));
        shouldRaiseException = true;
    }

    public Response(Class<? extends Throwable> raisesException, IErrorCode errorCode) {
        this.raisesException = raisesException;
        this.raisedExceptionCode = errorCode;
        shouldRaiseException = true;
    }

    public Response(Class<? extends Throwable> raisesException) {
        this.raisesException = raisesException;
        shouldRaiseException = true;
    }

    public Response viaUDP() {
        withUdp = true;
        return this;
    }

    public Response raiseException(Class<? extends Throwable> raisesException) {
        this.raisesException = raisesException;
        return this;
    }

    public Response withErrorParam(String value){
        this.errorParams.add(value);
        return this;
    }

    public Response withNoUDP() {
        withUdp = false;
        return this;
    }

    public Response getResponse(String action) {
        this.action = action;
        return this;
    }

    public Response withAction(String action) {
        this.action = action;
        return this;
    }

    public Response withUsers(User[] user) {
        this.users = users;
        return this;
    }

    public Response withUser(User user) {
        this.user = user;
        return this;
    }

    public Response set(ISFSObject params) {
        this.params = params;
        return this;
    }

    public Response putSFSObject(String param, ISFSObject value) {
        super.putSFSObject(param, value);
        return this;
    }

    public Response putString(String param, String value) {
        super.putString(param, value);
        return this;
    }

    public Response putInt(String param, int value) {
        super.putInt(param, value);
        return this;
    }

    public Boolean getWithUdp() {
        return withUdp;
    }

    public String getAction() {
        return action;
    }

    public User getUser() {
        return user;
    }

    public User[] getUsers() {
        return users;
    }

    public Class<? extends Throwable> getRaisesException() {
        return raisesException;
    }

    public void setRaisesException(Class<? extends Throwable> raisesException) {
        this.raisesException = raisesException;
    }

    public IErrorCode getRaisedExceptionCode() {
        return raisedExceptionCode;
    }

    public void setRaisedExceptionCode(IErrorCode raisedExceptionCode) {
        this.raisedExceptionCode = raisedExceptionCode;
    }

    public List<String> getErrorParams() {
        return errorParams;
    }

    public boolean isMustNotRaiseException() {
        return shouldRaiseException != null && !shouldRaiseException;
    }

    public boolean isMustRaiseException() {
        return shouldRaiseException != null && shouldRaiseException;
    }

    public Boolean getShouldRaiseException() {
        return shouldRaiseException;
    }

    public void setShouldRaiseException(Boolean shouldRaiseException) {
        this.shouldRaiseException = shouldRaiseException;
    }
}
