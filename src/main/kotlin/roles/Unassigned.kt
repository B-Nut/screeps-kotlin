package roles

import screeps.api.Creep

object Unassigned : Role {
    override fun run(creep: Creep) {
        // Do Nothing, you lazy bum.
    }

    override fun toString() = "Unassigned"

}