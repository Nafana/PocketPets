package net.nafana.pocketpets.enums;

public enum PetType {

    CHICKEN("chickenpet"),
    EXPERIENCE("experiencepet"),
    LEPRECHAUN("leprechaunpet"),
    SPIDER("spiderpet"),
    WOLF("wolfpet");

    String uniquepetID;

    PetType (String uniquepetID) {
        this.uniquepetID = uniquepetID;
    }

    public static PetType getPetTypeFromID (String input) {

        for (PetType petType : PetType.values()){

            if (petType.getUniquepetID().equals(input)) {
                return petType;
            }

        }

        return null;

    }

    public String getUniquepetID () {
        return uniquepetID;
    }

}
