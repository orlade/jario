package net.piemaster.jario.components;

import com.artemis.Component;

public class Item extends Component
{
	public enum ItemType
	{
		MUSHROOM, COIN, FLOWER, STAR
	};
	
	private ItemType type;
	private boolean dispensing;

	public Item(ItemType type)
	{
		this.type = type;
	}

	public ItemType getType()
	{
		return type;
	}

	public void setType(ItemType type)
	{
		this.type = type;
	}

	public boolean isDispensing()
	{
		return dispensing;
	}

	public void setDispensing(boolean dispensing)
	{
		this.dispensing = dispensing;
	}
}
