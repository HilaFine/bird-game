package com.example.birdsGameEx2.Logic;



public class GameManager {
    //todo: change to 5 from 3
    public static final int ROWS = 7;
    public static final int COLS = 5;
    public static final int EMPTY = 0;
    public static final int CAT = 1;
    public static final int COIN = 2;
    public enum GameToasts {CRUSH("The cat caught you!"),
        GAME_OVER("Game Over"),
        COIN("CUPCAKE!! GET 5 POINTS");
        final String toastsString;
        GameToasts (String toastsString){
            this.toastsString = toastsString;
        }
    }
    private int birdLocation;
    private int[][] constraints;
    private int life;
    private int center = 3;
    public GameManager(){
        this.life = 3;
        this.birdLocation = center;
        this.constraints = new int[ROWS][COLS];
    }
    public int getBirdLoction() {return birdLocation;}
    public void setBirdLocation(int col){
        if(col >= 0 && col < COLS){
            birdLocation = col;
        }
    }
    public int getLife() { return life; }
    public String getToastString(GameToasts toast){
        return toast.toastsString;
    }
    public int getConstraintsVisibility(int row, int col) {
        if(checkBoundaries(row, col)){
            return constraints[row][col];
        }
        return -1;
    }
    public void setCatVisible(int row, int col){
        if(checkBoundaries(row, col)){
            constraints[row][col] = CAT;
        }
    }
    public void setConstraintVisible(int constraint, int row, int col){
        if(checkBoundaries(row, col)){
            constraints[row][col] = constraint;
        }
    }
    public void setConstraintInvisible(int row, int col){
        if(checkBoundaries(row, col)){
            constraints[row][col] = EMPTY;
        }
    }
    public void setCoinVisible(int row, int col){
        if(checkBoundaries(row, col)){
            constraints[row][col] = COIN;
        }
    }
    private boolean checkBoundaries(int row, int col){
        return (col<COLS && row<ROWS && col>=0 && row>=0);
    }
    public void initialLife(){ life = 3; }
    public void reduceLife(){
        if(life > 0)
            life--;
    }
    public int birdCenterIndex(){
       return center;
    }
    public int isCrash(){

        return ( constraints[ROWS-1][birdLocation] );
    }
}
