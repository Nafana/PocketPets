package net.nafana.pocketpets.objects;

public class PetStats {

    private int level;
    private ExpCompletion expCompletion;

    public PetStats (int level, ExpCompletion expCompletion) {

        this.level = level;
        this.expCompletion = expCompletion;

    }

    public int getLevel() {
        return level;
    }

    public ExpCompletion getExpCompletion() {
        return expCompletion;
    }

}
