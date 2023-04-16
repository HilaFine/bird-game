package com.example.ex1.Logic;



public class GameManager {
    public static final int ROWS = 7;
    public static final int COLS = 3;

    public enum GameToasts {CRUSH("The cat caught you!"),
        GAME_OVER("Game Over");
        final String toastsString;
        GameToasts (String toastsString){
            this.toastsString = toastsString;
        }
    }

    private boolean center, right, left;
    private final int leftCol = 0;
    private final int conterCol = 1;
    private final int rightCol = 2;
    private boolean[][] catsVisibility;
    private int life;

    public GameManager(){
        this.life = 3;
        this.center = true;
        this.left = false;
        this.right = false;
        this.catsVisibility = new boolean[COLS][ROWS];
    }
    public boolean getCenter() { return center; }
    public boolean getRight() { return right; }
    public boolean getLeft() { return left; }
    public int getLife() { return life; }
    public String getToastString(GameToasts toast){
        return toast.toastsString;
    }
    public boolean getCatsVisibility(int col, int row) {
        if(checkBoundaries(col, row)){
            return catsVisibility[col][row];
        }
        return false;
    }
    public int getBirdColLocation(){
        if(left)
            return 0;
        if(center)
            return 1;
        else
            return 2;
    }

    public void setCatVisible(int col, int row){
        if(checkBoundaries(col, row)){
            catsVisibility[col][row] = true;
        }
    }
    public void setCatInvisible(int col, int row){
        if(checkBoundaries(col, row)){
            catsVisibility[col][row] = false;
        }
    }
    private boolean checkBoundaries(int col, int row){
        return (col<COLS && row<ROWS && col>=0 && row>=0);
    }

    public void initialLife(){ life = 3; }
    public void moveLeft(){
        left = true;
        center = false;
        right = false;
    }
    public void moveCenter(){
        left = false;
        center = true;
        right = false;
    }
    public void moveRight(){
        left = false;
        center = false;
        right = true;
    }
    public void reduceLife(){
        if(life > 0)
            life--;
    }
    public boolean isCrash(){
        return(checkCrashLeft() || checkCrashCenter() || checkCrashRight());
    }
    private boolean checkCrashLeft(){
        return (left && catsVisibility[leftCol][ROWS-1]);
    }
    private boolean checkCrashCenter(){
        return (center && catsVisibility[conterCol][ROWS-1]);
    }
    private boolean checkCrashRight(){
        return (right && catsVisibility[rightCol][ROWS-1]);
    }
}
