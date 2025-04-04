
function eval(str) {

}



function addItemInventory(id/*int*/, amount/*int*/, damage/*int*/) {
}

function bl_setMobSkin(entityId/*Object*/, tex/*String*/) {
}

function bl_spawnMob(x/*double*/, y/*double*/, z/*double*/, typeId/*int*/, tex/*String*/) {
}

function clientMessage(text/*String*/) {
}

function explode(x/*double*/, y/*double*/, z/*double*/, radius/*double*/, onfire/*boolean*/) {
}

function getCarriedItem() {
}

function getLevel() {
}

function getPitch(entObj/*Object*/) {
}

function getPlayerEnt() {
}

function getPlayerX() {
}

function getPlayerY() {
}

function getPlayerZ() {
}

function getTile(x/*int*/, y/*int*/, z/*int*/) {
}

function getYaw(entObj/*Object*/) {
}

function preventDefault() {
}

function print(o/*String*/) {
}

function rideAnimal(rider/*Object*/, mount/*Object*/) {
}

function setNightMode(isNight/*boolean*/) {
}

function setPosition(ent/*Object*/, x/*double*/, y/*double*/, z/*double*/) {
}

function setPositionRelative(ent/*Object*/, x/*double*/, y/*double*/, z/*double*/) {
}

function setRot(ent/*Object*/, yaw/*double*/, pitch/*double*/) {
}

function setTile(x/*int*/, y/*int*/, z/*int*/, id/*int*/, damage/*int*/) {
}

function setVelX(ent/*Object*/, amount/*double*/) {
}

function setVelY(ent/*Object*/, amount/*double*/) {
}

function setVelZ(ent/*Object*/, amount/*double*/) {
}

function spawnChicken(x/*double*/, y/*double*/, z/*double*/, tex/*String*/) {
}

function spawnCow(x/*double*/, y/*double*/, z/*double*/, tex/*String*/) {
}

function spawnPigZombie(x/*double*/, y/*double*/, z/*double*/, item/*int*/, tex/*String*/) {
}

const ModPE = {
    dumpVtable: function (className/*String*/, size/*int*/) {
    },
    getBytesFromTexturePack: function (name/*String*/) {
    },
    getI18n: function (key/*String*/) {
    },
    getLanguage: function () {
    },
    getMinecraftVersion: function () {
    },
    langEdit: function (key/*String*/, value/*String*/) {
    },
    leaveGame: function () {
    },
    log: function (str/*String*/) {
    },
    openInputStreamFromTexturePack: function (name/*String*/) {
    },
    overrideTexture: function (theOverridden/*String*/, url/*String*/) {
    },
    readData: function (prefName/*String*/) {
    },
    removeData: function (prefName/*String*/) {
    },
    resetFov: function () {
    },
    resetImages: function () {
    },
    saveData: function (prefName/*String*/, prefValue/*String*/) {
    },
    selectLevel: function (levelDir/*String*/) {
    },
    setCamera: function (entityId/*Object*/) {
    },
    setFoodItem: function (id/*int*/, iconName/*String*/, iconSubindex/*int*/, halfhearts/*int*/, name/*String*/, maxStackSize/*int*/) {
    },
    setFov: function (fov/*double*/) {
    },
    setGameSpeed: function (ticksPerSecond/*double*/) {
    },
    setGuiBlocks: function (url/*String*/) {
    },
    setItem: function (id/*int*/, iconName/*String*/, iconSubindex/*int*/, name/*String*/, maxStackSize/*int*/) {
    },
    setItems: function (url/*String*/) {
    },
    setTerrain: function (url/*String*/) {
    },
    setUiRenderDebug: function (render/*boolean*/) {
    },
    showTipMessage: function (msg/*String*/) {
    },
    takeScreenshot: function (fileName/*String*/) {
    },
};


const Level = {
    addParticle: function (type/*int*/, x/*double*/, y/*double*/, z/*double*/, xVel/*double*/, yVel/*double*/, zVel/*double*/, size/*int*/) {
    },
    biomeIdToName: function (id/*int*/) {
    },
    canSeeSky: function (x/*int*/, y/*int*/, z/*int*/) {
    },
    destroyBlock: function (x/*int*/, y/*int*/, z/*int*/, shouldDrop/*boolean*/) {
    },
    dropItem: function (x/*double*/, y/*double*/, z/*double*/, range/*double*/, id/*int*/, count/*int*/, damage/*int*/) {
    },
    explode: function (x/*double*/, y/*double*/, z/*double*/, radius/*double*/, onfire/*boolean*/) {
    },
    getAddress: function () {
    },
    getBiome: function (x/*int*/, z/*int*/) {
    },
    getBiomeName: function (x/*int*/, z/*int*/) {
    },
    getBrightness: function (x/*int*/, y/*int*/, z/*int*/) {
    },
    getChestSlot: function (x/*int*/, y/*int*/, z/*int*/, slot/*int*/) {
    },
    getChestSlotCount: function (x/*int*/, y/*int*/, z/*int*/, slot/*int*/) {
    },
    getChestSlotCustomName: function (x/*int*/, y/*int*/, z/*int*/, slot/*int*/) {
    },
    getChestSlotData: function (x/*int*/, y/*int*/, z/*int*/, slot/*int*/) {
    },
    getData: function (x/*int*/, y/*int*/, z/*int*/) {
    },
    getDifficulty: function () {
    },
    getFurnaceSlot: function (x/*int*/, y/*int*/, z/*int*/, slot/*int*/) {
    },
    getFurnaceSlotCount: function (x/*int*/, y/*int*/, z/*int*/, slot/*int*/) {
    },
    getFurnaceSlotData: function (x/*int*/, y/*int*/, z/*int*/, slot/*int*/) {
    },
    getGameMode: function () {
    },
    getGrassColor: function (x/*int*/, z/*int*/) {
    },
    getLightningLevel: function () {
    },
    getRainLevel: function () {
    },
    getSignText: function (x/*int*/, y/*int*/, z/*int*/, line/*int*/) {
    },
    getSpawnerEntityType: function (x/*int*/, y/*int*/, z/*int*/) {
    },
    getTile: function (x/*int*/, y/*int*/, z/*int*/) {
    },
    getTime: function () {
    },
    getWorldDir: function () {
    },
    getWorldName: function () {
    },
    playSound: function (x/*double*/, y/*double*/, z/*double*/, sound/*String*/, volume/*double*/, pitch/*double*/) {
    },
    playSoundEnt: function (ent/*Object*/, sound/*String*/, volume/*double*/, pitch/*double*/) {
    },
    setChestSlot: function (x/*int*/, y/*int*/, z/*int*/, slot/*int*/, id/*int*/, damage/*int*/, amount/*int*/) {
    },
    setChestSlotCustomName: function (x/*int*/, y/*int*/, z/*int*/, slot/*int*/, name/*String*/) {
    },
    setDifficulty: function (difficulty/*int*/) {
    },
    setFurnaceSlot: function (x/*int*/, y/*int*/, z/*int*/, slot/*int*/, id/*int*/, damage/*int*/, amount/*int*/) {
    },
    setGameMode: function (type/*int*/) {
    },
    setGrassColor: function (x/*int*/, z/*int*/, color/*int*/) {
    },
    setLightningLevel: function (val/*double*/) {
    },
    setNightMode: function (isNight/*boolean*/) {
    },
    setRainLevel: function (val/*double*/) {
    },
    setSignText: function (x/*int*/, y/*int*/, z/*int*/, line/*int*/, newText/*String*/) {
    },
    setSpawn: function (x/*int*/, y/*int*/, z/*int*/) {
    },
    setSpawnerEntityType: function (x/*int*/, y/*int*/, z/*int*/, type/*int*/) {
    },
    setTile: function (x/*int*/, y/*int*/, z/*int*/, id/*int*/, damage/*int*/) {
    },
    setTime: function (time/*int*/) {
    },
    spawnChicken: function (x/*double*/, y/*double*/, z/*double*/, tex/*String*/) {
    },
    spawnCow: function (x/*double*/, y/*double*/, z/*double*/, tex/*String*/) {
    },
    spawnMob: function (x/*double*/, y/*double*/, z/*double*/, typeId/*int*/, tex/*String*/) {
    },
};

const Player = {
    addExp: function (value/*int*/) {
    },
    addItemCreativeInv: function (id/*int*/, count/*int*/, damage/*int*/) {
    },
    addItemInventory: function (id/*int*/, amount/*int*/, damage/*int*/) {
    },
    canFly: function () {
    },
    clearInventorySlot: function (slot/*int*/) {
    },
    enchant: function (slot/*int*/, enchantment/*int*/, level/*int*/) {
    },
    getArmorSlot: function (slot/*int*/) {
    },
    getArmorSlotDamage: function (slot/*int*/) {
    },
    getCarriedItem: function () {
    },
    getCarriedItemCount: function () {
    },
    getCarriedItemData: function () {
    },
    getDimension: function () {
    },
    getEnchantments: function (slot/*int*/) {
    },
    getEntity: function () {
    },
    getExhaustion: function () {
    },
    getExp: function () {
    },
    getHunger: function () {
    },
    getInventorySlot: function (slot/*int*/) {
    },
    getInventorySlotCount: function (slot/*int*/) {
    },
    getInventorySlotData: function (slot/*int*/) {
    },
    getItemCustomName: function (slot/*int*/) {
    },
    getLevel: function () {
    },
    getName: function (ent/*Object*/) {
    },
    getPointedBlockData: function () {
    },
    getPointedBlockId: function () {
    },
    getPointedBlockSide: function () {
    },
    getPointedBlockX: function () {
    },
    getPointedBlockY: function () {
    },
    getPointedBlockZ: function () {
    },
    getPointedEntity: function () {
    },
    getPointedVecX: function () {
    },
    getPointedVecY: function () {
    },
    getPointedVecZ: function () {
    },
    getSaturation: function () {
    },
    getScore: function () {
    },
    getSelectedSlotId: function () {
    },
    getX: function () {
    },
    getY: function () {
    },
    getZ: function () {
    },
    isFlying: function () {
    },
    isPlayer: function (ent/*Object*/) {
    },
    setArmorSlot: function (slot/*int*/, id/*int*/, damage/*int*/) {
    },
    setCanFly: function (val/*boolean*/) {
    },
    setExhaustion: function (value/*double*/) {
    },
    setExp: function (value/*double*/) {
    },
    setFlying: function (val/*boolean*/) {
    },
    setHealth: function (value/*int*/) {
    },
    setHunger: function (value/*double*/) {
    },
    setInventorySlot: function (slot/*int*/, itemId/*int*/, count/*int*/, damage/*int*/) {
    },
    setItemCustomName: function (slot/*int*/, name/*String*/) {
    },
    setLevel: function (value/*int*/) {
    },
    setSaturation: function (value/*double*/) {
    },
    setSelectedSlotId: function (slot/*int*/) {
    },
};

const Entity = {
    addEffect: function (entity/*Object*/, potionId/*int*/, duration/*int*/, amplifier/*int*/, isAmbient/*boolean*/, showParticles/*boolean*/) {
    },
    getAll: function () {
    },
    getAnimalAge: function (animal/*Object*/) {
    },
    getArmor: function (entity/*Object*/, slot/*int*/) {
    },
    getArmorCustomName: function (entity/*Object*/, slot/*int*/) {
    },
    getArmorDamage: function (entity/*Object*/, slot/*int*/) {
    },
    getEntityTypeId: function (ent/*Object*/) {
    },
    getExtraData: function (entity/*Object*/, key/*String*/) {
    },
    getHealth: function (ent/*Object*/) {
    },
    getItemEntityCount: function (entity/*Object*/) {
    },
    getItemEntityData: function (entity/*Object*/) {
    },
    getItemEntityId: function (entity/*Object*/) {
    },
    getMaxHealth: function (entity/*Object*/) {
    },
    getMobSkin: function (entity/*Object*/) {
    },
    getNameTag: function (entity/*Object*/) {
    },
    getPitch: function (ent/*Object*/) {
    },
    getRenderType: function (entity/*Object*/) {
    },
    getRider: function (entity/*Object*/) {
    },
    getRiding: function (entity/*Object*/) {
    },
    getTarget: function (entity/*Object*/) {
    },
    getUniqueId: function (entity/*Object*/) {
    },
    getVelX: function (ent/*Object*/) {
    },
    getVelY: function (ent/*Object*/) {
    },
    getVelZ: function (ent/*Object*/) {
    },
    getX: function (ent/*Object*/) {
    },
    getY: function (ent/*Object*/) {
    },
    getYaw: function (ent/*Object*/) {
    },
    getZ: function (ent/*Object*/) {
    },
    isSneaking: function (ent/*Object*/) {
    },
    remove: function (ent/*Object*/) {
    },
    removeAllEffects: function (entity/*Object*/) {
    },
    removeEffect: function (entity/*Object*/, potionId/*int*/) {
    },
    rideAnimal: function (rider/*Object*/, mount/*Object*/) {
    },
    setAnimalAge: function (animal/*Object*/, age/*int*/) {
    },
    setArmor: function (entity/*Object*/, slot/*int*/, id/*int*/, damage/*int*/) {
    },
    setArmorCustomName: function (entity/*Object*/, slot/*int*/, name/*String*/) {
    },
    setCape: function (entity/*Object*/, location/*String*/) {
    },
    setCarriedItem: function (ent/*Object*/, id/*int*/, count/*int*/, damage/*int*/) {
    },
    setCollisionSize: function (entity/*Object*/, a/*double*/, b/*double*/) {
    },
    setExtraData: function (entity/*Object*/, key/*String*/, value/*String*/) {
    },
    setFireTicks: function (ent/*Object*/, howLong/*int*/) {
    },
    setHealth: function (ent/*Object*/, halfhearts/*int*/) {
    },
    setImmobile: function (entity/*Object*/, immobile/*boolean*/) {
    },
    setMaxHealth: function (ent/*Object*/, halfhearts/*int*/) {
    },
    setMobSkin: function (entity/*Object*/, tex/*String*/) {
    },
    setNameTag: function (entity/*Object*/, name/*String*/) {
    },
    setPosition: function (ent/*Object*/, x/*double*/, y/*double*/, z/*double*/) {
    },
    setPositionRelative: function (ent/*Object*/, x/*double*/, y/*double*/, z/*double*/) {
    },
    setRenderType: function (ent/*Object*/, renderType/*Object*/) {
    },
    setRot: function (ent/*Object*/, yaw/*double*/, pitch/*double*/) {
    },
    setSneaking: function (ent/*Object*/, doIt/*boolean*/) {
    },
    setTarget: function (entity/*Object*/, target/*Object*/) {
    },
    setVelX: function (ent/*Object*/, amount/*double*/) {
    },
    setVelY: function (ent/*Object*/, amount/*double*/) {
    },
    setVelZ: function (ent/*Object*/, amount/*double*/) {
    },
    spawnMob: function (x/*double*/, y/*double*/, z/*double*/, typeId/*int*/, tex/*String*/) {
    },
};

const Item = {
    addCraftRecipe: function (id/*int*/, count/*int*/, damage/*int*/, ingredientsScriptable/*Scriptable*/) {
    },
    addFurnaceRecipe: function (inputId/*int*/, outputId/*int*/, outputDamage/*int*/) {
    },
    addShapedRecipe: function (id/*int*/, count/*int*/, damage/*int*/, shape/*Scriptable*/, ingredients/*Scriptable*/) {
    },
    defineArmor: function (id/*int*/, iconName/*String*/, iconIndex/*int*/, name/*String*/, texture/*String*/, damageReduceAmount/*int*/, maxDamage/*int*/, armorType/*int*/) {
    },
    getMaxDamage: function (id/*int*/) {
    },
    getMaxStackSize: function (id/*int*/) {
    },
    getName: function (id/*int*/, damage/*int*/, raw/*boolean*/) {
    },
    getTextureCoords: function (id/*int*/, damage/*int*/) {
    },
    getUseAnimation: function (id/*int*/) {
    },
    internalNameToId: function (name/*String*/) {
    },
    isValidItem: function (id/*int*/) {
    },
    setCategory: function (id/*int*/, category/*int*/) {
    },
    setEnchantType: function (id/*int*/, flag/*int*/, value/*int*/) {
    },
    setHandEquipped: function (id/*int*/, yep/*boolean*/) {
    },
    setMaxDamage: function (id/*int*/, maxDamage/*int*/) {
    },
    setProperties: function (id/*int*/, props/*Object*/) {
    },
    setStackedByData: function (id/*int*/, stacked/*boolean*/) {
    },
    setUseAnimation: function (id/*int*/, animation/*int*/) {
    },
    translatedNameToId: function (name/*String*/) {
    },
};




const Block = {
    defineBlock: function (blockId/*int*/, name/*String*/, textures/*Object*/, materialSourceIdSrc/*Object*/, opaqueSrc/*Object*/, renderTypeSrc/*Object*/) {
    },
    defineLiquidBlock: function (blockId/*int*/, name/*String*/, textures/*Object*/, materialSourceIdSrc/*Object*/) {
    },
    getAllBlockIds: function () {
    },
    getDestroyTime: function (id/*int*/, damage/*int*/) {
    },
    getFriction: function (id/*int*/, damage/*int*/) {
    },
    getRenderType: function (blockId/*int*/) {
    },
    getTextureCoords: function (id/*int*/, damage/*int*/, side/*int*/) {
    },
    setColor: function (blockId/*int*/, colorArray/*Scriptable*/) {
    },
    setDestroyTime: function (blockId/*int*/, time/*double*/) {
    },
    setExplosionResistance: function (blockId/*int*/, resist/*double*/) {
    },
    setFriction: function (id/*int*/, friction/*double*/) {
    },
    setLightLevel: function (blockId/*int*/, lightLevel/*int*/) {
    },
    setLightOpacity: function (blockId/*int*/, lightLevel/*int*/) {
    },
    setRedstoneConsumer: function (id/*int*/, enabled/*boolean*/) {
    },
    setRenderLayer: function (blockId/*int*/, layer/*int*/) {
    },
    setRenderType: function (blockId/*int*/, renderType/*int*/) {
    },
    setShape: function (blockId/*int*/, v1/*double*/, v2/*double*/, v3/*double*/, v4/*double*/, v5/*double*/, v6/*double*/, damage/*int*/) {
    },
};

const Server = {
    getAddress: function () {
    },
    getAllPlayerNames: function () {
    },
    getAllPlayers: function () {
    },
    getPort: function () {
    },
    joinServer: function (serverAddress/*String*/, port/*int*/) {
    },
    sendChat: function (message/*String*/) {
    },
};

// can use preventDefault()
function attackHook(attacker, victim) {
}

// can use preventDefault()
function leaveGame(thatotherboolean) {
}

// can use preventDefault()
function chatHook(str) {
}

// can use preventDefault()
function continueDestroyBlock(x, y, z, side, progress) {
}

// can use preventDefault()
function destroyBlock(x, y, z, side) {
}

function projectileHitEntityHook(projectile, targetEntity) {
}

function eatHook(hearts, saturationRatio) {
}

function entityAddedHook(entity) {
}

// can use preventDefault()
function entityHurtHook(attacker, victim, halfhearts) {
}

function entityRemovedHook(entity) {
}

// can use preventDefault()
function explodeHook(entity, x, y, z, power, onFire) {
}

// can use preventDefault()
function serverMessageReceiveHook(str) {
}

// can use preventDefault()
function deathHook(attacker, victim) {
}

// can use preventDefault()
function playerAddExpHook(player, experienceAdded) {
}

// can use preventDefault()
function playerExpLevelChangeHook(player, levelsAdded) {
}

function redstoneUpdateHook(x, y, z, newCurrent, someBooleanIDontKnow, blockId, blockData) {
}

function newLevel() {
}

// can use preventDefault()
function startDestroyBlock(x, y, z, side) {
}

function projectileHitBlockHook(projectile, blockX, blockY, blockZ, side) {
}

function modTick() {
}

// can use preventDefault()
function useItem(x, y, z, itemid, blockid, side, itemDamage, blockDamage) {
}

const ChatColor = {
    AQUA: '§b',
    BEGIN: '§',
    BLACK: '§0',
    BLUE: '§9',
    BOLD: '§l',
    DARK_AQUA: '§3',
    DARK_BLUE: '§1',
    DARK_GRAY: '§8',
    DARK_GREEN: '§2',
    DARK_PURPLE: '§5',
    DARK_RED: '§4',
    GOLD: '§6',
    GRAY: '§7',
    GREEN: '§a',
    LIGHT_PURPLE: '§d',
    RED: '§c',
    RESET: '§r',
    WHITE: '§f',
    YELLOW: '§e',
};
const ItemCategory = {
    DECORATION: 2,
    FOOD: 4,
    INTERNAL: 0,
    MATERIAL: 1,
    TOOL: 3,
}
const ParticleType = {
    angryVillager: 30,
    bubble: 1,
    cloud: 4,
    crit: 2,
    dripLava: 22,
    dripWater: 21,
    enchantmenttable: 32,
    fallingDust: 23,
    flame: 6,
    happyVillager: 31,
    heart: 15,
    hugeexplosion: 13,
    hugeexplosionSeed: 12,
    ink: 27,
    itemBreak: 10,
    largeexplode: 5,
    lava: 7,
    mobFlame: 14,
    note: 34,
    portal: 18,
    rainSplash: 29,
    redstone: 9,
    slime: 28,
    smoke: 3,
    smoke2: 8,
    snowballpoof: 11,
    spell: 24,
    spell2: 25,
    spell3: 26,
    splash: 19,
    suspendedTown: 17,
    terrain: 16,
    waterWake: 20,
};
const EntityType = {
    ARROW: 80,
    BAT: 19,
    BLAZE: 43,
    BOAT: 90,
    CAVE_SPIDER: 40,
    CHICKEN: 10,
    COW: 11,
    CREEPER: 33,
    EGG: 82,
    ENDERMAN: 38,
    EXPERIENCE_ORB: 69,
    EXPERIENCE_POTION: 68,
    FALLING_BLOCK: 66,
    FIREBALL: 85,
    FISHING_HOOK: 77,
    GHAST: 41,
    IRON_GOLEM: 20,
    ITEM: 64,
    LAVA_SLIME: 42,
    LIGHTNING_BOLT: 93,
    MINECART: 84,
    MUSHROOM_COW: 16,
    OCELOT: 22,
    PAINTING: 83,
    PIG: 12,
    PIG_ZOMBIE: 36,
    PLAYER: 63,
    PRIMED_TNT: 65,
    RABBIT: 18,
    SHEEP: 13,
    SILVERFISH: 39,
    SKELETON: 34,
    SLIME: 37,
    SMALL_FIREBALL: 94,
    SNOWBALL: 81,
    SNOW_GOLEM: 21,
    SPIDER: 35,
    SQUID: 17,
    THROWN_POTION: 86,
    VILLAGER: 15,
    WOLF: 14,
    ZOMBIE: 32,
    ZOMBIE_VILLAGER: 44,
};
const EntityRenderType = {
    arrow: 25,
    bat: 10,
    blaze: 18,
    boat: 35,
    camera: 48,
    chicken: 5,
    cow: 6,
    creeper: 22,
    egg: 28,
    enderman: 24,
    expPotion: 45,
    experienceOrb: 40,
    fallingTile: 33,
    fireball: 37,
    fishHook: 26,
    ghast: 17,
    human: 3,
    ironGolem: 42,
    item: 4,
    lavaSlime: 16,
    lightningBolt: 41,
    map: 50,
    minecart: 34,
    mushroomCow: 7,
    ocelot: 43,
    painting: 32,
    pig: 8,
    player: 27,
    rabbit: 46,
    sheep: 9,
    silverfish: 21,
    skeleton: 19,
    slime: 23,
    smallFireball: 38,
    snowGolem: 44,
    snowball: 29,
    spider: 20,
    squid: 36,
    thrownPotion: 31,
    tnt: 2,
    unknownItem: 30,
    villager: 12,
    villagerZombie: 39,
    witch: 47,
    wolf: 11,
    zombie: 14,
    zombiePigman: 15,
};
const ArmorType = {
    boots: 3,
    chestplate: 1,
    helmet: 0,
    leggings: 2,
};
const MobEffect = {
    absorption: 22,
    blindness: 15,
    confusion: 9,
    damageBoost: 5,
    damageResistance: 11,
    digSlowdown: 4,
    digSpeed: 3,
    fireResistance: 12,
    harm: 7,
    heal: 6,
    healthBoost: 21,
    hunger: 17,
    invisibility: 14,
    jump: 8,
    movementSlowdown: 2,
    movementSpeed: 1,
    nightVision: 16,
    poison: 19,
    regeneration: 10,
    saturation: 23,
    waterBreathing: 13,
    weakness: 18,
    wither: 20,
};
const DimensionId = {
    NETHER: 1,
    NORMAL: 0,
};
const BlockFace = {
    DOWN: 0,
    EAST: 5,
    NORTH: 2,
    SOUTH: 3,
    UP: 1,
    WEST: 4,
};
const UseAnimation = {
    bow: 4,
    normal: 0,
};
const Enchantment = {
    AQUA_AFFINITY: 7,
    BANE_OF_ARTHROPODS: 11,
    BLAST_PROTECTION: 3,
    DEPTH_STRIDER: 8,
    EFFICIENCY: 15,
    FEATHER_FALLING: 2,
    FIRE_ASPECT: 13,
    FIRE_PROTECTION: 1,
    FLAME: 21,
    FORTUNE: 18,
    INFINITY: 22,
    KNOCKBACK: 12,
    LOOTING: 14,
    LUCK_OF_THE_SEA: 23,
    LURE: 24,
    POWER: 19,
    PROJECTILE_PROTECTION: 4,
    PROTECTION: 0,
    PUNCH: 20,
    RESPIRATION: 6,
    SHARPNESS: 9,
    SILK_TOUCH: 16,
    SMITE: 10,
    THORNS: 5,
    UNBREAKING: 17,
}
const EnchantType = {
    all: 16383,
    axe: 512,
    book: 16383,
    bow: 32,
    fishingRod: 4096,
    flintAndSteel: 256,
    hoe: 64,
    pickaxe: 1024,
    shears: 128,
    shovel: 2048,
    weapon: 16,
}
const BlockRenderLayer = {
    alpha: 4099,
    alpha_seasons: 5,
    alpha_single_side: 4,
    blend: 6,
    doubleside: 2,
    far: 8,
    opaque: 0,
    opaque_seasons: 1,
    seasons_far: 9,
    seasons_far_alpha: 10,
    water: 7,
}
