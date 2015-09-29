package entities.interactables;

import entities.Entity;
import entities.tiles.Tiles;
import game.Engine;
import entities.particles.Particle;
import items.*;
import items.materials.*;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import util.Images;

public class ResourceTile extends Entity implements Interactable {
    private static final int TREE = 10;
    private static final int CACTUS = 11;
    private static final int SNOWTREE = 18;
    private static final int OVERWORLDSTONE = 2;
    private static final int CAVE_STONE = 19;
    private int tileType;
    private int treeSubtype;
    private int treeHeight;
    private Color treeColor;

    private static final int POPPING_THRESHOLD = 10;

    private long lastHit = 0;
    private int progress = 0;

    private static final Color green0 = new Color(72, 240, 25);
    private static final Color green1 = new Color(72, 240, 72);
    private static final Color green2 = new Color(29, 191, 29);
    private static final Color green3 = new Color(102, 196, 59);
    private static final Color green4 = new Color(145, 237, 100);

    public ResourceTile(int type, int absX, int absY) {
        this.setX(absX);
        this.setY(absY);
        this.tileType = type;
        this.generateResources(type);
        if (type == Tiles.TREE || type == Tiles.SNOWTREE) {
            treeHeight = (int)(Math.random() * 5) + 5;
            int rand = (int)(Math.random() * 5);
            switch (rand) {
                case 0:
                    treeColor = green0;
                    break;
                case 1:
                    treeColor = green1;
                    break;
                case 2:
                    treeColor = green2;
                    break;
                case 3:
                    treeColor = green3;
                    break;
                case 4:
                    treeColor = green4;
                    break;
            }
        }
        if (type == Tiles.TREE) {
            this.treeSubtype = (int)(Math.random() * 5);
        }
    }

    public int getType() {
        return this.tileType;
    }

    public static ResourceTile[][] getResourceArrayFromMap(int[][] map, int mapX, int mapY) {
        ResourceTile[][] resources = new ResourceTile[map.length][map[0].length];
        int mapXOrigin = mapX * 64 * 16;
        int mapYOrigin = mapY * 64 * 16;
        for (int x = 0; x < map.length; ++x) {
            for (int y = 0; y < map.length; ++y) {
                if (map[x][y] == OVERWORLDSTONE || map[x][y] == TREE || map[x][y] == CACTUS || map[x][y] == SNOWTREE)
                    resources[x][y]
                            = new ResourceTile(map[x][y], mapXOrigin + (x * 16), mapYOrigin + (y * 16));
            }
        }
        return resources;
    }

    public static ResourceTile[][] getResourceArrayFromArea(int[][] map) {
        return getResourceArrayFromMap(map, 0, 0);
    }

    public void generateResources(int type) {
        if (type == OVERWORLDSTONE) {
            int numberOfResources = (int)(Math.random() * 20) + 10;
            for (int i = 0; i < numberOfResources; ++i) {
                double chance = Math.random();
                if (chance < .07) {
                    if (Math.random() < .5) this.resources[TIN_ORE]++;
                    else this.resources[COPPER_ORE]++;
                } else if (chance < .1) {
                    this.resources[COAL]++;
                } else {
                    this.resources[STONE]++;
                }
            }
        } else if (type == CAVE_STONE) {
            int numberOfResources = (int)(Math.random() * 10) + (int)(Math.random() * 10) + 3;
            for (int i = 0; i < numberOfResources; ++i) {
                double chance = Math.random();
                if (chance < .04) {
                    this.resources[IRON_ORE]++;
                } else if (chance < .05) {
                    if (Math.random() < .5) this.resources[TIN_ORE]++;
                    else this.resources[COPPER_ORE]++;
                } else if (chance < .2) {
                    this.resources[COAL]++;
                } else {
                    this.resources[STONE]++;
                }
            }
        }
        if (type == TREE || type == CACTUS || type == SNOWTREE) {
            int numberOfWood = (int)(Math.random() * 10) + 15;
            int numberOfScraps = 0;
            for (int i = 0; i < numberOfWood; ++i) {
                this.resources[WOOD]++;
            }
            for (int i = 0; i < numberOfScraps; ++i) {
                this.resources[SCRAPS]++;
            }
        }
    }

    public boolean isAppropriateTool(Item i) {
        if (this.tileType == OVERWORLDSTONE || this.tileType == CAVE_STONE) {
            if (i.getIsPick()) return true;
        }
        if (this.tileType == TREE || this.tileType == CACTUS || this.tileType == SNOWTREE) {
            if (i.getIsAxe()) return true;
        }
        return false;
    }

    public boolean hasResources() {
        for (int resource : this.resources) {
            if (resource > 0) {
                return true;
            }
        }
        return false;
    }

    public void pop() {
        for (int i = 0; i < this.resources.length; ++i) {
            while (resources[i] > 0) {
                resources[i]--;
                Item item = getItemFromArray(i);
                if (item != null) {
                    Engine.add(item, this, (float)(Math.random() * 4 - 2), (float)(Math.random() * 4 - 2), (float)(Math.random() * 10));
                    Engine.add(Particle.newTreeParticle(this));
                    Engine.add(Particle.newGreenTreeParticle(this));
                }
            }
        }
    }

    public Item harvest(double efficiency) {
        if ((this.tileType == Tiles.TREE || this.tileType == Tiles.CACTUS)) {
            if (progress >= POPPING_THRESHOLD) {
                pop();
            } else {
                progress += 2 * efficiency + (Math.random() * efficiency);
            }
        }
        double chance = Math.random();
        if (chance < efficiency) {
            return this.harvest();
        }
        return null;
    }

    public Item getItemFromArray(int index) {
        if (this.resources[index] > 0) {
            --this.resources[index];
            switch (index) {
                case 0:
                    return new Scraps();
                case 1:
                    return new Wood();
                case 2:
                    return new Stone();
                case 8:
                    return new Coal();
                case 9:
                    return new IronOre();
            }
        }
        return null;
    }

    public Item harvest() {
        if ((this.tileType == Tiles.TREE ||
                this.tileType == Tiles.CACTUS ||
                this.tileType == Tiles.SNOWTREE) &&
                progress >= POPPING_THRESHOLD) {
            pop();
            return null;
        }
        for (int i = 0; i < this.resources.length; ++i) {
            if (this.resources[i] > 0) {
                --this.resources[i];
                switch (i) {
                    case SCRAPS:
                        return new Scraps();
                    case WOOD:
                        return new Wood();
                    case STONE:
                        return new Stone();
                    case FOOD:
                    case COINS:
                    case GOLD:
                    case CRYSTAL:
                    case BONES:
                        return new Bone();
                    case COAL:
                        return new Coal();
                    case COPPER_ORE:
                        return new CopperOre();
                    case TIN_ORE:
                        return new TinOre();
                    case IRON_ORE:
                        return new IronOre();
                    case XERIUM_ORE:
                    case OBSIDIAN:
                }
            }
        }
        return null;
    }

    private int[] resources = new int[18];

	/*
     * This is the current master list of materials. MATLIST18 the above is a
	 * line to tag all times a mat list shows up, to be able to update all
	 * locations quickly the number refers to the number of "implemented"
	 * materials SCRAPS(0), WOOD(1), STONE(2), FOOD(3), COINS(4), GOLD(5),
	 * CRYSTAL(6), BONES(7), COAL(8), IRON_ORE(9), TITANIUM_ORE(10),
	 * XERIUM_ORE(11), OBSIDIAN(12), IRON(13), STEEL(14), TITANIUM(15),
	 * BLACK_METAL(16), XERIUM(17)
	 */

    private static final int SCRAPS = 0;
    private static final int WOOD = 1;
    private static final int STONE = 2;
    private static final int FOOD = 3;
    private static final int COINS = 4;
    private static final int GOLD = 5;
    private static final int CRYSTAL = 6;
    private static final int BONES = 7;
    private static final int COAL = 8;
    private static final int COPPER_ORE = 9;
    private static final int TIN_ORE = 10;
    private static final int IRON_ORE = 11;
    private static final int XERIUM_ORE = 12;
    private static final int OBSIDIAN = 13;

    public void interact() {
    }

    public void draw(float sx, float sy, float zoom, Graphics g) {
        if (this.tileType == Tiles.TREE) {
            Images.shadow.draw((getX() + 2 - sx) * zoom,
                    (getY() + 12 - sy) * zoom, 12 * zoom, 8 * zoom);
            if (this.treeSubtype == 0) {
                Images.tileSheet.draw(
                        (getX() - sx) * zoom,
                        (getY() - sy - 16) * zoom,
                        (getX() - sx + 16) * zoom,
                        (getY() - sy + 16) * zoom,
                        (3 * 16), (1 * 16),
                        (3 * 16) + 16, (1 * 16) + 32
                );
            } else if (this.treeSubtype == 1) {
                Images.tileSheet.draw(
                        (getX() - sx) * zoom,
                        (getY() - sy - 16) * zoom,
                        (getX() - sx + 16) * zoom,
                        (getY() - sy + 16) * zoom,
                        (5 * 16), (1 * 16),
                        (5 * 16) + 16, (1 * 16) + 32
                );
            } else if (this.treeSubtype == 2) {
                Images.tileSheet.draw(
                        (getX() - sx) * zoom,
                        (getY() - sy - 16) * zoom,
                        (getX() - sx + 16) * zoom,
                        (getY() - sy + 16) * zoom,
                        (3 * 16), (5 * 16),
                        (3 * 16) + 16, (5 * 16) + 32
                );
            } else {
                Images.tileSheet.draw(
                        (getX() - sx) * zoom,
                        (getY() - sy - 16) * zoom,
                        (getX() - sx + 16) * zoom,
                        (getY() - sy + 16) * zoom,
                        (4 * 16), (5 * 16),
                        (4 * 16) + 16, (5 * 16) + 32
                );
            }
        } else if (this.tileType == Tiles.SNOWTREE) {
            Images.tileSheet.draw(
                    (getX() - sx) * zoom, (getY() - sy) * zoom,
                    (getX() - sx + 16) * zoom, (getY() - sy + 16) * zoom,
                    (0 * 16), (1 * 16), (0 * 16) + 16, (1 * 16) + 16
            );
            Images.tileSheet.draw((this.getX() - sx) * zoom, (getY() - 24 - sy) * zoom,
                    (getX() - sx + 16) * zoom, (getY() - sy + 12) * zoom,
                    (2 * 16), (2 * 16) - 16, (2 * 16) + 16, (2 * 16) + 16, treeColor
            );
        } else if (this.tileType == Tiles.CACTUS) {
            Images.tileSheet.draw(
                    (this.getX() - sx) * zoom,
                    (getY() - 16 - sy) * zoom,
                    (getX() - sx + 16) * zoom,
                    (getY() - sy + 16) * zoom,
                    (4 * 16),
                    (1 * 16),
                    (4 * 16) + 16,
                    (1 * 16) + 32
            );
        } else if (this.tileType == Tiles.OVERWORLD_STONE) {
            Images.tileSheet.getSprite(1, 2).draw((getX() - sx) * zoom, (getY() - sy) * zoom, 16 * zoom, 16 * zoom);
        } else {
        }
    }

    public int getScraps() {
        return resources[SCRAPS];
    }

    public void setScraps(int i) {
        this.resources[SCRAPS] = i;
    }

    public Rectangle getAbsHitbox() {
        return new Rectangle(this.getXi(), this.getYi(), 16, 16);
    }

    public boolean isPassable() {
        return false;
    }

    public Rectangle getImpassableBounds() {
        return new Rectangle(this.getGX(), this.getGY(), 1, 1);
    }

    public boolean drawnAbove() {
        return true;
    }

    public boolean needToRelease() {
        return false;
    }

    public boolean aestheticOnly() {
        return false;
    }
}
