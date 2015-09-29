package areas;

import entities.characters.personas.Player;
import org.lwjgl.util.Point;
import util.Maths;
import util.Polygon;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import org.newdawn.slick.Color;

public class World implements Serializable {
    public static final int SIZE = 512;
    public static final int WATER_LEVEL = 12;

	//Biomes. Climates. Types of areas.
    public static final int INVALID     = -1;
    public static final int WATER       = 0;
    public static final int BEACH       = 1;
    public static final int PLAINS      = 2;
    public static final int MOUNTAINS   = 3;
    public static final int SWAMP       = 4;
    public static final int DESERT      = 5;
    public static final int FOREST      = 6;
    public static final int CLIFFS      = 7;
    public static final int TAIGA       = 8;
    public static final int TUNDRA      = 9;
    public static final int JUNGLE      = 10;
    public static final int DESOLATION  = 11;//like, near a volcano, but not the caldera
    public static final int VOLCANO     = 12;//like, near a volcano, but not the caldera


	//Possible features of an area. These are mutually exclusive. One feature at a time.
	public static final int IN_UNDERWORLD = -1; //any area in the underworld doesn't have any other features. sub-levels just exist
	public static final int NOTHING = 0;
    public static final int CAVE = 1;
    public static final int DUNGEON = 2;
	public static final int PASSIVE_VILLAGE_FEATURE = 3;
	public static final int BASE = 4;
	public static final int COLONY = 5;
	public static final int HOSTILE_VILLAGE = 6;
    public static final int MOUNTAIN_CAVES = 7;
    public static final int LABYRINTH = 8;
    public static final int CRYPT = 9;
    public static final int DESERT_TOMB = 10;
    public static final int CRYSTAL_PLAINS = 11;
    public static final int SPIRIT_FOREST = 12;
    public static final int ORC_CAMP = 13;
    public static final int ORC_STRONGHOLD = 14;
	public static final int STARTING_VILLAGE_FEATURE = 15;

	//States. These are also mutually exclusive modifiers.
	public static final int CLEARED = -1; //this is the basic cleared state.
	public static final int NOT_CLEARED = 0; //this is the basic not-cleared state. anything with more monsters also has a number
	public static final int OVERRUN = 1; //More than usual number of enemies
	public static final int HOSTILE_REINFORCEMENTS = 2; //Another village has troops in this area
	public static final int REINFORCEMENTS = 3; //Also acts as cleared, your troops are holding the area.
	public static final int PASSIVE_VILLAGE_STATE = 4; //Cannot be anything else
	public static final int UNDERWORLD_ENTRANCE_STATE = 5; //Cannot be anything else

    public static final Color WATER_COLOR = new Color(104,187,255);
    public static final Color SAND_COLOR = new Color(0xe7e2a2);
    public static final Color PLAINS_COLOR = new Color(0x8aec34);
    public static final Color FOREST_COLOR = new Color(0x6abc34);
    public static final Color SWAMP_COLOR = new Color(0x6a8c34);
    public static final Color DESERT_COLOR = new Color(0xf7f2a2);
    public static final Color MOUNTAINS_COLOR = new Color(0x958042);
    public static final Color CLIFFS_COLOR = new Color(0x755022);
    public static final Color TAIGA_COLOR = new Color(0xcafcd4);
    public static final Color TUNDRA_COLOR = new Color(0xdaece4);
    public static final Color JUNGLE_COLOR = new Color(0x6ac34);

    public static final Color CAVE_COLOR = new Color(0x755022);
    public static final Color DUNGEON_COLOR = new Color(0x555042);
    public static final Color PASSIVE_VILLAGE_COLOR = new Color(0xd0d0c0);

    private short[][] maps;
    private short[][] features;
    private short[][] states;
	private boolean[][] explored;
	/**
     * this will probably be removed by release
     * a way of storing the generated climate without touching it
     */
    private float[][] masterHeat;
	private int equator;//y-level of the equator
	private float equatorTemp;

	private int seed;
    Random rand;
    private String worldName;
    private String playerName;
    public World(int seed, Random rand, String worldName, String playerName){
        maps = new short[SIZE][SIZE];
        features = new short[SIZE][SIZE];
        states = new short[SIZE][SIZE];
        explored = new boolean[SIZE][SIZE];

        masterHeat = new float[SIZE][SIZE];

        this.seed = seed;
        this.rand = rand;
        this.worldName = worldName;
        this.playerName = playerName;
        init();
    }
    private void init(){
        int[][] heights = new int[SIZE][SIZE];
        float[][] wetness = new float[SIZE][SIZE];
        float[][] climate = new float[SIZE][SIZE];//heat level
        int[][] features = new int[SIZE][SIZE];


        equator = rand.nextInt(SIZE/3) + (SIZE/3);
        equatorTemp = rand.nextFloat()*.35f + .35f;

        generateHeights(heights);

        generateHeat(heights,masterHeat);
        generateHeat(heights,climate);
        for (int i = 0; i < 50; i++) {
            generatePolygonBiomes(1, heights,  wetness,  climate);

            if (i == 100){
                //	generateHeat(heights,climate);
            }


        }
        biomeScatter(15, wetness, climate);

        smoothFloatmap(wetness);
        smoothFloatmap(climate);
        biomeScatter(15, wetness, climate);

        smoothFloatmap(wetness);
        smoothFloatmap(climate);

        smoothFloatmap(wetness);
        smoothFloatmap(climate);
        //generateHeat(heights,climate);

        //generateMountainRangeContinent(heights, 1);
        //generateWetnessClimate(heights,wetness,climate);

		/*
		 try {
			testerOutputHeightmapImage(heights);
			//testerOutputFloatmapImage(climate);
			//testerOutputFloatmapImage(wetness);
		} catch (IOException e) {
			e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
		}//*/
        makeWorld001(heights,wetness,climate,features);
        generateFeatures(heights,wetness,climate, features);

    }
    private void makeWorld(int[][] heights,float[][] wetness,float[][] climate,int[][] features){
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                int h = heights[x][y];
                float w = wetness[x][y];
                float c = climate[x][y];
				/*
				if(heights[x][y] < WATER_LEVEL){
					maps[x][y] = WATER;
				}
				else if(heights[x][y] < WATER_LEVEL+1){
					maps[x][y] = BEACH;
				}
				else if(heights[x][y] < 63+1){
					maps[x][y] = PLAINS;
				}
				else if(heights[x][y] < 128+1){
					maps[x][y] = MOUNTAINS;
				}
//				else{
//					maps[x][y] =(short) heights[x][y];
//				}
				//*/
                //*
                if(h < WATER_LEVEL){
                    maps[x][y] = WATER;
                }
                else if(h>128 && w > .15 && w < .3){
                    maps[x][y] = MOUNTAINS;
                }
                else if(c > .75f && w < .35){
                    maps[x][y] = DESERT;
                }
                else if(c > .75f && w >= .35 && w <= .75){
                    if(w > .65){
                        if(rand.nextBoolean())maps[x][y] = JUNGLE;
                        else maps[x][y] = PLAINS;
                    }
                    else if (w<.45){
                        if(rand.nextBoolean())maps[x][y] = DESERT;
                        else maps[x][y] = PLAINS;
                    }else maps[x][y] = PLAINS;
                }
                else if(c > .75f && w > .75){
                    maps[x][y] = JUNGLE;
                }
                else if(w > .85 && h < 40 && c > .4 && c < .6){
                    maps[x][y] = SWAMP;
                }
                else if(c < .15 && h < 200){
                    if(rand.nextDouble() < .1)maps[x][y] = TAIGA;
                    else maps[x][y] = TUNDRA;
                }else if(c < .15 && h >= 200){
                    if(rand.nextDouble() < .2)maps[x][y] = TAIGA;
                    else maps[x][y] = MOUNTAINS;
                }else if(h < 128){
                    if(rand.nextDouble() < .01)maps[x][y] = FOREST;
                    else maps[x][y] = PLAINS;
                }else if(h >= 128){
                    if(rand.nextDouble() < .01)maps[x][y] = FOREST;
                    else if(rand.nextDouble() < .1)maps[x][y] = CLIFFS;
                    else maps[x][y] = MOUNTAINS;
                }
                //*/
                else{
                    maps[x][y] = INVALID;
                }
            }
        }
        cliffDetection(heights, maps);
    }
    private void makeWorld001(int[][] heights,float[][] wetness,float[][] climate,int[][] features){
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                int h = heights[x][y];
                float w = wetness[x][y];
                float c = climate[x][y];

                //*
                if(h < WATER_LEVEL){
                    maps[x][y] = WATER;
                }
                else if(h>=WATER_LEVEL && h < WATER_LEVEL+2){
                    maps[x][y] = BEACH;
                }
                else if(h>70){
                    maps[x][y] = MOUNTAINS;
                }
                else if(c > .35f && w < .15){
                    maps[x][y] = DESERT;
                }
                else if(c > .35f && w >= .15 && w <= .35){
                    if(w > .35){
                        if(rand.nextBoolean())maps[x][y] = JUNGLE;
                        else maps[x][y] = PLAINS;
                    }
                    else if (w<.15){
                        if(rand.nextBoolean())maps[x][y] = DESERT;
                        else maps[x][y] = PLAINS;
                    }else maps[x][y] = PLAINS;
                }
                else if(c > .35f && w > .45){
                    maps[x][y] = JUNGLE;
                }
                else if(w > .35 && h < 40 && c > .1 && c < .2){
                    maps[x][y] = SWAMP;
                }
                else if(c < .15 && h < 200){
                    if(rand.nextDouble() < .1)maps[x][y] = TAIGA;
                    else maps[x][y] = TUNDRA;
                }else if(c < .15 && h >= 200){
                    if(rand.nextDouble() < .2)maps[x][y] = TAIGA;
                    else maps[x][y] = MOUNTAINS;
                }else if(h < 128){
                    if(rand.nextDouble() < .01)maps[x][y] = FOREST;
                    else maps[x][y] = PLAINS;
                }else if(h >= 128){
                    if(rand.nextDouble() < .01)maps[x][y] = FOREST;
                    else if(rand.nextDouble() < .1)maps[x][y] = CLIFFS;
                    else maps[x][y] = MOUNTAINS;
                }
                //*/
                else{
                    maps[x][y] = INVALID;
                }
            }
        }
        cliffsAndMountains(heights, maps);
    }
    private void generateHeights(int[][] heights){
        for(int i = 0; i < (SIZE/25); i++){
            //if(i%(MAP_SIZE/10) == 0)cout<<"0";
            dropGenerate(SIZE*10, 2, SIZE/16, 0,255,true, heights);
        }
        polygonPairGeneration(1, heights);
        smoothHeightmap(heights);
        smoothHeightmap(heights);

        for(int i = 0; i < (SIZE/15); i++){
            //if(i%(MAP_SIZE/10) == 0)cout<<"0";
            dropGenerate(SIZE*5, 2, SIZE/16, 0,100,true, heights);
        }
        polygonPairGeneration(2, heights);
        smoothHeightmap(heights);
        for(int i = 0; i < (SIZE/25); i++){
            //if(i%(MAP_SIZE/10) == 0)cout<<"0";
            dropGenerate(SIZE*5, 2, SIZE/16, 5,100,true, heights);
        }
        for(int i = 0; i < (SIZE/25); i++){
            //if(i%(MAP_SIZE/10) == 0)cout<<"0";
            dropGenerate(SIZE*5, 2, SIZE/16, 25,200,true, heights);
        }
    }
    private void generateWetnessClimate(int[][] heights, float[][] wetness, float[][] climate){
        generateHeat(heights, climate);
//		wetness(heights, wetness);

    }
    private void generateHeat(int[][] heights, float[][] climate){
		/*
		* Heat level loops
		* lower temperature at higher altitudes and more extreme latitudes
		*
		* if loops are switched in order, then the lat. work could
		*       be done once per outer loop, as opposed to all the time (N^2 -> N improvement...)
		* */
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                //Altitude component
                int height = heights[x][y];
                if(height<=200){
                    climate[x][y] = -0.3f * ((float)(height))/(200.0f) + 0.35f;
                }
                else{
                    climate[x][y] = 0.1f;
                }
                //Latitude component
                float distFromEq = (float)Maths.dist(0,0,y,equator);
                distFromEq /= (float)SIZE;
                distFromEq *= distFromEq;
                climate[x][y] += -3.5f * distFromEq + equatorTemp;

                if(climate[x][y] > 1.0f)climate[x][y]=1.0f;
                if(climate[x][y] < 0.0f)climate[x][y]=0.0f;
            }
        }

    }
    private void wetness(int[][] heights, float[][] wetness){
		/*
		* Wetness/Humidity loop
		* first, set up first column, based on distance from eq.
		* then, iterating from x:0->SIZE-2 (<SIZE-1)
		* fill in the humidity in the x+1 column based on the current hum, height, etc
		*
		* (.5 * cos(8x)*e^(-2x^2) + .5) on [0,1]
		*  ^^^^ is really good too
		*
		* (.5 * cos(7x)*e^(-1x^2) + .5)^2 on [0,1]
		*  ^^^^ is really REALLY good too
		*
		* (.5 * cos(12x)*e^(-2x^2) + .5)^2 on [0,1]
		*  ^^^^ is really REALLY good too, better does the range probably
		*/
        for (int y = 0; y < SIZE; y++) {
            float distFromEq = (float)Maths.dist(0,0,y,equator);
            distFromEq /= (float)SIZE;
            //distFromEq *= distFromEq;
            float x = distFromEq;

            wetness[0][y] = 0.5f;
            wetness[0][y] += 0.10f * (float)(Math.cos(18 * x)) * (Math.exp(-2*x*x));
            wetness[0][y] *= wetness[0][y];

            //wetness[0][y] = .8f;
            wetness[0][y] += x*x;
            //wetness[0][y] *= wetness[0][y];

            if(wetness[0][y] < 0.1f)wetness[0][y]=0.1f;
			/*
			if(distFromEq < .15){
				wetness[0][y]=0.9f;
			}
			else if(distFromEq < .25){
				wetness[0][y]=0.0f;
			}
			else {
				distFromEq *= distFromEq;
				//distFromEq *= distFromEq;
				//distFromEq *= distFromEq;
				wetness[0][y]= 0.5f + (2*distFromEq) -.25f;
			}
			if(y>5){
				//System.out.println("blah");
			}

			*/

            if(wetness[0][y] > 1.0f)wetness[0][y]=1.0f;
            if(wetness[0][y] < 0.0f)wetness[0][y]=0.0f;
        }
        for (int x = 0; x < SIZE-1; x++) {
            for (int y = 0; y < SIZE; y++) {
                wetness[x+1][y] = wetness[x][y];
                if(heights[x][y] < WATER_LEVEL + 5){
                    wetness[x+1][y] += .0005f;
                    //if(wetness[x+1][y] < .7f)wetness[x+1][y] += .00001f;
                }else if (heights[x][y] > 100){
                    wetness[x+1][y] -= .002f;
                    wetness[x][y] += .001f;
                    if(y>0)wetness[x+1][y-1] += .001f;
                    if(y<SIZE-1)wetness[x+1][y+1] += .001f;
                }
                else{
                    wetness[x+1][y] -= .0001f;

                    if(y>0)wetness[x+1][y-1] += .00051f;
                    if(y<SIZE-1)wetness[x+1][y+1] += .00051f;
                }

                if(wetness[x+1][y] > 1.0f)wetness[x+1][y]=1.0f;
                if(wetness[x+1][y] < 0.0f)wetness[x+1][y]=0.0f;
            }
        }
    }
    private void generatePolygonBiomes(int sizeFactor, int[][] heights, float[][] wetness, float[][] climate){

        Polygon poly0 = new Polygon();
        int polyCenterX = rand.nextInt((SIZE/1));
        int polyCenterY = rand.nextInt((SIZE/1));

//		polyCenterX += SIZE/(4);
//		polyCenterY += SIZE/(4);
        int quartSize = SIZE/(4*sizeFactor);

        poly0.addPoint(new Point(polyCenterX-quartSize,	polyCenterY));
        poly0.addPoint(new Point(polyCenterX,			polyCenterY-quartSize));
        poly0.addPoint(new Point(polyCenterX+quartSize,	polyCenterY));
        poly0.addPoint(new Point(polyCenterX,			polyCenterY+quartSize));


        randomizePolygon(poly0, SIZE/150);
		/*
		*   Beware! I'm making this sample the heat that's already there
		*   so generateHeat() or something similar
		*   needs to have already been called.
		*/
        float w = rand.nextFloat();
        float c;// = rand.nextFloat() + .1f;
        c = masterHeat[polyCenterX][polyCenterY];

        applyBiomePolygon(poly0, w, c, wetness, climate);

    }
	//TODO
    private void generateFeatures(int[][] heights, float[][] wetness, float[][] climate,int[][] features){
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                if(heights[x][y] < WATER_LEVEL)continue;
                double randVal = rand.nextDouble();
                if(randVal < .0025)     {this.features[x][y] = CAVE;    this.states[x][y] = UNDERWORLD_ENTRANCE_STATE;}
                else if(randVal < .005) {this.features[x][y] = DUNGEON; this.states[x][y] = UNDERWORLD_ENTRANCE_STATE;}
                else if(randVal < .0075){this.features[x][y] = PASSIVE_VILLAGE_FEATURE; this.states[x][y] = PASSIVE_VILLAGE_STATE;}
            }
        }
    }
    private void biomeScatter(int size, float[][] wetness, float[][] climate){
        for (int x = 0; x < wetness.length - size; x+=5) {
            for (int y = 0; y < wetness.length - size; y+=5) {

                for (int i = 0; i < size*size; i++) {
                    int x0 = rand.nextInt(size) + x;
                    int y0 = rand.nextInt(size) + y;

                    int x1 = rand.nextInt(size) + x;
                    int y1 = rand.nextInt(size) + y;

                    float w0 = wetness[x0][y0];
                    float c0 = climate[x0][y0];

                    float w1 = wetness[x1][y1];
                    float c1 = climate[x1][y1];

                    wetness[x0][y0] = w1;
                    climate[x0][y0] = c1;

                    wetness[x1][y1] = w0;
                    climate[x1][y1] = c0;
                }


            }
        }

    }
    private void polygonPairGeneration(int sizeFactor, int[][] heights){

        Polygon poly0 = new Polygon();
        Polygon poly1 = new Polygon();
        int quartSize = SIZE/(4*sizeFactor);

        do{

            int polyCenterX = rand.nextInt((SIZE/2));
            int polyCenterY = rand.nextInt((SIZE/2));

            polyCenterX += SIZE/(4);
            polyCenterY += SIZE/(4);

            poly0.addPoint(new Point(polyCenterX-quartSize,	polyCenterY));
            poly0.addPoint(new Point(polyCenterX,			polyCenterY-quartSize));
            poly0.addPoint(new Point(polyCenterX+quartSize,	polyCenterY));
            poly0.addPoint(new Point(polyCenterX,			polyCenterY+quartSize));

            polyCenterX = rand.nextInt((SIZE/2));
            polyCenterY = rand.nextInt((SIZE/2));

            polyCenterX += SIZE/(4);
            polyCenterY += SIZE/(4);

            poly1.addPoint(new Point(polyCenterX-quartSize,	polyCenterY));
            poly1.addPoint(new Point(polyCenterX,			polyCenterY-quartSize));
            poly1.addPoint(new Point(polyCenterX+quartSize,	polyCenterY));
            poly1.addPoint(new Point(polyCenterX,			polyCenterY+quartSize));

        }while(!poly0.intersects(poly1));

        //cout<<poly->toString()<<'\n';
        randomizePolygon(poly0, SIZE/150);
        applyPolygon(poly0, heights);

        randomizePolygon(poly1, SIZE/150);
        applyPolygon(poly1, heights);
        //polygonBiomeHack(poly1);
        //polygonBiomeHack(poly0);
    }

    private void applyPolygon(Polygon polygon, int[][] heights){
        for(int x = (int)polygon.getMinX(); x<polygon.getMaxX();x++){
            if(x >= SIZE)continue;
            //if(x%(polygon.getMaxX()/10)==0)cout<<"0";
            for(int y = (int)polygon.getMinY(); y<polygon.getMaxY();y++){
                if(y >= SIZE)continue;
                Point point = new Point(x, y);
                if(polygon.contains(point)){
                    //if(heights[x][y] == 0)heights[x][y] += 1;
                    heights[x][y] += (polygon.distFromVertex(point));
                    heights[x][y] += (int)((Math.random()*(heights[x][y]/10)) - (heights[x][y]/20));
                }
                if(heights[x][y] > 255)heights[x][y] = 255;
                if(heights[x][y] <0)heights[x][y] = 0;
            }
        }
    }

    /**
     * applies the given biome to the given area.
     * currently just overwrites the entire polygon.
     */
    private void applyBiomePolygon(Polygon polygon, float w, float c, float[][] wetness, float[][] climate){
        for(int x = (int)polygon.getMinX(); x<polygon.getMaxX();x++){
            if(x >= SIZE)continue;if(x < 0)continue;
            //if(x%(polygon.getMaxX()/10)==0)cout<<"0";
            for(int y = (int)polygon.getMinY(); y<polygon.getMaxY();y++){
                if(y >= SIZE)continue;if(y < 0)continue;
                Point point = new Point(x, y);
                if(polygon.contains(point)){
                    wetness[x][y] = w;
                    climate[x][y] = c;


                    //if(heights[x][y] == 0)heights[x][y] += 1;
                    //heights[x][y] += (polygon.distFromVertex(point));
                    //heights[x][y] += (int)((Math.random()*(heights[x][y]/10)) - (heights[x][y]/20));
                }
                //if(heights[x][y] > 255)heights[x][y] = 255;
                //if(heights[x][y] <0)heights[x][y] = 0;
            }
        }
    }

    private Polygon polygonGeneration(int sizeFactor){

        Polygon poly = new Polygon();
        int polyCenterX = rand.nextInt((SIZE/2));
        int polyCenterY = rand.nextInt((SIZE/2));
        int quartSize = SIZE/(4*sizeFactor);

        polyCenterX += SIZE/(4);
        polyCenterY += SIZE/(4);

        poly.addPoint(new Point(polyCenterX-quartSize,	polyCenterY));
        poly.addPoint(new Point(polyCenterX,			polyCenterY-quartSize));
        poly.addPoint(new Point(polyCenterX+quartSize,	polyCenterY));
        poly.addPoint(new Point(polyCenterX,			polyCenterY+quartSize));
        //cout<<poly->toString()<<'\n';
        randomizePolygon(poly, SIZE/150);
        return poly;

    }
    private void randomizePolygon(Polygon poly, int iterations){
        //cout<<"\nRandom Poly:";
        for(int i = 0; i < iterations; i++){
            //cout<<"0";
            //cout<<"\n\n"<<i<<":\n\t";
            for(int j = 0; j < (poly.getVertices()); j++){
                Point point = poly.getPath().get(j);
                int disp = (1-(i/iterations))*30;
                dispPoint(point, disp);
            }
            for(int j = 0; j < (poly.getVertices()); j+=2){
                //cout<<j;
                Point point0 = poly.getPath().get(j);
                Point point1;

                if(j+1 < poly.getVertices()){
                    point1 = poly.getPath().get(j + 1);
                }
                else{
                    point1 = poly.getPath().get(0);
                }

                if(Maths.dist(point0, point1) < 4)continue;

                float dx = point1.getX() - point0.getX();
                float dy = point1.getY() - point0.getY();

                float newX = point0.getX() + (dx/2);
                float newY = point0.getY() + (dy/2);

                newX += (int)(Math.random()*16)-8;
                newY += (int)(Math.random()*16)-8;

                if(newX>SIZE)newX = SIZE-1;else if(newX < 0)newX = 0;
                if(newY>SIZE)newY = SIZE-1;else if(newY < 0)newY = 0;

                Point newPoint = new Point((int)newX, (int)newY);

                if(Maths.dist(point0, newPoint) < 4){
                    continue;
                }
                if(Maths.dist(newPoint, point1) < 4){
                    continue;
                }

                //if(!poly->contains(new Point(newX, newY)))
                poly.addPoint(newPoint, j + 1);

            }

        }
        //cout<<"\n";
    }
    private void dispPoint(Point point, int disp){

        int dispX = (int)(Math.random()*disp*2) - disp;
        int dispY = (int)(Math.random()*disp*2) - disp;
        point.setX(point.getX() + dispX);
        point.setY(point.getY() + dispY);
    }
    private void dropGenerate(float drops, int stickiness, int radius,
                              int minHeight, int maxHeight, boolean randStart,
                              int[][] heights){
//			for (int x = 0; x < SIZE; x++)
//			for (int y = 0; y < SIZE; y++) {
        // Body of loop
//			}//end double for()

//			int x = rand.Next( 0, SIZE);
//			int y = rand.Next( 0, SIZE);


        int startX = rand.nextInt((SIZE));
        int startY = rand.nextInt((SIZE));

        if(!randStart){
            startX = SIZE/2;
            startY = SIZE/2;
        }
        int x = startX;
        int y = startY;
        int height;
        for(int dropped = 0; dropped < drops; dropped++){

            height = (heights[x][y]) + stickiness;
            boolean placed = false;
            for (int dx = -1; dx <= 1; dx++){
                if(x+dx < 0 || x+dx >= SIZE || placed)continue;
                for (int dy = -1; dy <= 1; dy++) {
                    if(dx == 0 && dy == 0)continue;
                    if(y+dy < 0 || y+dy >= SIZE)continue;

                    if(heights[x+dx][y+dy] < height){
                        if(heights[x+dx][y+dy]<maxHeight && heights[x+dx][y+dy]>=minHeight)
                            heights[x+dx][y+dy] += 2;
                        placed = true;
                        break;
                    }
                }
            }//end double for()
            if(!placed && heights[x][y] < maxHeight && heights[x][y] >= minHeight){
                heights[x][y] += 1;
            }
			/*if(!placed && heights[x][y] < maxHeight && heights[x][y] >= minHeight){
				   heights[x][y] += 1;
			   }*/
//				Console.WriteLine("\t" + x + ", " + y + ": " + heights[x,y]);


            int oldX = x;
            int oldY = y;

            x += (int)(Math.random()*3) - 1;
            y += (int)(Math.random()*3) - 1;

            if(x < 0) x = SIZE-1;
            else if(x >= SIZE) x = 0;

            if(y < 0) y = 1;
            else if(y >= SIZE) y = SIZE-1;

            Point newP = new Point(x,y);
            Point oldP = new Point(oldX, oldY);
            if(Maths.dist(newP, oldP)> radius){
                x = startX;
                y = startY;
            }

        }
    }
    private void smoothHeightmap(int[][] heights){
        //cout<<"\nSmoothing:";
        for(int x = 0;x<SIZE;x++){
            //if(x%50 == 0)cout<<"0";
            for(int y = 0;y<SIZE;y++){
                int others = 1;
                int sum = 0;
                for (int dx = -1; dx <= 1; dx++){
                    if(x+dx < 0 || x+dx >= SIZE)continue;
                    for (int dy = -1; dy <= 1; dy++) {
                        if(y+dy < 0 || y+dy >= SIZE)continue;
                        sum += (heights[x+dx][y+dy]);
                        others++;
                    }
                }
                sum /= others;
                heights[x][y] = sum;
            }}
        //cout<<"\n";
    }
    private void smoothFloatmap(float[][] values){
        //cout<<"\nSmoothing:";
        for(int x = 0;x<SIZE;x++){
            //if(x%50 == 0)cout<<"0";
            for(int y = 0;y<SIZE;y++){
                float others = 1.0f;
                float sum = 0;
                for (int dx = -1; dx <= 1; dx++){
                    if(x+dx < 0 || x+dx >= SIZE)continue;
                    for (int dy = -1; dy <= 1; dy++) {
                        if(y+dy < 0 || y+dy >= SIZE)continue;
                        sum += (values[x+dx][y+dy]);
                        others++;
                    }
                }
                if(others == 0)continue;
                sum /= others;
                values[x][y] = sum;
            }}
        //cout<<"\n";
    }
    private void generateMountainRangeContinent(int[][] heights, int sizeFactor){
        //First, determine a path for the mountain range to follow
        //this is just a line across the map.
        //The line will always move right to left (positive dx) across the map.


        int dx = rand.nextInt(10) + 1;
        int dy = rand.nextInt(4) - 2;
        int intercept = (dy>0)?(rand.nextInt(SIZE/2) + SIZE/2 - 1):(rand.nextInt(SIZE/2));

        //now we have a line. {(dy/dx)t + intercept}
        //Next, we generate two continents centered on that line.

        int t = rand.nextInt(SIZE/(2*dx)) + ((SIZE/4)/dx);

        Polygon poly0 = new Polygon();
        Polygon poly1 = new Polygon();
        int quartSize = SIZE/(4*sizeFactor);

        do{

            int polyCenterX = dx*t;
            int polyCenterY = dy*t + intercept;

            //polyCenterX += SIZE/(4);
            //polyCenterY += SIZE/(4);

            poly0.addPoint(new Point(polyCenterX-quartSize,	polyCenterY));
            poly0.addPoint(new Point(polyCenterX,			polyCenterY-quartSize));
            poly0.addPoint(new Point(polyCenterX+quartSize,	polyCenterY));
            poly0.addPoint(new Point(polyCenterX,			polyCenterY+quartSize));


            t = rand.nextInt(SIZE/(2*dx)) + ((SIZE/4)/dx);
            polyCenterX = dx*t;
            polyCenterY = dy*t + intercept;

            //polyCenterX += SIZE/(4);
            //polyCenterY += SIZE/(4);

            poly1.addPoint(new Point(polyCenterX-quartSize,	polyCenterY));
            poly1.addPoint(new Point(polyCenterX,			polyCenterY-quartSize));
            poly1.addPoint(new Point(polyCenterX+quartSize,	polyCenterY));
            poly1.addPoint(new Point(polyCenterX,			polyCenterY+quartSize));

        }while(!poly0.intersects(poly1));

        //cout<<poly->toString()<<'\n';
        randomizePolygon(poly0, SIZE/150);
        applyPolygon(poly0, heights);

        randomizePolygon(poly1, SIZE/150);
        applyPolygon(poly1, heights);


        for (int i = 0; i < SIZE; i++) {
            int newX = dx*i;
            int newY = dy*i + intercept;
            try{
                heights[newX][newY] += 100;
            }catch (IndexOutOfBoundsException e){}
        }

    }
    private void cliffDetection(int[][] heights, short[][] maps){
        /**
         * Places cliffs in the world (maps) based on ?sobel filter? on the hightmap perhaps.
         *
         * the sobel filter uses matrices like so:
         * [
         * +a   0   -a
         * +b   0   -b
         * +c   0   -c
         * ]
         * */
        float a = 1;
        float b = 2;
        float c = 1;
        int maxCliffs = 5;
        for (int x = 1; x < SIZE-1; x++) {
            for (int y = 1; y < SIZE-1; y++) {

                if(maps[x][y] == WATER){continue;}
                if(heights[x][y] <= WATER_LEVEL*3){continue;}

                int cliffcount = 0;

                float s00 = heights[x-1][y-1];cliffcount++;if(maps[x-1][y-1] == CLIFFS&&cliffcount>=maxCliffs){continue;}
                float s10 = heights[x-1][y  ];cliffcount++;if(maps[x-1][y  ] == CLIFFS&&cliffcount>=maxCliffs){continue;}
                float s20 = heights[x-1][y+1];cliffcount++;if(maps[x-1][y+1] == CLIFFS&&cliffcount>=maxCliffs){continue;}

                float s01 = heights[x  ][y-1];cliffcount++;if(maps[x  ][y-1] == CLIFFS&&cliffcount>=maxCliffs){continue;}
                float s21 = heights[x  ][y+1];cliffcount++;if(maps[x  ][y+1] == CLIFFS&&cliffcount>=maxCliffs){continue;}

                float s02 = heights[x+1][y-1];cliffcount++;if(maps[x+1][y-1] == CLIFFS&&cliffcount>=maxCliffs){continue;}
                float s12 = heights[x+1][y  ];cliffcount++;if(maps[x+1][y  ] == CLIFFS&&cliffcount>=maxCliffs){continue;}
                float s22 = heights[x+1][y+1];cliffcount++;if(maps[x+1][y+1] == CLIFFS&&cliffcount>=maxCliffs){continue;}
                if(cliffcount >= maxCliffs-1){
                    if(rand.nextBoolean())continue;
                }
                if(cliffcount >= maxCliffs-2){
                    //if(rand.nextBoolean())continue;
                }
                float Sx = a*s00 + b*s10 + c*s20 - (a*s02 + b*s12 + c*s22);
                float Sy = a*s00 + b*s01 + c*s02 - (a*s20 + b*s21 + c*s22);

                float distSquared = Sx*Sx + Sy*Sy;

                if(distSquared > 350.5f){
                    maps[x][y] = CLIFFS;
                }
            }
        }

    }
    private void cliffsAndMountains(int[][] heights, short[][] maps){
        /**
         * Places cliffs in the world (maps) based on ?sobel filter? on the hightmap perhaps.
         *
         * the sobel filter uses matrices like so:
         * [
         * +a   0   -a
         * +b   0   -b
         * +c   0   -c
         * ]
         * */
        float a = 1;
        float b = 2;
        float c = 1;
        int maxCliffs = 5;
        for (int x = 1; x < SIZE-1; x++) {
            for (int y = 1; y < SIZE-1; y++) {

                if(maps[x][y] == WATER){continue;}
                if(heights[x][y] <= WATER_LEVEL*3){continue;}

                int cliffcount = 0;

                float s00 = heights[x-1][y-1];cliffcount++;if(maps[x-1][y-1] == CLIFFS&&cliffcount>=maxCliffs){continue;}
                float s10 = heights[x-1][y  ];cliffcount++;if(maps[x-1][y  ] == CLIFFS&&cliffcount>=maxCliffs){continue;}
                float s20 = heights[x-1][y+1];cliffcount++;if(maps[x-1][y+1] == CLIFFS&&cliffcount>=maxCliffs){continue;}

                float s01 = heights[x  ][y-1];cliffcount++;if(maps[x  ][y-1] == CLIFFS&&cliffcount>=maxCliffs){continue;}
                float s21 = heights[x  ][y+1];cliffcount++;if(maps[x  ][y+1] == CLIFFS&&cliffcount>=maxCliffs){continue;}

                float s02 = heights[x+1][y-1];cliffcount++;if(maps[x+1][y-1] == CLIFFS&&cliffcount>=maxCliffs){continue;}
                float s12 = heights[x+1][y  ];cliffcount++;if(maps[x+1][y  ] == CLIFFS&&cliffcount>=maxCliffs){continue;}
                float s22 = heights[x+1][y+1];cliffcount++;if(maps[x+1][y+1] == CLIFFS&&cliffcount>=maxCliffs){continue;}
                if(cliffcount >= maxCliffs-1){
                    if(rand.nextBoolean())continue;
                }
                if(cliffcount >= maxCliffs-2){
                    //if(rand.nextBoolean())continue;
                }
                float Sx = a*s00 + b*s10 + c*s20 - (a*s02 + b*s12 + c*s22);
                float Sy = a*s00 + b*s01 + c*s02 - (a*s20 + b*s21 + c*s22);

                float distSquared = Sx*Sx + Sy*Sy;

                if(distSquared > 350.5f){
                    maps[x][y] = CLIFFS;
                }else if(distSquared > 50.5f){
                    maps[x][y] = MOUNTAINS;
                }
            }
        }

    }
	//This is all commented out because It needs to be upgraded
	/*
    public void outputImage() throws IOException{

        File savDir = new File("sav/");
        if(!savDir.exists())savDir.mkdir();

        File playersDir = new File("sav/players");
        if(!playersDir.exists())playersDir.mkdir();

        File playerDir = new File("sav/players/"+playerName);
        if(!playerDir.exists())playerDir.mkdir();
        File worldsDir = new File("sav/players/"+playerName+"/worlds");
        if(!worldsDir.exists())worldsDir.mkdir();
        File worldDir = new File("sav/players/"+playerName+"/worlds/"+worldName);
        if(!worldDir.exists())worldDir.mkdir();

        File imgOut = new File("sav/players/"+playerName+"/worlds/"+worldName+"/map.png");
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        //Image map = new Image(SIZE,SIZE);

        //Graphics g = map.getGraphics();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                ////////////////////////////TERRAIN/////////////////////////////
                if(maps[x][y] == WATER){
                    g.setColor(WATER_COLOR);
                }
                else if(maps[x][y] == BEACH){
                    g.setColor(SAND_COLOR);
                }
                else if(maps[x][y] == PLAINS){
                    g.setColor(PLAINS_COLOR);
                }
                else if(maps[x][y] == MOUNTAINS){
                    g.setColor(MOUNTAINS_COLOR);
                }
                else if(maps[x][y] == CLIFFS){
                    g.setColor(CLIFFS_COLOR);
                }
                else if(maps[x][y] == SWAMP){
                    g.setColor(SWAMP_COLOR);
                }
                else if(maps[x][y] == DESERT){
                    g.setColor(DESERT_COLOR);
                }
                else if(maps[x][y] == JUNGLE){
                    g.setColor(JUNGLE_COLOR);
                }
                else if(maps[x][y] == TAIGA){
                    g.setColor(TAIGA_COLOR);
                }
                else if(maps[x][y] == TUNDRA){
                    g.setColor(TUNDRA_COLOR);
                }
                else if(maps[x][y] == FOREST){
                    g.setColor(FOREST_COLOR);
                }
                else {
                    g.setColor(new Color(maps[x][y], maps[x][y], maps[x][y]));
                }
                ////////////////////////////TERRAIN/////////////////////////////

                ////////////////////////////FEATURES////////////////////////////

                if(features[x][y] == CAVE){
                    g.setColor(CAVE_COLOR);
                }
                else if(features[x][y] == DUNGEON){
                    g.setColor(DUNGEON_COLOR);
                }
                else if(features[x][y] == PASSIVE_VILLAGE_FEATURE){
                    g.setColor(PASSIVE_VILLAGE_COLOR);
                }
                ////////////////////////////FEATURES////////////////////////////
                g.drawRect(x,y,1,1);

            }
        }
        //g.flush();
        //ImageOut.write(map, seed + ".png");

        ImageIO.write(image, "png", imgOut);
        System.out.println(
                "Wrote a " +SIZE+ "x" +SIZE+ "image, " + seed + ".png"
        );
    }
    public void testerOutputImage() throws IOException{

        File savDir = new File("map/");
        if(!savDir.exists())savDir.mkdir();

        File playersDir = new File("map/worldTest/");
        if(!playersDir.exists())playersDir.mkdir();

//		File playerDir = new File("sav/players/"+playerName);
//		if(!playerDir.exists())playerDir.mkdir();
//		File worldsDir = new File("sav/players/"+playerName+"/worlds");
//		if(!worldsDir.exists())worldsDir.mkdir();
//		File worldDir = new File("sav/players/"+playerName+"/worlds/"+worldName);
//		if(!worldDir.exists())worldDir.mkdir();

//		File imgOut = new File("sav/players/"+playerName+"_"+worldName+"_map.png");
        File imgOut = new File("map/worldTest/"+playerName+"_"+worldName+"_map.png");
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        //Image map = new Image(SIZE,SIZE);

        //Graphics g = map.getGraphics();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                ////////////////////////////TERRAIN/////////////////////////////
                if(maps[x][y] == WATER){
                    g.setColor(WATER_COLOR);
                }
                else if(maps[x][y] == BEACH){
                    g.setColor(SAND_COLOR);
                }
                else if(maps[x][y] == PLAINS){
                    g.setColor(PLAINS_COLOR);
                }
                else if(maps[x][y] == MOUNTAINS){
                    g.setColor(MOUNTAINS_COLOR);
                }
                else if(maps[x][y] == CLIFFS){
                    g.setColor(CLIFFS_COLOR);
                }
                else if(maps[x][y] == SWAMP){
                    g.setColor(SWAMP_COLOR);
                }
                else if(maps[x][y] == DESERT){
                    g.setColor(DESERT_COLOR);
                }
                else if(maps[x][y] == JUNGLE){
                    g.setColor(JUNGLE_COLOR);
                }
                else if(maps[x][y] == TAIGA){
                    g.setColor(TAIGA_COLOR);
                }
                else if(maps[x][y] == TUNDRA){
                    g.setColor(TUNDRA_COLOR);
                }
                else if(maps[x][y] == FOREST){
                    g.setColor(FOREST_COLOR);
                }
                else {
                    g.setColor(new Color(maps[x][y], maps[x][y], maps[x][y]));
                }
                ////////////////////////////TERRAIN/////////////////////////////

                ////////////////////////////FEATURES////////////////////////////

                if(features[x][y] == CAVE){
                    g.setColor(CAVE_COLOR);
                }
                else if(features[x][y] == DUNGEON){
                    g.setColor(DUNGEON_COLOR);
                }
                else if(features[x][y] == PASSIVE_VILLAGE_FEATURE){
                    g.setColor(PASSIVE_VILLAGE_COLOR);
                }
                ////////////////////////////FEATURES////////////////////////////
                g.drawRect(x,y,1,1);

            }
        }
        //for (int x = 0; x < SIZE; x++) {
        //	for (int y = 0; y < SIZE; y++) {
        //		if(features[x][y] == NOTHING){
        //			continue;
        //			//g.setColor(WATER_COLOR);
        //		}
        //		else if(features[x][y] == CAVE){
        //			g.setColor(CAVE_COLOR);
        //		}
        //		else if(features[x][y] == DUNGEON){
        //			g.setColor(DUNGEON_COLOR);
        //		}
        //		else if(features[x][y] == PASSIVE_VILLAGE_FEATURE){
        //			g.setColor(PASSIVE_VILLAGE_COLOR);
        //		}
        //		g.drawRect(x,y,1,1);
//
        //	}
        //}
        //g.flush();
        //ImageOut.write(map, seed + ".png");

        ImageIO.write(image, "png", imgOut);
        System.out.println(
                "Wrote a " +SIZE+ "x" +SIZE+ "image, " + seed + ".png"
        );
    }
	//*/
    public void testerOutputHeightmapImage(int[][] heights) throws IOException{

        File savDir = new File("map/");
        if(!savDir.exists())savDir.mkdir();

        File playersDir = new File("map/worldTest/");
        if(!playersDir.exists())playersDir.mkdir();

//		File playerDir = new File("sav/players/"+playerName);
//		if(!playerDir.exists())playerDir.mkdir();
//		File worldsDir = new File("sav/players/"+playerName+"/worlds");
//		if(!worldsDir.exists())worldsDir.mkdir();
//		File worldDir = new File("sav/players/"+playerName+"/worlds/"+worldName);
//		if(!worldDir.exists())worldDir.mkdir();

//		File imgOut = new File("sav/players/"+playerName+"_"+worldName+"_map.png");
        File imgOut = new File("map/worldTest/"+playerName+"_"+worldName+"_heightmap.png");
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        //Image map = new Image(SIZE,SIZE);

        //Graphics g = map.getGraphics();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                //if(maps[x][y] == WATER){
                //	g.setColor(WATER_COLOR);
                //}
                //else if(maps[x][y] == BEACH){
                //	g.setColor(SAND_COLOR);
                //}
                //else if(maps[x][y] == PLAINS){
                //	g.setColor(PLAINS_COLOR);
                //}
                //else if(maps[x][y] == MOUNTAINS){
                //	g.setColor(MOUNTAINS_COLOR);
                //}
                //else {
                //	g.setColor(new Color(maps[x][y], maps[x][y], maps[x][y]));
                //}
                g.setColor(new java.awt.Color(heights[x][y]*2, heights[x][y]*2, heights[x][y]*2));
                g.drawRect(x,y,1,1);

            }
        }
        //g.flush();
        //ImageOut.write(map, seed + ".png");

        ImageIO.write(image, "png", imgOut);
        System.out.println(
                "Wrote a " +SIZE+ "x" +SIZE+ "image, " + seed + ".png - heightmap"
        );
    }
    public void testerOutputFloatmapImage(float[][] heights) throws IOException{

        File savDir = new File("map/");
        if(!savDir.exists())savDir.mkdir();

        File playersDir = new File("map/worldTest/");
        if(!playersDir.exists())playersDir.mkdir();

//		File playerDir = new File("sav/players/"+playerName);
//		if(!playerDir.exists())playerDir.mkdir();
//		File worldsDir = new File("sav/players/"+playerName+"/worlds");
//		if(!worldsDir.exists())worldsDir.mkdir();
//		File worldDir = new File("sav/players/"+playerName+"/worlds/"+worldName);
//		if(!worldDir.exists())worldDir.mkdir();

//		File imgOut = new File("sav/players/"+playerName+"_"+worldName+"_map.png");
        File imgOut = new File("map/worldTest/"+playerName+"_"+worldName+"_floatmap.png");
        BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        //Image map = new Image(SIZE,SIZE);

        //Graphics g = map.getGraphics();
        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < SIZE; y++) {
                //if(maps[x][y] == WATER){
                //	g.setColor(WATER_COLOR);
                //}
                //else if(maps[x][y] == BEACH){
                //	g.setColor(SAND_COLOR);
                //}
                //else if(maps[x][y] == PLAINS){
                //	g.setColor(PLAINS_COLOR);
                //}
                //else if(maps[x][y] == MOUNTAINS){
                //	g.setColor(MOUNTAINS_COLOR);
                //}
                //else {
                //	g.setColor(new Color(maps[x][y], maps[x][y], maps[x][y]));
                //}
                g.setColor(new java.awt.Color(heights[x][y], heights[x][y], heights[x][y]));
                g.drawRect(x,y,1,1);

            }
        }
        //g.flush();
        //ImageOut.write(map, seed + ".png");

        ImageIO.write(image, "png", imgOut);
        System.out.println(
                "Wrote a " +SIZE+ "x" +SIZE+ "image, " + seed + ".png - floatmap"
        );
    }
    public short getBiome(Point mapPos){
        try{return maps[mapPos.getX()][mapPos.getY()];}
        catch (IndexOutOfBoundsException e){return INVALID;}
    }
	public short getState(Point mapPos){
		try{return states[mapPos.getX()][mapPos.getY()];}
		catch (IndexOutOfBoundsException e){return NOT_CLEARED;}
	}
	public short getFeature(Point mapPos){
		try{return features[mapPos.getX()][mapPos.getY()];}
		catch (IndexOutOfBoundsException e){return INVALID;}
	}
	public boolean isExplored(Point mapPos){
		try{return explored[mapPos.getX()][mapPos.getY()];}
		catch (IndexOutOfBoundsException e){return false;}
	}
	public boolean isRespawnable(Point mapPos){
		try{
			return
					features[mapPos.getX()][mapPos.getY()] == PASSIVE_VILLAGE_FEATURE
					|| features[mapPos.getX()][mapPos.getY()] == STARTING_VILLAGE_FEATURE
					|| features[mapPos.getX()][mapPos.getY()] == BASE;
		}
		catch (IndexOutOfBoundsException e){return false;}
	}
    private static boolean isValidStartingArea(int areaType, int feature){
        if(feature != NOTHING)return false;
        return
                areaType == PLAINS
                        ||areaType == SWAMP
                        ||areaType == FOREST;

    }
    public boolean isValidStart(int x, int y){

        try{
            return isValidStartingArea(maps[x][y], features[x][y]);
        }
        catch (IndexOutOfBoundsException e){return false;}


    }
    public OverworldArea createNewArea(Point mapPos,int id, Player player, double distFromSpawn){
        return new OverworldArea(
                rand,
                NewGameArea.DEFAULT_SIZE,
                id,
                player.getCharacter().getLvl(),
                maps[mapPos.getX()][mapPos.getY()],
                features[mapPos.getX()][mapPos.getY()],
                states[mapPos.getX()][mapPos.getY()],
		        distFromSpawn
        );
    }
	public void explore(Point location){
		explored[location.getX()][location.getY()] = true;
	}
	public void explore(int x, int y){
		explored[x][y] = true;
	}
	public void setStartingVillage(Point location){
		features[location.getX()][location.getY()] = STARTING_VILLAGE_FEATURE;
		states[location.getX()][location.getY()] = PASSIVE_VILLAGE_FEATURE;
	}
}
