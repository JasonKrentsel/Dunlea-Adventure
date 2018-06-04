package com.game.UI.Menu.Actors;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class ThemedTextButton extends ImageButton {
    String text;
    BitmapFont font;
    public ThemedTextButton(String text, ImageButtonStyle style, BitmapFont font) {
        super(style);
        this.text = text;
        this.font = font;
    }

    @Override
    public void draw(Batch batch, float paremtAlpha){
        super.draw(batch,paremtAlpha);
        font.draw(batch,text,getX()+getWidth()/2,getY()+getHeight()/2,getWidth(),text.length(),false);
    }
}
