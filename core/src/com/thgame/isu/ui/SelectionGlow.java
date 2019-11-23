package com.thgame.isu.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SelectionGlow extends Tile{

    private float alpha;
    private float speed = 125;

    private boolean remove;

    public SelectionGlow(float x, float y, float width, float height){
        super(x, y, width, height);
        this.width = width;
        this.height = height;
    }

    public boolean shouldRemove(){
        return remove;
    }

    public void update(float dt){

        timer += dt;
        width += dt * speed;
        height += dt * speed;
        if(timer >= maxTime) {remove = true;}
    }

    public void render(SpriteBatch batch){

        alpha = 1 - (timer / maxTime);

        batch.setColor(1, 1, 1, alpha);
        //batch.draw(light, x - width / 8, y - height / 8, width, height);
        batch.draw(light, x - width / 2, y - height / 2, width, height);
        batch.setColor(1, 1, 1, 1);
    }
}