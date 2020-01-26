package com.thgame.isu.stages;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.thgame.isu.GameManager;
import com.thgame.isu.ISU;
import com.thgame.isu.handler.Content;
import com.thgame.isu.ui.Incrementor;
import com.thgame.isu.ui.SelectionGlow;
import com.thgame.isu.ui.TextImage;
import com.thgame.isu.ui.Tile;

public class PlayStage extends Stage{

    public enum DIFFICULTY{
        EASY,
        NORMAL,
        HARD;
    }

    private DIFFICULTY difficulty;
    private int[] args;

    // Max amount of touch that can be input at once
    private final int MAX_TOUCH = 2;

    // Board (but not boring) stuff
    private Tile[][] tiles;
    private int tileSize;
    private float boardHeightOffset;
    private int boardHeight;

    // The tiles the player is trying to hit
    private Array<Tile> targets;

    // The tiles the player selected
    private Array<Tile> selectedTiles;
    // Selection Glow indicator
    private Array<SelectionGlow> glows;

    // Timers
    private float roundTimer;
    private float roundStartTimer;
    private float increaseTargetTimer;   // Timer to keep track of when to increase number of targets throughout the round
    private TextImage timerText;
    private Incrementor time;

    // Score
    private Incrementor score;
    private TextImage scoreText;

    private boolean gameReady; // The game is ready to take player input

    private int currentNumTargets;  // Number of available targets
    private int currentTargetMax;   // Number of targets according to the difficulty for the round
    private int maxTargetNum;       // Max number of targets on the board at once for the entire round

    private TextImage backButton;

    public PlayStage(GameManager gm, DIFFICULTY difficulty){
        super(gm);

        this.difficulty = difficulty;
        targets = new Array<Tile>();
        selectedTiles = new Array<Tile>();
        glows = new Array<SelectionGlow>();

        roundStartTimer = 1;
        roundTimer = 30;
        increaseTargetTimer = 5;

        //scoreText = new TextImage("score", ISU.WIDTH / 2, ISU.HEIGHT - 50, 30);
        //score = new Score(ISU.WIDTH - (ISU.WIDTH / 2), ISU.HEIGHT - 125, 30);

        timerText = new TextImage("time", 100, ISU.HEIGHT - 125, 30);
        time = new Incrementor(ISU.WIDTH - 200, ISU.HEIGHT - 125, 30);
        time.setText(Integer.toString((int) roundTimer));

        scoreText = new TextImage("score", 100, ISU.HEIGHT - 50, 30);
        score = new Incrementor(ISU.WIDTH - 200, ISU.HEIGHT - 50, 30);

        backButton = new TextImage("back", ISU.WIDTH / 2, 90, 30);

        gameReady = false;

        args = getArgs();
        createBoard(args[0], args[1]);

        createTargets(currentNumTargets);
    }

    private int[] getArgs(){
        //ret[0] is num rows
        //ret[1] is num cols

        int[] ret = new int[2];
        if(difficulty == DIFFICULTY.EASY){
            ret[0] = 3;
            ret[1] = 3;
            currentNumTargets = 2;
            currentTargetMax = 2;
            maxTargetNum = 4;
        }
        else if(difficulty == DIFFICULTY.NORMAL){
            ret[0] = 4;
            ret[1] = 4;
            currentTargetMax = 2;
            currentNumTargets = 2;
            maxTargetNum = 5;
        }
        else if(difficulty == DIFFICULTY.HARD){
            ret[0] = 6;
            ret[1] = 6;
            currentTargetMax = 3;
            currentNumTargets = 3;
            maxTargetNum = 5;
        }

        return ret;
    }

    private void createBoard(int numRows, int numCols){

        // 480 width x 800 height
        tiles = new Tile[numRows][numCols];

        // tiles.length is num of rows
        // tiles[0].length is num of cols
        tileSize = ISU.WIDTH / tiles[0].length;
        boardHeight = tileSize * tiles.length;
        boardHeightOffset = (ISU.HEIGHT - boardHeight) / 2;

        for(int row = 0; row < tiles.length; row++){

            for(int col = 0; col < tiles[0].length; col++){
                // Create a new tile and add it to the 2D array to create a board
                tiles[row][col] = new Tile(
                        col * tileSize + tileSize/2,
                        row * tileSize + boardHeightOffset + tileSize/2,
                        tileSize, tileSize);

                float delayTimer = 0.07f;

                /* Mathematically determine how squares animate into space */
                // This one has the tiles start from the left-most column and expand to the right
                // tiles[row][col].setTimer((-(tiles.length + col)) * delayTimer);

                // This one has the tiles start from the top-most row and expand down
                tiles[row][col].setTimer((-(tiles.length - row)) * delayTimer);

                // This one has the tiles come from the south-west corner and expands north-east
                //tiles[row][col].setTimer((-(tiles.length + row) - col) * delayTimer);
            }
        }
    }

    public void createTargets(int numCurrentTargets) {

        selectedTiles.clear();

        for(int i = 0; i < numCurrentTargets; i++){

            int row = 0;
            int col = 0;

            // Randomly choose a tile in the 2D array
            // If the tile is already delegated a target, reroll for another tile to be delegated
            do{

                row = MathUtils.random(tiles.length - 1);
                col = MathUtils.random( tiles[0].length - 1);
            } while(targets.contains(tiles[row][col], true));

            // Add the tile to the targets array
            targets.add(tiles[row][col]);

            tiles[row][col].setSolution(true);
        }
    }

    public void checkReady(float dt){
        if(!gameReady){
            roundStartTimer -= dt;
            if(roundStartTimer <= 0) {
                roundStartTimer = 0;
                gameReady = true;

                for(int i = 0; i < targets.size; i++){
                    targets.get(i).setLitUp(true);
                    targets.get(i).setLifeSpan(4);
                }
            }
        }
    }

    public void updateTargets(){

        if(gameReady){
            for(int i = 0; i < targets.size; i++){
                if(targets.get(i).getLifeSpan() <= 0){
                    targets.get(i).setSolution(false);
                    targets.get(i).setLitUp(false);
                    targets.removeIndex(i);
                }
            }
        }
    }

    public void updateClock(float dt){

        if(gameReady){
            roundTimer -= dt;

            increaseTargetTimer -= dt;
            if(roundTimer <= 0) { roundTimer = 0;}

            // Increase Target count based on how much time has elapsed in the round
            if(increaseTargetTimer <= 0 && currentTargetMax < maxTargetNum){
                currentTargetMax++;
                increaseTargetTimer = 5;
            }
        }
    }

    @Override
    public void handleInput() {

        if (Gdx.input.justTouched()) {
            mouse.x = Gdx.input.getX(); // Screen coordinates
            mouse.y = Gdx.input.getY();
            cam.unproject(mouse, gamePort.getScreenX(), gamePort.getScreenY(), gamePort.getScreenWidth(), gamePort.getScreenHeight());

            if (backButton.contains(mouse.x, mouse.y)) {
                gm.setStage(new TransitionStage(gm, this, new DifficultyStage(gm), TransitionStage.Type.EXPAND));
            }

            for (int i = 0; i < MAX_TOUCH; i++) {
                mouse.x = Gdx.input.getX(i);
                mouse.y = Gdx.input.getY(i);
                cam.unproject(mouse);

                if (Gdx.input.isTouched(i) && gameReady) {
                    // Keeps tile selecting from going "Array out of bounds"
                    if (mouse.x > 0 && mouse.x < ISU.WIDTH && mouse.y > boardHeightOffset && mouse.y < boardHeightOffset + boardHeight) {
                        int row = (int) ((mouse.y - boardHeightOffset) / tileSize);
                        int col = (int) (mouse.x / tileSize);

                        if (!targets.contains(tiles[row][col], true)) {   // Decrement score if wrong
                            tiles[row][col].setErrorSelection(true);
                            int decrement = -5;
                            score.incrementValue(decrement);
                            Content.playIncorrect();

                        } else if (targets.contains(tiles[row][col], true)) {
                            tiles[row][col].setSolution(false);
                            tiles[row][col].setLitUp(false);
                            int increment = (int) (tiles[row][col].getLifeSpan() * 5);  // Increment score by the lifespan that's left * 5
                            score.incrementValue(increment);
                            Content.playCorrect();
                            glows.add(new SelectionGlow(tiles[row][col].getX(), tiles[row][col].getY(), tileSize, tileSize));

                            targets.removeValue(tiles[row][col], true);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void update(float dt) {

        checkReady(dt);
        updateTargets();
        updateClock(dt);
        handleInput();
        score.update(dt);
        time.setText(Integer.toString((int)roundTimer));

        /*
        // End of the Round
        if(roundTimer <= 0) {
            int[] tempScores = Save.getDifficultyScores();  //Load scores for difficulties
            switch (difficulty){
                case EASY:
                    if(score.getScore() > tempScores[0]) {
                        Save.set(0, score.getScore());
                    }
                    break;

                case NORMAL:
                    if(score.getScore() > tempScores[1]) {
                        Save.set(1, score.getScore());
                    }
                    break;

                case HARD:
                    if(score.getScore() > tempScores[2]){
                        Save.set(2, score.getScore());
                    }
                    break;

                default:
                    break;
            }

            Save.save();
            gm.setStage(new TransitionStage(gm, this, new ScoreStage(gm, score.getScore()), TransitionStage.Type.EXPAND));
        }
        */

        if(roundTimer <= 0){
            switch(difficulty){
                case EASY:
                    if(score.getScore() > Content.getEasyScore()){
                        Content.setEasyScore(score.getScore());
                    }
                    break;
                case NORMAL:
                    if(score.getScore() > Content.getNormalScore()){
                        Content.setNormalScore(score.getScore());
                    }
                    break;
                case HARD:
                    if(score.getScore() > Content.getHardScore()){
                        Content.setHardScore(score.getScore());
                    }
                    break;
                default:
                    break;
            }
            gm.setStage(new TransitionStage(gm, this, new ScoreStage(gm, score.getScore()), TransitionStage.Type.EXPAND));
        }

        // Handle Glows
        for (int i = 0; i < glows.size; i++) {
            glows.get(i).update(dt);

            if (glows.get(i).shouldRemove()) {
                glows.removeIndex(i);
                i--;
            }
        }

        // Update all the tiles in the board
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                tiles[row][col].update(dt);
            }
        }

        if (targets.size < currentTargetMax) {
            createTargets(currentTargetMax - targets.size);
            for (int i = 0; i < targets.size; i++) {
                targets.get(i).setLitUp(true);
                targets.get(i).setLifeSpan(4);
            }
        }
    }

    @Override
    public void render(SpriteBatch batch){

        batch.setProjectionMatrix(cam.combined);
        batch.begin();

        for(int row = 0; row < tiles.length; row++){
            for(int col = 0; col < tiles[0].length; col++){
                tiles[row][col].render(batch);
            }
        }

        scoreText.render(batch);
        score.render(batch);
        timerText.render(batch);
        time.render(batch);
        backButton.render(batch);

        // Render glows
        for(int i = 0; i < glows.size; i++){
            glows.get(i).render(batch);
        }

        batch.end();

        resize(batch);
    }
}
