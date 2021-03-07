package spawnStrategies

import screeps.api.structures.StructureSpawn

abstract class SpawnStrategy(val spawn: StructureSpawn) {
    abstract fun spawnCreep()
}