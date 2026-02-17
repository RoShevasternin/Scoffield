package com.obezana.playtocrypto.game.utils

import com.badlogic.gdx.math.Vector2

fun Vector2.divOr0(scalar: Float): Vector2 {
    x = x.divOr0(scalar)
    y = y.divOr0(scalar)
    return this
}