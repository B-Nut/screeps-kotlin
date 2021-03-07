package roles

import screeps.api.*
import starter.building

object Builder : Role {
    override fun run(creep: Creep) {
        with(creep) {
            if (memory.building && store[RESOURCE_ENERGY] == 0) {
                memory.building = false
                say("🔄 harvest")
            }
            if (!memory.building && store[RESOURCE_ENERGY] == store.getCapacity()) {
                memory.building = true
                say("🚧 build")
            }

            if (memory.building) {
                val targets = this.room.find(FIND_MY_CONSTRUCTION_SITES)
                if (targets.isNotEmpty()) {
                    if (build(targets[0]) == ERR_NOT_IN_RANGE) {
                        moveTo(targets[0].pos)
                    }
                }
            } else {
                val sources = room.find(FIND_SOURCES)
                if (harvest(sources[0]) == ERR_NOT_IN_RANGE) {
                    moveTo(sources[0].pos)
                }
            }
        }
    }

    override fun toString() = "Builder"
}