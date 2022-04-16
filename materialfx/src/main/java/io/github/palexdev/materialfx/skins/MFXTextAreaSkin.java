package io.github.palexdev.materialfx.skins;

import io.github.palexdev.materialfx.controls.MFXTextArea;
import io.github.palexdev.materialfx.utils.ScrollUtils;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.skin.TextAreaSkin;

public class MFXTextAreaSkin extends TextAreaSkin {
    public MFXTextAreaSkin(MFXTextArea control) {
        super(control);
        ScrollPane node = ((ScrollPane) getChildren().get(0));
        node.getStyleClass().add("mfx-scroll-pane");
        ScrollUtils.addSmoothScrolling(node);
    }
}
