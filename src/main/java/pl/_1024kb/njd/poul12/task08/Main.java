package pl._1024kb.njd.poul12.task08;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import pl._1024kb.njd.poul12.task08.entity.Camel;

public class Main
{
    @Autowired
    static RestTemplate restTemplate;

    public static void main(String[] args)
    {
       // RestTemplate rest = new RestTemplate();
        String request = "http://localhost:8090/camel/";
        Camel camel = restTemplate.getForObject(request, Camel.class);

        //System.out.println(camel.toString());
    }
}
