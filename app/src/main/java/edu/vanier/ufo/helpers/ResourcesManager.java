package edu.vanier.ufo.helpers;

import java.util.HashMap;


/**
 * A resource manager providing useful resource definitions used in this game.
 *
 * @author Sleiman
 */
public class ResourcesManager {

    /**
     * Used to control the speed of the game.
     */
    public static final int FRAMES_PER_SECOND = 85;
    private static final String RESOURCES_FOLDER = "";
    private static final String IMAGES_FOLDER = RESOURCES_FOLDER + "images/";
    private static final String SOUNDS_FOLDER = RESOURCES_FOLDER + "sounds/";
    // Ship images.
    public static final String SPACE_SHIP = IMAGES_FOLDER + "spiked ship.png";
    public static final String SPACE_STAR_SHIP = IMAGES_FOLDER + "starship.png";
    public static final String SPACE_TANK = IMAGES_FOLDER + "tank.png";
    // Rocket images
    public static final String ROCKET_SMALL = IMAGES_FOLDER + "spaceMissiles_001.png";
    public static final String ROCKET_FIRE = IMAGES_FOLDER + "spaceMissiles_002.png";

    // Invader sprites.
    public static final String INVADER_LARGE_SHIP = IMAGES_FOLDER + "large_invader_b.png";
    public static final String INVADER_SMALL_SHIP = IMAGES_FOLDER + "small_invader_b.png";
    public static final String INVADER_UFO = IMAGES_FOLDER + "spaceStation_001.png";
    public static final String INVADER_SAT = IMAGES_FOLDER + "spaceStation_002.png";
    public static final String INVADER_ALIEN = IMAGES_FOLDER + "extra.png";
    public static final String INVADER_SCI_FI = IMAGES_FOLDER + "spaceAstronauts_009.png";

    // Explosion sprites.
    public static final String EXPLOSION = IMAGES_FOLDER + "explotion-explode.gif";


    // Sound effect files
    public static final String SOUND_LASER = SOUNDS_FOLDER + "burstFire.mp3";
    public static final String SOUND_COLLISION = SOUNDS_FOLDER + "explosion-5981 .mp3";
    //public static final String SOUND_LASER = SOUNDS_FOLDER + "alienMove2.wav";
    
    
    public static final String[] INADER_SPRITES_PATH = {			
			INVADER_UFO, INVADER_SAT, INVADER_ALIEN,INVADER_SCI_FI
	};

//    public static final String ROCKET_SMALL = IMAGES_FOLDER + "rocket.png";
    public static HashMap<Integer, String> getInvaderSprites() {
        HashMap<Integer, String> invaders = new HashMap<Integer, String>();
        invaders.put(1, ResourcesManager.IMAGES_FOLDER + "large_invader_b.png");
        invaders.put(2, ResourcesManager.IMAGES_FOLDER + "small_invader_b.png");
        return invaders;
    }

}
