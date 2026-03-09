# 🗂️ Pokédex Collection Manager

A Java-based Pokédex management system built on a **Binary Search Tree (BST)** data structure. Add, search, filter, save, and reload your Pokémon collection — all from an interactive console interface.

---

## 📋 Table of Contents

- [Features](#features)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [File Format](#file-format)
- [Running Tests](#running-tests)
- [Design Overview](#design-overview)

---

## ✨ Features

- **Add** Pokémon entries to your collection
- **Search** for a specific Pokémon using BST-based lookup
- **Filter** your collection by type (e.g., Fire, Electric, Dragon)
- **Print** all entries in your collection
- **Save** your collection to a file for later use
- **Load** a previously saved collection from file
- Supports **dual-type Pokémon** (e.g., Fire/Dragon)

---

## 🗂️ Project Structure

```
├── Client.java             # Main entry point; interactive console menu
├── CollectionManager.java  # BST-backed collection logic (add, contains, save, filter)
├── Pokedex.java            # Pokémon data model with parsing and comparison logic
└── Testing.java            # JUnit 5 test suite
```

---

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- JUnit 5 (for running tests)

### Compile

```bash
javac Client.java CollectionManager.java Pokedex.java
```

### Run

```bash
java Client
```

---

## 🎮 Usage

When launched, you'll be prompted to either start fresh or load from a file:

```
Welcome to the CSE 123 Collection Manager! To begin, enter your desired mode of operation:

1) Start with an empty collection manager
2) Load collection from file
Enter your choice here:
```

From the main menu, the following commands are available:

| Command    | Description                                      |
|------------|--------------------------------------------------|
| `add`      | Add a new Pokémon to your collection             |
| `contains` | Check if a specific Pokémon is in your collection|
| `print`    | Display all Pokémon in your collection           |
| `save`     | Export your collection to a text file            |
| `creative` | Filter your collection by Pokémon type           |
| `quit`     | Exit the program                                 |

### Example — Adding a Pokémon

```
Pokemon level: 25
Pokemon name: Pikachu
Pokemon abilities number: 1
Pokemon abilities:
Ability 1: Static
Pokemon types number: 1
Pokemon type(s):
Type 1: Electric
Pokemon species: Mouse Pokemon
```

---

## 📄 File Format

Saved collections use a pre-order BST format. Each Pokémon entry looks like this:

```
Pokemon level: 25
Pokemon name: Pikachu
Pokemon abilities: [Static]
Pokemon type(s): [Electric]
Pokemon species: Mouse Pokemon
```

This format ensures the tree structure is fully preserved and can be reloaded correctly.

---

## 🧪 Running Tests

The test suite uses **JUnit 5**. Tests cover all core behaviors:

| Test                    | What it checks                                          |
|-------------------------|---------------------------------------------------------|
| `emptyConstructorTest`  | Empty manager has no entries and correct default state  |
| `scannerConstructorTest`| Save → reload preserves collection correctly            |
| `addTest`               | BST insertion places nodes in correct positions         |
| `containsTest`          | Finds added entries; rejects entries never added        |
| `toStringTest`          | String output includes all Pokémon names                |
| `saveTest`              | Saved file contains correct name and level              |
| `filterTest`            | Filters by type; dual-types appear in multiple filters  |

### Run Tests (with JUnit 5 on classpath)

```bash
javac -cp .:junit-platform-console-standalone.jar Testing.java CollectionManager.java Pokedex.java
java -cp .:junit-platform-console-standalone.jar org.junit.platform.console.ConsoleLauncher --scan-classpath
```

---

## 🏗️ Design Overview

### `CollectionManager`

The core data structure is a **Binary Search Tree** where Pokémon are ordered by:
1. Level (ascending)
2. Name (alphabetical, as a tiebreaker)
3. Number of abilities, number of types, then species name

Key methods:
- `add(Pokedex entry)` — inserts into the BST
- `contains(Pokedex value)` — O(log n) BST search
- `filter(String type)` — full tree traversal, returns all matching types
- `save(PrintStream output)` — pre-order serialization
- `CollectionManager(Scanner input)` — pre-order deserialization

### `Pokedex`

Each entry stores:
- `level` — integer (must be ≥ 0)
- `name` — String
- `abilities` — List of Strings
- `type` — List of Strings (at least one required; supports dual-types)
- `species` — String

Implements `Comparable<Pokedex>` for BST ordering and `equals()` for exact matching.
