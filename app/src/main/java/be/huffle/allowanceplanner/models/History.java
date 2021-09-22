package be.huffle.allowanceplanner.models;

import java.util.Date;

public class History
{
	private Date executedDate;
	private float amount;
	private String description;

	public History(Date executedDate, float amount, String description)
	{
		this.executedDate = executedDate;
		this.amount = amount;
		this.description = description;
	}

	public Date getExecutedDate()
	{
		return executedDate;
	}

	public void setExecutedDate(Date executedDate)
	{
		this.executedDate = executedDate;
	}

	public float getAmount()
	{
		return amount;
	}

	public void setAmount(float amount)
	{
		this.amount = amount;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}
