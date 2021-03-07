package roles

import screeps.api.Creep
import starter.role

interface Role {
    fun run(creep: Creep)
}

internal fun Creep.run() = getRoleFromString(memory.role).run(this)

private fun getRoleFromString(roleString: String): Role = when (roleString) {
    Builder.toString() -> Builder
    Harvester.toString() -> Harvester
    Upgrader.toString() -> Upgrader
    else -> Unassigned
}