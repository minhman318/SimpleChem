package com.manminh.simplechem.search.engine;

import com.manminh.simplechem.search.SearchResult;

import java.io.IOException;
import java.util.ArrayList;

public interface SearchEngine {
    ArrayList<SearchResult> Search(String in, String out, int page) throws IOException;

    int getNumberItemsPerPage();
}
