package roles

import actions.collect
import actions.upgrade
import screeps.api.*
import states.BuildingState
import states.buildingState

object Upgrader : Role {

    override val body: Array<BodyPartConstant>
        get() {
            return arrayOf(WORK, WORK, CARRY, MOVE)
        }

    override fun run(creep: Creep) = with(creep) {
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