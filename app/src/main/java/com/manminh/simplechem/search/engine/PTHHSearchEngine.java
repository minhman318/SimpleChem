package com.manminh.simplechem.search.engine;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

import android.os.AsyncTask;


public class PTHHSearchEngine  extends SearchEngine {

    private static ArrayList<String> search_result = new ArrayList<>();

    public PTHHSearchEngine() {

    }

    /**
     *

     * @param in Join in element
     * @param out Product
     * @return
     */

    @Override
    public ArrayList<String> Search(String in, String out) {
        String url = "https://phuongtrinhhoahoc.com/?chat_tham_gia="+in+"&chat_san_pham=" + out;
        new SearchOperation().execute(url);
        return search_result;
    }


    private class SearchOperation extends AsyncTask<String, Void, Void > {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                Document mWeb = Jsoup.connect(strings[0]).get();
                Elements formulas = mWeb.select("div[class=formula-row]");
                String data_row = "";
                for(int i=0; i < formulas.size(); i++) {
                    Elements formula = formulas.eq(i);
                    for (int j =0; j < formula.size(); j++) {
                        Element substance = formula.get(i);
                        String subtext = substance.text();
                        data_row = data_row.concat(subtext);
                    }
                }
                search_result.add(data_row);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
