package edu.vanier.ufo.ui;

import edu.vanier.ufo.helpers.ResourcesManager;
import edu.vanier.ufo.engine.*;
import edu.vanier.ufo.game.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Random;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;

/**
 * This is a simple game world simulating a bunch of spheres looking like atomic
 * particles colliding with each other. When the game loop begins the user will
 * notice random spheres (atomic particles) floating and colliding. The user
 * will navigate his/her ship by right clicking the mouse to thrust forward and
 * left click to fire weapon to atoms.
 *
 * @author cdea
 */
public class GameWorld extends GameEngine {

    // mouse pt label
    //Label mousePtLabel = new Label();
    // mouse press pt label
    //Label mousePressPtLabel = new Label();
    //Current Score Label
    Label currentScore = new Label();
    //Current Level
    Label currentLevel = new Label();
    //Counter of lives
    Label currentLives = new Label();
    Ship spaceShip = new Ship();

    int currentLifeInt;



    int currentScoreInt;
    int currentLevelInt = 1;

    Stage primaryStage1;

    public GameWorld(int fps, String title) {
        super(fps, title);
    }


    /**
     * Initialize the game world by adding sprite objects.
     *
     * @param primaryStage The game window or primary stage.
     */
    @Override
    public void initialize(final Stage primaryStage) {
        primaryStage1 = primaryStage;
        // Sets the window title
        primaryStage.setTitle(getWindowTitle());
        //primaryStage.setFullScreen(true);

        // Create the scene
        setSceneNodes(new Group());
        setGameSurface(new Scene(getSceneNodes(), 1000, 600));

        // Change the background of the main scene.
        getGameSurface().setFill(Color.BLACK);

        primaryStage.setScene(getGameSurface());

        // Setup Game input
        setupInput(primaryStage);
        keyboardEventHandler(primaryStage);

        // Create many spheres
        generateManySpheres(15, ResourcesManager.INADER_SPRITES_PATH,2);

        getSpriteManager().addSprites(spaceShip);
        spaceShip.changeShip(ResourcesManager.SPACE_SHIP);
        getSceneNodes().getChildren().add(0, spaceShip.getNode());
        // set the ship to the center of the screen
        spaceShip.getNode().setTranslateX(getGameSurface().getWidth() / 2);
        spaceShip.getNode().setTranslateY(getGameSurface().getHeight() / 4 + 2 * getGameSurface().getHeight() / 4);
        // set lives/score/level
        setCurrentLife(3);
        setCurrentScore(0);
        setCurrentLevel(1);
        // mouse point
        VBox stats = new VBox();

        // HUD
        //HBox row1 = new HBox();
        //mousePtLabel.setTextFill(Color.WHITE);
        //row1.getChildren().add(mousePtLabel);
        //HBox row2 = new HBox();
        //mousePressPtLabel.setTextFill(Color.WHITE);
        //row2.getChildren().add(mousePressPtLabel);
        HBox row3 = new HBox();
        currentScore.setText("Current Score: ");
        currentScore.setTextFill(Color.GREEN);
        row3.getChildren().add(currentScore);
        HBox row4 = new HBox();
        currentLevel.setText("Current Level: ");
        currentLevel.setTextFill(Color.GREEN);
        row4.getChildren().add(currentLevel);
        HBox row5 = new HBox();
        currentLives.setText("Current Lives: " + getCurrentLife());
        currentLives.setTextFill(Color.RED);
        row5.getChildren().add(currentLives);

        //stats.getChildren().add(row1);
        //stats.getChildren().add(row2);
        stats.getChildren().add(row3);
        stats.getChildren().add(row4);
        stats.getChildren().add(row5);

        getSceneNodes().getChildren().add(0, stats);


        // load sound files
        getSoundManager().loadSoundEffects("laser", getClass().getClassLoader().getResource(ResourcesManager.SOUND_LASER));
        getSoundManager().loadSoundEffects("CollisionSound", getClass().getClassLoader().getResource(ResourcesManager.SOUND_COLLISION));
    }


    /**
     * Sets up the mouse input.
     *
     * @param primaryStage The primary stage (app window).
     */
    private void setupInput(Stage primaryStage) {
        System.out.println("Ship's center is (" + spaceShip.getCenterX() + ", " + spaceShip.getCenterY() + ")");

        EventHandler fireOrMove = (EventHandler<MouseEvent>) (MouseEvent event) -> {
            //mousePressPtLabel.setText("Mouse Press PT = (" + event.getX() + ", " + event.getY() + ")");
            if (event.getButton() == MouseButton.PRIMARY) {

                // Aim
                spaceShip.plotCourse(event.getX(), event.getY(), false);

                // fire
                Missile missile = spaceShip.fire();
                getSpriteManager().addSprites(missile);

                // play sound
                getSoundManager().playSound("laser");

                getSceneNodes().getChildren().add(0, missile.getNode());

            } else if (event.getButton() == MouseButton.SECONDARY) {
                // determine when all atoms are not on the game surface. Ship should be one sprite left.

                // stop ship from moving forward
                spaceShip.applyTheBrakes(event.getX(), event.getY());
                // move forward and rotate ship
                spaceShip.plotCourse(event.getX(), event.getY(), true);
            }
        };

        // Initialize input
        primaryStage.getScene().setOnMousePressed(fireOrMove);

        // set up stats
        EventHandler changeWeapons = (EventHandler<KeyEvent>) (KeyEvent event) -> {
            if (KeyCode.SPACE == event.getCode()) {
                spaceShip.shieldToggle();
                return;
            }
            spaceShip.changeWeapon(event.getCode());
        };
        primaryStage.getScene().setOnKeyPressed(changeWeapons);

        // set up stats
        EventHandler showMouseMove = (EventHandler<MouseEvent>) (MouseEvent event) -> {
            //mousePtLabel.setText("Mouse PT = (" + event.getX() + ", " + event.getY() + ")");
        };
        
        primaryStage.getScene().setOnMouseMoved(showMouseMove);
    }

    private void keyboardEventHandler(Stage primaryStage) {
        primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            if (KeyCode.W == event.getCode()) {
                System.out.println("W pressed");
                spaceShip.plotCourse(spaceShip.getCenterX(), spaceShip.getCenterY() - 10, true);
            } else if (KeyCode.A == event.getCode()) {
                spaceShip.plotCourse(spaceShip.getCenterX() - 10, spaceShip.getCenterY(), true);
            } else if (KeyCode.S == event.getCode()) {
                spaceShip.plotCourse(spaceShip.getCenterX(), spaceShip.getCenterY() + 10, true);
            } else if (KeyCode.D == event.getCode()) {
                spaceShip.plotCourse(spaceShip.getCenterX() + 10, spaceShip.getCenterY(), true);
            }
        });
        // if key is released, stop moving
     primaryStage.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
          if (KeyCode.W == event.getCode()) {
              System.out.println("W released");
             spaceShip.applyTheBrakes(spaceShip.getCenterX(), spaceShip.getCenterY());
          } else if (KeyCode.A == event.getCode()) {
              spaceShip.applyTheBrakes(spaceShip.getCenterX(), spaceShip.getCenterY());
          } else if (KeyCode.S == event.getCode()) {
              spaceShip.applyTheBrakes(spaceShip.getCenterX(), spaceShip.getCenterY());
          } else if (KeyCode.D == event.getCode()) {
              spaceShip.applyTheBrakes(spaceShip.getCenterX(), spaceShip.getCenterY());
          }
      });

    }

    /**
     * Make some more space spheres (Atomic particles)
     *
     * @param numSpheres The number of random sized, color, and velocity atoms
     *                   to generate.
     */
    private void generateManySpheres(int numSpheres, String[] invaderList, int velocity) {
        Random rnd = new Random();
        Scene gameSurface = getGameSurface();
        for (int i = 0; i < numSpheres; i++) {
            String[] alienList = invaderList;
            int alienRandom = (int)(Math.random()* alienList.length);
            Atom atom = new Atom(alienList[alienRandom]);
            ImageView atomImage = atom.getImageViewNode();
            // random 0 to 2 + (.0 to 1) * random (1 or -1)
            // Randomize the location of each newly generated atom.
            atom.setVelocityX((rnd.nextInt(velocity) + rnd.nextDouble()) * (rnd.nextBoolean() ? 1 : -1));
            atom.setVelocityY((rnd.nextInt(velocity) + rnd.nextDouble()) * (rnd.nextBoolean() ? 1 : -1));

            // random x between 0 to width of scene
            double newX = rnd.nextInt((int) gameSurface.getWidth() - 100);

            if (newX > (gameSurface.getWidth() - (rnd.nextInt(15) + 5 * 2))) {
                newX = gameSurface.getWidth() - (rnd.nextInt(15) + 5 * 2);
            }

            double newY = rnd.nextInt((int) (gameSurface.getHeight() - 300));
            if (newY > (gameSurface.getHeight() - (rnd.nextInt(15) + 5 * 2))) {
                newY = gameSurface.getHeight() - (rnd.nextInt(15) + 5 * 2);
            }

            atomImage.setTranslateX(newX);
            atomImage.setTranslateY(newY);
            atomImage.setVisible(true);
            atomImage.setId("invader");
            atomImage.setCache(true);
            atomImage.setCacheHint(CacheHint.SPEED);
            atomImage.setManaged(false);

            // add to actors in play (sprite objects)
            getSpriteManager().addSprites(atom);

            // add sprite's 
            getSceneNodes().getChildren().add(atom.getNode());
        }
    }

    /**
     * Each sprite will update it's velocity and bounce off wall borders.
     *
     * @param sprite - An atomic particle (a sphere).
     */
    @Override
    protected void handleUpdate(Sprite sprite) {
        // advance object
        sprite.update();
        if (sprite instanceof Missile) {
            removeMissiles((Missile) sprite);
        } else if (currentLifeInt == 0) {
            sprite.handleDeath(this);
            handleGameOver();
        } else if (getSpriteManager().getAllSprites().size()==1 && getCurrentLevel()==1) {
            handleLevel2();
        } else if (getSpriteManager().getAllSprites().size()==1 && getCurrentLevel()==2) {
            handleLevel3();
        } else if (getSpriteManager().getAllSprites().size()==1 && getCurrentLevel()==3) {
            shutdown();
            VBox gameOver = new VBox(40);
            gameOver.getChildren().add(new Text("The End"));
            gameOver.getChildren().add(new Text("Score: " + getCurrentScore()));
            gameOver.setAlignment(Pos.CENTER);
            Button theEnd = new Button("The End");
            gameOver.getChildren().add(theEnd);
            Scene gameOverScene = new Scene(gameOver, 400,400);
            setGameSurface(gameOverScene);
            primaryStage1.setScene(gameOverScene);
            theEnd.setOnAction(e -> {
                System.exit(0);
            });
        } else {
            bounceOffWalls(sprite);
        }
    }

    /**
     * Change the direction of the moving object when it encounters the walls.
     *
     * @param sprite The sprite to update based on the wall boundaries. TODO The
     *               ship has got issues.
     */
    private void bounceOffWalls(Sprite sprite) {
        // bounce off the walls when outside of boundaries

        Node displayNode;
        if (sprite instanceof Ship) {
            displayNode = sprite.getNode();//((Ship)sprite).getCurrentShipImage();
        } else {
            displayNode = sprite.getNode();
        }
        // Get the group node's X and Y but use the ImageView to obtain the width.
        if (sprite.getNode().getTranslateX() > (getGameSurface().getWidth() - displayNode.getBoundsInParent().getWidth())
                || displayNode.getTranslateX() < 0) {

            // bounce the opposite direction
            sprite.setVelocityX(sprite.getVelocityX() * -1);
        }
        // Get the group node's X and Y but use the ImageView to obtain the height.
        if (sprite.getNode().getTranslateY() > getGameSurface().getHeight() - displayNode.getBoundsInParent().getHeight()
                || sprite.getNode().getTranslateY() < 0) {
            sprite.setVelocityY(sprite.getVelocityY() * -1);
        }
    }

    /**
     * Remove missiles when they reach the wall boundaries.
     *
     * @param missile The missile to remove based on the wall boundaries.
     */
    private void removeMissiles(Missile missile) {
        // bounce off the walls when outside of boundaries
        if (missile.getNode().getTranslateX() > (getGameSurface().getWidth()
                - missile.getNode().getBoundsInParent().getWidth())
                || missile.getNode().getTranslateX() < 0) {

            getSpriteManager().addSpritesToBeRemoved(missile);
            getSceneNodes().getChildren().remove(missile.getNode());

        }
        if (missile.getNode().getTranslateY() > getGameSurface().getHeight()
                - missile.getNode().getBoundsInParent().getHeight()
                || missile.getNode().getTranslateY() < 0) {

            getSpriteManager().addSpritesToBeRemoved(missile);
            getSceneNodes().getChildren().remove(missile.getNode());
        }
    }

    /**
     * How to handle the collision of two sprite objects. Stops the particle by
     * zeroing out the velocity if a collision occurred. /** How to handle the
     * collision of two sprite objects. Stops the particle by
     *
     * @param spriteA Sprite from the first list.
     * @param spriteB Sprite from the second list.
     * @return boolean returns a true if the two sprites have collided otherwise
     * false.
     */
    @Override
    protected boolean handleCollision(Sprite spriteA, Sprite spriteB) {
        //TODO: implement collision detection here.
        // if collision between a missile and an invader occurs, remove both from the game
        // if collision between an invader and the ship occurs, remove the missile and decrement the ship's health

        if (spriteA instanceof Missile && spriteB.getClass().equals(Atom.class)) {
            if (spriteA.collide(spriteB) || spriteB.collide(spriteA)) {
                getSoundManager().playSound("CollisionSound");
                setCurrentScore(getCurrentScore() + 1);
                System.out.println("Missile and Atom collided");
                spriteA.handleDeath(this);
                spriteB.handleDeath(this);
                return true;
            }
        } else if (spriteA.getClass().equals(Atom.class) && spriteB instanceof Ship) {
            if (spriteA.collide(spriteB) || spriteB.collide(spriteA)) {
                if (!((Ship) spriteB).isShieldOn()) {
                    System.out.println("Atom and Ship collided");
                    spriteA.handleDeath(this);
                    // if the ship is hit, decrement the ship's health; if the ship's health is 0, remove the ship from the game and end the game
                    setCurrentLife(getCurrentLife() - 1);
                }
            }
        }
        return false;
    }

    public int getCurrentLife() {
        return currentLifeInt;
    }

    public void setCurrentLife(int currentLife) {
        this.currentLifeInt = currentLife;
        currentLives.setText("Lives: " + currentLife);
    }

    public int getCurrentScore() {
        return currentScoreInt;
    }

    public void setCurrentScore(int currentScoreInt) {
        this.currentScoreInt = currentScoreInt;
        currentScore.setText("Score: " + currentScoreInt);
    }

    public int getCurrentLevel() {
        return currentLevelInt;
    }

    public void setCurrentLevel(int currentLevelInt) {
        this.currentLevelInt = currentLevelInt;
        currentLevel.setText("Level: " + currentLevelInt);
    }
    public void handleGameOver(){
        shutdown();
        VBox gameOver = new VBox(40);
        gameOver.getChildren().add(new Text("Game Over"));
        gameOver.getChildren().add(new Text("Score: " + getCurrentScore()));
        gameOver.setAlignment(Pos.CENTER);
        Button restart = new Button("Restart");
        gameOver.getChildren().add(restart);
        Button quit = new Button("Quit");
        gameOver.getChildren().add(quit);
        Scene gameOverScene = new Scene(gameOver, 400,400);
        setGameSurface(gameOverScene);
        primaryStage1.setScene(gameOverScene);
        restart.setOnAction(e -> {
            setCurrentLife(3);
            getSceneNodes().getChildren().removeAll();
            getSpriteManager().getAllSprites().clear();
            setSoundManager(new SoundManager(3));
            beginGameLoop();
            initialize(primaryStage1);
            spaceShip.applyTheBrakes(spaceShip.getCenterX(), spaceShip.getCenterY());
        });
        quit.setOnAction(e -> {
            System.exit(0);
        });
    }
    public void handleLevel2(){
        shutdown();
        setCurrentLevel(getCurrentLevel()+1);
        VBox gameOver = new VBox(40);
        gameOver.getChildren().add(new Text("Next Level"));
        gameOver.getChildren().add(new Text("Score: " + getCurrentScore()));
        gameOver.setAlignment(Pos.CENTER);
        Button nextLevel = new Button("Next Level");
        gameOver.getChildren().add(nextLevel);
        Button quit = new Button("Too scared?");
        gameOver.getChildren().add(quit);
        Scene gameOverScene = new Scene(gameOver, 400,400);
        setGameSurface(gameOverScene);
        primaryStage1.setScene(gameOverScene);
        nextLevel.setOnAction(e -> {
            generateManySpheres(20,ResourcesManager.INADER_SPRITES_PATH2,5);
            spaceShip.changeShip(ResourcesManager.INVADER_SAT);
            setCurrentLife(3);
            getSceneNodes().getChildren().removeAll();
            getSpriteManager().getAllSprites().clear();
            setSoundManager(new SoundManager(3));
            beginGameLoop();
            primaryStage1.setTitle(getWindowTitle());
            setSceneNodes(new Group());
            setGameSurface(new Scene(getSceneNodes(), 1000, 600));
            getGameSurface().setFill(Color.BLACK);
            primaryStage1.setScene(getGameSurface());
            setupInput(primaryStage1);
            keyboardEventHandler(primaryStage1);
            generateManySpheres(15, ResourcesManager.INADER_SPRITES_PATH,2);
            getSpriteManager().addSprites(spaceShip);
            spaceShip.changeShip(ResourcesManager.SPACE_SHIP);
            getSceneNodes().getChildren().add(0, spaceShip.getNode());
            spaceShip.getNode().setTranslateX(getGameSurface().getWidth() / 2);
            spaceShip.getNode().setTranslateY(getGameSurface().getHeight() / 4 + 2 * getGameSurface().getHeight() / 4);
            setCurrentLevel(2);
            VBox stats = new VBox();
            HBox row3 = new HBox();
            currentScore.setText("Current Score: ");
            currentScore.setTextFill(Color.GREEN);
            row3.getChildren().add(currentScore);
            HBox row4 = new HBox();
            currentLevel.setText("Current Level: ");
            currentLevel.setTextFill(Color.GREEN);
            row4.getChildren().add(currentLevel);
            HBox row5 = new HBox();
            currentLives.setText("Current Lives: " + getCurrentLife());
            currentLives.setTextFill(Color.RED);
            row5.getChildren().add(currentLives);
            stats.getChildren().add(row3);
            stats.getChildren().add(row4);
            stats.getChildren().add(row5);
            getSceneNodes().getChildren().add(0, stats);
            getSoundManager().loadSoundEffects("laser", getClass().getClassLoader().getResource(ResourcesManager.SOUND_LASER));
            getSoundManager().loadSoundEffects("CollisionSound", getClass().getClassLoader().getResource(ResourcesManager.SOUND_COLLISION));
            spaceShip.applyTheBrakes(spaceShip.getCenterX(), spaceShip.getCenterY());
        });
        quit.setOnAction(e -> {
            System.exit(0);
        });
    }
    public void handleLevel3(){
        System.out.println("t bo");
//        setCurrentLevel(getCurrentLevel()+1);
//        System.out.println(currentLevelInt);
        shutdown();
        generateManySpheres(25,ResourcesManager.INADER_SPRITES_PATH2,7);
        //setCurrentLevel(getCurrentLevel()+1);
        VBox gameOver = new VBox(40);
        gameOver.getChildren().add(new Text("Next Level"));
        gameOver.getChildren().add(new Text("Score: " + getCurrentScore()));
        gameOver.setAlignment(Pos.CENTER);
        Button nextLevel = new Button("Next Level");
        gameOver.getChildren().add(nextLevel);
        Button quit = new Button("Too scared?");
        gameOver.getChildren().add(quit);
        Scene gameOverScene = new Scene(gameOver, 400,400);
        setGameSurface(gameOverScene);
        primaryStage1.setScene(gameOverScene);
        nextLevel.setOnAction(e -> {
            generateManySpheres(20,ResourcesManager.INADER_SPRITES_PATH2,5);
            spaceShip.changeShip(ResourcesManager.INVADER_SAT);
            setCurrentLife(3);
            getSceneNodes().getChildren().removeAll();
            getSpriteManager().getAllSprites().clear();
            setSoundManager(new SoundManager(3));
            beginGameLoop();
            primaryStage1.setTitle(getWindowTitle());
            setSceneNodes(new Group());
            setGameSurface(new Scene(getSceneNodes(), 1000, 600));
            getGameSurface().setFill(Color.BLACK);
            primaryStage1.setScene(getGameSurface());
            setupInput(primaryStage1);
            keyboardEventHandler(primaryStage1);
            generateManySpheres(15, ResourcesManager.INADER_SPRITES_PATH,2);
            getSpriteManager().addSprites(spaceShip);
            spaceShip.changeShip(ResourcesManager.SPACE_SHIP);
            getSceneNodes().getChildren().add(0, spaceShip.getNode());
            spaceShip.getNode().setTranslateX(getGameSurface().getWidth() / 2);
            spaceShip.getNode().setTranslateY(getGameSurface().getHeight() / 4 + 2 * getGameSurface().getHeight() / 4);
            setCurrentLevel(3);
            VBox stats = new VBox();
            HBox row3 = new HBox();
            currentScore.setText("Current Score: ");
            currentScore.setTextFill(Color.GREEN);
            row3.getChildren().add(currentScore);
            HBox row4 = new HBox();
            currentLevel.setText("Current Level: ");
            currentLevel.setTextFill(Color.GREEN);
            row4.getChildren().add(currentLevel);
            HBox row5 = new HBox();
            currentLives.setText("Current Lives: " + getCurrentLife());
            currentLives.setTextFill(Color.RED);
            row5.getChildren().add(currentLives);
            stats.getChildren().add(row3);
            stats.getChildren().add(row4);
            stats.getChildren().add(row5);
            getSceneNodes().getChildren().add(0, stats);
            getSoundManager().loadSoundEffects("laser", getClass().getClassLoader().getResource(ResourcesManager.SOUND_LASER));
            getSoundManager().loadSoundEffects("CollisionSound", getClass().getClassLoader().getResource(ResourcesManager.SOUND_COLLISION));
            spaceShip.applyTheBrakes(spaceShip.getCenterX(), spaceShip.getCenterY());
        });
        quit.setOnAction(e -> {
            System.exit(0);
        });
    }


}

