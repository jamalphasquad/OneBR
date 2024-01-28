package org.alphasquad;

import java.io.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        System.out.println("........Init........");

        //read a file in the project root folder
        File dataFile = new File("data.txt");
        FileOutputStream fos = null;

        try {
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }
            fos = new FileOutputStream(dataFile, true);
        } catch (IOException e) {
            System.out.println("-----------Error creating file------------");
            return;
        }

        //start time:
        long startTime = System.currentTimeMillis();


        //make an array of threads to run the TextWriter class
        Thread[] writerThreads = new Thread[8];

        for (int i = 0; i < writerThreads.length; i++) {
            writerThreads[i] = new Thread(new TextWriter(fos));
            writerThreads[i].start();
        }

        for (Thread writerThread : writerThreads) {
            try {
                writerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        long endTime = System.currentTimeMillis();

        System.out.println(".....Done----Time taken: " + (endTime - startTime) + " ms");

        //verify number of lines written to file:
        try {
            int lines = countLines("data.txt");
            System.out.println("Number of lines: " + lines);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static int countLines(String filePath) throws IOException {
        int lines = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) lines++;
        }
        return lines;
    }

}