/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aglfx.controller;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author Usuário
 */
public class ColetorDeCoordenadasController implements Initializable {
    
    private GraphicsContext gc;

    @FXML
    private Canvas canvas;

    @FXML
    void canvasMouseOnClickListener(MouseEvent event) {
        double x = event.getX();
        double y = event.getY();
        gc.setFill(Color.BLUE);
        gc.fillOval(x-5, y-5, 10, 10);        
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa o canvas com imagem (fins de teste somente).
        File file = new File("layout.png");
        Image image = new Image(file.toURI().toString());
        
        gc = canvas.getGraphicsContext2D();
        
        canvas.setWidth(image.getWidth());
        canvas.setHeight(image.getHeight());
        
        gc.drawImage(image, 0, 0, image.getWidth(), image.getHeight());
    }    
    
}
