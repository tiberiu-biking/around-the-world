package master.pam.world.servlet.exception;

import master.pam.server.impl.response.base.envelope.IResponseEnvelope;

public class WrongRequestException extends Exception {

    private static final long serialVersionUID = -4080590058274200724L;

    private IResponseEnvelope errorEnvelope;

    public IResponseEnvelope getErrorEnvelope() {
        return errorEnvelope;
    }

}
