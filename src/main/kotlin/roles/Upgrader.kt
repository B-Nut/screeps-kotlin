package roles

import actions.collect
import actions.upgrade
import screeps.api.BodyPartConstant
import screeps.api.CARRY
import screeps.api.Creep
import screeps.api.FIND_SOURCES
import screeps.api.MOVE
import screeps.api.WORK
import states.BuildingState
import states.buildingState

object Upgrader : Role {

    override val body: Array<BodyPartConstant>
        get() {
            return arrayOf(WORK, WORK, CARRY, MOVE)
        }

    override fun run(creep: Creep): Unit = with(creep) {
        when (buildingState) {
            BuildingState.Collecting -> {
                val sources = room.find(FIND_SOURCES)
                collect(sources[0])
            }
            BuildingState.Building -> upgrade(this.room.controller!!)
        }
    }

    override fun toString() = "Upgrader"
}