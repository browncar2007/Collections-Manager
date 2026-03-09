import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.*;

public class Testing {
    private Pokedex makeEntry(int level, String name, String ability,
                              String type, String species) {
        return new Pokedex(level, name, List.of(ability), List.of(type), species);
    }
    @Test
    public void emptyContructorTest() throws FileNotFoundException {
        CollectionManager cm = new CollectionManager();
        assertNull(cm.overallRoot);
        assertFalse(cm.contains(makeEntry(25, "Pikachu", "Static", "Electric", "Mouse Pokemon")));
        assertEquals("", cm.toString());
        assertTrue(cm.filter("Fire").isEmpty());
    }

    @Test
    public void scannerContructorTest() throws FileNotFoundException {
        CollectionManager original = new CollectionManager();
        original.add(makeEntry(50, "Charizard", "Blaze",  "Fire",     "Flame Pokemon"));

        File saved = new File("scannerConstructorFile.txt");
        original.save(new PrintStream(saved));

        CollectionManager reloaded = new CollectionManager(new Scanner(saved));
       assertTrue(reloaded.contains(makeEntry(50, "Charizard", "Blaze",  "Fire",     "Flame Pokemon")),
                   "reloaded tree should contain Charizard");
    }

    @Test
    public void addTest() throws FileNotFoundException {
        CollectionManager cm = new CollectionManager();
        Pokedex charizard = makeEntry(50, "Charizard", "Blaze",      "Fire",     "Flame Pokemon");
        Pokedex pikachu   = makeEntry(25, "Pikachu",   "Static",     "Electric", "Mouse Pokemon");
        Pokedex dragonite = makeEntry(80, "Dragonite", "Multiscale", "Dragon",   "Dragon Pokemon");

        cm.add(charizard);
        cm.add(pikachu);
        cm.add(dragonite);

        // BST ordering: all three should be present
        assertTrue(cm.contains(charizard), "root should be Charizard");
        assertTrue(cm.contains(pikachu),   "left child should be Pikachu");
        assertTrue(cm.contains(dragonite), "right child should be Dragonite");
    }
    @Test
    public void containsTest() throws FileNotFoundException {
        CollectionManager cm = new CollectionManager();
        Pokedex pikachu   = makeEntry(25, "Pikachu",   "Static",     "Electric", "Mouse Pokemon");
        Pokedex dragonite = makeEntry(80, "Dragonite", "Multiscale", "Dragon",   "Dragon Pokemon");

        cm.add(pikachu);

        assertTrue(cm.contains(pikachu),    "should find an entry that was added");
        assertFalse(cm.contains(dragonite), "should not find an entry that was never added");
    }
    @Test
    public void toStringTest() throws FileNotFoundException {
        CollectionManager cm = new CollectionManager();
        assertEquals("", cm.toString(), "empty tree should produce empty string");

        cm.add(makeEntry(50, "Charizard",  "Blaze",      "Fire",     "Flame Pokemon"));
        cm.add(makeEntry(25, "Pikachu",    "Static",     "Electric", "Mouse Pokemon"));
        cm.add(makeEntry(80, "Dragonite",  "Multiscale", "Dragon",   "Dragon Pokemon"));

        String result = cm.toString();

        assertTrue(result.contains("Charizard"), "should contain Charizard");
        assertTrue(result.contains("Pikachu"),   "should contain Pikachu");
        assertTrue(result.contains("Dragonite"), "should contain Dragonite");
    }

    @Test
    public void saveTest() throws FileNotFoundException {
        CollectionManager cm = new CollectionManager();
        Pokedex p = new Pokedex(25, "Pikachu", new ArrayList<>(Arrays.asList("ThunderBolt", "Volt Tackle", "Extreme Speed", "Static")),
                new ArrayList<>(Arrays.asList("Electric")), "Mouse Pokemon");
        cm.add(p);

        File saved = new File("saveFile.txt");
        PrintStream ps = new PrintStream(saved);
        cm.save(ps);

        Scanner input = new Scanner(saved);
        String text = "";

        while (input.hasNextLine()) {
            text += input.nextLine();
        }

        assertTrue(text.contains("Pikachu"), "Saved output should contain Pikachu");
        assertTrue(text.contains("25"), "Saved output should contain level 25");
    }

    @Test
    public void filterTest() throws FileNotFoundException {
        CollectionManager cm = new CollectionManager();
        cm.add(makeEntry(25, "Pikachu",   "Static", "Electric", "Mouse Pokemon"));
        cm.add(makeEntry(50, "Charizard", "Blaze",  "Fire",     "Flame Pokemon"));

        // Dual-type Pokémon should appear in both type filters
        Pokedex dualType = new Pokedex(55, "Charizard-X", List.of("Tough Claws"),
                                       List.of("Fire", "Dragon"), "Flame Pokemon");
        cm.add(dualType);

        List<Pokedex> electric = cm.filter("Electric");
        List<Pokedex> fire     = cm.filter("Fire");
        List<Pokedex> water    = cm.filter("Water");

        assertEquals(1, electric.size(), "should find exactly one Electric type");
        assertEquals("Pikachu", electric.get(0).getName());
        assertEquals(2, fire.size(),     "should find both Fire types");
        assertTrue(water.isEmpty(),      "no Water types should be found");
    }    
}
