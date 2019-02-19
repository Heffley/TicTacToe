package com.example.gosen.tictac;

/**
 */
public abstract class TicTacStrategy {
    /**
     * depth used in minimax to calculate next move
     */
    protected int depth;

    /**
     * gets the next move for the AI player
     * @param boardmanager looks at the board
     * @param depth used to calculate next move
     * @return the move for the ai
     */
    public abstract int getNextMovement(TicTacBoardManager boardmanager, int depth);

    /**
     * returns if the strategy is valid (AI strats are valid, player vs player is not)
     * @return (AI strats are true, player vs player is false)
     */
    public abstract boolean isValid();

    /**
     * constructor for strategy, needing depth
     */
    TicTacStrategy(int depth) {
        this.depth = depth;
    }

}
