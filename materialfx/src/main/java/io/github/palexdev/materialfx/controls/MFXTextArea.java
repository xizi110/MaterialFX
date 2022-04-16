package io.github.palexdev.materialfx.controls;

import io.github.palexdev.materialfx.MFXResourcesLoader;
import io.github.palexdev.materialfx.skins.MFXTextAreaSkin;
import javafx.scene.control.Skin;
import javafx.scene.control.TextArea;

public class MFXTextArea extends TextArea {
    //================================================================================
    // Properties
    //================================================================================
    private final String STYLE_CLASS = "mfx-text-area";
    private final String STYLESHEET = MFXResourcesLoader.load("css/MFXTextArea.css");

    public MFXTextArea() {
        getStyleClass().add(STYLE_CLASS);
    }

    public MFXTextArea(String text) {
        super(text);
        getStyleClass().add(STYLE_CLASS);
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new MFXTextAreaSkin(this);
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }
}
