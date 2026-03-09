 import java.io.*;
import java.util.*;

public class CollectionManager {
    // TODO: Implement your CollectionManager here! 
    public PokedexNode overallRoot;

    public CollectionManager() {
        overallRoot = null;
    }
    
    public CollectionManager(Scanner input) {
        if(input == null) {
            throw new IllegalArgumentException();
        }
        overallRoot = populateTree(input);
        if(overallRoot == null) {
            throw new IllegalStateException();
        }
    }

    private PokedexNode populateTree(Scanner input) {
        if(!input.hasNextLine()) {
            return null;
        }
        String nextLine = input.nextLine();

        if(nextLine.startsWith("Pokemon level: ")){
            int level = Integer.parseInt(nextLine.substring("Pokemon level: ".length()));
            String name = input.nextLine().substring("Pokemon name: ".length());
            nextLine = input.nextLine();
            nextLine = nextLine.substring("Pokemon abilities: [".length(),nextLine.lastIndexOf("]"));
            List<String> abilities = new ArrayList<>();
            String[] parts = nextLine.split(",");
            for(String ability : parts) {
                abilities.add(ability.trim());
            }
            nextLine = input.nextLine();
            nextLine = nextLine.substring("Pokemon type(s): [".length(),nextLine.lastIndexOf("]"));
            List<String> types = new ArrayList<>();
            parts = nextLine.split(",");
            for(String type : parts) {
                types.add(type.trim());
            }

            String species = input.nextLine().substring("Pokemon species: ".length());


            PokedexNode rootLeft = populateTree(input);
            PokedexNode rootRight = populateTree(input);

            Pokedex entry = new Pokedex(level, name, abilities, types, species);
            PokedexNode node = new PokedexNode(entry);

            node.left = rootLeft;
            node.right = rootRight;

            return node;
        } else {
            return null;
        }       
    }

    public void add(Pokedex entry) {
        overallRoot = add(entry, overallRoot);
    }

    private PokedexNode add(Pokedex entry, PokedexNode node) {
        if(node == null) {
            return new PokedexNode(entry);
        } else if (entry.compareTo(node.data) < 0) {
            node.left = add(entry, node.left);
        } else if (entry.compareTo(node.data) > 0) {
            node.right = add(entry, node.right);
        }
        return node;
    }

    public boolean contains(Pokedex value) {
        return contains(overallRoot, value);
    }
    
    private boolean contains(PokedexNode curr, Pokedex value) {
        if (curr == null) {
            return false;
        } else {
            int compare = value.compareTo(curr.data);
            if (compare == 0) {
                // The values are equal! Value found.
                return true;
            } else if (compare < 0) {
                // The value is less, search the left subtree.
                return contains(curr.left, value);
            } else {
                // The value is greater, search the right subtree.
                return contains(curr.right, value);
            }
        }
    }

    @Override
    public String toString() {
        return printTree(overallRoot);
    }

    private String printTree(PokedexNode node) {
        if(node != null) {
            return node.data.toString()+ printTree(node.left) + 
                    printTree(node.right);
        } else{
            return "";
        }
    }

    public void save(PrintStream output) {
        save(output, overallRoot);
    }

    private void save(PrintStream output, PokedexNode node) {
        if(node != null) {
            output.print(node.data.toString());
            save(output, node.left);
            save(output, node.right);
        }
    }

    public List<Pokedex> filter(String type) {
        List<Pokedex> result = new ArrayList<>();
        filter(type, result, overallRoot);
        return result;
    }

    private void filter(String type, List<Pokedex> result, PokedexNode node) {
        if(node != null) {
            if(node.data.getType().contains(type)) {
                result.add(node.data);
            } 
            filter(type,result,node.left);
            filter(type,result,node.right);            
        }
    }


     private static class PokedexNode {
        public Pokedex data;
        public PokedexNode left;
        public PokedexNode right;

        public PokedexNode(Pokedex data) {
            this.data = data;
        }
    }
}
