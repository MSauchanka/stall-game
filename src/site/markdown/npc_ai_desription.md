# Описание AI для npc

АI основан на выборе доступных Actions и шансе выбора (развесовки) каждой из этих ролей.
В зависимости от текущей Role, npc имеет список доступных Actions и дополнительный Actions.WAIT в каждом случае. 
Развесовка и выбор Actions не наступает, если npc пробыл в текущей Role < npc.MIN_AT_ROLE_TIME.
Если npc.atRole > npc.MIN_AT_ROLE_TIME производится развесовка:

1. Значение npc.atRole распределяется между доступными Actions для текущей Role у npc. Вес Actions.WAIT = 100 - atRole.
2. Если npc.Goal соответствуют инструкции в развесовке, тогда:
    - Половина веса Actions.WAIT распределяется между Actions для текущей npc.Goal  
    - Первый Actions в списке npc.Goal получают 50% от распределяемого веса
    - Остальные 50% делятся между оставшимися Actions из списка для текущего npc.Goal в равных долях 

С учетом того, что npc.atRole увеличивается с каждым tic, вес Actions.WAIT уменьшается.