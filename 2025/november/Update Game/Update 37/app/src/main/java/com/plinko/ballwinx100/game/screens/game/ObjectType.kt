package com.plinko.ballwinx100.game.screens.game

enum class ObjectType(
    val type: String,
    var actorName: String = "",
    val value: Float = 1f
) {
    BALL("ball"),
    PIN("pin"),
    BASKET_0("basket", "",0.5f),
    BASKET_1("basket","", 1f),
    BASKET_2("basket","", 2f),
    BASKET_3("basket","", 5f),
    ;

}