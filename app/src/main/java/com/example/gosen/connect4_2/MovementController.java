package com.example.gosen.connect4_2;

import android.content.Context;
import android.widget.Toast;


class MovementController {

    private Con4BoardManager boardManager = null;

    MovementController() {
    }

    void setBoardManager(Con4BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    void processTapMovement(Context context, int position) {
        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            // check i f game is solved
            Toast.makeText(context, "Add code to check puzzleSolved()", Toast.LENGTH_SHORT).show();
            //if (boardManager.puzzleSolved()) {
            //    Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
            //}
        } else {
            Toast.makeText(context, "Invalid Tap: position:" + position , Toast.LENGTH_SHORT).show();
        }
    }
}
