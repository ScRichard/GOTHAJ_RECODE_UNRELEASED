package vip.gothaj.client.event.events;

import vip.gothaj.client.event.Event;

public class EventReach extends Event
{
    public double range;

    public double getRange()
    {
        return range;
    }

    public void setRange(double range)
    {
        this.range = range;
    }

    public EventReach(double range)
    {
        this.range = range;
    }
}
