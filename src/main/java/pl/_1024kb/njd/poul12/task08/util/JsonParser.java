package pl._1024kb.njd.poul12.task08.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import pl._1024kb.njd.poul12.task08.api.HttpConnectFactory;
import pl._1024kb.njd.poul12.task08.api.HttpConnection;
import pl._1024kb.njd.poul12.task08.exception.NotFoundDesiredJsonDataException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class JsonParser<T>
{
    public List<T> parseJsonToList(HttpConnectFactory factory, String request, Class<? extends T> model)
    {
        List<T> entityList = new LinkedList<>();
        ObjectMapper mapper = new ObjectMapper();
        try(HttpConnection connection = factory.getConnection(request))
        {
            String response = connection.connect();
            if(response == null || response.isEmpty())
                throw new NotFoundDesiredJsonDataException("Not found any desired value");

            entityList = mapper.readValue(response, mapper.getTypeFactory().constructCollectionType(List.class, model));

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return entityList;
    }
}
