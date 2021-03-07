package roles

import actions._build
import actions.collect
import screeps.api.*
import states.BuildingState
import states.buildingState

object Builder : Role {

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
            BuildingState.Building -> {
                val targets = this.room.find(FIND_MY_CONSTRUCTION_SITES)
                if (targets.isNotEmpty()) {
                    _build(targets[0])
                }
            }
        }
    }

    override fun toString() = "Builder"
}