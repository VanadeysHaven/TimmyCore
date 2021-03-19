package me.Cooltimmetje.TimmyCore.Utilities;

import java.util.Random;

public class RNGManager {

    private Random random;

    public RNGManager(){
        random = new Random();
    }

    public RNGManager(long seed){
        random = new Random(seed);
    }

    public int integer(int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

}
