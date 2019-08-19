package program.view;


import program.controller.ActionMouseNewsListener;
import program.controller.Controller;
import program.model.sites.weather.YandexWeatherSite;
import program.view.mainButtons.*;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.StyleSheet;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class View {
    private Controller controller;
    private JFrame frameOfNewsAndWeather;
    private JFrame frame;
    private String fullSiteName;

    public View(Controller controller) {
        this.controller = controller;
    }

    public void start() {
        createMainMenuFrame();
        frame.getContentPane().setLayout(setAndGetLayoutManagerForMainFrame());
        frame.pack();
        frame.setVisible(true);
    }

    private void createMainMenuFrame() {
        frame = new JFrame("Сводка новостей и погода. Версия 1.0.1");
        setMainMenuFrame();
        frame.add(Box.createRigidArea(new Dimension(0, 10)));
        createMainButtons();
    }

    private LayoutManager setAndGetLayoutManagerForMainFrame() {
        BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
        return boxLayout;
    }

    private void setMainMenuFrame() {
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(500, 300));
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
    }

    private void createMainButtons() {
        createYandexNewsMainButton();
        createMailNewsMainButton();
        createYandexWeatherMainButton();
    }

    private void createYandexNewsMainButton() {
        YandexNewsButtonMainMenu yandexNewsButton = new YandexNewsButtonMainMenu();
        setControllerListenerAndAddToFrame(yandexNewsButton);
    }

    private void createMailNewsMainButton() {
        MailNewsButtonMainMenu mailButton = new MailNewsButtonMainMenu();
        setControllerListenerAndAddToFrame(mailButton);
    }

    private void createYandexWeatherMainButton() {
        YandexWeatherButtonMainMenu yandexWeatherButton = new YandexWeatherButtonMainMenu();
        setControllerListenerAndAddToFrame(yandexWeatherButton);
    }

    private void setControllerListenerAndAddToFrame(MainButtons button) {
        button.setControllerAndAddListener(controller);
        addButtonAndIndentionToFrame(button);
    }

    private void addButtonAndIndentionToFrame(MainButtons button) {
        frame.add(button);
        frame.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    public void getDisablingNewsWeatherFrame() {
        frameOfNewsAndWeather = new JFrame();
        setParametersNewsFrameNotActive();
    }

    private void setParametersNewsFrameNotActive() {
        setBasicParametersNewsWeatherFrame();
        setLayoutManagerForNewsWeatherFramesAndAddIndention();
    }

    private void setBasicParametersNewsWeatherFrame() {
        frameOfNewsAndWeather.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        frameOfNewsAndWeather.setPreferredSize(new Dimension(600, 400));
        frameOfNewsAndWeather.setLocationRelativeTo(frame);
        frameOfNewsAndWeather.setResizable(true);
    }

    private void setLayoutManagerForNewsWeatherFramesAndAddIndention() {
        BoxLayout boxLayout = new BoxLayout(frameOfNewsAndWeather.getContentPane(), BoxLayout.Y_AXIS);
        frameOfNewsAndWeather.getContentPane().setLayout(boxLayout);
        frameOfNewsAndWeather.add(Box.createRigidArea(new Dimension(5, 5)));
    }

    public void getEnablingNewsWeatherFrame() {
        frameOfNewsAndWeather.pack();
        frameOfNewsAndWeather.setVisible(true);
    }

    public void getFormattedNews(Map<String, String> linksAndNews) {
        setStyleOfNews();
        for (Map.Entry<String, String> pair : linksAndNews.entrySet()) {
            createNewsForFrame(pair.getKey(), pair.getValue());
        }
    }

    private void setStyleOfNews() {
        fullSiteName = controller.getFullSiteName();
        frameOfNewsAndWeather.setTitle("Новости " + fullSiteName);
        setHtmlEditorKitForNews();
    }

    private void setHtmlEditorKitForNews() {
        HTMLEditorKit kit = new HTMLEditorKit();
        StyleSheet styleOfLinks =  kit.getStyleSheet();
        styleOfLinks.addRule("a {color:#000000;}");
    }

    private void createNewsForFrame(String links, String news) {
        try {
            URI uri = new URI(links);
            JLabel label = createNewsLink(links, news);
            addActionMouseListenerToLabel(uri, label);
            frameOfNewsAndWeather.add(label);
        } catch (URISyntaxException exc) {
            new ErrorMessageFrame(exc);
        }
    }

    private JLabel createNewsLink(String links, String news) {
        JLabel label = new JLabel("<html><a href="
                + links
                + ">"
                + news
                + "</a></html>");
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return label;
    }

    private void addActionMouseListenerToLabel(URI uri, JLabel label) {
        ActionMouseNewsListener mouseListenerNews = new ActionMouseNewsListener();
        mouseListenerNews.setUri(uri);
        label.addMouseListener(mouseListenerNews);
    }

    public void getFormattedYandexWeather(){
        createYandexWeatherAlertsForFrame();
        createYandexWeatherDataForFrame();
    }

    private void createYandexWeatherAlertsForFrame() {
        List<String> weatherAlerts = YandexWeatherSite.getInstance().getWeatherAlertsAsList();
        setStyleOfWeatherAlerts();
        for (String weatherAlert : weatherAlerts) {
            JLabel labelAlerts = new JLabel("<html><font color='#ff4500'>" + weatherAlert + "</font></html>");
            frameOfNewsAndWeather.add(labelAlerts);
        }
    }

    private void createYandexWeatherDataForFrame(){
        List<String> weatherData = YandexWeatherSite.getInstance().getWeatherDataAsList();
        for (String data : weatherData) {
            JLabel labelAlerts = new JLabel(data);
            frameOfNewsAndWeather.add(labelAlerts);
        }
        createNewsForFrame("https://yandex.ru/pogoda/moscow", "Подробнее о погоде");
    }

    private void setStyleOfWeatherAlerts() {
        fullSiteName = controller.getFullSiteName();
        frameOfNewsAndWeather.setTitle(fullSiteName);
    }
}