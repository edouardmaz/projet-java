package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import sample.Music;

public class CardController {
    @FXML
    private Label artistName;

    @FXML
    private HBox box;

    @FXML
    private ImageView musicImage;

    @FXML
    private Label musicName;
    
    private String[] colors = {"B9E5FF", "BDB2FE", "FB9AA8", "FF5056"};

    public void setData(Music music) {
    	Image image = new Image(getClass().getResourceAsStream(music.getImageSrc()));
    	musicImage.setImage(image);
    	
    	musicName.setText(music.getName());
    	artistName.setText(music.getArtist());
    	box.setStyle("-fx-background-color: "+ Color.web(colors));
    }
}
