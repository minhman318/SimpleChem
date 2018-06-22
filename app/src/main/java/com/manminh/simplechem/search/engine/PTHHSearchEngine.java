package com.manminh.simplechem.search.engine;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import android.os.AsyncTask;
import android.text.Html;

import com.manminh.simplechem.model.Result;
import com.manminh.simplechem.search.engine.SearchEngine;


public class PTHHSearchEngine implements SearchEngine {


    public PTHHSearchEngine() { }

    /**
     *
     * @param in joined elements
     * @param out produced elements
     * @return
     */

    @Override
    public ArrayList<Result> Search(String in, String out) {
        ArrayList<Result> results = new ArrayList<>();

        String url = "https://phuongtrinhhoahoc.com/?chat_tham_gia="+in+"&chat_san_pham=" + out;

        try {
            Document mWeb = Jsoup.connect(url).get();
            Elements formulas = mWeb.select("tr[class=formula-row]");
            for(int i=0; i < formulas.size(); i++) {
                ArrayList<String> information = new ArrayList<>();
                Elements formula = formulas.eq(i);
                Elements conditionTab = mWeb.select("div[class=tab-content]").eq(i).get(0).children();
                for(int j = 0; j < conditionTab.size(); j++) {
                    information.add(conditionTab.get(j).text());
                }
                for(int j = conditionTab.size(); j < 3 ; j++) {
                    information.add("");
                }
                String equation = formula.text().replace(Html.fromHtml("&rarr").toString(), "->");
                results.add(new Result(equation, information.get(0), information.get(1), information.get(2)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return results;
    }

}
