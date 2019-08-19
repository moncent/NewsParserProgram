package program.model.sites.news;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import program.model.sites.weather.YandexWeatherSite;
import program.view.ErrorMessageFrame;
import program.view.mainButtons.ButtonMainMenu;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseSite {
    protected String siteName;
    protected String prefix;
    protected ButtonMainMenu buttonMainMenu;
    protected int maxCountNews;
    protected Elements elements;

    public BaseSite(String prefix, String siteName, ButtonMainMenu buttonMainMenu, int maxCountNews) {
        initSite(prefix, siteName);
        initMainButtonsAndMaxCountNews(buttonMainMenu, maxCountNews);
    }

    private void initSite(String prefix, String siteName) {
        this.prefix = prefix;
        this.siteName = siteName;
    }

    private void initMainButtonsAndMaxCountNews(ButtonMainMenu buttonMainMenu, int maxCountNews) {
        this.buttonMainMenu = buttonMainMenu;
        this.maxCountNews = maxCountNews;
    }

    public String getFullSiteName() {
        String fullSiteName = siteName.toUpperCase().charAt(0)
                + siteName.substring(1);
        return fullSiteName;
    }

    public Map<String, String> commonParse() throws RuntimeException, IOException {
        Document establishedConnection = setServerConnection();
        Map<String, String> result = parseEngine(establishedConnection);
        return result;
    }

    private Document setServerConnection() throws RuntimeException, IOException {
        Connection connection = Jsoup.connect("https://" + prefix + siteName + ".ru/")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:64.0) Gecko/20100101 Firefox/64.0")
                .timeout(5000);

        Document document =  connection.get();
        YandexWeatherSite.getInstance().checkStatusCode(connection);
        return document;
    }

    private Map<String, String> parseEngine(Document document) {
        Map<String, String> news = new HashMap<>();
        getAndCheckElementsOfNews(document);
        addAllNewsToMap(news);
        return news;
    }

    private void getAndCheckElementsOfNews(Document document) {
        try {
            elements = getJSoupElementsOfSite(document);
        } catch (RuntimeException ex) {
            new ErrorMessageFrame(ex);
        }
    }

    private void addAllNewsToMap(Map<String, String> news) {
        for (int i = 0; i < maxCountNews; i++) {
            addNewsToMap(i, news);
        }
    }

    abstract public Elements getJSoupElementsOfSite(Document document);
    abstract public void addNewsToMap(int i, Map<String, String> news);
}