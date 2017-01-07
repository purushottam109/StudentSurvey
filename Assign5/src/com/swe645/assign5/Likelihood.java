package com.swe645.assign5;

public enum Likelihood
{
	Very_Likely ("Very Likely"),
	Likely ("Likely"),
	Unlikely ("Unlikely");

	private final String name;

    private Likelihood(String s)
    {
        name = s;
    }

    public boolean equalsName(String otherName)
    {
        return (otherName == null)? false:name.equals(otherName);
    }

    @Override
	public String toString()
    {
       return name;
    }
}
