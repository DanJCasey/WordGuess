package com.github.zipcodewilmington;


import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author xt0fer
 * @version 1.0.0
 * @date 5/27/21 11:02 AM
 */
public class Hangman {
    //DanJCasey

    public static final String[] WordBank = {
            "GUITAR", "BASS", "DRUMS", "CELLO", "PIANO", "MICROPHONE", "TROMBONE", "ACCORDION", "VIOLIN", "TRUMPET"
    };
    public static final Random RANDOM = new Random();
    public static final int MAX_ERRORS = 8;
    private String wordToFind;
    private char[] wordFound;
    private int nbErrors;
    private ArrayList<String> letters = new ArrayList<>();


    private String nextWordToFind() {
        return WordBank[RANDOM.nextInt(WordBank.length)];
    }


    public void newGame() {
        nbErrors = 0;
        letters.clear();
        wordToFind = nextWordToFind();

        wordFound = new char[wordToFind.length()];

        for (int i = 0; i < wordFound.length; i++) {
            wordFound[i] = '_';
        }
    }
    public boolean wordFound() {
        return wordToFind.contentEquals(new String(wordFound));
    }

    private String wordFoundContent() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < wordFound.length; i++) {
            builder.append(wordFound[i]);
            if(i < wordFound.length - 1) {
                builder.append(" ");
            }
        }
        return builder.toString();
    }
    private void enter(String c) {
        if (!letters.contains(c)) {
            if(wordToFind.contains(c)) {
                int index = wordToFind.indexOf(c);
                while (index >= 0) {
                    wordFound[index] = c.charAt(0);
                    index = wordToFind.indexOf(c, index + 1);
                }
            } else {
                nbErrors++;
            }
            letters.add(c);
        }
    }

    public void play() {
        try (Scanner input = new Scanner(System.in)) {
            while (nbErrors < MAX_ERRORS) {
                System.out.println("\nEnter a letter : ");
                String str = input.next();

                if(str.length() > 1) {
                    str = str.substring(0, 1);
                }
                enter(str);
                System.out.println("\n" + wordFoundContent());
                if(wordFound()) {
                    System.out.println("\nYou win!");
                    break;
                } else {
                    System.out.println("\nTries remaining: " + (MAX_ERRORS - nbErrors));

                }
            }
            if (nbErrors == MAX_ERRORS) {
                System.out.println("\nYou lose!");
                System.out.println("\nWord to find was: " + wordToFind);
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("Welcome to musical instrument hangman");
        Hangman hangman = new Hangman();
        hangman.newGame();
        hangman.play();
    }
}
