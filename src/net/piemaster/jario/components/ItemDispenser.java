package net.piemaster.jario.components;

import net.piemaster.jario.components.Item.ItemType;

public class ItemDispenser extends Expires
{
	private static final int DEFAULT_LIFE = 1000;

	private ItemType type;
	private int number = 1;
	private boolean dispensing;
	private int itemId;

	public ItemDispenser(ItemType type)
	{
		super(DEFAULT_LIFE);
		this.type = type;
	}

	public ItemDispenser(ItemType type, int number)
	{
		super(DEFAULT_LIFE);
		this.type = type;
		this.number = number;
	}

	public ItemDispenser(ItemType type, int number, int duration)
	{
		super(duration);
		this.type = type;
		this.number = number;
	}

	public ItemType getType()
	{
		return type;
	}

	public void setType(ItemType type)
	{
		this.type = type;
	}

	public int getNumber()
	{
		return number;
	}

	public void setNumber(int number)
	{
		this.number = number;
	}

	public boolean isEmpty()
	{
		return number <= 0;
	}

	public void decrementNumber()
	{
		--number;
	}

	public boolean isDispensing()
	{
		return dispensing;
	}

	public void setDispensing(boolean dispensing)
	{
		this.dispensing = dispensing;
	}

	public int getItemId()
	{
		return itemId;
	}

	public void setItemId(int itemId)
	{
		this.itemId = itemId;
	}
}
