package roles

import screeps.api.*

object Unassigned : Role {

    override val body: Array<BodyPartConstant>
        get() {
            return arrayOf(WORK, CARRY, MOVE)
        }

    override fun run(creep: Creep) {
        // Do Nothing, you lazy bum.
    }

    override fun toString() = "Unassigned"
}