package actions

import screeps.api.ConstructionSite
import screeps.api.Creep
import screeps.api.ERR_NOT_IN_RANGE
import screeps.api.RoomObject
import screeps.api.ScreepsReturnCode
import screeps.api.Source
import screeps.api.structures.StructureController

fun Creep.collect(source: Source): ScreepsReturnCode = doOrWalk(source) { harvest(source) }

fun Creep.upgrade(controller: StructureController): ScreepsReturnCode =
    doOrWalk(controller) { upgradeController(controller) }

fun Creep._build(constructionSite: ConstructionSite): ScreepsReturnCode =
    doOrWalk(constructionSite) { build(constructionSite) }


private fun Creep.doOrWalk(target: RoomObject, action: () -> ScreepsReturnCode): ScreepsReturnCode {
    val actionReturnCode = action.invoke()
    return if (actionReturnCode == ERR_NOT_IN_RANGE) {
        moveTo(target)
    } else {
        actionReturnCode
    }
}