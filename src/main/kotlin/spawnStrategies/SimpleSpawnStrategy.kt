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

    private val body = arrayOf<BodyPartConstant>(WORK, CARRY, MOVE)

    private val roles: List<Pair<Role, Int>> = listOf(
        Harvester to 2,
        Upgrader to 1,
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
        if (spawn.room.energyAvailable < body.sumBy { BODYPART_COST[it]!! }) {
            return
        }

        val role: Role = nextCreepRole(creeps) ?: return
        val newName = "${role}_${Game.time}"

        spawn.spawnCreep(body, newName, options {
            memory = jsObject<CreepMemory> { this.role = role.toString() }
        }).also(this::handleSpawnReturn)
    }

    private fun handleSpawnReturn(code: ScreepsReturnCode) {
        when (code) {
            OK -> console.log("spawning with body $body")
            ERR_BUSY, ERR_NOT_ENOUGH_ENERGY -> run { } // do nothing
            else -> console.log("unhandled error code $code")
        }
    }

}