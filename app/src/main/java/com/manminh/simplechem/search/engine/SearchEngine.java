package com.manminh.simplechem.search.engine;

import com.manminh.simplechem.model.Result;

import java.util.ArrayList;

/**
 *
 */
public interface SearchEngine {

    public ArrayList<Result> Search(String in, String out);

}
