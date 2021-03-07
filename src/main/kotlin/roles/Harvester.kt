package roles

import actions.collect
import screeps.api.*
import states.HarvestState
import states.harvestState


object Harvester : Role {

    override val body: Array<BodyPartConstant>
        get() {
            return arrayOf(WORK, WORK, CARRY, MOVE)
        }

    override fun run(creep: Creep) = with(creep) {
        when (harvestState) {
            HarvestState.Collecting -> {
                val sources = room.find(FIND_SOURCES)
                collect(sources[0])
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