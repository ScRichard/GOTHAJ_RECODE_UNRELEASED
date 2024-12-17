package net.minecraft.util;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import vip.gothaj.client.Client;
import vip.gothaj.client.event.events.EventMouse;

public class MouseHelper
{
    public int deltaX;
    public int deltaY;

    public void grabMouseCursor()
    {
        Mouse.setGrabbed(true);
        this.deltaX = 0;
        this.deltaY = 0;
    }

    public void ungrabMouseCursor()
    {
        Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
        Mouse.setGrabbed(false);
    }

    public void mouseXYChange()
    {
    	EventMouse e = new EventMouse();
    	Client.INSTANCE.getEventBus().call(e);
    	if(e.isCancelled()) return;
    	
        this.deltaX = Mouse.getDX();
        this.deltaY = Mouse.getDY();
    }
}
