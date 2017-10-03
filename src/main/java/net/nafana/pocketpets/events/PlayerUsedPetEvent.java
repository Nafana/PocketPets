package net.nafana.pocketpets.events;

import net.nafana.pocketpets.objects.AdvancedPetContainer;
import net.nafana.pocketpets.pets.ExperiencePet;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerUsedPetEvent extends Event implements Cancellable{

    private final Player p;
    private final AdvancedPetContainer advancedPetContainer;
    private boolean isCancelled;

    private static final HandlerList HANDLERS = new HandlerList();

    public PlayerUsedPetEvent (Player p, AdvancedPetContainer advancedPetContainer) {

        this.p = p;
        this.advancedPetContainer = advancedPetContainer;
        this.isCancelled = false;

    }

    public AdvancedPetContainer getAdvancedPetContainer() {
        return advancedPetContainer;
    }

    public Player getPlayer() {
        return p;
    }

    public boolean isCancelled() {
        return false;
    }

    public static HandlerList getHandlerList () {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    private Class getPetClassInstance (String petIdentifier) {

        switch (petIdentifier){

            case "experiencepet":
                return ExperiencePet.class;
            default:
                return null;
        }

    }

}
