package master.pam.server.impl.exceptions;

public class RequestException extends Error {

  private static final long serialVersionUID = 2178080968620034475L;

  public RequestException(String aMessage) {
    super(aMessage);
  }

}
