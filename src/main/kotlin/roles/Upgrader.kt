package roles

import actions.collect
import actions.upgrade
import screeps.api.*
import states.HarvestState
import states.harvestState

object Upgrader : Role {

    override val body: Array<BodyPartConstant>
        get() {
            return arrayOf(WORK, CARRY, MOVE)
        }

    override fun run(creep: Creep) = with(creep) {
        when (harvestState) {
            HarvestState.Collecting -> {
                val sources = room.find(FIND_SOURCES)
                collect(sources[0])
            }
            HarvestState.Distributing -> upgrade(this.room.controller!!)
        }
    }

    override fun toString() = "Upgrader"
}