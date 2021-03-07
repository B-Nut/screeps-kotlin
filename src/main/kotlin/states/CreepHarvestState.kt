package states

import screeps.api.Creep
import screeps.api.RESOURCE_ENERGY
import screeps.api.compareTo
import screeps.api.get

sealed class HarvestState {
    object Collecting : HarvestState()
    object Distributing : HarvestState()
}

val Creep.harvestState: HarvestState
    get() = if (store[RESOURCE_ENERGY] < store.getCapacity()) {
        HarvestState.Collecting
    } else {
        HarvestState.Distributing
    }