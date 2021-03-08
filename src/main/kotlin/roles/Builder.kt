package roles

import actions._build
import actions.collect
import screeps.api.*
import screeps.utils.memory.memory
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
                clearConstructionSiteMemory()
                val sources = room.find(FIND_SOURCES)
                collect(sources[1])
            }
            BuildingState.Building -> {
                setConstructionSiteMemory(pos.findClosestByPath(FIND_MY_CONSTRUCTION_SITES) ?: return)
                val returnCode = _build(memory.constructionSite ?: return@with)
                if (returnCode != OK) {
                    clearConstructionSiteMemory()
                }
            }
        }
    }

    override fun toString() = "Builder"
}

var CreepMemory.constructionSiteId: String by memory { "" }
private val CreepMemory.constructionSite: ConstructionSite? get() = Game.getObjectById(constructionSiteId)

private fun Creep.clearConstructionSiteMemory() {
    if (memory.constructionSiteId.isEmpty().not()) memory.constructionSiteId = ""
}

private fun Creep.setConstructionSiteMemory(constructionSite: ConstructionSite) {
    if (memory.constructionSiteId.isEmpty()) memory.constructionSiteId = constructionSite.id
}