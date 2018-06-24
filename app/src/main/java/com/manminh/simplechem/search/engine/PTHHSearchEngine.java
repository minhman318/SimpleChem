package com.manminh.simplechem.search.engine;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import android.text.Html;

import com.manminh.simplechem.balance.exception.ParseEquationException;
import com.manminh.simplechem.model.Equation;
import com.manminh.simplechem.search.SearchResult;


public class PTHHSearchEngine implements SearchEngine {
    private static final String URL = "https://phuongtrinhhoahoc.com";

    @Override
    public ArrayList<SearchResult> Search(String in, String out) throws IOException {
        ArrayList<SearchResult> searchResults = new ArrayList<>();
        String url = buildQueryStr(in, out);
        Document mWeb = Jsoup.connect(url).get();
        Elements formulas = mWeb.select("tr[class=formula-row]");
        for (int i = 0; i < formulas.size(); i++) {
            ArrayList<String> information = new ArrayList<>();
            Elements formula = formulas.eq(i);
            Elements conditionTab = mWeb.select("div[class=tab-content]").eq(i).get(0).children();
            for (int j = 0; j < conditionTab.size(); j++) {
                information.add(conditionTab.get(j).text());
            }
            for (int j = conditionTab.size(); j < 3; j++) {
                information.add("");
            }

            String eqStr = formula.text();
            try {
                Equation equation = Equation.parseEquation(eqStr);
                eqStr = equation.toHtmlSpannedStr();
                equation = null;
            } catch (ParseEquationException e) {
            }

            SearchResult result = new SearchResult.Builder(eqStr)
                    .addDetail("Điều kiện", upperFirst(information.get(0)))
                    .addDetail("Cách thực hiện", upperFirst(information.get(1)))
                    .addDetail("Hiện tượng", upperFirst(information.get(2)))
                    .build();

            searchResults.add(result);
        }
        return searchResults;
    }

    public String buildQueryStr(String in, String out) {
        return URL + "/?chat_tham_gia=" + in + "&chat_san_pham=" + out;
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
}
