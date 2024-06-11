package moteurJeu;

//https://github.com/zarandok/megabounce/blob/master/MainCanvas.java

import gameLaby.laby.SpriteLoader;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Random;

// copied from: https://gist.github.com/james-d/8327842
// and modified to use canvas drawing instead of shapes

public class MoteurJeu extends Application {

    /**
     * gestion du temps : nombre de frame par secondes et temps par iteration
     */
    private static double FPS = 100;
    private static double dureeFPS = 1000 / (FPS + 1);

    /**
     * taille par defaut
     */
    private static double WIDTH = 800;
    private static double HEIGHT = 600;
    private static SpriteLoader loader;

    /**
     * statistiques sur les frames
     */
    private final FrameStats frameStats = new FrameStats();

    /**
     * jeu en Cours et renderer du jeu
     */
    private static Jeu jeu = null;
    private static DessinJeu dessin = null;


    /**
     * touches appuyee entre deux frame
     */
    Clavier controle = new Clavier();

    /**
     * lancement d'un jeu
     *
     * @param jeu    jeu a lancer
     * @param dessin dessin du jeu
     */
    public static void launch(Jeu jeu, DessinJeu dessin) {
        // le jeu en cours et son afficheur
        MoteurJeu.jeu = jeu;
        MoteurJeu.dessin = dessin;

        // si le jeu existe, on lance le moteur de jeu
        if (jeu != null)
            launch();
    }

    /**
     * frame par secondes
     *
     * @param FPSSouhaitees nombre de frames par secondes souhaitees
     */
    public static void setFPS(int FPSSouhaitees) {
        FPS = FPSSouhaitees;
        dureeFPS = 1000 / (FPS + 1);
    }

    public static void setTaille(double width, double height) {
        WIDTH = width;
        HEIGHT = height;
    }


    //#################################
    // SURCHARGE Application
    //#################################

    @Override
    /**
     * creation de l'application avec juste un canvas et des statistiques
     */
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);

        HBox bouton = new HBox(10);



        Button but1 = new Button("Quitter");
        but1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.exit(0);
            }
        });

        Button but2 = new Button("Lancer");

        Text ta = new Text();
        ta.setFill(Color.ANTIQUEWHITE);
        ta.setStrokeWidth(.1);
        ta.setStroke(Color.BLACK);
        ta.setText("Vous êtes un chevalier en quête de pouvoir.\n Votre but ? Récupérer le crâne du roi maudit,\n mais attention à ses sbires qui rôdent dans le donjon…");
        ta.setTextAlignment(TextAlignment.CENTER);
        Rectangle r = new Rectangle(300,50);
        r.setOpacity(.5);
        StackPane sp = new StackPane();
        sp.getChildren().addAll(r,ta);

        bouton.getChildren().addAll(but2, but1);
        bouton.setAlignment(Pos.CENTER);
        root.getChildren().addAll(sp,bouton);
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 500, 500);

        BackgroundImage bg= new BackgroundImage(new Image("image/_bb04f6fe-7366-4021-9dc1-fca904b09d3a.jpg",500,500,false,true), BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        root.setBackground(new Background(bg));

        primaryStage.setScene(scene);
        primaryStage.setTitle("Menu");
        primaryStage.show();
        but2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent)
        {// initialisation du canvas de dessin et du container
            final Canvas canvas = new Canvas();
            final Pane canvasContainer = new Pane(canvas);
            canvas.widthProperty().bind(canvasContainer.widthProperty());
            canvas.heightProperty().bind(canvasContainer.heightProperty());

            // affichage des stats
            final Label stats = new Label();
            stats.textProperty().bind(frameStats.textProperty());

            // ajout des statistiques en bas de la fenetre
            final BorderPane root = new BorderPane();
            root.setCenter(canvasContainer);
            root.setBottom(stats);

            // creation de la scene
            final Scene scene = new Scene(root, WIDTH, HEIGHT);
            primaryStage.setScene(scene);

            primaryStage.centerOnScreen();

            primaryStage.show();


            // listener clavier
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    controle.appuyerTouche(event);
                }
            });

            scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    controle.relacherTouche(event);
                }
            });


            // creation du listener souris
            canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                    new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent event) {
                            if (event.getClickCount() == 2) {
                                jeu.init();
                            }
                        }
                    });

            // lance la boucle de jeu
            startAnimation(canvas);
        }
        });
    }




    /**
     * gestion de l'animation (boucle de jeu)
     *
     * @param canvas le canvas sur lequel on est synchronise
     */
    private void startAnimation(final Canvas canvas) {
        // stocke la derniere mise e jour
        final LongProperty lastUpdateTime = new SimpleLongProperty(0);

        // création du spriteloader
        final SpriteLoader loader = new SpriteLoader("image");



        // timer pour boucle de jeu
        final AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long timestamp) {

                // si jamais passe dans la boucle, initialise le temps
                if (lastUpdateTime.get() == 0) {
                    lastUpdateTime.set(timestamp);
                }

                // mesure le temps ecoule depuis la derniere mise a jour
                long elapsedTime = timestamp - lastUpdateTime.get();
                double dureeEnMilliSecondes = elapsedTime / 1_000_000.0;


                // si le temps ecoule depasse le necessaire pour FPS souhaite
                if (dureeEnMilliSecondes > dureeFPS) {
                    // met a jour le jeu en passant les touches appuyees
                    try {
                        jeu.update(dureeEnMilliSecondes / 1_000., controle);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    // dessine le jeu
                    dessin.dessinerJeu(jeu, canvas, loader);

                    // ajoute la duree dans les statistiques
                    frameStats.addFrame(elapsedTime);

                    // met a jour la date de derniere mise a jour
                    lastUpdateTime.set(timestamp);
                }

            }
        };

        // lance l'animation
        timer.start();
    }


}