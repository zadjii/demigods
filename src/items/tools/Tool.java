package items.tools;

import items.Item;

public class Tool extends Item {

	private double harvestRate;
	private double harvestAmount;

	public double getHarvestRate() {
		return this.harvestRate;
	}

	public double getHarvestAmount() {
		return this.harvestAmount;
	}

	public void setHarvestRate(double i) {
		this.harvestRate = i;
	}

	public void setHarvestAmount(double i) {
		this.harvestAmount = i;
	}

	public void incrementHarvestAmount() {
		this.harvestAmount += this.harvestRate;
	}

	public int getHarvestedResources() {
		int amount = 0;
		while (this.getHarvestAmount() >= 1) {
			this.setHarvestAmount(this.getHarvestAmount() - 1);
			++amount;
		}
		return amount;
	}

	public boolean useTick() {
		this.harvestAmount += this.harvestRate;
		if (this.harvestAmount >= 1) {
			--this.harvestAmount;
			// --durability;
			return true;
		}
		return false;
	}

	public Tool() {
		super();
	}
}
