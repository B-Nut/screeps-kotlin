package roles

import screeps.api.BodyPartConstant
import screeps.api.CARRY
import screeps.api.Creep
import screeps.api.MOVE
import screeps.api.WORK

object Unassigned : Role {
    override val bodies: Array<Array<BodyPartConstant>>
        get() {
            return arrayOf(arrayOf(WORK, CARRY, MOVE))
        }

    override fun run(creep: Creep) {
        // Do Nothing, you lazy bum.
    }

    override fun toString() = "Unassigned"
}