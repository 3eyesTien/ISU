package com.thgame.isu.ui;

// libGdx rectangle class uses bottom-left corner centered
// This Box class will have the x,y start at the center of the box
// So x would be the midpoint of the width, and y the midpoint of the height of the box

public class Box {

    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public boolean contains(float x, float y){
        // Draw out this collision statement if it's confusing to get a better view/grasp!
        return x > this.x - width/2 &&
                x < this.x + width/2 &&
                y > this.y - height/2 &&
                y < this.y + height/2;
    }

    // Getter methods
    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }

    // Setter methods
    public void setX(float newX) {this.x = newX; }
    public void setY(float newY) {this.y = newY;}
    public void setWidth(float newW) { this.width = newW; }
    public void setHeight(float newH) {this.height = newH;}
}