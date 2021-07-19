import model.ArticleModel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import thread.ArticleThread;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ArticleModel model = new ArticleModel();
        ArrayList<String> listUrl = getListUrl();
        ArrayList<ArticleThread> listThread = new ArrayList<>();
        for (String s : listUrl) {
            ArticleThread articleThread = new ArticleThread(s);
            listThread.add(articleThread);
            articleThread.start();
        }
        for (ArticleThread articleThread : listThread) {
            try {
                articleThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (ArticleThread articleThread : listThread) {
            model.insert(articleThread.getArticle());
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime + " mls.");
    }

    private static ArrayList<String> getListUrl() {
        ArrayList<String> list = new ArrayList<>();
        Document doc;
        try {
            doc = Jsoup.connect("https://vnexpress.net/the-thao").get();
            Elements els = doc.select(".title-news a");
            if (els.size() > 0) {
                for (org.jsoup.nodes.Element el : els) {
                    list.add(el.attr("href"));
                }
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        System.out.printf("Got %d link.", list.size());
        return list;
    }
}