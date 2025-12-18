package com.seed.pizza;

import static com.seed.pizza.NyPizza.Size.SMALL;
import static com.seed.pizza.Pizza.Topping.*;

public class Test {
    public static void main(String[] args) {
        NyPizza pizza = new NyPizza.Builder(SMALL)
                .addTopping(SAUSAGE).addTopping(ONION).build();
        Calzone calzone = new Calzone.Builder()
                .addTopping(HAM).sauceInside().build();

        System.out.println(pizza);
        System.out.println(calzone);
    }
}
