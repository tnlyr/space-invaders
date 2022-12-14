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
    public static final String INVADER_BG2_1 = IMAGES_FOLDER + "bgLevel2.1.gif";
    public static final String INVADER_BG2_2 = IMAGES_FOLDER + "bgLevel2.2.gif";
    public static final String INVADER_BG2_3 = IMAGES_FOLDER + "bgLevel2.3.gif";
    public static final String INVADER_BG2_4 = IMAGES_FOLDER + "bgLevel2.4.gif";
    public static final String INVADER_BG3_1 = IMAGES_FOLDER + "bgLevel3.1.png";
    public static final String INVADER_BG3_2 = IMAGES_FOLDER + "bgLevel3.2.png";
    public static final String INVADER_BG3_3 = IMAGES_FOLDER + "bgLevel3.3.png";
    public static final String INVADER_BG3_4 = IMAGES_FOLDER + "bgLevel3.4.png";

    // Explosion sprites.
    public static final String EXPLOSION = IMAGES_FOLDER + "explotion-explode.gif";


    // Sound effect files
    public static final String SOUND_LASER = SOUNDS_FOLDER + "burstFire.mp3";
    public static final String SOUND_LASER2 = SOUNDS_FOLDER + "shootingLevel2.wav";
    public static final String SOUND_LASER3 = SOUNDS_FOLDER + "ShootingLevel3.wav";
    public static final String SOUND_COLLISION = SOUNDS_FOLDER + "explosion-5981 .mp3";
    public static final String SOUND_COLLISION2 = SOUNDS_FOLDER + "explode.wav";
    public static final String SOUND_COLLISION3 = SOUNDS_FOLDER + "explode3.wav";


    //public static final String SOUND_LASER = SOUNDS_FOLDER + "alienMove2.wav";
    
    
    public static final String[] INADER_SPRITES_PATH = {			
			INVADER_UFO, INVADER_SAT, INVADER_ALIEN,INVADER_SCI_FI
	};
    public static final String[] INADER_SPRITES_PATH2 = {
            INVADER_BG2_1,INVADER_BG2_2,INVADER_BG2_3,INVADER_BG2_4
    };
    public static final String[] INADER_SPRITES_PATH3 = {
            INVADER_BG3_1,INVADER_BG3_2,INVADER_BG3_3,INVADER_BG3_4
    };

//    public static final String ROCKET_SMALL = IMAGES_FOLDER + "rocket.png";
    public static HashMap<Integer, String> getInvaderSprites() {
        HashMap<Integer, String> invaders = new HashMap<Integer, String>();
        invaders.put(1, ResourcesManager.IMAGES_FOLDER + "large_invader_b.png");
        invaders.put(2, ResourcesManager.IMAGES_FOLDER + "small_invader_b.png");
        return invaders;
    }

}
