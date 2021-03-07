package builderStrategies

import screeps.api.RoomPosition
import screeps.api.structures.Structure

interface BuilderStrategy {
    fun newBuildings(): List<Pair<Structure, RoomPosition>>
}