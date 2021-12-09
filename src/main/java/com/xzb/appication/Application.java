package com.xzb.appication;

import com.xzb.DocumentMocks;

public class Application {
    public static void main(String[] args)
    {
        DocumentMocks documentMocks = new DocumentMocks();
        String[][] docs = documentMocks.generateDocument(100, 1000, "the");



    }
}
