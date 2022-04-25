import io.github.palexdev.materialfx.alerts.MFXAlert;
import io.github.palexdev.materialfx.alerts.MFXAlerts;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXSimpleNotification;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.effects.DepthLevel;
import io.github.palexdev.materialfx.enums.ButtonType;
import io.github.palexdev.materialfx.font.MFXFontIcon;
import io.github.palexdev.materialfx.notifications.base.INotification;
import io.github.palexdev.materialfx.utils.ColorUtils;
import io.github.palexdev.materialfx.utils.RandomUtils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

public class AlertTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		StackPane stackPane = new StackPane();

		MFXButton button = new MFXButton("SHOW");
		button.backgroundProperty().addListener((observable, oldValue, newValue) -> {
			List<BackgroundFill> fills = newValue.getFills();
			Paint[] colors = fills.stream().map(BackgroundFill::getFill).toArray(Paint[]::new);
			System.out.println(Arrays.toString(colors));
		});
		button.setPrefSize(180, 36);
		button.setButtonType(ButtonType.RAISED);
		button.setDepthLevel(DepthLevel.LEVEL1);

		button.setOnAction(event -> {
			MFXAlerts.show(stackPane, "https://docs.gradle.org/7.3.3/userguide/command_line_interface.html#sec:command_line_warnings",
					MFXAlert.Type.INFO, Pos.TOP_CENTER);
		});

		stackPane.getChildren().add(button);
		Scene scene = new Scene(stackPane, 800, 800);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private INotification createDummyNotification() {
		MFXTextField label = MFXTextField.asLabel("Random Label n." + RandomUtils.random.nextInt());
		label.setLeadingIcon(MFXFontIcon.getRandomIcon(32, ColorUtils.getRandomColor()));
		label.setAlignment(Pos.CENTER_LEFT);
		label.setMaxWidth(Double.MAX_VALUE);
		HBox.setHgrow(label, Priority.ALWAYS);

		MFXTextField time = MFXTextField.asLabel();
		time.setAlignment(Pos.CENTER_RIGHT);

		HBox box = new HBox(label, time);
		box.setMinSize(450, 100);
		box.setStyle("-fx-background-color: white");
		box.setAlignment(Pos.CENTER_LEFT);
		MFXSimpleNotification notification = new MFXSimpleNotification(box);
		notification.setOnUpdateElapsed((longElapsed, stringElapsed) -> Platform.runLater(() -> time.setText(stringElapsed)));
		time.setText(notification.getTimeToStringConverter().apply(notification.getElapsedTime()));
		box.setStyle("" +
				"-fx-background-color: transparent;\n" +
				"-fx-border-color: red");
		return notification;
	}
}
