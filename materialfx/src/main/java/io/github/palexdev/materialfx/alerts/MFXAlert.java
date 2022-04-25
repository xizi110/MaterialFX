package io.github.palexdev.materialfx.alerts;

import io.github.palexdev.materialfx.MFXResourcesLoader;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.util.Locale;

import static io.github.palexdev.materialfx.alerts.MFXAlert.Type.INFO;

/**
 * 警告弹窗，请使用 MFXAlerts.show() 调用
 *
 * @author zhong
 * @date 2022-04-22 14:38:51 周五
 */
public class MFXAlert extends HBox {

    private final String STYLE_CLASS = "mfx-alert";
    private final String STYLESHEET = MFXResourcesLoader.load("css/MFXAlert.css");
    private StringProperty content = new SimpleStringProperty();

    public String getContent() {
        return content.get();
    }

    public StringProperty contentProperty() {
        return content;
    }

    public void setContent(String content) {
        this.content.set(content);
    }

    public MFXAlert(String content) {
        this(content, INFO);
    }

    public MFXAlert(String content, Type type) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MFXResourcesLoader.loadURL("fxml/alert.fxml"));
        loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getStyleClass().setAll(STYLE_CLASS, STYLE_CLASS + "-" + type.name().toLowerCase(Locale.ROOT));
        setContent(content);
    }

    @FXML
    private Label contentLabel;

    @FXML
    private MFXButton closeBtn;

    public void initialize() {
        closeBtn.setOnMouseClicked(event -> {
            if (onClose != null) {
                onClose.run();
            }
        });
        contentLabel.textProperty().bind(contentProperty());
    }

    private Runnable onClose;

    public void setOnClose(Runnable onClose) {
        this.onClose = onClose;
    }

    /**
     * 警告提示类型
     */
    private Type type;

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String getUserAgentStylesheet() {
        return STYLESHEET;
    }

    public enum Type {
        INFO, ERROR, WARNING, SUCCESS
    }
}
