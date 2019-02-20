package pl._1024kb.njd.poul12.task08.exception;

public class WrongServerStatusException extends RuntimeException
{
    public WrongServerStatusException(String message)
    {
        super(message);
    }

    public WrongServerStatusException(Throwable cause)
    {
        super(cause);
    }
}
