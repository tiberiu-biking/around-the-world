package master.pam.server.api.request;

import master.pam.server.api.ServerActionsEnum;

import java.util.List;

public interface IServerRequest {

    ServerActionsEnum getAction();

    void setAction(ServerActionsEnum aActionName);

    /**
     * Adds a new parameter to the request with the specified value.
     */
    void addField(String aParamName, Object aParamValue);

    void addLong(String aParamName, Long aParamValue);

    Object getObject(String aParamName);

    String getString(String aParamName);

    Long getLong(String aParamName);

    <T> T getDto(Class<T> aClass);

    <T> T get(String aParamName, Class<T> aResultClass);

    <T> List<T> getDtoList(Class<T> aClass);
}
