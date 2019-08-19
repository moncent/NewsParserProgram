package program.model.sites.news;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import program.view.mainButtons.ButtonMainMenu;

import java.util.Map;

public class YandexSite extends BaseSite {

    public YandexSite() {
        super("", "yandex", ButtonMainMenu.YANDEX_NEWS, 10);
    }

    @Override
    public Elements getJSoupElementsOfSite(Document document) {
        Elements elements = document.getElementsByClass("home-link list__item-content list__item-content_with-icon home-link_black_yes");
        return elements;
    }

    @Override
    public void addNewsToMap(int i, Map<String, String> news) {
        news.put(elements.get(i).attr("href"), elements.get(i).text());
    }

}