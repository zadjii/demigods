package areas;

import areas.eventQueue.MasterEventsGenerator;
import entities.buildings.*;
import entities.characters.Human;
import entities.characters.Meep;
import entities.characters.personas.*;
import entities.interactables.AestheticTile;
import entities.interactables.Interactable;
import entities.interactables.ResourceTile;
import entities.interactables.TallGrass;
import entities.tiles.Tiles;
import areas.eventQueue.EventQueue;
import org.lwjgl.util.Point;
import org.newdawn.slick.Color;
import util.Constants;
import util.Maths;

import java.util.ArrayList;
import java.util.Random;

/**
 * A generic "Overworld" Area, as he name implies. Mas functionality will come eventually.
 */
public class OverworldArea extends NewGameArea{


    /**This value will be set when the area is constructed.*/
    private int playerLevel = 0;

	public static final int NO_UNDERWORLD = -1;


	private int underworldMapID = NO_UNDERWORLD;
	private double distFromSpawn = -1;

	private Point respawnLocation = null;//only areas that have villages get this value assigned

    public OverworldArea(Random rand, int size, int ID, int playerLevel, int areaType, int feature, int state, double distFromSpawn) {
        super(rand, size, ID);
        this.playerLevel = playerLevel;
	    this.biome = areaType;
	    this.feature = feature;
	    this.state = state;
	    this.distFromSpawn = distFromSpawn;
        this.generate(areaType, feature);
	    this.generateEventQueue(areaType, feature, state, playerLevel, distFromSpawn);

    }
	public void rebootEventQueue(int state){
		this.state = state;
		if(this.events != null)this.events = null;
		if(feature == World.BASE
				||feature == World.COLONY
				||feature == World.STARTING_VILLAGE_FEATURE
				||feature == World.PASSIVE_VILLAGE_FEATURE
				||this.state == World.PASSIVE_VILLAGE_STATE
				||this.state == World.CLEARED
				){
			return;
		}
		this.getPersonas().clear();
		this.generateEventQueue(this.biome, this.feature, this.state, this.playerLevel, this.distFromSpawn);

		if(this.biome == World.PLAINS){
			for(int i =0;i<5;i++){
				int randX = rand.nextInt(size);
				int randY = rand.nextInt(size);
				if(passable[randX][randY]){
					PassiveMob mob = new PassiveMob(new Meep(randX, randY));
					this.getPersonas().add(mob);
				}
			}
		}
	}
	private void generateEventQueue(int biome, int feature, int state, int playerLevel, double distFromSpawn) {
		if(feature == World.BASE
				||feature == World.COLONY
				||feature == World.STARTING_VILLAGE_FEATURE
				||feature == World.PASSIVE_VILLAGE_FEATURE
				||state == World.PASSIVE_VILLAGE_STATE
				||state == World.CLEARED
				){
			this.events = null;
			return;
		}
		if(feature == World.HOSTILE_VILLAGE){
			//Here we'd add some mobs to the world based on the layout of the village, type of village.
			//maybe we wouldn't, instead doing that during village creation
			this.events = null;
			return;
		}
		this.events = MasterEventsGenerator.addBiomeEvents(this.events, biome, state, playerLevel, distFromSpawn);
		this.events = MasterEventsGenerator.addFeatureEvents(this.events, feature, state, playerLevel, distFromSpawn);

		if(feature == World.HOSTILE_REINFORCEMENTS){
			//Here we'd add some extra mobs to the queue based on what type of hostile village it is.
		}
	}

	//    public OverworldArea(Random rand, int size, int ID, int playerLevel, int areaType) {
//        super(rand, size, ID);
//        this.playerLevel = playerLevel;
//        this.generate(areaType);
//    }
//    public OverworldArea(Random rand, int size, int ID, int playerLevel) {
//        super(rand, size, ID);
//        this.playerLevel = playerLevel;
//        this.generate(rand.nextInt(4));
//    }
//    private void generate(int areaType){
//		/*
//		* THIS WILL NEED TO BE CHANGED INTO A WORLD-COMPATIBLE GENERATOR.
//		*
//		* NOT A VALID WORLD-BASED GENERATOR. USE generate(int areaType, int feature)
//		* */
//        switch (areaType){
//            case 0:
//                this.map = this.generateGenericPlains();this.biome = World.PLAINS;break;
//            case 1:
//                this.map = this.generateGenericDesert();this.biome = World.DESERT;break;
//            case 2:
//                this.map = this.generateGenericWinter();this.biome = World.TUNDRA;break;
//            case 3:
//                this.map = this.generateGenericForest();this.biome = World.FOREST;break;
//        }
//
//        //this.map = this.generateGenericPlains();
//        this.events = EventQueue.newClearingTester();
//
//        if(playerLevel >= 3){
//            caveEntrance();
//            this.events = EventQueue.newChainedPlainsTester();
//        }
//        this.resources = ResourceTile.getResourceArrayFromArea(this.map);
//        NewGameArea.refreshPassable(this);
//
////		if(playerLevel > 10)
////			generateBasicVillage();
////		generateAwesomeVillage();
//
//        if(this.biome == World.PLAINS){
//            for(int i =0;i<5;i++){
//                int randX = rand.nextInt(size);
//                int randY = rand.nextInt(size);
//                if(passable[randX][randY]){
//                    PassiveMob mob = new PassiveMob(new Meep(randX, randY));
//                    this.getPersonas().add(mob);
//                }
//            }
//        }
//    }
    private void generate(int areaType, int features){

        switch (areaType){
            case World.BEACH:
                this.map = this.generateGenericPlains();this.biome = World.PLAINS;break;
            case World.PLAINS:
                this.map = this.generateGenericPlains();this.biome = World.PLAINS;break;
            case World.MOUNTAINS:
                this.map = this.generateGenericMountains();this.biome = World.MOUNTAINS;break;
            case World.SWAMP:
                this.map = this.generateGenericSwamp();this.biome = World.SWAMP;break;
            case World.DESERT:
                this.map = this.generateGenericDesert();this.biome = World.DESERT;break;
            case World.FOREST:
                this.map = this.generateGenericForest();this.biome = World.FOREST;break;
            case World.CLIFFS:
                this.map = this.generateGenericCliffs();this.biome = World.CLIFFS;break;
            case World.TAIGA:
                this.map = this.generateGenericTaiga();this.biome = World.TAIGA;break;
            case World.TUNDRA:
                this.map = this.generateGenericWinter();this.biome = World.TUNDRA;break;
            case World.JUNGLE:
                this.map = this.generateGenericJungle();this.biome = World.JUNGLE;break;
        }

//        this.events = EventQueue.newClearingTester();
//	    this.events = EventQueue.newChainedPlainsTester();


//		dungeonEntrance();

        if(features == World.CAVE){
            caveEntrance();
//            this.events = EventQueue.newChainedPlainsTester();
        }
        else if(features == World.DUNGEON){
            dungeonEntrance();
//            this.events = EventQueue.newChainedPlainsTester();
        }
        else if(features == World.PASSIVE_VILLAGE_FEATURE){
            generateAwesomeVillage();
            this.events = null;
	        this.state = World.PASSIVE_VILLAGE_STATE;
        }
        else if(features == World.STARTING_VILLAGE_FEATURE){
	        generateStartingVillage();
	        this.events = null;
	        this.state = World.PASSIVE_VILLAGE_STATE;
        }
        else{
//            generateAwesomeVillage();
        }
        this.resources = ResourceTile.getResourceArrayFromArea(this.map);
        NewGameArea.refreshPassable(this);

        if(this.biome == World.PLAINS){
            for(int i =0;i<5;i++){
                int randX = rand.nextInt(size);
                int randY = rand.nextInt(size);
                if(passable[randX][randY]){
                    PassiveMob mob = new PassiveMob(new Meep(randX, randY));
                    this.getPersonas().add(mob);
                }
            }
        }
    }

    private int[][] generateGenericBeach(){
        int[][] tiles = new int[this.size][this.size];
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles.length; y++){

                int value = randomPlains();
                if(value == Tiles.LEAVES){
                    //figure out if tallgrass, of one of the little rock things.
                    double chance = this.rand.nextDouble();

                    if(chance < .55) value = Tiles.GRASS10;
                    else value = Tiles.GRASS00;

                    if(chance < .05){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.ROCK);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .1){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.PEBBLES);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .2){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.GRASS_ROCKS);
                        interactables[x][y] = tile;
                    }
                    else{
                        interactables[x][y] = new TallGrass(x*16, y*16);
                    }
                }

                tiles[x][y] = value;
            }
        }
        return tiles;
    }

    private int[][] generateGenericPlains(){
        int[][] tiles = new int[this.size][this.size];
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles.length; y++){

                int value = randomPlains();
                if(value == Tiles.LEAVES){
                    //figure out if tallgrass, of one of the little rock things.
                    double chance = this.rand.nextDouble();

                    if(chance < .55) value = Tiles.GRASS10;
                    else value = Tiles.GRASS00;

                    if(chance < .05){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.ROCK);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .1){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.PEBBLES);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .2){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.GRASS_ROCKS);
                        interactables[x][y] = tile;
                    }
                    else{
                        interactables[x][y] = new TallGrass(x*16, y*16);
                    }
                }

                tiles[x][y] = value;
            }
        }
        return tiles;
    }

    private int[][] generateGenericMountains(){
        int[][] tiles = new int[this.size][this.size];
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles.length; y++){

                int value = randomMountains();
                if(value == Tiles.LEAVES){
                    //figure out if tallgrass, of one of the little rock things.
                    double chance = this.rand.nextDouble();

//                    if(chance < .55)
	                    value = Tiles.DIRT;

//                    else value = Tiles.CAVE_STONE;//todo: these stone, cave stone references probs don't work?

                    if(chance < .2){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.ROCK);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .5){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.PEBBLES);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .7){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.GRASS_ROCKS);
                        interactables[x][y] = tile;
                    }
                    else{
//                        value = Tiles.OVERWORLD_STONE;//todo, make this stone?

	                    AestheticTile tile = new AestheticTile(x*16, y*16);
	                    tile.setType(AestheticTile.PEBBLES);
	                    interactables[x][y] = tile;
//						interactables[x][y] = new TallGrass(x*16, y*16);
                    }
                }

                tiles[x][y] = value;
	            passable[x][y] = Tiles.passable[value];
            }
        }
        return tiles;
    }

    private int[][] generateGenericSwamp(){
        int[][] tiles = new int[this.size][this.size];
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles.length; y++){

                int value = randomSwamp();
                if(value == Tiles.LEAVES){
                    //figure out if tallgrass, of one of the little rock things.
                    double chance = this.rand.nextDouble();

                    if(chance < .15) value = Tiles.DIRT;
                    else if(chance < .45) value = Tiles.GRASS10;
                    else value = Tiles.GRASS00;

                    if(chance < .05){
                        value = Tiles.TREE;
                    }
                    else if(chance < .3){
                        TallGrass tile = new TallGrass(x*16, y*16);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .4){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.GRASS_ROCKS);
                        interactables[x][y] = tile;
                    }
                    else{
                        value = Tiles.TREE;
                    }
                }

                tiles[x][y] = value;
            }
        }
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles.length; y++){
                if(Maths.dist(x,size/2,y,size/2) > (size/4)){
                    double chance = this.rand.nextDouble();
                    if(chance < .15) tiles[x][y] = Tiles.TREE;
                }
            }
        }
        return tiles;
    }

    private int[][] generateGenericCliffs(){
        int[][] tiles = new int[this.size][this.size];
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles.length; y++){

                int value = randomMountains();
                if(value == Tiles.LEAVES){
                    //figure out if tallgrass, of one of the little rock things.
                    double chance = this.rand.nextDouble();

                    if(chance < .55) value = Tiles.DIRT;
                    else if(chance < .75)value = Tiles.GRASS00;
                    else value = Tiles.GRASS10;

                    if(chance < .2){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.ROCK);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .5){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.PEBBLES);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .6){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.GRASS_ROCKS);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .9){
//                        value = Tiles.OVERWORLD_STONE;//todo stone here too
	                    value = Tiles.DIRT;
//						interactables[x][y] = new TallGrass(x*16, y*16);
                    }
                    else{
//						value = Tiles.OVERWORLD_STONE;
                        interactables[x][y] = new TallGrass(x*16, y*16);
                    }
                }

                tiles[x][y] = value;
            }
        }
        return tiles;
    }

    private int[][] generateGenericTaiga(){
        int[][] tiles = new int[this.size][this.size];
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles.length; y++){

                int value = randomPlains();
                if(value == Tiles.LEAVES){
                    //figure out if tallgrass, of one of the little rock things.
                    double chance = this.rand.nextDouble();

                    if(chance < .55) value = Tiles.SNOW;//Tiles.GRASS10;
                    else value = Tiles.SNOW;//Tiles.GRASS00;

                    if(chance < .05){
                        value = Tiles.SNOWTREE;
                    }
                    else if(chance < .3){
                        TallGrass tile = new TallGrass(x*16, y*16);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .4){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.GRASS_ROCKS);
                        interactables[x][y] = tile;
                    }
                    else{
                        value = Tiles.SNOWTREE;
                    }
                }

                tiles[x][y] = value;
            }
        }
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles.length; y++){
                if(Maths.dist(x,size/2,y,size/2) > (size/4)){
                    double chance = this.rand.nextDouble();
                    if(chance < .15) tiles[x][y] = Tiles.SNOWTREE;
                }
            }
        }
        return tiles;
    }

    private int[][] generateGenericTundra(){
        int[][] tiles = new int[this.size][this.size];
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles.length; y++){

                int value = randomPlains();
                if(value == Tiles.LEAVES){
                    //figure out if tallgrass, of one of the little rock things.
                    double chance = this.rand.nextDouble();

                    if(chance < .55) value = Tiles.SNOW;
                    else value = Tiles.SNOW;

                    if(chance < .05){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.ROCK);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .1){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.PEBBLES);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .2){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.GRASS_ROCKS);
                        interactables[x][y] = tile;
                    }
                    else{
                        //interactables[x][y] = new TallGrass(x*16, y*16);
                    }
                }

                tiles[x][y] = value;
            }
        }
        return tiles;
    }

    private int[][] generateGenericJungle(){
        int[][] tiles = new int[this.size][this.size];
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles.length; y++){

                int value = randomJungle();
                if(value == Tiles.LEAVES){
                    //figure out if tallgrass, of one of the little rock things.
                    double chance = this.rand.nextDouble();

                    if(chance < .55) value = Tiles.GRASS10;
                    else value = Tiles.GRASS00;

                    if(chance < .05){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.ROCK);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .1){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.PEBBLES);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .15){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.GRASS_ROCKS);
                        interactables[x][y] = tile;
                    }
                    else{
                        interactables[x][y] = new TallGrass(x*16, y*16);
                    }
                }

                tiles[x][y] = value;
            }
        }
        return tiles;
    }

    private int[][] generateGenericForest(){
        int[][] tiles = new int[this.size][this.size];
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles.length; y++){

                int value = randomPlains();
                if(value == Tiles.LEAVES){
                    //figure out if tallgrass, of one of the little rock things.
                    double chance = this.rand.nextDouble();

                    if(chance < .55) value = Tiles.GRASS10;
                    else value = Tiles.GRASS00;

                    if(chance < .05){
                        value = Tiles.TREE;
                    }
                    else if(chance < .3){
                        TallGrass tile = new TallGrass(x*16, y*16);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .4){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.GRASS_ROCKS);
                        interactables[x][y] = tile;
                    }
                    else{
                        value = Tiles.TREE;
                    }
                }

                tiles[x][y] = value;
            }
        }
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles.length; y++){
                if(Maths.dist(x,size/2,y,size/2) > (size/4)){
                    double chance = this.rand.nextDouble();
                    if(chance < .15) tiles[x][y] = Tiles.TREE;
                }
            }
        }
        return tiles;
    }

    private int[][] generateGenericDesert(){
        int[][] tiles = new int[this.size][this.size];
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles.length; y++){

                int value = randomDesert();
                if(value == Tiles.LEAVES){
                    //figure out if tallgrass, of one of the little rock things.
                    double chance = this.rand.nextDouble();

                    if(chance < .55) value = Tiles.SAND;
                    else value = Tiles.SAND;

                    if(chance < .05){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.ROCK);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .1){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.PEBBLES);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .2){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.ROCK);
                        interactables[x][y] = tile;
                    }
                    else{
                        //interactables[x][y] = new TallGrass(x*16, y*16);
                    }
                }

                tiles[x][y] = value;
            }
        }
        return tiles;

    }

    private int[][] generateGenericWinter(){
        int[][] tiles = new int[this.size][this.size];
        for(int x = 0; x < tiles.length; x++){
            for(int y = 0; y < tiles.length; y++){

                int value = randomWinter();
                if(value == Tiles.LEAVES){
                    //figure out if tallgrass, of one of the little rock things.
                    double chance = this.rand.nextDouble();

                    if(chance < .55) value = Tiles.SNOW;
                    else value = Tiles.SNOW;

                    if(chance < .05){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.ROCK);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .1){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.PEBBLES);
                        interactables[x][y] = tile;
                    }
                    else if(chance < .2){
                        AestheticTile tile = new AestheticTile(x*16, y*16);
                        tile.setType(AestheticTile.GRASS_ROCKS);
                        interactables[x][y] = tile;
                    }
                    else{
                        interactables[x][y] = new TallGrass(x*16, y*16);
                    }
                }

                tiles[x][y] = value;
            }
        }
        return tiles;
    }

    private int randomPlains(){
        double chance = this.rand.nextDouble();
        if(chance < .005)return Tiles.TREE;
        if(chance < .05)return Tiles.LEAVES;
        if(chance < .55)return Tiles.GRASS00;
        else return Tiles.GRASS10;
    }
    private int randomJungle(){
        double chance = this.rand.nextDouble();
        if(chance < .05)return Tiles.TREE;
        if(chance < .45)return Tiles.LEAVES;
        if(chance < .75)return Tiles.GRASS00;
        else return Tiles.GRASS10;
    }
    private int randomSwamp(){
        double chance = this.rand.nextDouble();
        if(chance < .005)return Tiles.TREE;
        if(chance < .01)return Tiles.WATER;
        if(chance < .25)return Tiles.LEAVES;
        if(chance < .35)return Tiles.DIRT;
        if(chance < .65)return Tiles.GRASS00;
        else return Tiles.GRASS10;

    }
    private int randomDesert(){
        double chance = this.rand.nextDouble();
        if(chance < .005)return Tiles.CACTUS;
        if(chance < .05)return Tiles.LEAVES;
        if(chance < .55)return Tiles.SAND;
        else return Tiles.SAND;
    }
    private int randomWinter(){
        double chance = this.rand.nextDouble();
        if(chance < .005)return Tiles.SNOWTREE;
        if(chance < .05)return Tiles.LEAVES;
        if(chance < .55)return Tiles.SNOW;
        else return Tiles.SNOW;

    }
    private int randomMountains(){
        double chance = this.rand.nextDouble();
        if(chance < .005)return Tiles.TREE;
        if(chance < .15)return Tiles.LEAVES;
        if(chance < .65)return Tiles.DIRT;
        else return Tiles.CAVE_STONE;

    }

    private void caveEntrance(){

        this.feature = World.CAVE;

        int centerGX = (rand.nextInt(size/2) + size/4);
        int centerGY = (rand.nextInt(size/2) + size/4);

        int centerX = centerGX*16;
        int centerY = centerGY*16;

        for(int radius = 0; radius < 16 * 8; radius+= 4){
            for(Point p:getCircle(centerX, centerY, radius)){

                this.map[p.getX()/16][p.getY()/16] = Tiles.DIRT;
                if(radius > (16 * 5))
                    if(rand.nextDouble()<.2)
                        this.map[p.getX()/16][p.getY()/16] = Tiles.OVERWORLD_STONE;
            }
        }

        CaveBuilding cave = new CaveBuilding(0,0);
        cave.setXY(centerX - (3*16), centerY - 3*16);
        interactables
                [centerGX+(int)cave.getWidth()/16 - 1 - 3]
                [centerGY+(int)cave.getHeight()/16 - 1 - 3] = cave;

	    NewGameArea.refreshPassable(this);

    }
    private void dungeonEntrance(){

        this.feature = World.DUNGEON;

        int centerGX = (rand.nextInt(size/2) + size/4);
        int centerGY = (rand.nextInt(size/2) + size/4);

        int centerX = centerGX*16;
        int centerY = centerGY*16;

        for(int radius = 0; radius < 16 * 8; radius+= 4){
            for(Point p:getCircle(centerX, centerY, radius)){

                this.map[p.getX()/16][p.getY()/16] = Tiles.DIRT;
                if(radius > (16 * 6)){
                    if(rand.nextDouble()<.1)
                        this.map[p.getX()/16][p.getY()/16] = Tiles.OVERWORLD_STONE;
                    else{}//mostly here to end scope
                }else{
                    if(rand.nextDouble()<.3)
                        this.map[p.getX()/16][p.getY()/16] = Tiles.DUNGEONFLOOR;
                    else{}//mostly here to end scope
                }
            }
        }

        DungeonBuilding dung = new DungeonBuilding(0,0);
        dung.setXY(centerX - (3*16), centerY - 3*16);
        interactables
                [centerGX+(int)dung.getWidth()/16 - 1 - 3]
                [centerGY+(int)dung.getHeight()/16 - 1 - 3] = dung;

	    NewGameArea.refreshPassable(this);

    }

	private void generateAwesomeVillage(){
		PassiveVillage.generateVillage(this, this.size/16);
//        PaladinVillage.apply(this, PassiveVillage.generateOverview(this, this.size/16));

    }

	private void generateStartingVillage(){
		StartingVillage.generateVillage(this, this.size/16);
//		StartingVillage.apply(this, StartingVillage.generateOverview(this, this.size/16));

	}


	public int getUnderworldMapID() {
		return underworldMapID;
	}

	public void setUnderworldMapID(int underworldMapID) {
		this.underworldMapID = underworldMapID;
	}

	public Point getRespawnLocation() {
		return respawnLocation;
	}

	public void setRespawnLocation(Point respawnLocation) {
		this.respawnLocation = respawnLocation;
	}

}
