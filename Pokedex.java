import java.util.*;
public class Pokedex implements Comparable<Pokedex> {
    private int level;
    private String name;
    private List<String> abilities;
    private final List<String> type;
    private final String species;

    public Pokedex(int level, String name, List<String> abilities, List<String> type,
            String species) {
        if(level < 0) {
            throw new IllegalArgumentException("level cannot be negative");
        }
        if(name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if(abilities == null) {
            throw new IllegalArgumentException("abilities cannot be null");
        }
        if(type == null){
            throw new IllegalArgumentException("type cannot be null");
        }
        if(type.isEmpty()) {
            throw new IllegalArgumentException("Pokemon must have a type");
        }
        if(species == null) {
            throw new IllegalArgumentException("species cannot be null");
        }

        this.level = level;
        this.name = name;
        this.abilities = abilities;
        this.type = type;
        this.species = species;
    }

    public Pokedex(Pokedex entry){
        this(entry.level, entry.name, entry.abilities,
                entry.type, entry.species);
    }

    public List<String> getType() { 
        return new ArrayList<>(type);
    }

    public String getName() {
        return name;
    }
    
    public static Pokedex parse(Scanner input) {
        System.out.print("Pokemon level: ");
        int level = Integer.parseInt(input.nextLine());

        System.out.print("Pokemon name: ");
        String name = input.nextLine();

        System.out.print("Pokemon abilities number: ");
        int num = Integer.parseInt(input.nextLine());
        List<String> abilities = new ArrayList<>();
        System.out.println("Pokemon abilities: ");
        int counter = 1;
        while(counter < num + 1) {
            System.out.print("Ability " + counter + ": ");
            abilities.add(input.nextLine());
            counter++;
        }

        System.out.print("Pokemon types number: ");
        num = Integer.parseInt(input.nextLine());
        List<String> types = new ArrayList<>();
        System.out.println("Pokemon type(s): ");
        counter = 1;
        while(counter < num + 1) {
            System.out.print("Type " + counter + ": ");
            types.add(input.nextLine());
            counter++;
        }

        System.out.print("Pokemon species: ");
        String species = input.nextLine();
        
        return new Pokedex(level, name, abilities, types, species);
    }


    @Override
    public String toString() {
        String result = "";
        result += "Pokemon level: ";
        result += level + "\n";
        result += "Pokemon name: ";
        result += name + "\n";
        result += "Pokemon abilities: ";
        result += abilities + "\n";
        result += "Pokemon type(s): ";
        result += type + "\n";
        result += "Pokemon species: ";
        result += species + "\n";
        result += "\n";
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if(other instanceof Pokedex) {
            Pokedex o = (Pokedex) other;
            if(this.level == o.level && this.name.equals(o.name) && this.abilities.equals(o.abilities)
                                    && this.type.equals(o.type) && this.species.equals(o.species)) {
                return true;
            }
        }
        return false;
    }

    private int hashCode(String value) {
        int h = 0;
        for (int i = 0; i < value.length(); i++) {
            h = 31 * h + value.charAt(i);
        }
        return h;
    }


    public int compareTo(Pokedex other) {
        if(this.level != other.level) {
            return this.level - other.level;
        } else if(!this.name.equals(other.name)) {
            return this.name.compareTo(other.name);
        } else if(this.abilities.size() != other.abilities.size()) {
            return this.abilities.size() - other.abilities.size();
        } else if(this.type.size() != other.type.size()) {
            return this.type.size() - other.type.size();
        } else {
            return this.species.compareTo(other.species);
        }
    }
}
