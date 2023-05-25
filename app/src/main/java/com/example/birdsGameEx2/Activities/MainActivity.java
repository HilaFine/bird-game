package com.example.birdsGameEx2.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.birdsGameEx2.Logic.GameManager;
import com.example.birdsGameEx2.Models.Record;
import com.example.birdsGameEx2.Utilities.SignalGenerator;
import com.example.ex1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;


public class MainActivity extends AppCompatActivity {
    private int delay = 1000;

    private boolean gameOver = false;
    AppCompatImageView game_background;
    private FloatingActionButton leftFab;
    private FloatingActionButton rightFab;
    private ShapeableImageView[] hearts;
    private MaterialTextView gameTextViewScore;
    private int score;
    private ShapeableImageView[] birds;
    private ShapeableImageView[][] constraints;
    private GameManager gameManager;
    private long startTime = 0;
    private CountDownTimer timer;
    private MediaPlayer gameOverSound, catSound, cupcakeSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        gameManager = new GameManager();
        initGame();
        Glide
                .with(this)
                .load(R.drawable.background_2)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_background)
                .into(game_background);

    }

    private void startTimer() {
        if (timer == null) {
            timer = new CountDownTimer(999999999, delay) {
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
        setNewConstraint(GameManager.CAT);
    }
    protected void onStop() {
        super.onStop();
        timer.cancel();
    }

    private void updateGame() {
        startTime++;
        changeScore(1);
        updateConstraints();
        if(startTime % 5 == 0)
            setNewConstraint(GameManager.CAT);
        if(startTime % 5 == 3)
            setNewConstraint(GameManager.COIN);
        checkCrash();
    }
    private void updateScoreText(){
        gameTextViewScore.setText("" + score);
    }
    private void updateConstraints(){
        for (int i = GameManager.ROWS - 1; i >= 0; i--) {
            for (int j = 0; j < GameManager.COLS; j++) {
                if (gameManager.getConstraintsVisibility(i, j) != GameManager.EMPTY) {
                    fartherConstraints(i, j);
                }
            }
        }
    }
    private void fartherConstraints(int row, int col) {
        int constraint = gameManager.getConstraintsVisibility(row, col);
        setConstraintsInvisible(row, col);
        if(row != GameManager.ROWS-1)
            setConstraintVisible(constraint, row+1, col);
    }
    private void setConstraintsInvisible(int row, int col){
        constraints[row][col].setVisibility(View.INVISIBLE);
        gameManager.setConstraintInvisible(row, col);
    }
    private void setNewConstraint(int cons) {
        //done
        boolean added = false;
        while(!added) {
            int random = (int) ((Math.random() * GameManager.COLS));
            int constraint = gameManager.getConstraintsVisibility(0, random);
            if (constraint == GameManager.EMPTY){
                setConstraintVisible(cons, 0, random);
                added = true;
            }
        }
    }
    private void setConstraintVisible(int constraint, int row, int col) {
        //DONE
        if(checkBoundaries(row, col) && checkConstraintBound(constraint) && constraint != 0) {
            if(constraint == GameManager.CAT){
                constraints[row][col].setImageResource(R.drawable.happy);
            }
            if(constraint == GameManager.COIN){
                constraints[row][col].setImageResource(R.drawable.cupcake);
            }
            constraints[row][col].setVisibility(View.VISIBLE);
            gameManager.setConstraintVisible(constraint, row, col);
        }
    }
    private boolean checkBoundaries(int row, int col){
        //DONE
        return (col<GameManager.COLS && row<GameManager.ROWS && col>=0 && row>=0);
    }
    private boolean checkConstraintBound(int constraint){
        //DONE
        return (constraint > 0 && constraint < 3);
    }
    private void initGame() {
        this.score = 0;
        initGameFromIntent();
        initBirds();
        initConstraints();
        initFABs();
    }

    private void initGameFromIntent() {
        Intent intent = getIntent();
        if(intent.getIntExtra("speed",0 ) == MenuActivity.SLOW){
            delay = 1500;
        }
        if(intent.getIntExtra("speed",0 ) == MenuActivity.FAST){
            delay = 800;
        }

    }

    private void initConstraints() {
        for (int i = 0; i < GameManager.ROWS; i++) {
            for (int j = 0; j < GameManager.COLS; j++) {
                constraints[i][j].setVisibility(View.INVISIBLE);
            }
        }
    }
    private void initBirds() {
        for(int i = 0; i < GameManager.COLS; i++){
            if(i != gameManager.birdCenterIndex())
                birds[i].setVisibility(View.INVISIBLE);
            else
                birds[i].setVisibility(View.VISIBLE);
        }
    }
    private void initFABs() {
        rightFab.setOnClickListener(v -> clickRight());
        leftFab.setOnClickListener(v -> clickLeft());
    }

    private void clickLeft() {
        //done
        int birdLocation = gameManager.getBirdLoction();
        if(birdLocation > 0 && birdLocation < GameManager.COLS)
            moveLeft();
    }

    private void clickRight() {
        //done
        int birdLocation = gameManager.getBirdLoction();
        if(birdLocation >= 0 && birdLocation < GameManager.COLS -1)
            moveRight();
    }

    private void moveLeft() {
        //done
        int birdLocation = gameManager.getBirdLoction();
        gameManager.setBirdLocation(birdLocation-1);
        for(int i = 0; i < GameManager.COLS; i++){
            if(i != birdLocation-1)
                birds[i].setVisibility(View.INVISIBLE);
            else
                birds[i].setVisibility(View.VISIBLE);
        }
    }

    private void moveRight() {
        //done
        int birdLocation = gameManager.getBirdLoction();
        gameManager.setBirdLocation(birdLocation+1);
        for(int i = 0; i < GameManager.COLS; i++){
            if(i != birdLocation+1)
                birds[i].setVisibility(View.INVISIBLE);
            else
                birds[i].setVisibility(View.VISIBLE);
        }
    }

    private void checkCrash() {
        int isCrash = gameManager.isCrash();
        if (isCrash == GameManager.CAT) {
            toastCrash();
            vibrate();
            reduceLife();
        }
        else if(isCrash == GameManager.COIN){
            toastCoin();
            changeScore(5);
        }
    }
    public void changeScore(int i){
        score += i;
        updateScoreText();
    }

    private void toastCrash() {
        SignalGenerator.getInstance().toast(gameManager.getToastString(GameManager.GameToasts.CRUSH), Toast.LENGTH_LONG);
        //Toast.makeText(getApplicationContext(), gameManager.getToastString(GameManager.GameToasts.CRUSH), Toast.LENGTH_LONG).show();
    }

    private void toastGameOver() {
        SignalGenerator.getInstance().toast(gameManager.getToastString(GameManager.GameToasts.GAME_OVER), Toast.LENGTH_LONG);
        //Toast.makeText(getApplicationContext(), gameManager.getToastString(GameManager.GameToasts.GAME_OVER), Toast.LENGTH_LONG).show();
    }
    private void toastCoin() {
        SignalGenerator.getInstance().toast(gameManager.getToastString(GameManager.GameToasts.COIN), Toast.LENGTH_LONG);
        //Toast.makeText(getApplicationContext(), gameManager.getToastString(GameManager.GameToasts.COIN), Toast.LENGTH_LONG).show();
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
        timer.cancel();
        Record record = new Record();
        record.setScore(score);
        gameOverSound.start();
        toastGameOver();
        gameOver = true;
    }

    private void vibrate() {
        SignalGenerator.getInstance().vibrate(500);
    }
    private void saveRecord(){
    }
    private void findViews() {
        game_background = (AppCompatImageView) findViewById(R.id.game_background);
        gameOverSound = MediaPlayer.create(this, R.raw.game_over);
        catSound = MediaPlayer.create(this, R.raw.cat);
        cupcakeSound = MediaPlayer.create(this, R.raw.yummy);
        leftFab = findViewById(R.id.game_fabLeft);
        rightFab = findViewById(R.id.game_fabRight);
        gameTextViewScore = findViewById(R.id.main_score);
        hearts = new ShapeableImageView[]{
                findViewById(R.id.game_heart1),
                findViewById(R.id.game_heart2),
                findViewById(R.id.game_heart3)};
        birds = new ShapeableImageView[]{
                findViewById(R.id.bird0),
                findViewById(R.id.bird1),
                findViewById(R.id.bird2),
                findViewById(R.id.bird3),
                findViewById(R.id.bird4)};
        constraints = new ShapeableImageView[GameManager.ROWS][GameManager.COLS];
        for(int i = 0; i < GameManager.ROWS; i++){
            for(int j = 0; j < GameManager.COLS; j++){
                String stringNameID = "cat_row" + i + "_col" + j;
                int viewId = this.getResources().getIdentifier(stringNameID,"id", this.getPackageName());
                constraints[i][j] = findViewById(viewId);
            }
        }
    }
}