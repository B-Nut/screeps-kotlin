package actions

import screeps.api.ConstructionSite
import screeps.api.Creep
import screeps.api.ERR_NOT_IN_RANGE
import screeps.api.Source
import screeps.api.structures.StructureController

fun Creep.collect(source: Source) {
    if (harvest(source) == ERR_NOT_IN_RANGE) {
        moveTo(source.pos)
    }
}

fun Creep.upgrade(controller: StructureController) {
    if (upgradeController(controller) == ERR_NOT_IN_RANGE) {
        moveTo(controller.pos)
    }
}

fun Creep._build(constructionSite: ConstructionSite) {
    if (build(constructionSite) == ERR_NOT_IN_RANGE) {
        moveTo(constructionSite.pos)
    }
}