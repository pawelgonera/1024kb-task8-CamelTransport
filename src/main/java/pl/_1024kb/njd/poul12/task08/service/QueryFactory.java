package pl._1024kb.njd.poul12.task08.service;

public class QueryFactory
{
    public String getQuery(RestQuery query)
    {
        return query.getRequestUrl();
    }

    public String getQuery(RestQuery query, String parameter)
    {
        return String.format(query.getRequestUrl(), parameter);
    }

    public String getQuery(RestQuery query, long parameter)
    {
        return String.format(query.getRequestUrl(), parameter);
    }

}
