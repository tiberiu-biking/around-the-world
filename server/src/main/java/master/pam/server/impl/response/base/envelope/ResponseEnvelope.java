package master.pam.server.impl.response.base.envelope;

import master.pam.server.api.response.ResponseConstants;

import java.util.HashMap;
import java.util.Map;

public class ResponseEnvelope implements IResponseEnvelope {

    private Object error;
    private Object errors;
    private Map<String, Object> data;

    public ResponseEnvelope() {
        data = new HashMap<String, Object>();
    }

    @Override
    public Object getErrors() {
        return errors;
    }

    @Override
    public void setErrors(Object errors) {
        this.errors = errors;
    }

    @Override
    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public IResponseEnvelope addData(String aName, Object aData) {
        data.put(aName, aData);
        return this;
    }

    @Override
    public <T> T getData(String aName, Class<T> aClass) {
        return (T) data.get(aName);
    }

    @Override
    public Object getError() {
        return error;
    }

    @Override
    public void setError(Object error) {
        this.error = error;
    }

    @Override
    public void addDataMessage(String aMessage) {
        addData(ResponseConstants.MESSAGE, aMessage);
    }

    @Override
    public String getDataMessage() {
        return getData(ResponseConstants.MESSAGE, String.class);
    }

}
