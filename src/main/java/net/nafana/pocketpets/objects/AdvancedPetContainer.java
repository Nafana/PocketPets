package net.nafana.pocketpets.objects;

import org.bukkit.inventory.ItemStack;

public class AdvancedPetContainer {

    PetContainer petContainer;
    ItemStack stack;

    public AdvancedPetContainer (PetContainer petContainer, ItemStack stack) {

        this.petContainer = petContainer;
        this.stack = stack;

    }

    public PetContainer getPetContainer() {
        return petContainer;
    }

    public ItemStack getStack() {
        return stack;
    }
}
