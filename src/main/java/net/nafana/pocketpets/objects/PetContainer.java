package net.nafana.pocketpets.objects;

import net.nafana.pocketpets.enums.PetType;

public class PetContainer {

    PetType petType;
    int slotContainedIn;

    public PetContainer (PetType petType, int slotContainedIn) {

        this.petType = petType;
        this.slotContainedIn = slotContainedIn;

    }

    public PetType getPetType() {
        return petType;
    }

    public int getSlotContainedIn() {
        return slotContainedIn;
    }
}
