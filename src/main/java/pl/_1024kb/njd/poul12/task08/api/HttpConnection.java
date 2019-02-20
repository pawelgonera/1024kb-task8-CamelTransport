package pl._1024kb.njd.poul12.task08.api;

import pl._1024kb.njd.poul12.task08.exception.WrongServerStatusException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection implements AutoCloseable
{
    private HttpURLConnection connection;

    public HttpConnection(String url)
    {
        try
        {
            connection = (HttpURLConnection) new URL(url).openConnection();
            //connection.setRequestMethod("POST");
        }catch (IOException e)
        {
            throw new WrongServerStatusException(e);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public String connect()
    {
        serverResponseValidate();
        StringBuilder response = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())))
        {
            String line;
            while ((line = reader.readLine()) != null)
                response.append(line);

        }catch (IOException e)
        {
            throw new WrongServerStatusException(e);
        }

        return response.toString();
    }

    private void serverResponseValidate()
    {
        try
        {
            int responseCode = connection.getResponseCode();
            if(responseCode != 200)
            {
                String statusMessage = connection.getResponseMessage();
                throw new WrongServerStatusException("Problem with server = code: " + responseCode + " - " + statusMessage);
            }

        }catch (IOException e)
        {
            throw new WrongServerStatusException(e);
        }
    }

    @Override
    public void close()
    {
        connection.disconnect();
    }
}
