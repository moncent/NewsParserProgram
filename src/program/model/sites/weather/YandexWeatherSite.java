package program.model.sites.weather;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class YandexWeatherSite {

    private static YandexWeatherSite yandexWeatherSite;
    private static List<String> weatherAlerts;
    private static List<String> weatherData;
    private String fullName = "Погода в Москве от Яндекс";

    private YandexWeatherSite(){}

    public static YandexWeatherSite getInstance() {
        if (yandexWeatherSite == null) {
            init();
        }
        return yandexWeatherSite;
    }

    private static void init() {
        weatherAlerts = new LinkedList<>();
        weatherData = new LinkedList<>();
        yandexWeatherSite = new YandexWeatherSite();
    }

    public List<String> getWeatherAlertsAsList() {
        return weatherAlerts;
    }

    public List<String> getWeatherDataAsList() {
        return weatherData;
    }

    public String getFullName() {
        return fullName;
    }

    public void commonParse() throws RuntimeException, IOException {
            Document establishedConnection = setServerConnection();
            parseEngine(establishedConnection);
    }

    private Document setServerConnection() throws IOException, RuntimeException {
        Connection connection = Jsoup.connect("https://yandex.ru/pogoda/moscow")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0")
                .timeout(5000);
        Document document = connection.get();
        checkStatusCode(connection);
        return document;
    }

    public void checkStatusCode(Connection connection) throws RuntimeException {
        int statusCode = connection.response().statusCode();
        throwExceptionByBadStatusCode(statusCode);
    }

    private void throwExceptionByBadStatusCode(int statusCode) {
        if (statusCode != 200) {
            throw new RuntimeException("ОШИБКА " + statusCode + ". УДАЛЁННЫЙ СЕРВЕР НЕ ОТВЕЧАЕТ. ПРОВЕРЬТЕ СОЕДИНЕНИЕ С СЕРВЕРОМ.");
        }
    }

    private void parseEngine(Document document) {
        addWeatherAlerts(document);
        addWeatherDatas(document);
    }

    private void addWeatherAlerts(Document document) {
        Elements alerts = document.body().getElementsByAttributeValueContaining("class", "default-alert");
        checkWeatherAlerts(alerts);
    }

    private void checkWeatherAlerts(Elements alerts) {
        if (alerts.size() > 0) {
            addRealAlerts(alerts);
        } else {
            weatherAlerts.add("Предупреждений нет.");
        }
    }

    private void addRealAlerts(Elements alerts) {
        for (int i = 0; i < alerts.size(); i++) {
            weatherAlerts.add("Текущее предупреждение №" + (i + 1) + ": " + alerts.text());
        }
    }

    private void addWeatherDatas(Document document){
        addCurrentIndications(document);
        addTomorrowIndications(document);
    }

    private void addCurrentIndications(Document document) {
        addCurrentTemperatureWeatherState(document);
        addCurrentWindHumidityPressure(document);
    }


    private void addCurrentTemperatureWeatherState(Document document) {
        addCurrentTemperature(document);
        addCurrentWeatherState(document);
    }

    private void addCurrentTemperature(Document document) {
        Element currentTemperature = document.getElementsByClass("temp__value").get(0);
        weatherData.add("Текущая температура: " + currentTemperature.text() + " \u00B0C");
    }

    private void addCurrentWeatherState(Document document) {
        Element currentWeatherState = document.getElementsByClass("link__feelings fact__feelings").get(0);
        weatherData.add("Текущее состояние погоды: " + currentWeatherState.text().split("Ощущается")[0]);
    }

    private void addCurrentWindHumidityPressure(Document document) {
        addCurrentWind(document);
        addCurrentHumidity(document);
        addCurrentPressure(document);
    }

    private void addCurrentWind(Document document) {
        Element currentWind = document.getElementsByClass("term__value").get(2);
        weatherData.add("Текущий ветер: " + currentWind.text());
    }

    private void addCurrentHumidity(Document document) {
        Element currentHumidity = document.getElementsByClass("term__value").get(3);
        weatherData.add("Текущая влажность: " + currentHumidity.text());
    }

    private void addCurrentPressure(Document document) {
        Element currentPressure = document.getElementsByClass("term__value").get(4);
        weatherData.add("Текущее давление: " + currentPressure.text());
    }

    private void addTomorrowIndications(Document document){
        addTomorrowTemperature(document);
        addTomorrowWeatherState(document);
    }

    private void addTomorrowTemperature(Document document) {
        Element tomorrowTemperature = document.getElementsByClass("temp__value").get(5);
        weatherData.add("Температура на завтра: " + tomorrowTemperature.text() + " \u00B0C");
    }

    private void addTomorrowWeatherState(Document document) {
        Element tomorrowWeatherState = document.getElementsByClass("forecast-briefly__condition").get(1);
        weatherData.add("Состояние погоды на завтра: " + tomorrowWeatherState.text());
    }

    public void clear() {
        weatherAlerts.clear();
        weatherData.clear();
    }
}