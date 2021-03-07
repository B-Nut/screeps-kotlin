package spawnStrategies

import roles.Builder
import roles.Harvester
import roles.Role
import roles.Upgrader
import screeps.api.*
import screeps.api.structures.StructureSpawn
import screeps.utils.unsafe.jsObject
import starter.role

class SimpleSpawnStrategy(spawn: StructureSpawn) : SpawnStrategy(spawn) {

    private val roles: List<Pair<Role, Int>> = listOf(
        Harvester to 4,
        Upgrader to 2,
        Builder to 2
    )

    private fun Array<Creep>.withRole(role: Role): Int = this.count { it.memory.role == role.toString() }

    private fun nextCreepRole(creeps: Array<Creep>): Role? {
        roles.forEach { roleMapEntry ->
            if (creeps.withRole(roleMapEntry.first) < roleMapEntry.second) {
                return roleMapEntry.first
            }
        }
        return null
    }

    override fun spawnCreep() {
        val creeps: Array<Creep> = Game.creeps.values

        val role: Role = nextCreepRole(creeps) ?: return

        if (spawn.room.energyAvailable < role.body.sumBy { BODYPART_COST[it]!! }) {
            return
        }

        val newName = "${role}_${Game.time}"
        spawn.spawnCreep(role.body, newName, options {
            memory = jsObject<CreepMemory> { this.role = role.toString() }
        }).also { this.handleSpawnReturn(it, role) }
    }

    private fun handleSpawnReturn(code: ScreepsReturnCode, role: Role) {
        when (code) {
            OK -> console.log("Spawning $role with body ${role.body}")
            ERR_BUSY, ERR_NOT_ENOUGH_ENERGY -> run { } // do nothing
            else -> console.log("unhandled error code $code")
        }
    }

}