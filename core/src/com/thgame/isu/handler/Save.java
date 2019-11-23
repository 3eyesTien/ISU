package com.thgame.isu.handler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.thgame.isu.stages.PlayStage;

import java.io.BufferedReader;

public abstract class Save {

    private static final String path = "save.dat";
    private static int[] difficultyScores; // High scores for the respective difficulties

    public static void save(){
        if(difficultyScores == null){
            difficultyScores = new int[PlayStage.DIFFICULTY.values().length];
        }

        FileHandle file = Gdx.files.local(path);
        StringBuilder sBuilder = new StringBuilder();

        for(int i = 0; i < difficultyScores.length; i++){
            sBuilder.append(difficultyScores[i]);
            sBuilder.append("\n");
        }

        file.writeString(sBuilder.toString(), false);
    }

    public static void load(){
        FileHandle file = Gdx.files.internal(path);

        if(!file.exists()){
            save();
            return;
        }

        difficultyScores = new int[PlayStage.DIFFICULTY.values().length];
        BufferedReader bReader = file.reader(1024);
        for(int i = 0; i < difficultyScores.length; i++){
            try{
                int score = Integer.parseInt(bReader.readLine());
                difficultyScores[i] = score;
            }
            catch (Exception e){
                save();
                return;
            }
        }
    }

    public static void set(int i, int score){
        if(difficultyScores[i] == 0 || score > difficultyScores[i]){
            difficultyScores[i] = score;
        }
    }

    public static int[] getDifficultyScores(){
        return difficultyScores;
    }
}