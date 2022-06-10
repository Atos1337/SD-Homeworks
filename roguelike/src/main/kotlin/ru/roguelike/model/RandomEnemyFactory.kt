package ru.roguelike.model

import ru.roguelike.util.Constants.CYBORG_PROBABILITY
import ru.roguelike.util.Constants.DRAGON_PROBABILITY
import ru.roguelike.util.Constants.SKELETON_PROBABILITY
import kotlin.random.Random

class RandomEnemyFactory : EnemyFactory {
    override fun create(x: Int, y: Int): Enemy {
        val probList = mutableListOf(0.0)
        probList.add(probList.last() + DRAGON_PROBABILITY)
        probList.add(probList.last() + SKELETON_PROBABILITY)
        probList.add(probList.last() + CYBORG_PROBABILITY)

        val randVal = Random.nextDouble()
        var ind = 0
        for (i in 0 until probList.lastIndex) {
            if (probList[i] < randVal && randVal <= probList[i + 1]) {
                ind = i
            }
        }
        return when (EnemyStyle.values()[ind]) {
            EnemyStyle.DRAGON -> DragonFactory().create(x, y)
            EnemyStyle.SKELETON -> SkeletonFactory().create(x, y)
            EnemyStyle.CYBORG -> CyborgFactory().create(x, y)
        }
    }
}
