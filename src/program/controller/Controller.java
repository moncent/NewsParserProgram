/*
    Парсер новостей и погоды. Версия *1.0.1*
*/
package program.controller;

import program.model.Model;
import program.view.ErrorMessageFrame;
import program.view.mainButtons.ButtonMainMenu;
import program.view.View;

import java.io.IOException;
import java.util.Map;

public class Controller {

    private Model model;
    private View view;
    private String fullSiteName;

    public Controller() {
        model = new Model(this);
        view = new View(this);
        init();
    }

    public void setFullSiteName(String fullSiteName) {
        this.fullSiteName = fullSiteName;
    }

    public String getFullSiteName() {
        return fullSiteName;
    }

    public void init() {
        view.start();
    }

    public void openNewsAndWeatherFrame(ButtonMainMenu buttonMainMenu) {
        try {
            disableParseAndEnableNewsWeatherFrame(buttonMainMenu);
        } catch (RuntimeException | IOException e) {
            new ErrorMessageFrame(e);
        }
    }

    public void disableParseAndEnableNewsWeatherFrame(ButtonMainMenu buttonMainMenu) throws IOException, RuntimeException {
        view.getDisablingNewsWeatherFrame();
        parse(buttonMainMenu);
        view.getEnablingNewsWeatherFrame();
    }

    public void parse(ButtonMainMenu buttonMainMenu) throws RuntimeException, IOException {
        switch (buttonMainMenu){
            case YANDEX_NEWS:
                parseYandexNews();
                break;
            case MAIL_NEWS:
                parseMailNews();
                break;
            case YANDEX_WEATHER:
                parseYandexWeather();
                break;
        }
    }

    public void parseYandexNews() throws RuntimeException, IOException {
        Map<String, String> yandexNews = model.initYandexNewsWithLinksAndTitles();
        view.getFormattedNews(yandexNews);
    }

    public void parseMailNews() throws RuntimeException, IOException {
        Map<String, String> mailNews = model.initMailNewsWithLinksAndTitles();
        view.getFormattedNews(mailNews);
    }

    public void parseYandexWeather() throws RuntimeException, IOException {
        model.initYandexWeather();
        view.getFormattedYandexWeather();
        model.clearYandexWeatherFrame();
    }

    public static void main(String[] args) {
        new Controller();
    }
}