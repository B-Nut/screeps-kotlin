package roles

import screeps.api.*

object Upgrader : Role {
    override fun run(creep: Creep) {
        with(creep) {
            val controller = this.room.controller!!

            if (store[RESOURCE_ENERGY] == 0) {
                val sources = room.find(FIND_SOURCES)
                if (harvest(sources[0]) == ERR_NOT_IN_RANGE) {
                    moveTo(sources[0].pos)
                }
            } else {
                if (upgradeController(controller) == ERR_NOT_IN_RANGE) {
                    moveTo(controller.pos)
                }
            }
        }
    }

    override fun toString() = "Upgrader"
}