package com.xzb.task;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
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
        Integer result=null;
        if (endPos - startPos < 100) {
            result=count(lineDocs, startPos, endPos, searchWord);
        } else {
            int mid=(startPos + endPos)/2;
            LineTask task1=new LineTask( startPos, mid,lineDocs, searchWord);
            LineTask task2=new LineTask(mid, endPos, lineDocs, searchWord);
            invokeAll(task1, task2);
            try {
                result= task1.get() + task2.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }


    private Integer count(String[] line, int start, int end, String word) {
        long count = Arrays.stream(Arrays.copyOfRange(line,start,end)).filter(it -> it.equalsIgnoreCase(word)).count();
        return Long.valueOf(count).intValue();
    }
}
