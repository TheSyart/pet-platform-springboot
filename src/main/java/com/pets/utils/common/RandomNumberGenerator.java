package com.pets.utils.common;
import java.util.Random;

public class RandomNumberGenerator {

    public int random(int digitCount) {
        int min = (int) Math.pow(10, digitCount - 1); // 最小值
        int max = (int) Math.pow(10, digitCount) - 1; // 最大值

        Random random = new Random();
        int randomNumber = random.nextInt(max - min + 1) + min;
        return randomNumber;
    }

}
