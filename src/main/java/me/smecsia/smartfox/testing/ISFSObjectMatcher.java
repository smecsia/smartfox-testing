package me.smecsia.smartfox.testing;

import com.smartfoxserver.v2.entities.data.ISFSObject;
import com.smartfoxserver.v2.entities.data.SFSDataWrapper;
import org.mockito.internal.matchers.Equals;

/**
 * @author Ilya Sadykov
 *         Date: 05.10.12
 *         Time: 16:28
 */
public class ISFSObjectMatcher extends Equals {
    public ISFSObjectMatcher(Object wanted) {
        super(wanted);
    }

    public boolean matches(Object actual) {
        if (!(actual instanceof ISFSObject)) {
            return false;
        }
        ISFSObject actualObj = (ISFSObject) actual;
        ISFSObject wanted = (ISFSObject) getWanted();
        for (String key : wanted.getKeys()) {

            SFSDataWrapper actualData = actualObj.get(key);
            SFSDataWrapper wantedData = wanted.get(key);
            if (actualData != null && wantedData != null) {
                final Boolean valuesEqual;
                if (actualData.getObject() instanceof ISFSObject) {
                    valuesEqual = new ISFSObjectMatcher(wantedData.getObject()).matches(actualData.getObject());
                } else {
                    valuesEqual = wantedData.getObject().equals(actualData.getObject());
                }
                if (!actualData.getTypeId().equals(wantedData.getTypeId()) || !valuesEqual) {
                    return false;
                }
            }
        }
        return true;
    }

}
