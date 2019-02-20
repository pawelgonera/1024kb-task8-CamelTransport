package pl._1024kb.njd.poul12.task08.api;

public class HttpConnectFactory
{
    public HttpConnection getConnection(String url)
    {
        return new HttpConnection(url);
    }
}
