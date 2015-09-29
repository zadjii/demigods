package areas.underworlds;

import areas.NewGameArea;
import entities.interactables.ResourceTile;
import entities.tiles.Tiles;
import areas.eventQueue.EventQueue;
import org.lwjgl.util.Point;

import java.util.Random;

public class Cave extends NewGameArea {

    public static final int SIZE = 512;

    public Cave(Random rand, int size, int ID) {
        super(rand, SIZE, ID);
        init();
    }

    private void init() {
        this.setMap(toCave(makeWalls(generateNewCave(SIZE))));
        refreshPassable(this);
        this.events = EventQueue.newLvlZeroPlains();
    }

    public String toString() {
        return "Cave";
    }

    private void setTileValue(int x, int y, int initValue, int finalValue, int[][] map, boolean replace) {
        if (finalValue == ROCKS && !replace) {
            if (rand.nextDouble() < .75) finalValue = FLOOR;
        }
        if (finalValue == FLOOR && replace) {
            if (rand.nextDouble() < .095) finalValue = WALL;
        }
        if (replace) {
            try {
                if (map[x / 16][y / 16] == initValue) map[x / 16][y / 16] = finalValue;
            } catch (IndexOutOfBoundsException ignored) {
            }
        } else try {
            map[x / 16][y / 16] = finalValue;
        } catch (IndexOutOfBoundsException ignored) {
        }
    }

    private void fillCircle(int x0, int y0, int radius, int initValue, int finalValue, int[][] map, boolean replace) {
        int f = 1 - radius;
        int ddF_x = 1;
        int ddF_y = -2 * radius;
        int x = 0;
        int y = radius;
        setTileValue(x0, y0 + radius, initValue, finalValue, map, replace);
        setTileValue(x0, y0 - radius, initValue, finalValue, map, replace);
        setTileValue(x0 + radius, y0, initValue, finalValue, map, replace);
        setTileValue(x0 - radius, y0, initValue, finalValue, map, replace);
        while (x < y) {
            if (f >= 0) {
                y--;
                ddF_y += 2;
                f += ddF_y;
            }
            x++;
            ddF_x += 2;
            f += ddF_x;
            setTileValue(x0 + x, y0 + y, initValue, finalValue, map, replace);
            setTileValue(x0 - x, y0 + y, initValue, finalValue, map, replace);
            setTileValue(x0 + x, y0 - y, initValue, finalValue, map, replace);
            setTileValue(x0 - x, y0 - y, initValue, finalValue, map, replace);
            setTileValue(x0 + y, y0 + x, initValue, finalValue, map, replace);
            setTileValue(x0 - y, y0 + x, initValue, finalValue, map, replace);
            setTileValue(x0 + y, y0 - x, initValue, finalValue, map, replace);
            setTileValue(x0 - y, y0 - x, initValue, finalValue, map, replace);
        }
    }

    public int[][] generateNewCave(int size) {
        boolean finished = false;
        int[][] map = new int[size][size];
        for (int r = 0; r < map.length; ++r) {
            for (int c = 0; c < map.length; ++c) {
                map[r][c] = -1;
            }
        }
        int numberOfIterations = 750;
        int iterations = 0;
        while (iterations < numberOfIterations) {
            iterations++;
            int range = size / 8;
            int start = (size / 2) - (range / 2);
            if (iterations > 300) {
                range *= (int)((4) * ((float)iterations / (float)numberOfIterations));
                start = (size / 2) - (range / 2);
            }
            Point center = new Point((rand.nextInt(range) + start), (rand.nextInt(range) + start));
            int innerRadiusMinimum = (int)((10) * ((float)iterations / (float)numberOfIterations));
            int innerRadiusRangeAddition = 0;
            if (iterations > 500)
                innerRadiusRangeAddition += (int)((20) * ((float)iterations / (float)numberOfIterations));
            int innerRadius = rand.nextInt(20 + innerRadiusRangeAddition) + innerRadiusMinimum;
            int radiusDifference = rand.nextInt(5) + 5;
            int tileValue = rand.nextInt(4) - 1;
            boolean replace = rand.nextBoolean();
            for (int r = innerRadius; r < innerRadius + radiusDifference; r++) {
                if (tileValue == ROCKS && replace) {
                    fillCircle(center.getX() * 16, center.getY() * 16,
                            r * 16, FLOOR, tileValue, map, replace);
                } else {
                    fillCircle(center.getX() * 16, center.getY() * 16,
                            r * 16, -1, tileValue, map, false);
                }
            }
        }
        for (int r = 0; r < 10; r++) {
            fillCircle(SIZE * 16 / 2, SIZE * 16 / 2,
                    r * 16, -1, FLOOR, map, false);
        }
        return map;
    }

    public int[][] makeWalls(int[][] intMap) {
        for (int r = 1; r < intMap.length - 1; ++r) {
            for (int c = 1; c < intMap.length - 1; ++c) {
                if (intMap[r][c] != NOTHING && intMap[r][c] != WALL) {
                    if (intMap[r - 1][c - 1] == NOTHING) {
                        intMap[r - 1][c - 1] = WALL;
                    }
                    if (intMap[r - 1][c] == NOTHING) {
                        intMap[r - 1][c] = WALL;
                    }
                    if (intMap[r - 1][c + 1] == NOTHING) {
                        intMap[r - 1][c + 1] = WALL;
                    }
                    if (intMap[r][c - 1] == NOTHING) {
                        intMap[r][c - 1] = WALL;
                    }
                    if (intMap[r][c + 1] == NOTHING) {
                        intMap[r][c + 1] = WALL;
                    }
                    if (intMap[r + 1][c - 1] == NOTHING) {
                        intMap[r + 1][c - 1] = WALL;
                    }
                    if (intMap[r + 1][c] == NOTHING) {
                        intMap[r + 1][c] = WALL;
                    }
                    if (intMap[r + 1][c + 1] == NOTHING) {
                        intMap[r + 1][c + 1] = WALL;
                    }
                }
            }
        }
        return intMap;
    }

    private static final int FLOOR = 1;
    private static final int WALL = 0;
    private static final int NOTHING = -1;
    private static final int ROCKS = 2;

    private static final double additionalLevelChance = .026;
    private static final double bossLevelChance = .01;

    public int[][] toCave(int[][] intMap) {
        for (int r = 0; r < intMap.length; ++r) {
            for (int c = 0; c < intMap.length; ++c) {
                if (intMap[r][c] == WALL) {
                    intMap[r][c] = Tiles.CAVEWALL;
                } else if (intMap[r][c] == FLOOR) {
                    intMap[r][c] = Tiles.DIRT;
                } else if (intMap[r][c] == ROCKS) {
                    intMap[r][c] = Tiles.OVERWORLD_STONE;
                    this.getResources()[r][c] = new ResourceTile(Tiles.OVERWORLD_STONE, r * 16, c * 16);
                } else if (intMap[r][c] == 3) {
                    intMap[r][c] = 5;
                } else if (intMap[r][c] == -1) {
                    intMap[r][c] = 0;
                } else if (intMap[r][c] == 4) {
                }
            }
        }
        return intMap;
    }
}
