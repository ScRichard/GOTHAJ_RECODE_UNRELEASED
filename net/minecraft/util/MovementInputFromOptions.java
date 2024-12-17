package net.minecraft.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import vip.gothaj.client.Client;
import vip.gothaj.client.event.events.EventMoveButton;
import vip.gothaj.client.event.events.EventSilentMoveFix;

public class MovementInputFromOptions extends MovementInput
{
    private final GameSettings gameSettings;
    
    private Minecraft mc = Minecraft.getMinecraft();
    private float lastForward;
    private float lastStrafe;

    public MovementInputFromOptions(GameSettings gameSettingsIn)
    {
        this.gameSettings = gameSettingsIn;
    }

    public void updatePlayerMoveState()
    {
        EventMoveButton event = new EventMoveButton(this.gameSettings.keyBindLeft.isKeyDown(), this.gameSettings.keyBindRight.isKeyDown(), this.gameSettings.keyBindBack.isKeyDown(), this.gameSettings.keyBindForward.isKeyDown(), this.gameSettings.keyBindSneak.isKeyDown(), this.gameSettings.keyBindJump.isKeyDown());
        Client.INSTANCE.getEventBus().call(event);
        if(event.isCancelled())return;
        this.moveStrafe = 0.0F;
        this.moveForward = 0.0F;

        if (event.isCancelled())
        {
            return;
        }

        if (event.forward)
        {
            ++this.moveForward;
        }

        if (event.backward)
        {
            --this.moveForward;
        }

        if (event.left)
        {
            ++this.moveStrafe;
        }

        if (event.right)
        {
            --this.moveStrafe;
        }

        this.jump = event.jump;
        this.sneak = event.sneak;
        
        EventSilentMoveFix e = new EventSilentMoveFix(this.moveForward, this.moveStrafe);
        Client.INSTANCE.getEventBus().call(e);
        
        this.moveForward = e.getForward();
        this.moveStrafe = e.getStrafe();

        if (this.sneak)
        {
            this.moveStrafe = (float)((double) this.moveStrafe * 0.3D);
            this.moveForward = (float)((double) this.moveForward * 0.3D);
        }
    }
}
