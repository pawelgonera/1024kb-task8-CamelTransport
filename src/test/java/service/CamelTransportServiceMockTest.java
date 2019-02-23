package service;

import org.hamcrest.Matcher;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl._1024kb.njd.poul12.task08.api.CamelTransportService;
import pl._1024kb.njd.poul12.task08.api.HttpConnectFactory;
import pl._1024kb.njd.poul12.task08.api.HttpConnection;
import pl._1024kb.njd.poul12.task08.exception.NotFoundDesiredJsonDataException;
import pl._1024kb.njd.poul12.task08.service.CamelTransportServiceImpl;
import pl._1024kb.njd.poul12.task08.service.QueryFactory;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

public class CamelTransportServiceMockTest
{
    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private HttpConnectFactory factoryMock;

    @Mock
    private HttpConnection connectionMock;

    private CamelTransportServiceImpl camelTransportApi;

    @Mock
    private CamelTransportService service;

    @Mock
    private QueryFactory queryFactory;

    @Before
    public void setUp()
    {
        when(factoryMock.getConnection(anyString())).thenReturn(connectionMock);

        camelTransportApi = new CamelTransportServiceImpl(factoryMock, queryFactory);
    }

    @Test
    public void testNotFoundDesiredJsonDataException()
    {
        when(service.getCamelByName(anyString())).thenThrow(new NotFoundDesiredJsonDataException("error message"));

        Assertions.assertThrows(NotFoundDesiredJsonDataException.class, () -> service.getCamelByName("Pablito"));
    }


}
