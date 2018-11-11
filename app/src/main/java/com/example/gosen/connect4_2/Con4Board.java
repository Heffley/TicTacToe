package com.example.gosen.connect4_2;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;

/**
 * The sliding Markers board .
 * Implements Serializable and Iterable<Marker> Interface
 */
public class Con4Board extends Observable implements Serializable, Iterable<Marker> {

    /**
     * The number of rows.
     */
    static int NUM_ROWS = 6;

    /**
     * The number of rows.
     */
    static int NUM_COLS = 7;
    /**
     * Type of the board, number or image.
     */
    private static String TYPE = "number";
    /**
     * The image to be used if it is an image board.
     */
    private static String IMAGE = "Flower";

    /**
     * The Markers on the board in row-major order.
     */
    private Marker[][] Markers = new Marker[NUM_ROWS][NUM_COLS];
    /**
     * A new board of Markers in row-major order.
     * Precondition: len(Markers) == NUM_ROWS * NUM_COLS
     *
     * @param Markers the Markers for the board
     */
    Con4Board(List<Marker> Markers) {

        Iterator<Marker> iter = Markers.iterator();

        for (int row = 0; row < Con4Board.NUM_ROWS; row++) {
            for (int col = 0; col < Con4Board.NUM_COLS; col++) {
                this.Markers[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the Marker at (row, col)
     *
     * @param row the Marker row
     * @param col the Marker column
     * @return the Marker at (row, col)
     */
    Marker getMarker(int row, int col) {
        return Markers[row][col];
    }

    /**
     * Set the size of the board
     * @param size the size of the board
     */
    public static void setBoardSize(int size) {
        NUM_ROWS=size;
        NUM_COLS=size;
    }

    /**
     * Set image of the board.
     * @param image Image to be set
     */
    static void setIMAGE(String image) {
        IMAGE = image;
    }

    /**
     * Gets the image of this board.
     * @return the image of this board.
     */
    static String getIMAGE() {
        return IMAGE;
    }

    /**
     * Sets the type of the board.
     * @param type type of the board
     */
    public static void setType(String type){
        TYPE = type;
    }

    /**
     * Gets the type of the board.
     * @return returns the type of the board
     */
    public static String getType() {
        return TYPE;
    }

    /**
     * Get the size of the Board
     * @return the number of rows in the board
     */
    public int getBoardSize() {
        return NUM_ROWS;
    }

    /**
     * Swap the Markers at (row1, col1) and (row2, col2)
     *
     * @param row1 the first Marker row
     * @param col1 the first Marker col
     * @param row2 the second Marker row
     * @param col2 the second Marker col
     */
    void swapMarkers(int row1, int col1, int row2, int col2) {
        Marker temp = Markers[row1][col1]; //to store original Marker
        Markers[row1][col1] = Markers[row2][col2];
        Markers[row2][col2] = temp;


        setChanged();
        notifyObservers();
    }

    // set background
    void setBackground(int row, int col, int background) {
        Markers[row][col].setBackground(background); //to store original Marker



        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Board{" +
                "Markers=" + Arrays.toString(Markers) +
                '}';
    }

    //implementing the iterator
    @NonNull
    @Override
    public Iterator<Marker> iterator() {
        return new BoardIterator();
    }

    /**
     * The iterator for the board that implements the Iterator<Marker> Interface
     */
    private class BoardIterator implements Iterator<Marker> {

        /**
         * Row of the next Marker in the board
         */
        int nextRow = 0;

        /**
         * Column of the next Marker in the board
         */
        int nextColumn = 0;

        @Override
        public boolean hasNext() {
            return nextRow < NUM_ROWS && nextColumn < NUM_COLS;
        }

        @Override
        public Marker next() {

            if (!this.hasNext()) {
                throw new NoSuchElementException("End of board reached");
            }
            //iterate through the rows of the board
            if (nextRow < NUM_ROWS) {

                //iterate through the columns of the board
                if (nextColumn < NUM_COLS) {

                    if (Markers[nextRow][nextColumn] != null) {
                        Marker nextMarker = Markers[nextRow][nextColumn];
                        nextColumn++;

                        //reset nextColumn and nextRow if end of column reached
                        if (nextColumn == NUM_COLS) {
                            nextColumn = 0;
                            nextRow++;
                        }
                        return nextMarker;
                    } else {
                        nextColumn++;
                        //reset nextColumn and nextRow if end of column reached
                        if (nextColumn == NUM_COLS) {
                            nextColumn = 0;
                            nextRow++;
                        }
                    }
                }
                nextRow++;
            }
            return null;
        }

    }
}
