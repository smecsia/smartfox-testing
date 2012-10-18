package me.smecsia.smartfox.testing;

import com.smartfoxserver.v2.entities.data.ISFSArray;
import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSObject;

import java.util.Arrays;

/**
 * @author Ilya Sadykov
 *         Date: 05.10.12
 *         Time: 16:48
 */
public class Params {
    protected ISFSObject params = new SFSObject();

    public Params() {
    }

    public Params putSFSArray(String param, ISFSArray value) {
        params.putSFSArray(param, value);
        return this;
    }

    public Params putIntArray(String param, Integer... value) {
        params.putIntArray(param, Arrays.asList(value));
        return this;
    }

    public Params putSFSObject(String param, ISFSObject value) {
        params.putSFSObject(param, value);
        return this;
    }

    public Params putString(String param, String value) {
        params.putUtfString(param, value);
        return this;
    }

    public Params putInt(String param, int value) {
        params.putInt(param, value);
        return this;
    }

    public Params set(ISFSObject params) {
        this.params = params;
        return this;
    }

    public ISFSObject get() {
        return params;
    }
}
