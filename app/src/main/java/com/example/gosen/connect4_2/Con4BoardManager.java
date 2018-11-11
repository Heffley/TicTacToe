package com.example.gosen.connect4_2;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class Con4BoardManager implements Serializable {

    /**
     * score
     */
    private Integer score;

    /**
     * The board being managed.
     */
    private Con4Board board;
    MoveStack stack;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    Con4BoardManager(Con4Board board) {
        this.board = board;
        this.score = 0;
        this.stack = new MoveStack();
    }

    /**
     * Return the current board.
     */
    Con4Board getBoard() {
        return board;
    }

    /**
     * Manage a new shuffled board.
     */
    Con4BoardManager() {
        List<Marker> markers = new ArrayList<>();
        final int numMarkers = Con4Board.NUM_ROWS * Con4Board.NUM_COLS;
        for (int row = 0; row < Con4Board.NUM_ROWS; row++) {
            for (int col = 0; col < Con4Board.NUM_COLS; col++) {
                markers.add(new Marker(row, col, 0));
            }

        }
        //tiles.add(new Marker(24));
        this.board = new Con4Board(markers);
        this.score = 0;
        stack = new MoveStack();
    }

    /**
     * A getter for the score
     * @return the score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * A setter for the score.
     * @param s The score to be set.
     */
    public void setScore(int s) {
        score = s;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return True if the tiles are in row major order, false if otherwise.
     */
    boolean puzzleSolved() {
        Iterator<Marker> MarkerIterator = this.board.iterator();
        Marker past = MarkerIterator.next();
        boolean solved = true;
        while (true) {
            if (MarkerIterator.hasNext()) {
                Marker current = MarkerIterator.next();
                if (past.getId() > current.getId()) {
                    solved = false;
                    break;
                }
                else {
                    past = current;
                }
            }
            else {
                break;
            }
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {

        int row = position / Con4Board.NUM_COLS;
        int col = position % Con4Board.NUM_COLS;
        return (board.getMarker(row,col).getBackground() == R.drawable.blank_marker);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {

        int row = position / Con4Board.NUM_COLS;
        int col = position % Con4Board.NUM_COLS;
        int blankId = 25;

        if (isValidTap(position)) {
            //check whos turn it is and drop the marker accordingly
            //get row and column of blank tile
            board.getMarker(row, col).setBackground(1);
 /*           int blankPos = 0;
            Iterator<Marker> iter = board.iterator();
            while (iter.next().getId() != blankId) {
                blankPos++;
            }
            int blankRow = blankPos / Con4Board.NUM_COLS;
            int blankCol = blankPos % Con4Board.NUM_COLS;

            //swap them
            board.swapMarkers(row, col, blankRow, blankCol);
            Integer[] c = {row, col, blankRow, blankCol};
            stack.add(c);*/
            // just a test
            board.swapMarkers(row, col, row, col);

        }
        score++;

    }

}
