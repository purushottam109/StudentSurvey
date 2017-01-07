package com.swe645.assign5;

public enum HowInterested
{
	Friends ("Friends"),
	Television ("Television"),
	Internet ("Internet"),
	Other ("Other");

	private final String name;

    private HowInterested(String s)
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
