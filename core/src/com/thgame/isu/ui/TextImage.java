package com.thgame.isu.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.thgame.isu.handler.Content;

public class TextImage extends Box{

    private TextureRegion[][] fontSheet;
    private String text;
    private int splitSize;       // Size of each char in spritesheet.  Use this value for splitting the font sheet
    private float charSize;   // Size of the char when displayed onscreen

    public TextImage(String text, float x, float y, float newSize){

        this.text = text;
        this.x = x;
        this.y = y;

        this.charSize = newSize;
        splitSize = 50;
        setText(text);
        TextureRegion sheet = Content.getAtlas("pack").findRegion("fontsheet");
        fontSheet = sheet.split(splitSize, splitSize);

        /*        int numCols = sheet.getRegionWidth() / size;
        int numRows = sheet.getRegionHeight() / size;
        fontSheet = new TextureRegion[numRows][numCols];

        for(int row = 0; row < numRows; row++){
            for(int col = 0; col < numCols; col++){
                //(full sheet texture, x, y, width height)
                fontSheet[row][col] = new TextureRegion(sheet, size * col, size * row, size, size);
            }
        }
*/
    }

    public void setText(String text){
        this.text = text;

        width = charSize * text.length();
        height = charSize;
    }

    public void render(SpriteBatch batch){
        for(int i = 0; i < text.length(); i++){
            char c = text.charAt(i);

            if(c >= 'a' && c <= 'z'){
                c -= 'a';
            } else if (c >= '0' && c <= '9'){
                c -= '0';
                c += 27;    // Because the fontsheet starts at A
            }

            int index = (int) c;
            int row = index / fontSheet[0].length;
            int col = index % fontSheet[0].length;

            // (fontSheet, x + 54 pixels for each proceeding character, y)
            batch.draw(fontSheet[row][col], this.x - width / 2  + (charSize + 4) * i, this.y - height / 2, width / text.length(), height);
            //batch.draw(fontSheet[row][col], x - width / 2 + 54 * (i), y - height / 2);
        }
    }

    public float getTextLength(){
        return text.length();
    }
}