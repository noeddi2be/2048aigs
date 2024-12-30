package ch.fhnw.richards.aigsspringserver.user;

@SuppressWarnings("serial")
public class UserException extends RuntimeException {
	UserException(String message) {
		super(message);
	}
}
