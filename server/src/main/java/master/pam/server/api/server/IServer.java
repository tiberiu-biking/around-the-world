package master.pam.server.api.server;

import master.pam.server.api.request.IServerRequest;
import master.pam.server.impl.response.base.envelope.IResponseEnvelope;

public interface IServer {

    IServerRequest createRequest();

    IResponseEnvelope sendRequest(IServerRequest serverRequest);

    void startup();

    void shutdown();

}
