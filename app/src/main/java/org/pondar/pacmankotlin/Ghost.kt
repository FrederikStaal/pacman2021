package org.pondar.pacmankotlin

import kotlin.random.Random

class Ghost(var x: Int, var y: Int) {

    fun move() {
        if (Random.nextInt(1, 4) == 1) {
            this.x - 1
            this.y - 1
        } else if (Random.nextInt(1, 4) == 2) {
            this.x + 1
            this.y - 1
        } else if (Random.nextInt(1, 4) == 3) {
            this.x - 1
            this.y + 1
        } else if (Random.nextInt(1, 4) == 4) {
            this.x + 1
            this.y + 1
        }
    }
}