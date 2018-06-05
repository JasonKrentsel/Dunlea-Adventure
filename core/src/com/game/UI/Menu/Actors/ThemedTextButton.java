package com.game.UI.Menu.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ThemedTextButton extends TextButton {
    public static final BitmapFont font = new BitmapFont((Gdx.files.internal("Menu/Font/font.fnt")));

    public ThemedTextButton(String text) {
        super(text, new TextButtonStyle(new TextureRegionDrawable(new TextureRegion(new Texture("Menu/button.png"))),null,null,font));
        final TextButtonStyle a = new TextButtonStyle(new TextureRegionDrawable(new TextureRegion(new Texture("Menu/button.png"))),null,null,font);
        final TextButtonStyle s = new TextButtonStyle(new TextureRegionDrawable(new TextureRegion(new Texture("Menu/buttonHover.png"))),null,null,font);
        addListener(new InputListener(){
            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                setStyle(s);
            }
            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                setStyle(a);
            }
        });
    }
}
