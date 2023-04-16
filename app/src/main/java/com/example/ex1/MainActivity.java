package com.example.ex1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ex1.Logic.GameManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;



public class MainActivity extends AppCompatActivity {
    private final int DELAY = 1000;

    private boolean gameOver = false;
    AppCompatImageView game_background;
    private FloatingActionButton leftFab;

    private FloatingActionButton rightFab;

    private ShapeableImageView[] hearts;

    private ShapeableImageView[] birds;

    private ShapeableImageView[][] cats;

    private GameManager gameManager;

    private long startTime = 0;

    private CountDownTimer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initGame();
        Glide
                .with(this)
                .load("https://e0.pxfuel.com/wallpapers/270/390/desktop-wallpaper-picsart-blur-nature-background.jpg")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(game_background);
        gameManager = new GameManager();
    }

    private void startTimer() {
        if (timer == null) {
            timer = new CountDownTimer(999999999, DELAY) {
                @Override
                public void onTick(long millisUntilFinished) {
                    Log.d("run in StartTime:", "" + System.currentTimeMillis() + " Thread name: " + Thread.currentThread().getName());
                    updateGame();
                }

                @Override
                public void onFinish() {
                    timer.cancel();
                }
            }.start();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTimer();
        setNewCat();
    }
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    private void updateGame() {
        startTime++;
        movingCats();
        checkCrash();
    }

    private void movingCats() {
        fartherCats();
        if( startTime % 3 == 0)
            setNewCat();

    }

    private void fartherCats() {
        for (int i = 0; i < GameManager.COLS; i++) {
            for (int j = GameManager.ROWS - 1; j >= 0; j--) {
                if(gameManager.getCatsVisibility(i, j))
                    fartherCatRowCol(i, j);
            }
        }
    }

    private void fartherCatRowCol(int col, int row) {
            setCatInvisible(col, row);
            //we farther the cat location only if its not on the last row and if the bird is not there
            if (row != (GameManager.ROWS-1))
                setCatVisible(col, row + 1);
    }
    //(&& row != (GameManager.ROWS-2) || (row == (GameManager.ROWS-2) &&(gameManager.getBirdColLocation()!=col)

    private void setNewCat() {
        boolean added = false;
        while(!added) {
            int random = (int) ((Math.random() * 3));
            if (!gameManager.getCatsVisibility(random, 0)){
                setCatVisible(random, 0);
                added = true;}
        }
    }

    private void initGame() {
        initBirds();
        initCats();
        initFABs();
    }

    private void initCats() {
        for (int i = 0; i < GameManager.COLS; i++) {
            for (int j = 0; j < GameManager.ROWS; j++) {
                cats[i][j].setVisibility(View.INVISIBLE);
            }
        }
    }

    private void initBirds() {
        birds[0].setVisibility(View.INVISIBLE);
        birds[2].setVisibility(View.INVISIBLE);
    }

    private void initFABs() {
        rightFab.setOnClickListener(v -> clickRight());
        leftFab.setOnClickListener(v -> clickLeft());
    }

    private void clickLeft() {
        if (gameManager.getCenter())
            moveToLeft();
        if (gameManager.getRight())
            moveToCenter();
    }

    private void clickRight() {
        if (gameManager.getCenter())
            moveToRight();
        if (gameManager.getLeft())
            moveToCenter();
    }

    private void moveToLeft() {
        gameManager.moveLeft();
        birds[0].setVisibility(View.VISIBLE);
        birds[1].setVisibility(View.INVISIBLE);
        birds[2].setVisibility(View.INVISIBLE);
    }

    private void moveToCenter() {
        gameManager.moveCenter();
        birds[0].setVisibility(View.INVISIBLE);
        birds[1].setVisibility(View.VISIBLE);
        birds[2].setVisibility(View.INVISIBLE);
    }

    private void moveToRight() {
        gameManager.moveRight();
        birds[0].setVisibility(View.INVISIBLE);
        birds[1].setVisibility(View.INVISIBLE);
        birds[2].setVisibility(View.VISIBLE);
    }

    private void setCatVisible(int col, int row) {
        cats[col][row].setVisibility(View.VISIBLE);
        gameManager.setCatVisible(col, row);
    }

    private void setCatInvisible(int col, int row) {
        cats[col][row].setVisibility(View.INVISIBLE);
        gameManager.setCatInvisible(col, row);
    }

    private void checkCrash() {
        if (gameManager.isCrash()) {
            toastCrash();
            vibrate();
            reduceLife();
        }
    }

    private void toastCrash() {
        Toast.makeText(getApplicationContext(), gameManager.getToastString(GameManager.GameToasts.CRUSH), Toast.LENGTH_LONG).show();
    }

    private void toastGameOver() {
        Toast.makeText(getApplicationContext(), gameManager.getToastString(GameManager.GameToasts.GAME_OVER), Toast.LENGTH_LONG).show();
    }

    private void reduceLife() {
        if (gameManager.getLife() == 3)
            hearts[0].setVisibility(View.INVISIBLE);
        if (gameManager.getLife() == 2)
            hearts[1].setVisibility(View.INVISIBLE);
        if (gameManager.getLife() == 1)
            hearts[2].setVisibility(View.INVISIBLE);
        gameManager.reduceLife();
        if (gameManager.getLife() == 0)
            gameOver();
    }

    private void gameOver() {
        //toastGameOver();
        gameOver = true;
    }

    private void vibrate() {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    private void findViews() {
        game_background = (AppCompatImageView) findViewById(R.id.game_background);
        leftFab = findViewById(R.id.game_fabLeft);
        rightFab = findViewById(R.id.game_fabRight);
        hearts = new ShapeableImageView[]{
                findViewById(R.id.game_heart1),
                findViewById(R.id.game_heart2),
                findViewById(R.id.game_heart3)};
        birds = new ShapeableImageView[]{
                findViewById(R.id.bird0),
                findViewById(R.id.bird1),
                findViewById(R.id.bird2)};
        cats = new ShapeableImageView[][]{
                {findViewById(R.id.cat_row0_col0), findViewById(R.id.cat_row1_col0), findViewById(R.id.cat_row2_col0)
                        , findViewById(R.id.cat_row3_col0), findViewById(R.id.cat_row4_col0), findViewById(R.id.cat_row5_col0)
                        , findViewById(R.id.cat_row6_col0)},

                {findViewById(R.id.cat_row0_col1), findViewById(R.id.cat_row1_col1), findViewById(R.id.cat_row2_col1)
                        , findViewById(R.id.cat_row3_col1), findViewById(R.id.cat_row4_col1), findViewById(R.id.cat_row5_col1)
                , findViewById(R.id.cat_row6_col1)},

                {findViewById(R.id.cat_row0_col2), findViewById(R.id.cat_row1_col2), findViewById(R.id.cat_row2_col2)
                        , findViewById(R.id.cat_row3_col2), findViewById(R.id.cat_row4_col2), findViewById(R.id.cat_row5_col2)
                        , findViewById(R.id.cat_row6_col2)}};
    }
}