package com.swe645.assign5;

public enum Likes
{
	Students ("Students"),
	Location ("Location"),
	Campus ("Campus"),
	Atmosphere ("Atmosphere"),
	Dorm_Rooms ("Dorm Rooms"),
	Sports ("Sports");

	private final String name;

    private Likes(String s)
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
