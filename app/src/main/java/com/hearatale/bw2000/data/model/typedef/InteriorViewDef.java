package com.hearatale.bw2000.data.model.typedef;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        InteriorViewDef.INTERIOR_TOYS,
        InteriorViewDef.INTERIOR_BAKERY,
        InteriorViewDef.INTERIOR_JOB,
        InteriorViewDef.INTERIOR_VEHICLES,
        InteriorViewDef.INTERIOR_SPORTS,
        InteriorViewDef.INTERIOR_CLOTHING,
        InteriorViewDef.INTERIOR_GROCERIES,
        InteriorViewDef.INTERIOR_MUSIC,
        InteriorViewDef.INTERIOR_HAIR_SALON,
        InteriorViewDef.INTERIOR_TOOLS,
        InteriorViewDef.BIRDS,
        InteriorViewDef.WATER,
        InteriorViewDef.DINOSAUR,
        InteriorViewDef.MEAT,
        InteriorViewDef.MONKEY,
        InteriorViewDef.REPTILES,
        InteriorViewDef.PLANT,
        InteriorViewDef.INTERIOR_ZOO,
        InteriorViewDef.INTERIOR_NURSERY,
        InteriorViewDef.INTERIOR_FARM_ANIMALS,
        InteriorViewDef.INTERIOR_SCHOOL,
        InteriorViewDef.INTERIOR_MEDICAL_CENTER,
        InteriorViewDef.INTERIOR_THINGS,
        InteriorViewDef.INTERIOR_PEOPLE,
        InteriorViewDef.PEDIATRICIAN,
        InteriorViewDef.BUILDING4A,
        InteriorViewDef.BUILDING4B,
        InteriorViewDef.BUILDING4C,
        InteriorViewDef.INTERIOR_PARK,
        InteriorViewDef.BUILDING7A,
        InteriorViewDef.INTERIOR_HOUSE,
        InteriorViewDef.BUILDING11A,

})
@Retention(RetentionPolicy.SOURCE)
public @interface InteriorViewDef {
    int INTERIOR_TOYS = 0;
    int INTERIOR_BAKERY = 1;
    int INTERIOR_JOB = 2;
    int INTERIOR_VEHICLES = 3;
    int INTERIOR_SPORTS = 4;
    int INTERIOR_CLOTHING = 5;
    int INTERIOR_GROCERIES = 6;
    int INTERIOR_MUSIC = 7;
    int INTERIOR_HAIR_SALON = 8;
    int INTERIOR_TOOLS = 9;
    int BIRDS = 10;
    int WATER = 11;
    int DINOSAUR = 12;
    int MEAT = 13;
    int MONKEY = 14;
    int REPTILES = 15;
    int PLANT = 16;
    int INTERIOR_ZOO = 17;
    int INTERIOR_NURSERY = 18;
    int INTERIOR_FARM_ANIMALS = 19;
    int INTERIOR_SCHOOL = 20;
    int INTERIOR_MEDICAL_CENTER = 21;
    int INTERIOR_THINGS = 22;
    int INTERIOR_PEOPLE = 23;
    int PEDIATRICIAN = 24;
    int BUILDING4A = 25;
    int BUILDING4B = 26;
    int BUILDING4C = 27;
    int INTERIOR_PARK = 28;
    int BUILDING7A = 29;
    int INTERIOR_HOUSE = 30;
    int BUILDING11A = 31;
}
