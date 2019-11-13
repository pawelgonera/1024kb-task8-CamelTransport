package pl._1024kb.njd.poul12.task08.service;

import pl._1024kb.njd.poul12.task08.api.CamelTransportService;
import pl._1024kb.njd.poul12.task08.api.HttpConnectFactory;
import pl._1024kb.njd.poul12.task08.entity.Camel;
import pl._1024kb.njd.poul12.task08.entity.CamelRide;
import pl._1024kb.njd.poul12.task08.entity.City;
import pl._1024kb.njd.poul12.task08.exception.NotFoundDesiredJsonDataException;
import pl._1024kb.njd.poul12.task08.util.JsonParser;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CamelTransportServiceImpl implements CamelTransportService
{
    private HttpConnectFactory connectFactory;
    private QueryFactory queryFactory;

    public CamelTransportServiceImpl(HttpConnectFactory connectFactory, QueryFactory queryFactory)
    {
        this.connectFactory = connectFactory;
        this.queryFactory = queryFactory;
    }

    public CamelTransportServiceImpl()
    {
        this(new HttpConnectFactory(), new QueryFactory());
    }

    @Override
    public List<CamelRide> getAllCamelRides()
    {
        String request = queryFactory.getQuery(RestQuery.CAMELRIDES);
        return new JsonParser<CamelRide>().parseJsonToList(connectFactory, request, CamelRide.class);
    }

    @Override
    public List<CamelRide> getCamelRidesByFromCity(String fromCityName)
    {
        return getAllCamelRides().stream()
                            .filter(camelRide -> camelRide.getFrom().getName().equals(fromCityName))
                            .collect(Collectors.toList());
    }

    @Override
    public List<CamelRide> getCamelRidesByCityDestination(String destinationCityName)
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

    @Override
    public List<City> getCitiesByCountryName(String countryName)
    {
        String request = queryFactory.getQuery(RestQuery.CITIES_BY_COUNTRY, countryName);
        return new JsonParser<City>().parseJsonToList(connectFactory, request, City.class);
    }

    private List<Camel> getAllCamels()
    {
        String request = queryFactory.getQuery(RestQuery.CAMELS);
        return new JsonParser<Camel>().parseJsonToList(connectFactory, request, Camel.class);
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

    @Override
    public List<Camel> getCamelsByGender(String gender)
    {
        String request = queryFactory.getQuery(RestQuery.CAMELS_BY_GENDER, gender);
        return new JsonParser<Camel>().parseJsonToList(connectFactory, request, Camel.class);
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
