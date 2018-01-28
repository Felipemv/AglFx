/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aglfx.controller;

import aglfx.model.bean.Pallet;
import com.sun.javafx.geom.Point2D;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
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
    private List<Pallet> pallets;
    private Point2D inicio;
    private Point2D fim;
    private int stackSize;
    private Point2D posClick;
    private Point2D translate;
    
    private final int RAIO_ELLIPSE = 5;
    private final int MARGEM_MARCACAO = 3;

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
        if (tBtnPonto.isSelected()) {
            double coordenadaX = event.getX();
            double coordenadaY = event.getY();

            Ellipse ellipse = new Ellipse(RAIO_ELLIPSE, RAIO_ELLIPSE);
            ellipse.setTranslateX(coordenadaX - RAIO_ELLIPSE);
            ellipse.setTranslateY(coordenadaY - RAIO_ELLIPSE);
            ellipse.setFill(Color.BLUE);
            
            stack.getChildren().add(ellipse);
            stackSize++;

            ellipse.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event1) {
                    if (tBtnMover.isSelected()) {
                        posClick = new Point2D((float) event1.getSceneX(),
                                (float) event1.getSceneY());

                        translate = new Point2D((float) ellipse.getTranslateX(),
                                (float) ellipse.getTranslateY());
                    }
                }
            });

            ellipse.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event1) {
                    if (tBtnMover.isSelected()) {
                        ellipse.setCursor(Cursor.MOVE);
                        double deslocX = event1.getSceneX() - posClick.x;
                        double deslocY = event1.getSceneY() - posClick.y;

                        ellipse.setTranslateX(deslocX + translate.x);
                        ellipse.setTranslateY(deslocY + translate.y);
                    }
                }
            });

            ellipse.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event1) {
                    ellipse.setCursor(Cursor.DEFAULT);
                }

            });
        }
    }

    @FXML
    private void stackOnMouseDraggedListener(MouseEvent event) {

        if (tBtnBloco.isSelected()) {
            fim = new Point2D((float) event.getX(), (float) event.getY());

            double width = fim.x - inicio.x;
            double height = fim.y - inicio.y;

            Rectangle retangulo = new Rectangle(Math.abs(width), Math.abs(height));
            retangulo.setFill(Color.rgb(0, 0, 0, 0.3));

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

            retangulo.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event1) {
                    if (tBtnMover.isSelected()) {
                        retangulo.setCursor(Cursor.MOVE);
                        posClick = new Point2D((float) event1.getSceneX(),
                                (float) event1.getSceneY());

                        translate = new Point2D((float) retangulo.getTranslateX(),
                                (float) retangulo.getTranslateY());
                        System.out.println("ID: "+retangulo.getId());
                    }
                }

            });

            retangulo.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event1) {
                    if (tBtnMover.isSelected()) {
                        double deslocX = event1.getSceneX() - posClick.x;
                        double deslocY = event1.getSceneY() - posClick.y;

                        retangulo.setTranslateX(deslocX + translate.x);
                        retangulo.setTranslateY(deslocY + translate.y);
                    }
                }

            });

            retangulo.setOnMouseReleased(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event1) {
                    retangulo.setCursor(Cursor.DEFAULT);
                }

            });

            retangulo.setOnMouseMoved(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event1) {

                    //canto esquerdo superior
                    double x = retangulo.getTranslateX();
                    double y = retangulo.getTranslateY();

                    //Dimensões no retangulo
                    double w = retangulo.getWidth();
                    double h = retangulo.getHeight();

                    //local onde o mouse está
                    double evtX = event1.getX() + x;
                    double evtY = event1.getY() + y;

                    if (!tBtnMover.isSelected()) {
                        //Linha de cima
                        if (evtX >= x && evtX <= x + w &&  evtY <= y + MARGEM_MARCACAO) {
                            retangulo.setCursor(Cursor.CROSSHAIR);
                            
                        // Linha de baixo    
                        } else if (evtX >= x && evtX <= x + w && evtY >= y + h - MARGEM_MARCACAO) {
                            retangulo.setCursor(Cursor.CROSSHAIR);
                            
                        // Linha da esquerda
                        } else if (evtX <= x + MARGEM_MARCACAO && evtY >= y && evtY <= evtY + h) {
                            retangulo.setCursor(Cursor.CROSSHAIR);
                            
                        //Linha da direita
                        } else if (evtX >= x + w - MARGEM_MARCACAO && evtY >= y && evtY <= y + h) {
                            retangulo.setCursor(Cursor.CROSSHAIR);                            
                            
                        } else {
                            retangulo.setCursor(Cursor.DEFAULT);
                        }
                    }
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
