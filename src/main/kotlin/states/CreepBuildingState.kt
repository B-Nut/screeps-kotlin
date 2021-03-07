package states

import screeps.api.Creep
import screeps.api.RESOURCE_ENERGY
import screeps.api.get
import starter.building

sealed class BuildingState {
    object Collecting : BuildingState()
    object Building : BuildingState()
}

val Creep.buildingState: BuildingState
    get() {
        if (memory.building && store[RESOURCE_ENERGY] == 0) {
            memory.building = false
            return BuildingState.Collecting
        }
        if (!memory.building && store[RESOURCE_ENERGY] == store.getCapacity()) {
            memory.building = true
            return BuildingState.Building
        }

        return when (memory.building) {
            true -> BuildingState.Building
            false -> BuildingState.Collecting
        }
    }