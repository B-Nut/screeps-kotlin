package states

import screeps.api.Creep
import screeps.api.CreepMemory
import screeps.api.RESOURCE_ENERGY
import screeps.api.get
import screeps.utils.memory.memory

var CreepMemory.isBuilding: Boolean by memory { false }

sealed class BuildingState {
    object Collecting : BuildingState()
    object Building : BuildingState()
}

val Creep.buildingState: BuildingState
    get() {
        if (memory.isBuilding && store[RESOURCE_ENERGY] == 0) {
            memory.isBuilding = false
            return BuildingState.Collecting
        }
        if (!memory.isBuilding && store[RESOURCE_ENERGY] == store.getCapacity()) {
            memory.isBuilding = true
            return BuildingState.Building
        }

        return when (memory.isBuilding) {
            true -> BuildingState.Building
            false -> BuildingState.Collecting
        }
    }