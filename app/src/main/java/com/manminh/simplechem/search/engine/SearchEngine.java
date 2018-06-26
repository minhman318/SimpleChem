package com.manminh.simplechem.search.engine;

import com.manminh.simplechem.search.SearchResult;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Interface for search engine
 */
public interface SearchEngine {

    /**
     * search method
     *
     * @param beforeStr before chemicals search string. Ex: "H2 O2"
     * @param afterStr  after chemicals search string. Ex: "H2O"
     * @param page      id which page of the source website to search
     * @return list of SearchResult objects
     * @throws IOException if cannot access source website
     */
    ArrayList<SearchResult> search(String beforeStr, String afterStr, int page) throws IOException;

    // Return number results per page of source website
    int getNumberItemsPerPage();
}
