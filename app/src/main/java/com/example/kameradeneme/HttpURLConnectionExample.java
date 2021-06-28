package com.example.kameradeneme;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;



import com.fasterxml.jackson.databind.ObjectMapper;

public class HttpURLConnectionExample  {
    private final String USER_AGENT = "Mozilla/5.0";

    String searchSynonym(String wordToSearch) throws Exception {
       // System.out.println("Sending request...");

        String url = "https://api.datamuse.com/words?rel_syn=" + wordToSearch;

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();


       // int responseCode = con.getResponseCode();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        // System.out.println("\nSending request to: " + url);
       // System.out.println("JSON Response: " + responseCode + "\n");

        // ordering the response
        StringBuilder response;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            String inputLine;
            response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            // converting JSON array to ArrayList of words
            ArrayList<Word> words = mapper.readValue(
                    response.toString(),
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Word.class)
            );

            //System.out.println("Synonym word of '" + wordToSearch + "':");

            if(words.size() > 0) {
                for(Word word : words) {
                   return ((words.indexOf(word) + 1) + ". " + word.getWord() + "");
                }
            }
            else {
               // System.out.println("none synonym word!");
            }
        }
        catch (IOException e) {
            e.getMessage();
        }
        return url;
    }



    // word and score attributes are from DataMuse API
    static class Word {
        private String word;
        private int score;

        public String getWord() {return this.word;}
        public int getScore() {return this.score;}
    }





}


