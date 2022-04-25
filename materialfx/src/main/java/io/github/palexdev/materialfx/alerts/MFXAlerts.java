package io.github.palexdev.materialfx.alerts;

import io.github.palexdev.materialfx.effects.DepthLevel;
import io.github.palexdev.materialfx.effects.Interpolators;
import io.github.palexdev.materialfx.effects.MFXDepthManager;
import io.github.palexdev.materialfx.utils.AnimationUtils;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Function;

/**
 * 显示警告弹窗
 *
 * @author zhong
 * @date 2022-04-22 14:38:51 周五
 */
public final class MFXAlerts {

    /**
     * 显示弹窗
     *
     * @param pane      显示的容器，javafx 节点必须加到树结构里面
     * @param content   警告内容
     * @param type      警告类型
     * @param pos       显示位置
     * @param animation 出场动画
     */
    public static void show(Pane pane, String content, MFXAlert.Type type, Pos pos, Function<Node, Timeline> animation) {
        new Thread(() -> {
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
                    group.setLayoutY(10);
                    break;
                }
                case BOTTOM_LEFT: {
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
                    break;
                }

                case CENTER: {
                    DoubleBinding xBinding = Bindings.createDoubleBinding(() -> (pane.getWidth() - group.getLayoutBounds().getWidth() - 10) / 2, pane.widthProperty(), group.layoutBoundsProperty());
                    DoubleBinding yBinding = Bindings.createDoubleBinding(() -> (pane.getHeight() - group.getLayoutBounds().getHeight() - 10) / 2, pane.heightProperty(), group.layoutBoundsProperty());
                    group.layoutXProperty().bind(xBinding);
                    group.layoutYProperty().bind(yBinding);
                    break;
                }

                case TOP_RIGHT: {
                    DoubleBinding binding = Bindings.createDoubleBinding(() -> pane.getLayoutBounds().getWidth() - group.getLayoutBounds().getWidth() - 10, pane.layoutBoundsProperty(), group.layoutBoundsProperty());
                    group.layoutXProperty().bind(binding);
                    group.setLayoutY(10);
                }
            }
            if (animation != null) {
                Timeline timeline = animation.apply(group);
                timeline.play();
                timeline.setOnFinished(event -> delayHide(pane, group));
            } else {
                delayHide(pane, group);
            }

        }).start();
    }

    /**
     * 显示弹窗
     *
     * @param pane    显示的容器，javafx 节点必须加到树结构里面
     * @param content 警告内容
     * @param type    警告类型
     * @param pos     显示位置
     */
    public static void show(Pane pane, String content, MFXAlert.Type type, Pos pos) {
        Function<Node, Timeline> animation;
        switch (pos) {
            case TOP_LEFT:
            case BOTTOM_LEFT: {
                animation = node -> AnimationUtils.TimelineBuilder.build().show(150, node)
                        .add(AnimationUtils.KeyFrames.of(150,
                                node.translateXProperty(), 10, Interpolators.EASE_IN
                        )).getAnimation();
                break;
            }
            case TOP_RIGHT:
            case BOTTOM_RIGHT: {
                animation = node -> AnimationUtils.TimelineBuilder.build().show(150, node)
                        .add(AnimationUtils.KeyFrames.of(150,
                                node.translateXProperty(), -10, Interpolators.EASE_IN
                        )).getAnimation();
                break;
            }
            case TOP_CENTER: {
                animation = node -> AnimationUtils.TimelineBuilder.build().show(100, node)
                        .add(AnimationUtils.KeyFrames.of(100,
                                node.translateYProperty(), 20, Interpolators.EASE_IN
                        )).getAnimation();
                break;
            }
            case CENTER: {
                animation = node -> AnimationUtils.TimelineBuilder.build().show(150, node)
                        .add(AnimationUtils.KeyFrames.of(0,
                                node.scaleXProperty(), 0, Interpolators.EASE_IN
                        ))
                        .add(AnimationUtils.KeyFrames.of(0,
                                node.scaleYProperty(), 0, Interpolators.EASE_IN
                        ))
                        .add(AnimationUtils.KeyFrames.of(150,
                                node.scaleXProperty(), 1, Interpolators.EASE_IN
                        )).add(AnimationUtils.KeyFrames.of(150,
                                node.scaleYProperty(), 1, Interpolators.EASE_IN
                        )).getAnimation();
                break;
            }
            default: {
                animation = null;
            }
        }
        show(pane, content, type, pos, animation);
    }

    private static void delayHide(Pane pane, Node node) {
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                if (pane.getChildren().contains(node)) {
                    Timeline timeline = AnimationUtils.TimelineBuilder.build()
                            .hide(150, node).getAnimation();
                    timeline.play();
                    timeline.setOnFinished(event -> Platform.runLater(() -> pane.getChildren().remove(node)));
                }
            }
        }, 3000);
    }
}
