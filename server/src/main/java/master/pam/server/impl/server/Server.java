package master.pam.server.impl.server;

import master.pam.crud.impl.CrudImpl;
import master.pam.server.api.request.IServerRequest;
import master.pam.server.api.server.IServer;
import master.pam.server.impl.request.RequestFactory;
import master.pam.server.impl.request.ServerRequest;
import master.pam.server.impl.response.base.envelope.IResponseEnvelope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Server implements IServer {

    private final Logger log = LoggerFactory.getLogger(Server.class);

    @Override
    public IServerRequest createRequest() {
        return new ServerRequest();
    }

    @Override
    public IResponseEnvelope sendRequest(IServerRequest aServerRequest) {
        log.debug("Send request: " + aServerRequest);
        return RequestFactory.createResult(aServerRequest);

    }

    @Override
    public void startup() {
        CrudImpl.getInstance().startup();
    }

    @Override
    public void shutdown() {
        CrudImpl.getInstance().shutdown();
    }
}
