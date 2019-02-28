package stallgame;

import stallgame.character.PlayableCharacter;

import java.time.Instant;
import java.util.Set;

public class WorldLoop implements Runnable {

    @Override
    public void run() {
        while (!GameServer.worldByUUID.isEmpty()) {
            try {
                GameServer.worldByUUID.entrySet().forEach(entry -> {
                    Set<PlayableCharacter> wrappedNpc = entry.getValue().wrappedNpcs;
                    long epochSecondStart = Instant.now().toEpochMilli();
                    GameServer.gameLoop(entry.getValue(), wrappedNpc);
                    if (GameServer.serverSocketByUuid.containsKey(entry.getKey())) {
                        GameServer.serverSocketByUuid.get(entry.getKey()).sendWorldInstance(entry.getValue());
                    }
                    long epochSecondEnd = Instant.now().toEpochMilli();
                    // Calculate amount of millisec required for one frame.
                    // If game loop execution took less, then wait.
                    long delay = 1000 / World.serverFramesFrequency - (epochSecondEnd - epochSecondStart);
                    if (delay > 0) {
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    entry.getValue().tics += 1;

                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
