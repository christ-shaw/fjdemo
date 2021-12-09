package com.xzb.task;

import java.util.concurrent.RecursiveTask;

public class LineTask extends RecursiveTask<Integer> {
    private int startPos;
    private int endPos;
    private String[] lineDocs;
    private String searchWord;

    public LineTask(int startPos, int endPos, String[] lineDocs, String searchWord) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.lineDocs = lineDocs;
        this.searchWord = searchWord;
    }

    @Override
    protected Integer compute() {
        return null;
    }
}
