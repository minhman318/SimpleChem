package com.manminh.simplechem.search.engine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import android.util.Log;

import com.manminh.simplechem.balance.exception.ParseEquationException;
import com.manminh.simplechem.model.Equation;
import com.manminh.simplechem.search.SearchResult;


public class PTHHSearchEngine implements SearchEngine {
    public static final String URL = "https://phuongtrinhhoahoc.com";
    public static final int ITEMS_PER_PAGE = 2;

    @Override
    public ArrayList<SearchResult> Search(String in, String out, int page) throws IOException {
        ArrayList<SearchResult> searchResults = new ArrayList<>();
        String url = buildQueryStr(in, out, page);
        Document mWeb = Jsoup.connect(url).get();
        Elements equations = mWeb.select("tr[class=formula-row]");

        for (int i = 0; i < equations.size(); i++) {
            Elements formula = equations.eq(i);
            String eqStr = formula.text();
            Log.d("EQ", "page " + String.valueOf(page) + " eq: " + eqStr);
            try {
                Equation eq = Equation.parseEquation(eqStr);
                eqStr = eq.toHtmlSpannedStr();
                eq = null;
            } catch (ParseEquationException e) {
            }

            Elements conditionTab = mWeb.select("div[class=tab-content]").eq(i).get(0).children();
            SearchResult.Builder builder = new SearchResult.Builder(eqStr);

            for (int j = 0; j < conditionTab.size(); j++) {
                String idStr = conditionTab.get(j).id();
                if (idStr.contains("condition")) {
                    String content = conditionTab.get(j).text();
                    if (!content.equals("")) {
                        builder.addDetail("Điều kiện", upperFirst(content));
                    }
                } else if (idStr.contains("phenomenon")) {
                    String content = conditionTab.get(j).text();
                    if (!content.equals("")) {
                        builder.addDetail("Hiện tượng", upperFirst(content));
                    }
                } else if (idStr.contains("how-to")) {
                    String content = conditionTab.get(j).text();
                    if (!content.equals("")) {
                        builder.addDetail("Cách thực hiện", upperFirst(content));
                    }
                }
            }
            searchResults.add(builder.build());
        }
        return searchResults;
    }

    public String buildQueryStr(String in, String out, int page) {
        return URL + "/?chat_tham_gia=" + in + "&chat_san_pham=" + out + "&page=" + String.valueOf(page);
    }

    private String upperFirst(String str) {
        if (!str.equals("")) {
            StringBuilder buider = new StringBuilder(str);
            char first = buider.charAt(0);
            buider.setCharAt(0, Character.toUpperCase(first));
            return buider.toString();
        }
        return str;
    }

    @Override
    public int getNumberItemsPerPage() {
        return ITEMS_PER_PAGE;
    }
}
