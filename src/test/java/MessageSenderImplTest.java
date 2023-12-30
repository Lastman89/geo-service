import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.geo.GeoServiceImpl;
import ru.netology.i18n.LocalizationService;
import ru.netology.i18n.LocalizationServiceImpl;
import ru.netology.sender.MessageSender;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderImplTest {

    //Проверка, что MessageSenderImpl всегда отправляет только русский текст, если ip относится к российскому сегменту адресов.
    @Test
    void test_get_MessageSender_Russia() {
        LocalizationServiceImpl localService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("172.0.32.11")).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));

        MessageSender messageSenderTest = new MessageSenderImpl(geoService, localService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("x-real-ip", "172.0.32.11");
        messageSenderTest.send(headers);


        GeoService geoServiceExp = new GeoServiceImpl();
        LocalizationService localizationService = new LocalizationServiceImpl();
        MessageSender messageSenderExp = new MessageSenderImpl(geoServiceExp, localizationService);

        Map<String, String> headersExp = new HashMap<String, String>();
        headersExp.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.0.32.11");
        messageSenderExp.send(headersExp);

        Assertions.assertEquals(messageSenderExp.send(headers), messageSenderTest.send(headersExp));

    }
//Проверка, что MessageSenderImpl всегда отправляет только английский текст, если ip относится к американскому сегменту адресов.
    @Test
    void test_get_MessageSender_USA() {
        LocalizationServiceImpl localService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localService.locale(Country.USA)).thenReturn("Welcome");

        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("96.0.32.11")).thenReturn(
                        new Location("New York", Country.USA, null,  0)
        );

        MessageSender messageSenderTest = new MessageSenderImpl(geoService, localService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("x-real-ip", "96.0.32.11");
        messageSenderTest.send(headers);

        GeoService geoServiceExp = new GeoServiceImpl();
        LocalizationService localizationService = new LocalizationServiceImpl();
        MessageSender messageSenderExp = new MessageSenderImpl(geoServiceExp, localizationService);

        Map<String, String> headersExp = new HashMap<String, String>();
        headersExp.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.0.32.11");
        messageSenderExp.send(headersExp);

        Assertions.assertEquals(messageSenderExp.send(headers), messageSenderTest.send(headersExp));
    }
// тесты для проверки возвращаемого текста и по ip
    @Test
    void test_get_MessageSender_Russia_with_ip_USA() {
        LocalizationServiceImpl localService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localService.locale(Country.USA)).thenReturn("Welcome");

        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("96.0.32.11")).thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));

        MessageSender messageSenderTest = new MessageSenderImpl(geoService, localService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("x-real-ip", "96.0.32.11");
        messageSenderTest.send(headers);


        GeoService geoServiceExp = new GeoServiceImpl();
        LocalizationService localizationService = new LocalizationServiceImpl();
        MessageSender messageSenderExp = new MessageSenderImpl(geoServiceExp, localizationService);

        Map<String, String> headersExp = new HashMap<String, String>();
        headersExp.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.0.32.11");
        messageSenderExp.send(headersExp);

        Assertions.assertEquals(messageSenderExp.send(headers), messageSenderTest.send(headersExp));
    }

    @Test
    void test_get_MessageSender_USA_with_ip_Russia() {
        LocalizationServiceImpl localService = Mockito.mock(LocalizationServiceImpl.class);
        Mockito.when(localService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        GeoServiceImpl geoService = Mockito.mock(GeoServiceImpl.class);
        Mockito.when(geoService.byIp("172.0.32.11")).thenReturn(new Location("New York", Country.USA, null,  0));

        MessageSender messageSenderTest = new MessageSenderImpl(geoService, localService);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("x-real-ip", "172.0.32.11");
        messageSenderTest.send(headers);


        GeoService geoServiceExp = new GeoServiceImpl();
        LocalizationService localizationService = new LocalizationServiceImpl();
        MessageSender messageSenderExp = new MessageSenderImpl(geoServiceExp, localizationService);

        Map<String, String> headersExp = new HashMap<String, String>();
        headersExp.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.0.32.11");
        messageSenderExp.send(headersExp);

        Assertions.assertEquals(messageSenderExp.send(headers), messageSenderTest.send(headersExp));
    }
}

