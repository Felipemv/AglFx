/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aglfx.controller;

import com.sun.javafx.geom.Point2D;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;

/**
 * FXML Controller class
 *
 * @author Usuário
 */
public class ColetorDeCoordenadasController implements Initializable {

    private GraphicsContext gc;
    private List<Rectangle> blocos;
    private Point2D inicio;
    private Point2D fim;
    private Rectangle retangulo;
    private int stackSize;
    private Point2D posClick;
    private Point2D translate;

    @FXML
    private Pane paneFerramentas;

    @FXML
    private Pane paneCoordenadas;

    @FXML
    private Label lblCoordenadas;

    @FXML
    private StackPane stack;

    @FXML
    private ScrollPane scroll;

    @FXML
    private ToggleButton tBtnBloco;

    @FXML
    private ToggleButton tBtnPonto;

    @FXML
    private ToggleButton tBtnMover;

    @FXML
    private void stackOnMouseClickedListener(MouseEvent event) {        
        if(tBtnPonto.isSelected()){
            double coordenadaX = event.getX();
            double coordenadaY = event.getY();

            int eSize = 8;

            Ellipse ellipse = new Ellipse(0, 0, eSize, eSize);
            ellipse.setTranslateX(coordenadaX - eSize);
            ellipse.setTranslateY(coordenadaY - eSize);
            ellipse.setFill(Color.BLUE);

            stack.getChildren().add(ellipse);
            stackSize++;
        }
    }

    @FXML
    private void stackOnMouseDraggedListener(MouseEvent event) {

        if (tBtnBloco.isSelected()) {
            fim = new Point2D((float) event.getX(), (float) event.getY());

            retangulo = new Rectangle(Math.abs(fim.x - inicio.x), Math.abs(fim.y - inicio.y));
            
            if (inicio.x > fim.x) {
                retangulo.setTranslateX(fim.x);
                if (inicio.y > fim.y) {
                    retangulo.setTranslateY(fim.y);
                } else {
                    retangulo.setTranslateY(inicio.y);
                }
            } else {
                retangulo.setTranslateX(inicio.x);
                if (inicio.y < fim.y) {
                    retangulo.setTranslateY(inicio.y);
                } else {
                    retangulo.setTranslateY(fim.y);
                }
            }

            int size = stack.getChildren().size();

            if (size > stackSize) {
                stack.getChildren().remove(size - 1);
            }

            stack.getChildren().add(retangulo);
                        
            retangulo.setOnMousePressed((MouseEvent event1) -> {
                if(tBtnMover.isSelected()){
                    posClick = new Point2D((float)event1.getSceneX(), 
                            (float)event1.getSceneY());
                    
                    translate = new Point2D((float) retangulo.getTranslateX(), 
                        (float) retangulo.getTranslateY());
                }
            });
            retangulo.setOnMouseDragged((MouseEvent event1) -> {
                if(tBtnMover.isSelected()){
                    double deslocX = event1.getSceneX() - posClick.x;
                    double deslocY = event1.getSceneY() - posClick.y;
                    
                    retangulo.setTranslateX(deslocX + translate.x);
                    retangulo.setTranslateY(deslocY + translate.y);
                }
            });
        }

    }

    @FXML
    private void stackOnMouseMovedListener(MouseEvent event) {
        lblCoordenadas.setText("Coordenadas: (" + event.getX() + " ," + event.getY() + ")");
    }

    @FXML
    private void stackOnMousePressedListener(MouseEvent event) {
        
        inicio = new Point2D((float) event.getX(), (float) event.getY());
    }

    @FXML
    private void stackOnMouseReleasedListener(MouseEvent event) {
        stackSize++;
        
    }

    @FXML
    private void stackOnMouseEnteredListener(MouseEvent event) {
//        stack.setCursor(Cursor.HAND);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa o canvas com imagem (fins de teste somente).
        File file = new File("layout.png");
        Image image = new Image(file.toURI().toString());

        paneCoordenadas.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        paneFerramentas.setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        stack.setAlignment(Pos.TOP_LEFT);
        stack.getChildren().add(new ImageView(image));
        stackSize = stack.getChildren().size();

        ToggleGroup tg = new ToggleGroup();
        tg.getToggles().add(tBtnBloco);
        tg.getToggles().add(tBtnPonto);
        tg.getToggles().add(tBtnMover);
    }

}
