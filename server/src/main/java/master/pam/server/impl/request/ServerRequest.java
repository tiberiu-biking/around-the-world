package master.pam.server.impl.request;

import master.pam.server.api.ServerActionsEnum;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.request.RequestConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerRequest implements IServerRequest {

    private final Logger logger = LoggerFactory.getLogger(ServerRequest.class);

    private ServerActionsEnum action;

    private Map<String, Object> fields;

    @Override
    public void setAction(ServerActionsEnum aAction) {
        action = aAction;
        fields = new HashMap<String, Object>();
    }

    @Override
    public ServerActionsEnum getAction() {
        return action;
    }

    @Override
    public void addField(String aParamName, Object aValue) {
        fields.put(aParamName, aValue);
    }

    @Override
    public Long getLong(String aParamName) {
        return get(aParamName, Long.class);
    }

    @Override
    public Object getObject(String aParamName) {
        return get(aParamName, Object.class);
    }

    @Override
    public String getString(String aParamName) {
        return get(aParamName, String.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String aParamName, Class<T> aResultClass) {
        Object result = fields.get(aParamName);
        try {
            return (T) result;
        } catch (ClassCastException e) {
            logger.error("Cannot convert field to " + aResultClass.getCanonicalName());
            // TODO
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T getDto(Class<T> aClass) {
        return get(RequestConstants.DTO, aClass);
    }

    @Override
    public <T> List<T> getDtoList(Class<T> aClass) {
        return get(RequestConstants.DTO_LIST, List.class);
    }

    public Map<String, Object> getFields() {
        return fields;
    }

    @Override
    public void addLong(String aParamName, Long aParamValue) {
        addField(aParamName, aParamValue);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ServerRequest [action=");
        builder.append(action);
        builder.append(", fields=");
        builder.append(fields);
        builder.append("]");
        return builder.toString();
    }

}
