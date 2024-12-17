package vip.gothaj.client.event.events;

import vip.gothaj.client.event.Event;

public class EventKeyPress extends Event
{
    private int key;

    public int getKey()
    {
        return key;
    }

    public void setKey(int key)
    {
        this.key = key;
    }

    public EventKeyPress(int key)
    {
        super();
        this.key = key;
    }
}
