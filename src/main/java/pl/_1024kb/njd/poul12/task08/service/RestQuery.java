package pl._1024kb.njd.poul12.task08.service;

public enum RestQuery
{
    CAMELS("http://localhost:8090/camel"),
    CAMELRIDES("http://localhost:8090/camelRide"),
    CITIES("http://localhost:8090/city"),
    CAMELS_BY_GENDER(CAMELS.getRequestUrl() + "/by?gender=%s"),
    CAMELS_BY_ID(CAMELS.getRequestUrl() + "/%d"),
    CAMELRIDES_BY_DESTINATIONID(CAMELRIDES.getRequestUrl() + "/destination/%d"),
    CAMELRIDES_BY_FROMID(CAMELRIDES.getRequestUrl() + "/from/%d"),
    CAMELRIDES_BY_ID(CAMELRIDES.getRequestUrl() + "/%d"),
    CITIES_BY_COUNTRY(CITIES.getRequestUrl() + "/by?country=%s");

    private String requestUrl;

    RestQuery(String requestUrl)
    {
        this.requestUrl = requestUrl;
    }

    public String getRequestUrl()
    {
        return requestUrl;
    }

}
