package io.github.palexdev.materialfx.alerts;

import io.github.palexdev.materialfx.effects.DepthLevel;
import io.github.palexdev.materialfx.effects.Interpolators;
import io.github.palexdev.materialfx.effects.MFXDepthManager;
import io.github.palexdev.materialfx.utils.AnimationUtils;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.layout.Pane;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zhong
 * @date 2022-04-22 14:38:51 周五
 */
public final class MFXAlerts {

    private static ExecutorService executor = Executors.newCachedThreadPool();

    public static void show(Pane pane, String content, MFXAlert.Type type, Pos pos) {
        executor.submit(() -> {
            Group group = new Group();
            MFXAlert alert = new MFXAlert(content, type);
            Platform.runLater(() -> {
                group.getChildren().add(alert);
                pane.getChildren().add(group);
            });
            group.setManaged(false);
            group.setEffect(MFXDepthManager.shadowOf(DepthLevel.LEVEL1));
            alert.setOnClose(() -> pane.getChildren().remove(group));
            switch (pos) {
                case TOP_LEFT: {
                    group.setLayoutX(10);
                    group.setLayoutY(10);
                    AnimationUtils.TimelineBuilder.build().show(150, group)
                            .add(AnimationUtils.KeyFrames.of(150,
                                    group.translateXProperty(), 50, Interpolators.EASE_IN
                            )).getAnimation().play();
                    break;
                }
                case BOTTOM_LEFT: {
                    group.setLayoutX(10);
                    DoubleBinding binding = Bindings.createDoubleBinding(() -> pane.getHeight() - group.getLayoutBounds().getHeight() - 10, pane.heightProperty(), group.layoutBoundsProperty());
                    group.layoutYProperty().bind(binding);
                    break;
                }
                case BOTTOM_RIGHT: {
                    DoubleBinding xBinding = Bindings.createDoubleBinding(() -> pane.getWidth() - group.getLayoutBounds().getWidth() - 10, pane.widthProperty(), group.layoutBoundsProperty());
                    DoubleBinding yBinding = Bindings.createDoubleBinding(() -> pane.getHeight() - group.getLayoutBounds().getHeight() - 10, pane.heightProperty(), group.layoutBoundsProperty());
                    group.layoutXProperty().bind(xBinding);
                    group.layoutYProperty().bind(yBinding);
                    break;
                }

                case TOP_CENTER: {
                    DoubleBinding xBinding = Bindings.createDoubleBinding(() -> (pane.getWidth() - group.getLayoutBounds().getWidth() - 10) / 2, pane.widthProperty(), group.layoutBoundsProperty());
                    group.layoutXProperty().bind(xBinding);
                    group.setLayoutY(20);
                    AnimationUtils.TimelineBuilder.build().show(100, group)
                            .add(AnimationUtils.KeyFrames.of(100,
                                    group.translateYProperty(), 20, Interpolators.EASE_IN
                            )).getAnimation().play();
                    break;
                }

                case CENTER: {
                    DoubleBinding xBinding = Bindings.createDoubleBinding(() -> (pane.getWidth() - group.getLayoutBounds().getWidth() - 10) / 2, pane.widthProperty(), group.layoutBoundsProperty());
                    DoubleBinding yBinding = Bindings.createDoubleBinding(() -> (pane.getHeight() - group.getLayoutBounds().getHeight() - 10) / 2, pane.heightProperty(), group.layoutBoundsProperty());
                    group.layoutXProperty().bind(xBinding);
                    group.layoutYProperty().bind(yBinding);
                    break;
                }

                default: {
                    DoubleBinding binding = Bindings.createDoubleBinding(() -> pane.getLayoutBounds().getWidth() - group.getLayoutBounds().getWidth() - 10, pane.layoutBoundsProperty(), group.layoutBoundsProperty());
                    group.layoutXProperty().bind(binding);
                    group.setLayoutY(10);
                }
            }

        });
    }
}
