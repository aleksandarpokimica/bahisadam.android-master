package com.bahisadam.model.teamInfo;


public class Scored
{
    private String total;

    private Period[] period;

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    public Period[] getPeriod ()
    {
        return period;
    }

    public void setPeriod (Period[] period)
    {
        this.period = period;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [total = "+total+", period = "+period+"]";
    }
}

