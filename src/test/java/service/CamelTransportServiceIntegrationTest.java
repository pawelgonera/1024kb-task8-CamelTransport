package service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl._1024kb.njd.poul12.task08.api.CamelTransportService;
import pl._1024kb.njd.poul12.task08.entity.Camel;
import pl._1024kb.njd.poul12.task08.entity.CamelRide;
import pl._1024kb.njd.poul12.task08.entity.City;
import pl._1024kb.njd.poul12.task08.service.CamelTransportServiceImpl;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;

public class CamelTransportServiceIntegrationTest
{
    private CamelTransportService service;

    @Before
    public void setUp()
    {
        service = new CamelTransportServiceImpl();
    }

    @Test
    public void testGetAllCamelRides()
    {
        List camelRideList = service.getAllCamelRides();
        Assert.assertEquals(camelRideList.size(), 58);
    }

    @Test
    public void testGetCamelRidesByFromCity()
    {
        List<CamelRide> camelRideList = service.getCamelRidesByFromCity("Zakopane");
        Assert.assertEquals(camelRideList.size(), 2);
    }

    @Test
    public void testGetCamelRidesByCityDestionation()
    {
        List<CamelRide> camelRideList = service.getCamelRidesByCityDestination("Warszawa");
        Assert.assertEquals(camelRideList.size(), 2);
    }

    @Test
    public void testGetCamelRidesByCamel()
    {
        List<CamelRide> camelRideList = service.getCamelRidesByCamel(6L);
        Assert.assertEquals(camelRideList.size(), 4);
    }

    @Test
    public void testGetCamelRidesByDepartureDateRange()
    {
        Timestamp start = Timestamp.valueOf("2019-02-01 18:47:52.06");
        Timestamp end = Timestamp.valueOf("2019-02-03 18:47:52.06");

        List<CamelRide> camelRideList = service.getCamelRidesByDepartureDateRange(start, end);
        Assert.assertEquals(camelRideList.size(), 7);
    }

    @Test
    public void testGetCamelRidesByDepartureDate()
    {
        Timestamp ts = Timestamp.valueOf("2019-01-30 18:47:52.06");

        List<CamelRide> camelRideList = service.getCamelRidesByDepartureDate(ts);
        Assert.assertEquals(camelRideList.size(), 3);
    }

    @Test
    public void testGetCamelRidesByFromCityAndCamelCapacity()
    {
        List<CamelRide> camelRideList = service.getCamelRidesByFromCityAndCamelCapacity("Wrocław", 85.0);
        Assert.assertEquals(camelRideList.size(), 2);
    }

    @Test
    public void testGetCitiesWhereCamelHadBeen()
    {
        List<City> citiesList = service.getCitiesWhereCamelHadBeen(5L);
        Assert.assertEquals(citiesList.size(), 5);
    }

    @Test
    public void testGetCitiesByCountryName()
    {
        List<City> citiesList = service.getCitiesByCountryName("Poland");
        Assert.assertEquals(citiesList.size(), 13);
    }

    @Test
    public void testGetCamelsOlderThan()
    {
        List<Camel> camelList = service.getCamelsOlderThan(30);
        Assert.assertEquals(camelList.size(), 5);
    }

    @Test
    public void testGetCamelsYoungerThan()
    {
        List<Camel> camelList = service.getCamelsYoungerThan(20);
        Assert.assertEquals(camelList.size(), 3);
    }

    @Test
    public void testGetCamelsByGender()
    {
        List<Camel> camelList = service.getCamelsByGender("female");
        Assert.assertEquals(camelList.size(), 4);
    }

    @Test
    public void testGetCamelByName()
    {
        String camelName = "Pablo";
        Camel camel = service.getCamelByName(camelName);
        Assert.assertEquals(camel.getName(), camelName);
    }

    @Test
    public void testGetCamelWorkingDuration()
    {
        Duration duration = service.getCamelWorkingDuration(1L);
        Assert.assertEquals(duration.getSeconds(), 3101403);
    }

}
