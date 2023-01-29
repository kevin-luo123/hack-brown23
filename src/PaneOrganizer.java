import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class PaneOrganizer{
    public BorderPane root;
    private double bPM;
    private Background white;
    private Background black;
    private Timeline timeline;
    private Timeline timeline2;
    private int leftNum;
    private int rightNum;

    public PaneOrganizer(){
        this.bPM=0;
        this.root=new BorderPane();
        this.createButtonPane();
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

    private void setNorm(){
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
            this.setUpTimelineNorm();
            timeline.play();});
    }

    private void setPoly(){
        if(this.timeline!=null){
            this.timeline.stop();
        }
        Pane bpmEntry = new Pane();
        bpmEntry.setStyle("-fx-background-color: #000000");
        this.root.setCenter(bpmEntry);
        TextField beatsPer = new TextField();
        beatsPer.setStyle("-fx-background-color: #808080");
        Button button = new Button("enter");
        Label label = new Label("input beats per minute below");
        label.setLayoutY(100);
        label.setLayoutX(150);
        beatsPer.setLayoutY(150);
        beatsPer.setLayoutX(150);
        button.setLayoutY(200);
        button.setLayoutX(150);

        TextField leftBeats = new TextField();
        leftBeats.setStyle("-fx-background-color: #808080");
        Label labelLeft = new Label("input first beat division");
        labelLeft.setLayoutY(300);
        labelLeft.setLayoutX(20);
        leftBeats.setLayoutY(350);
        leftBeats.setLayoutX(20);

        Button buttonBeats = new Button("enter beat divisions");
        buttonBeats.setLayoutX(150);
        buttonBeats.setLayoutY(260);

        TextField rightBeats = new TextField();
        leftBeats.setStyle("-fx-background-color: #808080");
        Label labelRight = new Label("input second beat below");
        labelRight.setLayoutY(300);
        labelRight.setLayoutX(300);
        rightBeats.setLayoutY(350);
        rightBeats.setLayoutX(300);


        bpmEntry.getChildren().addAll(label,beatsPer,button);
        Rectangle left = new Rectangle(0, 200, 100, 100);
        left.setFill(Color.BLUE);
        Rectangle right = new Rectangle(400, 200, 100, 100);
        right.setFill(Color.BLUE);
        bpmEntry.getChildren().addAll(left, right, rightBeats, leftBeats, buttonBeats, labelLeft, labelRight);
        button.setOnAction(e -> {
            if (this.isNumeric(beatsPer.getText())) {
                this.bPM = 60.0 / Double.parseDouble(beatsPer.getText());
            }
        });
        buttonBeats.setOnAction(e -> {
            if(this.isNumeric(leftBeats.getText())){
                this.leftNum = Integer.parseInt(leftBeats.getText());
            }
            if(this.isNumeric(rightBeats.getText())){
                this.rightNum = Integer.parseInt(rightBeats.getText());
            }
            this.setUpTimelinePoly(left, right, this.leftNum, this.rightNum);
            this.flashPoly(left);
            this.flashPoly(right);
            timeline.play();
            timeline2.play();
        });
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
    private void setUpTimelineNorm(){
        if (this.timeline != null){
            this.timeline.stop();
        }
        KeyFrame kf = new KeyFrame(Duration.seconds(this.bPM),(ActionEvent e)-> this.flashNorm());
        this.timeline = new Timeline(kf);
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void setUpTimelinePoly(Rectangle left, Rectangle right, int leftPoly, int rightPoly){
        System.out.println("LEFT POLY: " + this.bPM/(double)leftPoly);
        System.out.println("RIGHT POLY: " + this.bPM/(double)rightPoly);
        KeyFrame lf = new KeyFrame(Duration.seconds((this.bPM)/(double) leftPoly), (ActionEvent e) -> this.flashPoly(left));
        KeyFrame rf = new KeyFrame(Duration.seconds((this.bPM)/(double) rightPoly), (ActionEvent e) -> this.flashPoly(right));
        this.timeline = new Timeline(lf);
        this.timeline2 =new Timeline(rf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline2.setCycleCount(Animation.INDEFINITE);
    }

    private void flashPoly(Rectangle rect){
        if(rect.getFill() == Color.WHITE){
            rect.setFill(Color.BLUE);
        }
        else{
            rect.setFill(Color.WHITE);
        }
    }
    private void flashNorm(){
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
        this.root.setTop(buttonPane);
        Button norm = new Button("Metronome");
        Button poly = new Button("Polyrhythm");
        buttonPane.getChildren().addAll(button, norm, poly);
        button.setOnAction((ActionEvent e)->System.exit(0));
        norm.setOnAction((ActionEvent e)->this.setNorm());
        poly.setOnAction((ActionEvent e)->this.setPoly());
    }

    public BorderPane getRoot(){
        return this.root;
    }
}
