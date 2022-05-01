# Roguelike

## Usage
Just run:
```shell
./gradlew run
```

Change settings in `Constants` if something wrong with your UI.

## Tests
For tests just run:
```shell
./gradlew test
```

## Description
`*` - main player
### Map
`.` - cell's where player can run

`#` - sell's where player can't run (walls)

### Items
`@` - apple, increases player's hp, can be used only ones

`$` - sword, increases player's damage, can be used many times

`-` - shield, increases player's armor, can be used many times

### Enemies
`P` - passive enemy, just always stays in one place

`A` - aggressive enemy, when he can see player, trys to attack him

`S` - sneaky enemy, when he can see player, trys to run away from him

`C` - confused enemy, when you attack enemy, he becomes confused and begin to walk randomly 

## Control
`H` - open instructions

`M` - open map

`I` - open inventory

`E` - equip item(put it to inventory or put on)

`U` - unequip item (item steel be in inventory)

`D` - drop item (item drops from inventory to field)

To walk on map or navigate threw items in inventory use arrows

