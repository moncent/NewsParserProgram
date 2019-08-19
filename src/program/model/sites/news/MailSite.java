package program.model.sites.news;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import program.view.mainButtons.ButtonMainMenu;

import java.util.Map;

public class MailSite extends BaseSite {

    public MailSite() {
        super("news." ,"mail", ButtonMainMenu.MAIL_NEWS, 10);
    }

    @Override
    public Elements getJSoupElementsOfSite(Document document) {
        Elements elementsOfNewsFromHtml = document.getElementsByClass("link link_flex");
        return elementsOfNewsFromHtml;
    }

    @Override
    public void addNewsToMap(int i, Map<String, String> news) {
        String link = "https://"
                    + prefix
                    + siteName
                    + ".ru"
                    + elements.get(i).attr("href");

        news.put(link, elements.get(i).text());
    }
}