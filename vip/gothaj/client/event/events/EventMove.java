package vip.gothaj.client.event.events;

import vip.gothaj.client.event.Event;

public class EventMove extends Event
{
    private double x, y, z;

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double getZ()
    {
        return z;
    }

    public void setZ(double z)
    {
        this.z = z;
    }

    public EventMove(double x, double y, double z)
    {
        super();
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
