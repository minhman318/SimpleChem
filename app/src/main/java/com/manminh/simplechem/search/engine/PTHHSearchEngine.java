package com.manminh.simplechem.search;
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



public class PTHHSearchEngine  implements SearchEngine {

    private ArrayList<String> data;

    public PTHHSearchEngine() {
        data = new ArrayList<>();
    }

    /**
     *
     * @param source Website URL
     * @param in Join in element
     * @param out Product
     * @return
     */

    @Override
    public void Search(String source, String in, String out) {
        new SearchOperation().execute();
    }


    private class SearchOperation extends AsyncTask<String, Void, Void > {

        @Override
        protected Void doInBackground(String... strings) {
            try {
                Document mWeb = Jsoup.connect(strings[0]).get();
                Elements formulas = mWeb.select("div[class=formula-row]");
                String data_row = "";
                for(int i=0; i< formulas.size(); i++) {
                    Elements formula = formulas.eq(i);
                    for (int j =0; j < formula.size(); j++) {
                        Element substance = formula.get(i);
                        String subtext = substance.text();
                        data_row = data_row.concat(subtext);
                    }
                }
                data.add(data_row);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
