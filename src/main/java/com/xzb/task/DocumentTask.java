package com.xzb.task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DocumentTask extends RecursiveTask<Integer> {

    private Integer startLine;// inclusive
    private Integer endLine; // exclusive
    private String[][] documents;
    private String searchWord;

    public DocumentTask(Integer startLine, Integer endLine, String[][] documents, String searchWord) {
        this.startLine = startLine;
        this.endLine = endLine;
        this.documents = documents;
        this.searchWord = searchWord;
    }



    @Override
    protected Integer compute() {
          if (endLine - startLine < 10)
          {
              return processLines(startLine,endLine,documents,searchWord);
          }
          else // split into subworks
          {
              Integer mid = (endLine - startLine) / 2;
              DocumentTask leftTask = new DocumentTask(startLine,mid,documents,searchWord);
              DocumentTask rightTask = new DocumentTask(mid  , endLine,documents,searchWord);
               invokeAll(leftTask,rightTask);
              try {
                  Integer result = leftTask.get() + rightTask.get();return result;
              } catch (InterruptedException e) {
                  e.printStackTrace();
              } catch (ExecutionException e) {
                  e.printStackTrace();
              }
              return 0;
          }
    }

    private Integer processLines(Integer startLine, Integer endLine, String[][] documents, String searchWord)
    {
        List<LineTask> lineTasks = new ArrayList<>();
        for (int i = startLine;i < endLine; ++i)
        {
             lineTasks.add(new LineTask(0, documents[i].length, documents[i], searchWord));
        }

        invokeAll(lineTasks);

      return   lineTasks.stream()
        .collect(Collectors.summingInt(it-> {
            try {
                return it.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return 0;
        }));
    }
}
