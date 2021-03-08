package spawnStrategies

import roles.Builder
import roles.Harvester
import roles.Role
import roles.Upgrader
import screeps.api.BODYPART_COST
import screeps.api.BodyPartConstant
import screeps.api.Creep
import screeps.api.CreepMemory
import screeps.api.ERR_BUSY
import screeps.api.ERR_NOT_ENOUGH_ENERGY
import screeps.api.Game
import screeps.api.OK
import screeps.api.ScreepsReturnCode
import screeps.api.SpawnMemory
import screeps.api.get
import screeps.api.options
import screeps.api.structures.StructureSpawn
import screeps.api.values
import screeps.utils.memory.memory
import screeps.utils.unsafe.jsObject
import starter.role

var SpawnMemory.nextBodyDebug: String by memory { "" }

class SimpleSpawnStrategy(spawn: StructureSpawn) : SpawnStrategy(spawn) {

    private val roles: List<Pair<Role, Int>> = listOf(
        Harvester to 4,
        Upgrader to 3,
        Builder to 3
    )

    private val role: Role?
        get() = roles.firstOrNull { roleMapEntry ->
            Game.creeps.values.withRole(roleMapEntry.first) < roleMapEntry.second
        }?.first

    private fun Array<Creep>.withRole(role: Role): Int = this.count { it.memory.role == role.toString() }

    private val Role.body: Array<BodyPartConstant>?
        get() = this.bodies.minByOrNull {
            val diff = spawn.room.energyCapacityAvailable - it.cost
            if (diff >= 0) {
                diff
            } else {
                spawn.room.energyCapacityAvailable
            }
        }
    private val Array<BodyPartConstant>.cost: Int
        get() = this.sumBy { BODYPART_COST[it]!! }

    override fun spawnCreep() {
        val role = role ?: return
        val body = role.body ?: return

        if (spawn.room.energyAvailable < body.cost) {
            spawn.memory.nextBodyDebug = "Next Creep: $role with body: $body"
            return
        }

        val newName = "${role}_${Game.time}"
        spawn.spawnCreep(body, newName, options {
            memory = jsObject<CreepMemory> { this.role = role.toString() }
        }).also(this::handleSpawnReturn)
    }

    private fun handleSpawnReturn(code: ScreepsReturnCode) {
        when (code) {
            OK -> console.log("Spawning $role")
            ERR_BUSY, ERR_NOT_ENOUGH_ENERGY -> run { } // do nothing
            else -> console.log("unhandled error code $code")
        }
    }

}