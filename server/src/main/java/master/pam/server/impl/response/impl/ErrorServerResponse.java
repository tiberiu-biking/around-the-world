package master.pam.server.impl.response.impl;

import master.pam.server.api.request.IServerRequest;
import master.pam.server.impl.exceptions.RequestException;
import master.pam.server.impl.response.base.AbstractResponse;
import master.pam.server.impl.response.base.envelope.IResponseEnvelope;

public class ErrorServerResponse extends AbstractResponse {

  private RequestException error;

  public ErrorServerResponse(IServerRequest aRequest, RequestException aException) {
    super(aRequest);
    error = aException;
  }

  @Override
  public void doRequest() {
  }

  @Override
  public void buildResponseEnvelope(IResponseEnvelope aResponseEnvelope) {
    aResponseEnvelope.setError(error);
  }

}
