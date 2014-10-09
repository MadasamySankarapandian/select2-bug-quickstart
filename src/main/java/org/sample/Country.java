package org.sample;

import java.io.Serializable;

/**
 * @author Madasamy
 * @since x.x
 */
public class Country implements Serializable
{
    private String countryName;

    public Country()
    {

    }

    public Country(String countryName)
    {
        this.countryName = countryName;
    }

    public String getCountryName()
    {
        return countryName;
    }

    public void setCountryName(String countryName)
    {
        this.countryName = countryName;
    }

    @Override
    public String toString()
    {
        return "countryName='" + countryName;

    }
}
