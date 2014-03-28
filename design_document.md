Data
----
Our game data will be saved using JSON, in a way that is easy to serialize,
deserialize, and be read by humans. A game file will be represented as a hash
containing keys representing object types and values representing the objects of
that type that exist in the game.

Example
```json
{
  "Map": [
    {
      "name": "Level 1",
      "points": "40",
      "structureFile": "level1.txt",
      "enemies": [
        {
          "name": "Goomba",
          "health": "100"
        }
      ]
    }
  ],
  "Player": [
    {
      "name": "Mario",
      "health": "1000",
      "favoriteFood": "mushrooms"
    }
  ]
}
```

Each class that is able to be serialized in this way will implement the
`JsonSerializable` interface, which will require these methods to be
implemented.
- `public Map<String, String> serialize()`
  - Return a Map of attributes and their values that are needed to recreate it
    again.
- `public JsonSerializable deserialize(Map<String, String>)`
  - Return an object that is recreated from the map of attributes

There will be a `GameData` class, which will handle the reading and writing of
game data. It will have methods
- `public GameData(String filename)`
  - Load and read a file containing the serialized objects
- `public void add(String type, Map<String, String> attributes)`
  - Add a new object of type `type` with attributes `attributes`
- `public List<Map<String, String>> getObjects(String type)`
  - Returns a list of attribute maps of objects of type `type`
- `public void save()`
  - Save the file
