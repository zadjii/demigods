package areas.eventQueue;

import areas.World;
import entities.characters.*;
import entities.characters.personas.*;
import game.Engine;
import util.Constants;

import java.util.Random;

import static areas.eventQueue.EventQueue.TPM;

/**
 * Created with IntelliJ IDEA.
 * User: zadjii
 * Date: 11/5/13
 * Time: 4:10 PM
 */
public class MasterEventsGenerator {
	private static int xO(boolean overrun){
		return overrun? 2:1;
	}

	public static EventQueue addBiomeEvents(EventQueue events, int biome, int state, int playerLevel,  double distFromSpawn){
		if(events == null) events = new EventQueue();
		int effectiveLevel = playerLevel + (int)(distFromSpawn/5);
		boolean overrun = (state==World.OVERRUN);
		switch (biome){
			case World.BEACH        :
				generatePlains(events, overrun, effectiveLevel);
				break;
			////////////////////////////////////////////////////////////////////
			case World.PLAINS       :
				generatePlains(events, overrun, effectiveLevel);
				break;
			////////////////////////////////////////////////////////////////////
			case World.SWAMP        :
				generatePlains(events, overrun, effectiveLevel);
				break;
			////////////////////////////////////////////////////////////////////
			case World.DESERT       :
				generateDesert(events, overrun, effectiveLevel);
				break;
			////////////////////////////////////////////////////////////////////
			case World.FOREST       :
				generatePlains(events, overrun, effectiveLevel);
				break;
			////////////////////////////////////////////////////////////////////
			case World.MOUNTAINS    :
			case World.CLIFFS       :
				generatePlains(events, overrun, effectiveLevel);
				break;
			////////////////////////////////////////////////////////////////////
			case World.TAIGA        :
			case World.TUNDRA       :
				generatePlains(events, overrun, effectiveLevel);
				break;
			////////////////////////////////////////////////////////////////////
			case World.JUNGLE       :
				generatePlains(events, overrun, effectiveLevel);
				break;
			////////////////////////////////////////////////////////////////////
			case World.DESOLATION   :
				//gen desolation
				generatePlains(events, overrun, effectiveLevel);
			case World.VOLCANO      :
				//add in volcano mobs. volcanoes are scary hard.
				generatePlains(events, overrun, effectiveLevel);
			////////////////////////////////////////////////////////////////////
		}
		return events;
	}

	//todo, very similar to add biome events
	public static EventQueue addFeatureEvents(EventQueue events, int feature, int state, int playerLevel,  double distFromSpawn){
		if(events == null) events = new EventQueue();
		int effectiveLevel = playerLevel + (int)(distFromSpawn/5);
		boolean overrun = (state==World.OVERRUN);
//		switch (feature){
//			case World.BEACH        :
//				generatePlains(events, overrun, effectiveLevel);
//				break;
//			////////////////////////////////////////////////////////////////////
//			case World.PLAINS       :
//				generatePlains(events, overrun, effectiveLevel);
//				break;
//			////////////////////////////////////////////////////////////////////
//			case World.SWAMP        :
//				generatePlains(events, overrun, effectiveLevel);
//				break;
//			////////////////////////////////////////////////////////////////////
//			case World.DESERT       :
//				generateDesert(events, overrun, effectiveLevel);
//				break;
//			////////////////////////////////////////////////////////////////////
//			case World.FOREST       :
//				generatePlains(events, overrun, effectiveLevel);
//				break;
//			////////////////////////////////////////////////////////////////////
//			case World.MOUNTAINS    :
//			case World.CLIFFS       :
//				generatePlains(events, overrun, effectiveLevel);
//				break;
//			////////////////////////////////////////////////////////////////////
//			case World.TAIGA        :
//			case World.TUNDRA       :
//				generatePlains(events, overrun, effectiveLevel);
//				break;
//			////////////////////////////////////////////////////////////////////
//			case World.JUNGLE       :
//				generatePlains(events, overrun, effectiveLevel);
//				break;
//			////////////////////////////////////////////////////////////////////
//			case World.DESOLATION   :
//				//gen desolation
//				generatePlains(events, overrun, effectiveLevel);
//			case World.VOLCANO      :
//				//add in volcano mobs. volcanoes are scary hard.
//				generatePlains(events, overrun, effectiveLevel);
//				////////////////////////////////////////////////////////////////////
//		}
		return events;
	}

	/**
	 *
	 * @param events
	 * @param o whether or not the area is overrun. used in (numEnemies * xO(o)) calls.
	 * @param level the effective level of the area, player + dist/5
	 * @return
	 */
	private static EventQueue generatePlains(EventQueue events, boolean o, int level){
		//saving space for 3 general purpose numbers.
		double randVarA;
		double randVarB;
		double randVarC;
		Random rand = Engine.getRand();
		EventChain chain;
		switch (level){
			case 0:
			case 2:
				events.events.add(new WaveEvent(1.0*TPM, 1.5 * TPM, new AStarSimpleMob(), new SmallLizard(), 20*xO(o), 2));//TODO: Lizards
			case 1:
				///////////////////////
				chain = new EventChain(0, 0);
				chain.subEvents.add(new WaveEvent(0, 1.5 * TPM, new AStarHunter(), new GreenSlime(), 10*xO(o), 1));
				chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));
				chain.subEvents.add(new WaveEvent(0, .5 * TPM, new SimpleCasterAI(), new ThrowAcornSquirrel(), 5*xO(o), 2));
				chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));
				chain.subEvents.add(new WaveEvent(0, .5 * TPM, new AStarHunter(), new GreenSlime(), 15*xO(o), 3));
				chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));
				///////////////////////
//				chain.subEvents.add(new WaveEvent(0, .1 * TPM, new AStarSimpleMob(), new Orc(), 1, 1));
				events.events.add(chain);

//				events.events.add(new WaveEvent(0.0*TPM, 1.1 * TPM, new DervishAI(), new SandDervish(), 10*xO(o), 1));//TODO: worms
				events.events.add(new WaveEvent(1.7*TPM, 1.25 * TPM, new AStarHunter(), new BlueSlime(), 20*xO(o), 2));
				events.events.add(new WaveEvent(1.5*TPM, 2.5 * TPM, new AStarStaffAttacker(), new EnergyStaffGoblin(), 10*xO(o), 2));
				events.events.add(new WaveEvent(.5*TPM, 3.5 * TPM, new WaspAI(), new Bee(), 10*xO(o), 2));

				break;
			case 4:////////////////////////////////////////////////////////////////////////////////////////////
				events.events.add(new WaveEvent(.7*TPM, .5 * TPM, new AStarSimpleMob(), new RedSlime(), 20*xO(o), 5));//todo shrubbery
				events.events.add(new WaveEvent(.1*TPM, 1.5 * TPM, new WaspAI(), new Bee(), 20*xO(o), 5));
				///////////////////////
				chain = new EventChain(0, 0);
				chain.subEvents.add(new WaveEvent(0.2, .3 * TPM, new AStarSimpleMob(), new SmallLizard(), 10*xO(o), 3));
				chain.subEvents.add(new WaveEvent(0, .5 * TPM, new AStarSimpleMob(), new BlueSlime(), 20*xO(o), 2));
				chain.subEvents.add(new WaveEvent(0, 1.5 * TPM, new AStarSimpleMob(), new RedSlime(), 15*xO(o), 3));
				events.events.add(chain);
				///////////////////////
			case 3:
				chain = new EventChain(0, 0);
				chain.subEvents.add(new WaveEvent(0, .2 * TPM, new AStarSimpleMob(), new GreenSlime(), 20*xO(o), 3));
				chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));

				chain.subEvents.add(new WaveEvent(0, .5 * TPM, new SimpleCasterAI(), new ThrowAcornSquirrel(), 20*xO(o), 2));
				chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));

				chain.subEvents.add(new WaveEvent(0, .5 * TPM, new WaspAI(), new Bee(), 15*xO(o), 3));
				chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));

//				chain.subEvents.add(new WaveEvent(0, .1 * TPM, new AStarSimpleMob(), new Orc(), 1, 1));
				events.events.add(chain);


				events.events.add(new WaveEvent(0, .5 * TPM, new SimpleCasterAI(), new ThrowAcornSquirrel(), 20*xO(o), 3));
				events.events.add(new WaveEvent(0.4*TPM, .1 * TPM, new AStarSimpleMob(), new SmallLizard(), 5*xO(o), 2));//TODO: lizards
				events.events.add(new WaveEvent(0.4*TPM, .1 * TPM, new AStarSimpleMob(), new BlueSlime(), 5*xO(o), 2));//TODO: blue slimes
				events.events.add(new WaveEvent(.7*TPM, .5 * TPM, new AStarSimpleMob(), new GreenSlime(), 20*xO(o), 2));//todo shrubbery
				events.events.add(new WaveEvent(1.5*TPM, .5 * TPM, new WaspAI(), new Bee(), 10*xO(o), 3));//todo wasps

				break;
			case 7:
				///////////////////////
				chain = new EventChain(0, 0);
				chain.subEvents.add(new WaveEvent(0.2, .3 * TPM, new AStarSimpleMob(), new SmallLizard(), 10*xO(o), 3));
				chain.subEvents.add(new WaveEvent(0, .5 * TPM, new AStarSimpleMob(), new BlueSlime(), 20*xO(o), 2));
				chain.subEvents.add(new WaveEvent(0, 1.5 * TPM, new AStarSimpleMob(), new RedSlime(), 15*xO(o), 3));
				events.events.add(chain);
				///////////////////////
			case 6:
				events.events.add(new WaveEvent(.7*TPM, .5 * TPM, new AStarSimpleMob(), new RedSlime(), 20*xO(o), 5));//todo shrubbery
				events.events.add(new WaveEvent(.1*TPM, 1.5 * TPM, new WaspAI(), new Bee(), 20*xO(o), 5));
				///////////////////////
				chain = new EventChain(0, 0);
				chain.subEvents.add(new WaveEvent(0.2, .3 * TPM, new AStarSimpleMob(), new SmallLizard(), 10*xO(o), 3));
				chain.subEvents.add(new WaveEvent(0, .5 * TPM, new AStarSimpleMob(), new Orc(), 20*xO(o), 2));
				chain.subEvents.add(new WaveEvent(0, 1.5 * TPM, new AStarSimpleMob(), new RedSlime(), 15*xO(o), 3));
				events.events.add(chain);
				///////////////////////
			case 5:
				chain = new EventChain(0, 0);
				chain.subEvents.add(new WaveEvent(0, .2 * TPM, new AStarSimpleMob(), new GreenSlime(), 20*xO(o), 3));
				chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));

				chain.subEvents.add(new WaveEvent(0, .5 * TPM, new SimpleCasterAI(), new ThrowAcornSquirrel(), 20*xO(o), 2));
				chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));

				chain.subEvents.add(new WaveEvent(0, .5 * TPM, new WaspAI(), new Bee(), 15*xO(o), 3));
				chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));

//				chain.subEvents.add(new WaveEvent(0, .1 * TPM, new AStarSimpleMob(), new Orc(), 1, 1));
				events.events.add(chain);


				events.events.add(new WaveEvent(0, .5 * TPM, new SimpleCasterAI(), new ThrowAcornSquirrel(), 20*xO(o), 3));
				events.events.add(new WaveEvent(0.4*TPM, .1 * TPM, new AStarSimpleMob(), new SmallLizard(), 5*xO(o), 2));//TODO: lizards
				events.events.add(new WaveEvent(0.4*TPM, .1 * TPM, new AStarSimpleMob(), new BlueSlime(), 5*xO(o), 2));//TODO: blue slimes
				events.events.add(new WaveEvent(.7*TPM, .5 * TPM, new AStarSimpleMob(), new GreenSlime(), 20*xO(o), 2));//todo shrubbery
				events.events.add(new WaveEvent(1.5*TPM, .5 * TPM, new WaspAI(), new Bee(), 10*xO(o), 3));//todo wasps

				break;
			default:
				//todo: stupidly difficult shit in here
				events.events.add(new WaveEvent(0.0     , .5 * TPM, new IronGolemAI(), new IronGolem(), 1*xO(o), 10));
				//^^even just one is fucking hard, 4 is impossibru in a normal area. you're always in laser range.
				events.events.add(new WaveEvent(0.3*TPM , .1 * TPM, new AStarSimpleMob(), new Orc(), 10*xO(o), 5));
				events.events.add(new WaveEvent(0.5*TPM , .5 * TPM, new AStarSimpleMob(), new RedSlime(), 20*xO(o), 2));
				events.events.add(new WaveEvent(0.5*TPM , .5 * TPM, new SimpleCasterAI(), new IcicleSkeleton(), 20*xO(o), 5));
				events.events.add(new WaveEvent(0.7*TPM , .5 * TPM, new WaspAI(), new Bee(), 50*xO(o), 3));
				break;
		}
		return events;
	}

	/**
	 *
	 * @param events
	 * @param o whether or not the area is overrun. used in (numEnemies * xO(o)) calls.
	 * @param level the effective level of the area, player + dist/5
	 * @return
	 */
	private static EventQueue generateDesert(EventQueue events, boolean o, int level){
		//saving space for 3 general purpose numbers.
		double randVarA;
		double randVarB;
		double randVarC;
		Random rand = Engine.getRand();
		EventChain chain;
		switch (level){
			case 0:
			case 2:
				events.events.add(new WaveEvent(1.0*TPM, .5 * TPM, new AStarSimpleMob(), new GreenSlime(), 20*xO(o), 2));//TODO: Lizards
			case 1:
				chain = new EventChain(0, 0);
				chain.subEvents.add(new WaveEvent(0, .5 * TPM, new AStarSimpleMob(), new GreenSlime(), 20*xO(o), 1));
				chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));

				chain.subEvents.add(new WaveEvent(0, .5 * TPM, new SimpleCasterAI(), new ThrowAcornSquirrel(), 20*xO(o), 2));
				chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));

				chain.subEvents.add(new WaveEvent(0, .5 * TPM, new DervishAI(), new SandDervish(), 15*xO(o), 3));
				chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));

//				chain.subEvents.add(new WaveEvent(0, .1 * TPM, new AStarSimpleMob(), new Orc(), 1, 1));
				events.events.add(chain);

				events.events.add(new WaveEvent(0.0*TPM, .1 * TPM, new DervishAI(), new SandDervish(), 10*xO(o), 1));
				events.events.add(new WaveEvent(.7*TPM, .5 * TPM, new AStarSimpleMob(), new GreenSlime(), 20*xO(o), 2));
				events.events.add(new WaveEvent(1.5*TPM, .5 * TPM, new SimpleCasterAI(), new ThrowAcornSquirrel(), 20*xO(o), 2));//todo scorpion

				break;
			case 4:
				events.events.add(new WaveEvent(.7*TPM, .5 * TPM, new AStarSimpleMob(), new RedSlime(), 20*xO(o), 5));//todo snake
			case 3:
				chain = new EventChain(0, 0);
				chain.subEvents.add(new WaveEvent(0, .2 * TPM, new AStarSimpleMob(), new GreenSlime(), 20*xO(o), 3));
				chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));

				chain.subEvents.add(new WaveEvent(0, .5 * TPM, new DervishAI(), new SandDervish(), 20*xO(o), 4));
				chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));

				chain.subEvents.add(new WaveEvent(0, .5 * TPM, new AStarSimpleMob(), new RedSlime(), 15*xO(o), 3));
				chain.subEvents.add(new TimingBufferEvent(0, 5 * Constants.TICKS_PER_SECOND));

//				chain.subEvents.add(new WaveEvent(0, .1 * TPM, new AStarSimpleMob(), new Orc(), 1, 1));
				events.events.add(chain);

				events.events.add(new WaveEvent(0, .5 * TPM, new SimpleCasterAI(), new ThrowAcornSquirrel(), 20*xO(o), 3));
				events.events.add(new WaveEvent(0.4*TPM, .1 * TPM, new AStarSimpleMob(), new GreenSlime(), 10*xO(o), 2));//TODO: lizards

				events.events.add(new WaveEvent(.7*TPM, .5 * TPM, new SimpleMob(), new FloatingTorso(), 5*xO(o), 4));//todo cactar
				events.events.add(new WaveEvent(1.5*TPM, .5 * TPM, new DervishAI(), new SandDervish(), 10*xO(o), 3));//todo something

				break;
			default:
				//todo: stupidly difficult shit in here
				events.events.add(new WaveEvent(0.0     , .5 * TPM, new SimpleCasterAI(), new IcicleSkeleton(), 40*xO(o), 3));
				events.events.add(new WaveEvent(0.3*TPM , .1 * TPM, new AStarSimpleMob(), new Orc(), 10*xO(o), 5));
				events.events.add(new WaveEvent(0.3*TPM , .5 * TPM, new DervishAI(), new SandDervish(), 100*xO(o), 3));
				events.events.add(new WaveEvent(0.5*TPM , .5 * TPM, new AStarSimpleMob(), new RedSlime(), 20*xO(o), 2));
				events.events.add(new WaveEvent(0.5*TPM , .5 * TPM, new AStarSimpleMob(), new Skeleton(), 20*xO(o), 5));
				events.events.add(new WaveEvent(0.7*TPM , .5 * TPM, new BatAI(), new Bat(), 50*xO(o), 3));
				break;
		}
		return events;
	}

}
