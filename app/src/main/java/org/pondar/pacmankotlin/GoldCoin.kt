package org.pondar.pacmankotlin

//Here you need to fill out what should be in a GoldCoin and what should the constructor be
class GoldCoin(x: Int, y: Int) {
    var coinx = 0
    var maxX = 0
    var coiny = 0
    var maxY = 0

    var taken = false

    init {
        coinx = x
        maxX = coinx + 100
        coiny = y
        maxY = coiny + 100
    }
}