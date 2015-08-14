package io;

import scanner.tokenizer.Token;
import scanner.tokenizer.TokenClass;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Author:          Tristan Newmann
 * Student Number:  c3163181
 * Email:           c3163181@uon.edu.au
 * Date Created:    8/8/2015
 * File Name:       A1Output
 * Project Name:    CD15 Compiler
 * Description:     output class specifically designed to output the results of the scanner
 *                  based on what is required for the Part 1 requirements
 */
public class A1Output extends AssignmentOutput {

    // Create a char buffer and a token buffer
    // add tokens to buffer, when tokens exceed 60 print line
    // clear buffer

    private final int LINE_BUFFER_LENGTH = 60;

    ArrayList<Token> tokenBuffer;
    private int lineCharacterCount;
    private PrintStream out;

    public A1Output(PrintStream writeLocation) {
        this.tokenBuffer = new ArrayList<>();
        this.lineCharacterCount = 0;
        this.out = writeLocation;
    }

    public void addTokenToBuffer(Token token) {

        // If we encounter a TUNDF, flush the buffer, add token to buffer and then flush
        if (token.getTokenClass() == TokenClass.TUNDF) {
            /*
            this.flushBufferToConsole();
            this.clearBuffer();
            String s = token.shortString();
            s = s.replace("\n", "");
            this.out.print(s);
            this.out.flush();
            this.out.print('\n');
            */
            if ( this.tokenBuffer.size() > 0 ) {
                this.flushBufferToConsole();
            }
            this.clearBuffer();
            this.tokenBuffer.add(token);
            this.flushBufferToConsole();
            this.clearBuffer();
            return;
        }

        // We always push into the token buffer first
        tokenBuffer.add(token);
        this.lineCharacterCount += token.shortString().length();

        if ( this.lineCharacterCount > LINE_BUFFER_LENGTH ) {
            this.flushBufferToConsole();
            this.clearBuffer();
        }

    }

    /*
    Removes tokens from token output stream and resets line char count
     */
    private void clearBuffer() {
        this.lineCharacterCount = 0;
        this.tokenBuffer.clear();
    }


    private void flushBufferToConsole() {

        for ( Token token : this.tokenBuffer ) {
            this.out.print( token.shortString() );
        }
        out.append('\n');
        out.flush();
    }
}
