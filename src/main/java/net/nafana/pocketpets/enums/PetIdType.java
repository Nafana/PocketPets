package net.nafana.pocketpets.enums;

public enum PetIdType {

    SLIME("MHF_Slime"),
    SPIDER("MHF_Spider"),
    CHICKEN("MHF_Chicken"),
    WOLF("Wolf"),
    GOLEM("Golem");

    public String skullID;

    PetIdType(String skullID) {

        this.skullID = skullID;

    }

    public String getSkullID() {
        return skullID;
    }
}
