package com.example.gosen.connect4_2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;

import com.example.gosen.connect4_2.ui.con4game.Con4GameFragment;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Con4GameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private Con4BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> markerButtons;

    /**
     * The calculated column width and Height Based on Device Size
     */
    private static int columnWidth, columnHeight;

    /**
     * The GridView of the Game
     */
    private Con4GridView gridView;

    /**
     * ArrayList where the parts of the image are going to be stored.
     */
    ArrayList<Bitmap> chunckedImages;

    /**
     * Checks the type the Board class is set as.
     *
     * @return whether the Board type is set to Image Tile
     */
    private boolean isImage() {
        return Con4Board.getType().equals("Image tiles");
    }

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(markerButtons, columnWidth, columnHeight));

        /*//autosave
        saveToFile(StartingActivity.SAVE_FILENAME);
        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);*/
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(boardManager);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        //uploadUserBoard(fileName);
    }

    //@Override
    public void update(Observable o, Object arg) {
        System.out.println("Con4GameActivity.update()==========");
        display();
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Con4Board board = boardManager.getBoard();
        markerButtons = new ArrayList<>();
        for (int row = 0; row < Con4Board.NUM_ROWS; row++) {
            for (int col = 0; col < Con4Board.NUM_COLS; col++) {
                Button tmp = new Button(context);
                System.out.println("Add ROW: " + row + "  COL: " + col);
                tmp.setBackgroundResource(board.getMarker(row, col).getBackground());
                this.markerButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Con4Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : markerButtons) {
            int row = nextPos / Con4Board.NUM_COLS;
            int col = nextPos % Con4Board.NUM_COLS;
            System.out.println("ROW: " + row + "  COL: " + col);
            b.setBackgroundResource(board.getMarker(row, col).getBackground());
            nextPos++;
        }
        //final TextView score = findViewById(R.id.score);
        //score.setText(String.valueOf(boardManager.getScore()));
        //saveUserInformationOnDatabase();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = new Con4BoardManager();
        setContentView(R.layout.con4_game_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Con4GameFragment.newInstance())
                    .commitNow();
        }

        createTileButtons(this);
        // set to con4_game_activity
        setContentView(R.layout.con4_game_activity);
        // Add View to activity
        gridView = findViewById(R.id.gridView);
        gridView.setBoardManager(boardManager);
        gridView.setNumColumns(Con4Board.NUM_COLS);
        boardManager.getBoard().addObserver(this);

        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / Con4Board.NUM_COLS;
                        columnHeight = displayHeight / Con4Board.NUM_ROWS;

                        display();
                    }
                });
    }
}
