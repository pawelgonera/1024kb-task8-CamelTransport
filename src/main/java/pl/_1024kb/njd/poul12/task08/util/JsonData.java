package pl._1024kb.njd.poul12.task08.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl._1024kb.njd.poul12.task08.api.HttpConnectFactory;
import pl._1024kb.njd.poul12.task08.api.HttpConnection;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class JsonData
{
    public List<?> parseJsonToList(HttpConnectFactory factory, String request, Class<?> entity)
    {
        List<?> entityList = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();
        try(HttpConnection connection = factory.getConnection(request))
        {
            String response = connection.connect();
            entityList = mapper.readValue(response, mapper.getTypeFactory().constructCollectionType(List.class, entity));

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return entityList;
    }
}
