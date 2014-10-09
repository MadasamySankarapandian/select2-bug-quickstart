package org.sample;

import java.io.Serializable;

/**
 * @author Madasamy
 * @since x.x
 */
public class User implements Serializable
{
    private String name;
    private Country country;

    public User()
    {
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Country getCountry()
    {
        return country;
    }

    public void setCountry(Country country)
    {
        this.country = country;
    }

    @Override
    public String toString()
    {
        return "Name: " + name + "," + "Country: " + country;
    }

}
