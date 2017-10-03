package net.nafana.pocketpets;

import net.nafana.pocketpets.pets.*;

public class PocketPetsLoader {

    private static PocketPetsLoader instance;
    private ExperiencePet experiencePet;
    private ChickenPet chickenPet;
    private LeprechaunPet leprechaunPet;
    private SpiderPet spiderPet;
    private WolfPet wolfPet;

    public PocketPetsLoader () {
        instance = this;

        this.experiencePet = new ExperiencePet();
        this.wolfPet = new WolfPet();
        this.chickenPet = new ChickenPet();
        this.leprechaunPet = new LeprechaunPet();
        this.spiderPet = new SpiderPet();

    }

    public static PocketPetsLoader getInstance() {
        return instance;
    }

    public ExperiencePet getExperiencePet() {
        return experiencePet;
    }

    public ChickenPet getChickenPet() {
        return chickenPet;
    }

    public LeprechaunPet getLeprechaunPet() {
        return leprechaunPet;
    }

    public SpiderPet getSpiderPet() {
        return spiderPet;
    }

    public WolfPet getWolfPet() {
        return wolfPet;
    }
}
