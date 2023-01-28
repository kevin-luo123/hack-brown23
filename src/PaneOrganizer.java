import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.awt.*;
import java.security.Key;

public class PaneOrganizer{
    public BorderPane root;
    private double bPM;
    private Background white;
    private Background black;
    private Timeline timeline;
    private KeyFrame keyFrame;

    public PaneOrganizer(){
        this.bPM=0;
        this.root=new BorderPane();
        this.createButtonPane();
        this.createTextField();
        Image img = new Image("https://upload.wikimedia.org/wikipedia/commons/thumb/4/49/A_black_image.jpg/1200px" +
                "-A_black_image.jpg?20201103073518");
        BackgroundImage bImg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.black = new Background(bImg);
        Image wimg = new Image("https://www.macmillandictionary.com/external/slideshow/full/White_full.png");
        BackgroundImage wImg = new BackgroundImage(wimg, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        this.white = new Background(wImg);
        this.root.setBackground(white);
    }

    private void createTextField(){
        Pane bpmEntry = new Pane();
        this.root.setCenter(bpmEntry);
        TextField beatsPer = new TextField();
        beatsPer.setStyle("-fx-background-color: #808080");
        Button button = new Button("enter");
        Label label = new Label("input beats per minute below");
        label.setLayoutY(200);
        label.setLayoutX(150);
        beatsPer.setLayoutY(250);
        beatsPer.setLayoutX(150);
        button.setLayoutY(300);
        button.setLayoutX(150);
        bpmEntry.getChildren().addAll(label,beatsPer,button);
        button.setOnAction(e -> {
            if(this.isNumeric(beatsPer.getText())){
                this.bPM = 60.0/Double.parseDouble(beatsPer.getText());
            }
            this.setUpTimeline();
            timeline.play();});
    }

    private boolean isNumeric(String text){
        try{
            Double.parseDouble(text);
            return true;
        }
        catch (Exception e){
            System.out.println("please input a number");
            return false;
        }
    }
    private void setUpTimeline(){
        if (this.timeline != null){
            this.timeline.stop();
        }
        this.keyFrame = new KeyFrame(Duration.seconds(this.bPM),(ActionEvent e)-> this.flash());
        this.timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void flash(){
        if(this.root.getBackground()==white){
            this.root.setBackground(black);
        }
        else{
            this.root.setBackground(white);
        }
    }
    private void createButtonPane(){
        HBox buttonPane = new HBox();
        Button button = new Button("Quit");
        buttonPane.getChildren().add(button);
        this.root.setTop(buttonPane);
        button.setOnAction((ActionEvent e)->System.exit(0));
    }

    public BorderPane getRoot(){
        return this.root;
    }
}
