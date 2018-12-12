package stallgame.character;

import org.junit.Assert;
import org.junit.Test;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class NonPlayableCharacterTest {

    @Test
    public void getFullName() {
        NonPlayableCharacter npc = new NonPlayableCharacter();
        Assert.assertTrue(null != npc.getFullName());
    }
}