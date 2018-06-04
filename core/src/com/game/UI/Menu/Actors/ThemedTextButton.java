package com.game.UI.Menu.Actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class ThemedTextButton extends TextButton {
    public ThemedTextButton(String text) {
        super(text, new TextButtonStyle(new TextureRegionDrawable(new TextureRegion(new Texture("Menu/button.png"))),new TextureRegionDrawable(new TextureRegion(new Texture("Menu/buttonHover.png"))),null,new BitmapFont(new FileHandle("Menu/Font/font.fnt"))));
    }
}
