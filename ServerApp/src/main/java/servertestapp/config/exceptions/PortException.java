package servertestapp.config.exceptions;

public class PortException extends Exception{

    public PortException() {
        super("Invalid port. Port have to be in 1024—49151");
    }
    
}
