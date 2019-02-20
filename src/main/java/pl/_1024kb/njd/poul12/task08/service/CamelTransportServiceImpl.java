package pl._1024kb.njd.poul12.task08.service;

import pl._1024kb.njd.poul12.task08.api.CamelTransportService;
import pl._1024kb.njd.poul12.task08.api.HttpConnectFactory;
import pl._1024kb.njd.poul12.task08.entity.Camel;
import pl._1024kb.njd.poul12.task08.entity.CamelRide;
import pl._1024kb.njd.poul12.task08.entity.City;
import pl._1024kb.njd.poul12.task08.exception.NotFoundDesiredJsonDataException;
import pl._1024kb.njd.poul12.task08.util.JsonData;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CamelTransportServiceImpl implements CamelTransportService
{
    private static String CAMEL_REQUEST_QUERY = "http://localhost:8090/camel/";
    private static String CITY_REQUEST = "http://localhost:8090/city/";
    private static String CAMELRIDE_REQUEST_QUERY = "http://localhost:8090/camelRide/";
    private HttpConnectFactory connectFactory;
    private Jsonb jsonb;
    private JsonData jsonData;

    private CamelTransportServiceImpl(HttpConnectFactory connectFactory, Jsonb jsonb, JsonData jsonData)
    {
        this.connectFactory = connectFactory;
        this.jsonb = jsonb;
        this.jsonData = jsonData;
    }

    public CamelTransportServiceImpl()
    {
        this(new HttpConnectFactory(), JsonbBuilder.create(), new JsonData());

    }

    @SuppressWarnings("unchecked")
    @Override
    public List<CamelRide> getAllCamelRides()
    {
        return (List<CamelRide>) jsonData.parseJsonToList(connectFactory, CAMELRIDE_REQUEST_QUERY, CamelRide.class);
    }

    @Override
    public List<CamelRide> getCamelRidesByFromCity(String fromCityName)
    {
        return getAllCamelRides().stream()
                            .filter(camelRide -> camelRide.getFrom().getName().equals(fromCityName))
                            .collect(Collectors.toList());
    }

    @Override
    public List<CamelRide> getCamelRidesByCityDestionation(String destinationCityName)
    {
        return getAllCamelRides().stream()
                            .filter(camelRide -> camelRide.getDestination().getName().equals(destinationCityName))
                            .collect(Collectors.toList());
    }

    @Override
    public List<CamelRide> getCamelRidesByCamel(Long camelId)
    {
        return getAllCamelRides().stream()
                            .filter(camelRide -> camelRide.getCamel().getId().equals(camelId))
                            .collect(Collectors.toList());
    }

    @Override
    public List<CamelRide> getCamelRidesByDepartureDateRange(Date startDepartureDate, Date endDepartureDate)
    {
        return getAllCamelRides().stream()
                        .filter(camelRide -> camelRide.getDepartureDate().after(startDepartureDate) &&
                                             camelRide.getDepartureDate().before(endDepartureDate))
                        .collect(Collectors.toList());
    }

    @Override
    public List<CamelRide> getCamelRidesByDepartureDate(Date departureDate)
    {
        return getAllCamelRides().stream()
                                 .filter(camelRide -> camelRide.getDepartureDate().compareTo(departureDate) == 0)
                                 .collect(Collectors.toList());
    }

    @Override
    public List<CamelRide> getCamelRidesByFromCityAndCamelCapacity(String fromCityName, double capacity)
    {
        return getAllCamelRides().stream()
                                 .filter(camelRide -> camelRide.getFrom().getName().equals(fromCityName))
                                 .filter(camelRide -> camelRide.getCamel().getCapacity() > capacity)
                                 .collect(Collectors.toList());
    }

    @Override
    public List<City> getCitiesWhereCamelHadBeen(Long camelId)
    {
        return getAllCamelRides().stream()
                                 .filter(camelRide -> camelRide.getCamel().getId().equals(camelId))
                                .map(CamelRide::getDestination)
                                .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<City> getCitiesByCountryName(String countryName)
    {
        return (List<City>) jsonData.parseJsonToList(connectFactory, String.format(CITY_REQUEST + "by?country=%s", countryName), City.class);
    }

    @SuppressWarnings("unchecked")
    private List<Camel> getAllCamels()
    {
        return (List<Camel>) jsonData.parseJsonToList(connectFactory, CAMEL_REQUEST_QUERY, Camel.class);
    }

    @Override
    public List<Camel> getCamelsOlderThan(int age)
    {
        return getAllCamels().stream()
                             .filter(camel -> camel.getAge() > age)
                             .collect(Collectors.toList());
    }

    @Override
    public List<Camel> getCamelsYoungerThan(int age)
    {
        return getAllCamels().stream()
                             .filter(camel -> camel.getAge() < age)
                             .collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Camel> getCamelsByGender(String gender)
    {
        return (List<Camel>) jsonData.parseJsonToList(connectFactory, CAMEL_REQUEST_QUERY + String.format("by?gender=%s", gender.toLowerCase()), Camel.class);
    }

    @Override
    public Camel getCamelByName(String name)
    {
        return getAllCamels().stream()
                             .filter(camel -> camel.getName().equals(name))
                             .findFirst()
                             .orElseThrow(this::newRunTimeException);

    }

    @Override
    public Duration getCamelWorkingDuration(Long camelId)
    {
        List<CamelRide> camelRideList = getAllCamelRides().stream()
                                                .filter(camelRide -> camelRide.getCamel().getId().equals(camelId))
                                                .collect(Collectors.toList());

        Duration duration = Duration.ZERO;
        for(CamelRide camelRide : camelRideList)
        {
            Duration previousDuration = Duration.between(camelRide.getDepartureDate().toInstant(), camelRide.getArrivalDate().toInstant());
            duration = duration.plus(previousDuration);
        }

        return duration;
    }

    private NotFoundDesiredJsonDataException newRunTimeException()
    {
        return new NotFoundDesiredJsonDataException("Not found any desired value");
    }
}
