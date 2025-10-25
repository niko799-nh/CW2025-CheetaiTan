package com.comp2042;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.effect.Reflection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.scene.control.Label;
import java.awt.Point;



import java.net.URL;
import java.util.ResourceBundle;

public class GuiController implements Initializable {

    private static final int BRICK_SIZE = 20;

    @FXML
    private GridPane nextBrickPanel;

    @FXML
    private Label scoreDisplay;

    @FXML
    private GridPane gamePanel;

    @FXML
    private Group groupNotification;

    @FXML
    private GridPane brickPanel;

    @FXML
    private GameOverPanel gameOverPanel;

    private Rectangle[][] displayMatrix;

    private InputEventListener eventListener;

    private Rectangle[][] rectangles;

    private Timeline timeLine;

    private final BooleanProperty isPause = new SimpleBooleanProperty();
    private Label pauseLabel;

    private final BooleanProperty isGameOver = new SimpleBooleanProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getClassLoader().getResource("digital.ttf").toExternalForm(), 38);
        gamePanel.setFocusTraversable(true);
        gamePanel.requestFocus();
        gamePanel.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
                    if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                        refreshBrick(eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER)));
                        SoundEffect.playMove();
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                        refreshBrick(eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER)));
                        SoundEffect.playMove();
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                        refreshBrick(eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER)));
                        SoundEffect.playMove();
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                        moveDown(new MoveEvent(EventType.DOWN, EventSource.USER));
                        SoundEffect.playMove();
                        keyEvent.consume();
                    }
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        down(new MoveEvent(EventType.DOWN, EventSource.USER));
                        keyEvent.consume();
                    }

                }
                if (keyEvent.getCode() == KeyCode.N) {
                    newGame(null);
                }
                if (keyEvent.getCode() == KeyCode.P) {
                    pauseGame(null);
                }
            }
        });
        gameOverPanel.setVisible(false);

        final Reflection reflection = new Reflection();
        reflection.setFraction(0.8);
        reflection.setTopOpacity(0.9);
        reflection.setTopOffset(-12);
    }

    public void initGameView(int[][] boardMatrix, ViewData brick) {
        displayMatrix = new Rectangle[boardMatrix.length][boardMatrix[0].length];
        for (int i = 2; i < boardMatrix.length; i++) {
            for (int j = 0; j < boardMatrix[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(Color.TRANSPARENT);
                displayMatrix[i][j] = rectangle;
                gamePanel.add(rectangle, j, i - 2);
            }
        }

        rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
        for (int i = 0; i < brick.getBrickData().length; i++) {
            for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                rectangle.setFill(getFillColor(brick.getBrickData()[i][j]));
                rectangles[i][j] = rectangle;
                brickPanel.add(rectangle, j, i);
            }
        }
        brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
        brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);


        timeLine = new Timeline(new KeyFrame(
                Duration.millis(400),
                ae -> moveDown(new MoveEvent(EventType.DOWN, EventSource.THREAD))
        ));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    private Paint getFillColor(int i) {
        Paint returnPaint;
        switch (i) {
            case 0:
                returnPaint = Color.TRANSPARENT;
                break;
            case 1:
                returnPaint = Color.AQUA;
                break;
            case 2:
                returnPaint = Color.BLUEVIOLET;
                break;
            case 3:
                returnPaint = Color.DARKGREEN;
                break;
            case 4:
                returnPaint = Color.YELLOW;
                break;
            case 5:
                returnPaint = Color.RED;
                break;
            case 6:
                returnPaint = Color.BEIGE;
                break;
            case 7:
                returnPaint = Color.BURLYWOOD;
                break;
            default:
                returnPaint = Color.WHITE;
                break;
        }
        return returnPaint;
    }


    private void refreshBrick(ViewData brick) {
        if (isPause.getValue() == Boolean.FALSE) {
            gamePanel.getChildren().removeIf(node -> node.getStyleClass().contains("ghost"));
            //ghost(predicted landing)
            if (eventListener instanceof GameController) {
                GameController controller = (GameController) eventListener;
                Point ghostPos = controller.getBoard().getGhostPosition();

                for (int i = 0; i < brick.getBrickData().length; i++) {
                    for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                        if (brick.getBrickData()[i][j] != 0) {
                            Rectangle ghostBlock = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                            ghostBlock.setFill(Color.web("#FFFFFF", 0.25)); // Light transparent white
                            ghostBlock.setArcHeight(9);
                            ghostBlock.setArcWidth(9);
                            ghostBlock.getStyleClass().add("ghost"); // So we can clear them later

                            gamePanel.add(ghostBlock, j + ghostPos.x, i + ghostPos.y - 2);

                        }
                    }
                }
            }
            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(-42 + gamePanel.getLayoutY() + brick.getyPosition() * brickPanel.getHgap() + brick.getyPosition() * BRICK_SIZE);
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    setRectangleData(brick.getBrickData()[i][j], rectangles[i][j]);
                }
            }
        }
    }


    public void refreshGameBackground(int[][] board) {
        for (int i = 2; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                setRectangleData(board[i][j], displayMatrix[i][j]);
            }
        }
    }

    private void setRectangleData(int color, Rectangle rectangle) {
        rectangle.setFill(getFillColor(color));
        rectangle.setArcHeight(9);
        rectangle.setArcWidth(9);
    }

    private void moveDown(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE) {
            DownData downData = eventListener.onDownEvent(event);
            if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                NotificationPanel notificationPanel = new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                groupNotification.getChildren().add(notificationPanel);
                notificationPanel.showScore(groupNotification.getChildren());
                SoundEffect.playClear();
            }
            refreshBrick(downData.getViewData());
        }
        gamePanel.requestFocus();
    }

    public void setEventListener(InputEventListener eventListener) {
        this.eventListener = eventListener;
    }

    public void bindScore(IntegerProperty scoreProperty) {
        scoreDisplay = new Label("Score: 0");
        scoreDisplay.setStyle("-fx-text-fill: #00FA9A; -fx-font-size: 18px; -fx-font-weight: bold;");
        scoreDisplay.setLayoutX(225);
        scoreDisplay.setLayoutY(-190);

        //Keep it updated automatically
        scoreDisplay.textProperty().bind(scoreProperty.asString("Score: %d"));
        groupNotification.getChildren().add(scoreDisplay);
    }

    public void gameOver(int score) {
        timeLine.stop();
        SoundEffect.playGameOver();

        //GameOverPanel that displays the score
        GameOverPanel panel = new GameOverPanel(score);

        // Center it inside the game area
        panel.setLayoutX((gamePanel.getWidth() - 235));  // adjust leftright
        panel.setLayoutY((gamePanel.getHeight() - 500)); // adjust height

        //Add a background style to make it pop
        panel.setStyle("-fx-background-color: rgba(0, 0, 0, 0.6); -fx-padding: 20; -fx-alignment: center;");
        groupNotification.getChildren().clear();
        groupNotification.getChildren().add(panel);

        isGameOver.setValue(Boolean.TRUE);
    }


    public void newGame(ActionEvent actionEvent) {
        timeLine.stop();
        //Hide Game Over
        groupNotification.getChildren().clear();

        //Re-add score label after clearing
        if (scoreDisplay != null) {
            groupNotification.getChildren().add(scoreDisplay);
        }
        //Clearing
        if (nextBrickPanel != null) {
            nextBrickPanel.getChildren().clear();
        }
        if (gameOverPanel != null) {
            gameOverPanel.setVisible(false);
        }
        //Reset game state
        eventListener.createNewGame();

        // Start again
        gamePanel.requestFocus();
        timeLine.play();

        isPause.setValue(Boolean.FALSE);
        isGameOver.setValue(Boolean.FALSE);
    }

    public void pauseGame(ActionEvent actionEvent) {
        if (isPause.getValue()) {
            //game is paused thenresume
            timeLine.play();
            isPause.setValue(false);
            SoundEffect.resumeMusic();

            //Hide the pause word
            if (pauseLabel != null) {
                groupNotification.getChildren().remove(pauseLabel);
            }

        } else {
            //If game is running then pause
            timeLine.pause();
            isPause.setValue(true);
            SoundEffect.pauseMusic();
            //Show "PAUSED" text in the middle
            pauseLabel = new Label("PAUSED");
            pauseLabel.setStyle("-fx-text-fill: red; -fx-font-size: 36px; -fx-font-weight: bold;");
            pauseLabel.setLayoutX((gamePanel.getWidth() - 150)/2);
            pauseLabel.setLayoutY((gamePanel.getHeight() - 500)/2);
            groupNotification.getChildren().add(pauseLabel);
        }

        gamePanel.requestFocus();
    }

    public void updateNextBrick(int[][] nextBrickShape) {
        nextBrickPanel.getChildren().clear(); // clear old preview

        for (int i = 0; i < nextBrickShape.length; i++) {
            for (int j = 0; j < nextBrickShape[i].length; j++) {
                if (nextBrickShape[i][j] != 0) {
                    Rectangle r = new Rectangle(18, 18);
                    r.setArcHeight(6);
                    r.setArcWidth(6);
                    r.setFill(getFillColor(nextBrickShape[i][j]));
                    nextBrickPanel.add(r, j, i);
                }
            }
        }
    }
    private boolean canMoveFurther(ViewData brick) {
        return brick.getyPosition() < 30;
    }
    public void down(MoveEvent event) {
        if (isPause.getValue() == Boolean.FALSE && isGameOver.getValue() == Boolean.FALSE) {
            DownData downData = null;
            ViewData viewData;
            //Keep moving down until brick can't move anymore
            while (true) {
                downData = eventListener.onDownEvent(event);
                // If nothing to show or game ended — stop loop
                if (downData == null || downData.getViewData() == null) {
                    break;
                }
                viewData = downData.getViewData();
                refreshBrick(viewData);
                //play clear sound
                if (downData.getClearRow() != null && downData.getClearRow().getLinesRemoved() > 0) {
                    NotificationPanel notificationPanel =
                            new NotificationPanel("+" + downData.getClearRow().getScoreBonus());
                    groupNotification.getChildren().add(notificationPanel);
                    notificationPanel.showScore(groupNotification.getChildren());
                    SoundEffect.playClear();
                }
                //Stop if brick cannot move anymore
                if (downData.getClearRow() != null || !canMoveFurther(viewData)) {
                    break;
                }
            }
            //Merge to background to finalize placement
            eventListener.onDownEvent(new MoveEvent(EventType.DOWN, EventSource.THREAD));
        }
    }

}