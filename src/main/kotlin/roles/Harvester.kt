package roles

import screeps.api.*


object Harvester : Role {
    override fun run(creep: Creep) = with(creep) {
        when (harvestState) {
            HarvestState.Collecting -> {
                val sources = room.find(FIND_SOURCES)
                if (harvest(sources[0]) == ERR_NOT_IN_RANGE) {
                    moveTo(sources[0].pos)
                }
            }
            HarvestState.Distributing -> {
                val targets = room.find(FIND_MY_STRUCTURES)
                    .filter { (it.structureType == STRUCTURE_EXTENSION || it.structureType == STRUCTURE_SPAWN) }
                    .map { it.unsafeCast<StoreOwner>() }
                    .filter { it.store[RESOURCE_ENERGY] < it.store.getCapacity(RESOURCE_ENERGY) }

                if (targets.isNotEmpty()) {
                    if (transfer(targets[0], RESOURCE_ENERGY) == ERR_NOT_IN_RANGE) {
                        moveTo(targets[0].pos)
                    }
                }
            }
        }
    }


    override fun toString() = "Harvester"
}

private val Creep.harvestState: HarvestState
    get() =
        if (store[RESOURCE_ENERGY] < store.getCapacity()) {
            HarvestState.Collecting
        } else {
            HarvestState.Distributing
        }

private sealed class HarvestState {
    object Collecting : HarvestState()
    object Distributing : HarvestState()
}