package com.bahisadam.model.teamInfo;

public class Period
{
    private String _id;

    private String name;

    private String value;

    public String get_id ()
    {
        return _id;
    }

    public void set_id (String _id)
    {
        this._id = _id;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getValue ()
    {
        return value;
    }

    public void setValue (String value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [_id = "+_id+", name = "+name+", value = "+value+"]";
    }
}

