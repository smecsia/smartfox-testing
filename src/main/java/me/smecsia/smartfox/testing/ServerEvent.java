package me.smecsia.smartfox.testing;

import com.smartfoxserver.bitswarm.sessions.ISession;
import com.smartfoxserver.bitswarm.sessions.Session;
import com.smartfoxserver.v2.core.*;
import com.smartfoxserver.v2.entities.data.SFSObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Ilya Sadykov
 *         Date: 09.10.12
 *         Time: 19:52
 */
public class ServerEvent {
    Map<ISFSEventParam, Object> params = new HashMap<ISFSEventParam, Object>();
    SFSEventType type;
    SFSObject loginInData = new SFSObject();
    SFSObject loginOutData = new SFSObject();
    ISession session = new Session();

    public ServerEvent(SFSEventType type) {
        this.type = type;
        if (type.equals(SFSEventType.USER_LOGIN)) {
            withLoginInOut();
        }
        withSession(session);
    }

    public ServerEvent withParam(ISFSEventParam param, Object value) {
        params.put(param, value);
        return this;
    }

    public ServerEvent withLoginInString(String param, String value) {
        loginInData.putUtfString(param, value);
        return this;
    }

    public ServerEvent withLoginInInt(String param, Integer value) {
        loginInData.putInt(param, value);
        return this;
    }

    public ServerEvent withLoginInBool(String param, Boolean value) {
        loginInData.putBool(param, value);
        return this;
    }

    public ServerEvent withLoginInOut() {
        params.put(SFSEventParam.LOGIN_IN_DATA, loginInData);
        params.put(SFSEventParam.LOGIN_OUT_DATA, loginOutData);
        return this;
    }


    public void withSession(ISession session) {
        this.session = session;
        params.put(SFSEventParam.SESSION, session);
    }

    public ISFSEvent get() {
        return new SFSEvent(type, params);
    }

}
