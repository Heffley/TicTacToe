package com.example.gosen.tictac;

import java.util.ArrayList;
import java.util.Random;

/**
 * Minimax strategy class for AI
 */
public class TicTacMinimaxStrategy extends TicTacStrategy {

    /**
     * a class for the the moves of the AI in minimax
     */
    class Move {
        public int id;
        public int score;

        //default constructor
        Move() {
            this.id = -1;
            this.score = -1;
        }

        //constructor with id and score
        Move(int id, int score) {
            this.id = id;
            this.score = score;
        }
    }

    /**
     * call super class Strategy
     *
     * @param depth
     */
    public TicTacMinimaxStrategy(int depth) {
        super(depth);
    }

    //return the next position for movement

    /**
     * gets the next move for the AI player, uses recursion
     *
     * @param boardmanager looks at the board
     * @param depth        used to calculate next move
     * @return the move for the ai
     */
    public int getNextMovement(TicTacBoardManager boardmanager, int depth) {
        System.out.println("FIRST BOARD PLAYER: " + boardmanager.getBoard().getCurrentPlayer());
        //boardmanager.changeTurns();
        TicTacBoardManager newBoardManager = boardmanager.DeepCopy();//boardmanager.DeepCopy();
        Move move = CallMiniMax(newBoardManager, 0);
        return move.id;
    }

    /**
     * calculates the best possible move for the get nextmovement
     *
     * @param boardManager   looks at the board to see the valid moves
     * @param depth          used to help calcualte the best move
     * @return
     */
    public Move CallMiniMax(TicTacBoardManager boardManager, int depth) {

        TicTacBoard board = boardManager.getBoard();
        ArrayList<Integer> validMoves = boardManager.getValidMoves();
        //change players
        int current_player = board.getCurrentPlayer();
        //loop through available spots
        Move bestMove = new Move();
        bestMove.score = -10000;
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < validMoves.size(); i++) {
            Move move = new Move();
            move.id = validMoves.get(i);

            Move newMove;
            TicTacBoardManager newBoardManager;
            newBoardManager = boardManager.DeepCopy();//)boardManager.DeepCopy();
            //make move
            newBoardManager.makeMove(move.id);
            newMove = miniMax(newBoardManager, current_player, move.id,0);
            move.score = newMove.score;
            //add the move with id and score in the list
            moves.add(move);
            //collect the score resulting from calling minimax on the opponent of the current player
            // choose the move with the hghest score
            if (bestMove.score < move.score) {
                bestMove.id = move.id;
                bestMove.score = move.score;
            }
        }

/*
        for (int i = 0; i < moves.size(); i++) {
            for (int j = 0; j < depth; j++) {
                System.out.print("==+");
            }
            //if (depth == 0) {
            System.out.println("FINAL Player: " + current_player + " Move id: " + moves.get(i).id + " Depth: " + depth + " score : " + moves.get(i).score);
            //}
        }

        //if (depth == 0) {
        for (int j = 0; j < depth; j++) {
            System.out.print("==+");
        }
        System.out.println("Depth: " + depth + " BestMOVEid: " + bestMove.id + " BESTMOVESCORE: " + bestMove.score);
        //}

        System.out.println("FINAL #### CHOOSEN Depth: "+ depth + " BestMOVEid: " + bestMove.id + " BESTMOVESCORE: " + bestMove.score);
*/

        return bestMove;
    }


    public Move miniMax(TicTacBoardManager boardManager, int current_player, int position, int depth)
    {
        TicTacBoard board = boardManager.getBoard();
        ArrayList<Integer> validMoves = boardManager.getValidMoves();

        //checks for win, lose, tie and return value accordingly
        //(boardManager.getWinner(current_player)))
        if (boardManager.getWinner(position)) {
            if (boardManager.isPlayer1Win()) {
                //human wins so return 10 because human player is the palyer when miniMax is initially called
                return new Move(-1, (10 - depth));
            } else {
                //AI wins so return -10
                return new Move(-1, (-10 + depth));
            }
        }
        else if (validMoves.size() == 0) {
            return new Move (-1, 0);
        }

        //Create a list for moves
        ArrayList<Move> moves = new ArrayList<>();
        //loop through available spots
        Move bestMove = new Move();
        bestMove.score = -10000;

        for (int i = 0; i < validMoves.size(); i++) {
            Move move = new Move();
            move.id = validMoves.get(i);
            //collect the score resulting from calling minimax on the opponent of the current player
            Move newMove;
            TicTacBoardManager newBoardManager;
            newBoardManager = boardManager.DeepCopy();//)boardManager.DeepCopy();
            //make move
            position = move.id;
            current_player = newBoardManager.makeMove(position);
            newMove = miniMax(newBoardManager, current_player, position, depth + 1);
            // conver an oppent' score
            move.score = newMove.score * (-1);
            //add the move with id and score in the list
            moves.add(move);
        }

        // AI chooses a move with the highest score
        if (current_player == boardManager.getBoard().getPlayer2()) {
            bestMove.score = -10000;
            for (int i = 0; i < moves.size(); i++) {
                if (bestMove.score <= moves.get(i).score) {
                    bestMove.score = moves.get(i).score;
                    bestMove.id = moves.get(i).id;
                }
            }
        }
        else
        {
            // human chooses a move with the low score
            bestMove.score = 10000;
            for (int i = 0; i < moves.size(); i++) {
                if (bestMove.score > moves.get(i).score) {
                    bestMove.score = moves.get(i).score;
                    bestMove.id = moves.get(i).id;
                }
            }
        }
//        // print debug info
//        if (depth <= 2) {
//            for (int i = 0; i < moves.size(); i++) {
//                for (int j = 0; j < depth; j++) {
//                    System.out.print("==+");
//                }
//                //if (depth == 0) {
//                System.out.println("Player: " + current_player + " Move id: " + moves.get(i).id + " Depth: " + depth + " score : " + moves.get(i).score);
//                //}
//            }
//
//            //if (depth == 0) {
//            for (int j = 0; j < depth; j++) {
//                System.out.print("==+");
//            }
//            System.out.println("Depth: " + depth + " BestMOVEid: " + bestMove.id + " BESTMOVESCORE: " + bestMove.score);
//            //}
//        }

        // make the best move
        boardManager.makeMove(bestMove.id);

        // return the chosen move from the moves arraylist
        return bestMove;
    }

    /**
     * returns if the strategy is valid
     * @return true because random strategy is AI strategy
     */
    @Override
    public boolean isValid() {
        return true;
    }
}