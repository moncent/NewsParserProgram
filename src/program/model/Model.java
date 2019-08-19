package program.model;

import program.controller.Controller;
import program.model.sites.news.MailSite;
import program.model.sites.news.YandexSite;
import program.model.sites.weather.YandexWeatherSite;

import java.io.IOException;
import java.util.Map;


public class Model {
    private Controller controller;
    private YandexSite yandexNewsSite;
    private MailSite mailNewsSite;
    private YandexWeatherSite yandexWeatherSite;

    public Model(Controller controller){
        this.controller = controller;
    }

    public Map<String, String> initYandexNewsWithLinksAndTitles() throws RuntimeException, IOException {
        createYandexNewsObjectAndSetFullName();
        Map<String, String> yandexNews = yandexNewsSite.commonParse();
        return yandexNews;
    }

    public Map<String, String> initMailNewsWithLinksAndTitles() throws RuntimeException, IOException {
        createMailNewsObjectAndSetFullName();
        Map<String, String> mailNews = mailNewsSite.commonParse();
        return mailNews;
    }

    public void initYandexWeather() throws RuntimeException, IOException {
        createYandexWeatherObjectAndSetFullName();
        yandexWeatherSite.commonParse();
    }

    private void createYandexNewsObjectAndSetFullName() {
        yandexNewsSite = new YandexSite();
        setFullNameForYandexNews();
    }

    private void createMailNewsObjectAndSetFullName() {
        mailNewsSite = new MailSite();
        setFullNameForMailNews();
    }

    private void createYandexWeatherObjectAndSetFullName(){
        yandexWeatherSite = YandexWeatherSite.getInstance();
        setFullNameForYandexWeather();
    }


    private void setFullNameForYandexNews() {
        String fullNameYandexNews = yandexNewsSite.getFullSiteName();
        controller.setFullSiteName(fullNameYandexNews);
    }

    private void setFullNameForMailNews() {
        String fullNameMailNews = mailNewsSite.getFullSiteName();
        controller.setFullSiteName(fullNameMailNews);
    }

    private void setFullNameForYandexWeather() {
        String fullNameYandexWeather = yandexWeatherSite.getFullName();
        controller.setFullSiteName(fullNameYandexWeather);
    }

    public void clearYandexWeatherFrame() {
        yandexWeatherSite.clear();
    }
}