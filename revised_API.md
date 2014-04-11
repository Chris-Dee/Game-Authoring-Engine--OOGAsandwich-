OOGASandwich Revised API

Genre: Platform Games

Our Design is split into 4 groups which work together in the following way: The user intereacts with the game authoring environment and the user's choices are saved into a data file by the data group. This data is then parsed and given to the game player which will then use the data to construct a game in the game engine. The game engine will accept the data using parameters and run the game in Jgame.

One of the difficult parts of making platform games is that there are many different types including puzzle and action games. In order to make our project flexible, our design has to be general enough to be able to make all these varieties of platform games, while also making the creation and gameplay experience smooth for the user.


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

There will be a `GameData` class, which will handle the reading and writing of
game data. It will have methods
- `public GameData(String filename)`
  - Load and read a file containing the serialized objects
- `public void addObj(Object obj)`
   - Adds `obj` to be serialized. Its class is determined and it is placed into
     a section corresponding to its class.
- `public List<Object> getObjects(String... classNames)`
  - Returns a list of attribute maps of objects with type in `classNames`
  - `classNames` name(s) of class(es) to retrieve
  - Exception `ClassNotFoundException` is thrown when the class cannot be found.
- `public Map<String, List<Object>> parse()`
  - Reads the file and returns a Map mapping class names to a list of the
    objects of that type.
- `public void write()`
  - Writes the contents of the objMap to a file called `filename`.
- `public void setFileName(String filename)`
  - Sets the name of the associated JSON file to `filename`
- `public String toString()`
  - Returns the JSON representation of the GameData

Graphical Authoring Environment
----
Design Goals:
--
The goals of the Authoring Environment are as follows: the environment should be modular to allow for isolation of elements and a minimization of dependencies, the environment should be extendable to allow for future elements to be added to a game through the authoring environment, and the environment should be intuitive to use, making it easier for developers to use the environment than to manually code an entire game.

The goal of modularity can be accomplished by separating elements into separate classes, with one centralized class (a level or gameobject class) holding all of the individual components together.  In this way, the only dependency between all of the objects is the commmon level they share (and the common characteristics of this level) - the behavior of one object or force should not rely on the behavior of another object or class.

The goal of extendability can be accomplished through the use of object and force templates to allow for new types of objects and forces to be created from the same source.  The methods used to add the objects or forces to the level of a game will rely on the templates as opposed to the individual classes, meaning there will be minimal code modification required to add in new types of objects or forces, or to combine or add events.

The goal of intuitiveness will be more difficult to plan from the start, as how "intuitive" a certain program is depends on feedback from individual users.  However, to set a solid foundation to make the program more intuitive, the program can use an interactive GUI that minimizes user input through text and makes adjustments simple (i.e. using sliders to toggle variables, checkboxes to add/remove forces, the ability to immediately save information in the enviornment for quick-play capabilities).  We believe that such features of our code will make it easier for the designer to use the authoring environment, and thereby make the front-end authoring GUI seem more intuitive. The different data needed to completely define a game object will also be presented in a user-friendly and easy to understand way, not appearing in the wasy it was coded (i.e. collision ID can be names in the interface, as will movement patterns and collision patterns.




Primary Classes and Methods for authoring environment module:
-
writeToData: Takes in data from the authoring environment (namely the variables of interest from each object class that represents each object in the environment) and puts them into a format that works with the data module's writing method.  Currently, this format will be a linked list implementation built in the form of a tree: the highest level on the tree will be the game object we create as a starting node, the next level on the tree will be all of the levels that the game has (main levels, minigame levels, boss levels, etc...), and the third level will hold all of the objects and objectives for the particular level that is the parent node.

addLevel: This method will create the framework for a particular level in a game.  Since we are developing a side-scrolling platformer, the level will be one long stretch of game real estate that will hold all of the platforms, obstacles, objects, and other elements added to the game.  This method will create the basic rules needed for the level, and establish the particular node that will later be used in our writeToData tree. This method also adds a new LevelPanelComponent associated with the created level as a visualization for the Level object, and a way for the user to access a specific LevelEditor.

addObject: The method will add the class associated with a particular object to the level in question.  Instead of having a bunch of individual classes, an object template will be established, and 'modules' will be added to these GmaeObjects in the constructors (i.e. movement patterns, collision patterns).  This will allow all objects in the game to be more extensible and flexible with regards to properties, movement types etc. Ths will also make the game more user-friendly, as the player can decide all traits regarding the objects, without having to rely on certain preset object types that have been defined.

addEnvironmentalForce: This method will implement forces on the objects, both computer and player controlled, by adding them in the same object level to the writeToData tree as other objects.  However, because environmental forces will interact with the engine differently than standard objects will, a new method needs to be implemented to add these forces to the game itself. Forces could include gravity, repulsion, wind, etc.... depending on the requirements of the game.

deleteLevel: This method is fairly self-explanatory - it removes a particular level from the writeToData tree, and therefore removes all of the objects, forces, rules, and states attached to it.

deleteObject: This method is fairly self-explanatory - it removes a particular object from a level based on the user input.  It will delete the object either because the user has manually deleted the object, or because the object no longer is part of the environment due to other means (replacement of one object with another, etc...)

deleteEnviornmentalForce: This method is fairly self-explanatory - it removes a particular environmental force from the given level, either because it has been manually deleted or because it has been replaced by another force.

changeVariable: This set of methods is what will be used to change variables to a particular object.  This method will be called when the user changes the characteristics of a certain object or force.  Since each object or force will ideally have a set of variables tied to it, this single method can change the variable in a GameObject, or a nested class within GameObject based on what is put in as a parameter and the new value tied to it.

changeDefaultBackground: This method is called from the ComboBox in the main front window, and sets the background image of a given level to the selected background. This image is then added to the .tbl if not already in. 

defineImage (one for BG, one for Object Image): Allows user to define image to be used. Either copies image into resources file, or uses full URL. Yet to be decided based on JGame terribleness.

Object template: This template will be available for implementation by all of the objects in the authoring environment.  By using a single template that other objects implement, the authoring environment can simplify addition, subtraction, or changing of objects.

Force template: This template will be available for implementation by all of the forces in the authoring environment.  By using a single template that other objects implement, the authoring environment can simplify addition, subtraction, or changing of objects.

FrontEnd package--Defines the front screen wihich allows for manipulation of the game on a Level scale (background, order etc.), and enables the opening of the LevelEdtiorWindow, which allows manipulation on a per-object basis. This module also contains the packageFactory, a factory to easily make any often used panel types or frequented layouts.

Example Code:
-
	For the authoring enviornment:
		When adding an item/force:
			addObject(JGObject object)/addForce(Force force)
				// Method defines object/force parameters based on GUI
				// input and adds object/force node to game tree
			writeToData(Gameobject node)
				//The object added to the game tree is accessed from node
				//(responsibility of data module), so writing data will
				// require authoring environment to pass node
		When removing an item/force:
			deleteObject(JGObject object)/deleteForce(Force force)
				// Method will delete the object/force from memory and
				// remove reference to object from game object tree
			writeToData(Gameobject node)
				// Updates game tree and writes it to data file, so that
				// if user should choose to hit play the updates are
				// included in the new level
		Changing a variable:
			changeVariable(Object object, objectVariable variable)
			OR
			changeVariable(Force force, forceVariable variable, Int value)
				// Based on type of changeVariable used, the particular
				// object or force that is passed in will have the 
				//	variable
				// associated with that object/force set to the value
				//	given
			writeToData(Gameobject node)
				// Updates the game tree to store the object with the
				// new variable
				
			
			
Alternative methods considered: 
We considered storing all objects in a separate tree, but decided storing them all as parameters of others would build on the engine's
plan more effectively, and would be easier for us to implement.
We also considered, in the obejct panel having every parameter stored as strings, and then updating the object when the object was deselected. However, this created some problems with keeping each image tied to certian attributes, and was unnecessarily complicated to work with.

We considered several ideas for the GUI, and decided on a click based fornt screen, as this was faster and more intuitive than drag and drop, with both pop-up and internal menus, as it uses space more efficeintly than text boxes/only tabs. We also debated making the LevelEditor a grid like most other groups have implemented, but decided on  a real time editor, with a key-based mover, and a 

We considered initializing all objects externally and then copying them into the game. This was deemed unnecessary, and we're creating every object individually.

Team Breakdown:
Brett Fox, Chris Dee, Dennis Lynch
--Roles within GAE as yet undecided, and most likely will be fluid.

Game Engine
----
--

The game engine API has been revised to make the objects more general. Now, every displayed object in the game is a GameObject. This is an example of one of the largest abstractions we found and are using, and the idea here is that basically, there isn't that much of a difference between a goomba that paces and a floating platform that moves back and forth, or even a player controlled object or a powerup object. This may seem counterintuitive at first, but upon further inspection it becomes clear that every object, whether it be a player, enemy, or platform, has many features in common. For example, a platform can move just like an enemy and can also hurt the player with a single parameter changed. By using this form of a GameObject and passing in many parameters to distinguish each type of object, we don't restrict certain types of objects to specific predefined "types".

We also have an UninstantiatedGameObject class which takes in the same parameters as GameObject and stores them, but does not create the actual JGameObject on the screen. The purpose of this is to only instantiate GameObjects when we need them on the screen to avoid wasting memory on GameObjects outside of the current view. This class also helps the data group because they do not need to create the JGameObjects right away and therefore can avoid dealing with JGame. 
Because we are using general GameObjects instead of a hierarchy for each specific type of object, dealing with collisions is not necessarily harder, but does require the usage of unique IDs as well as collision IDs. The collision class that we have therefore creates different "events" for collisions between objects with different collision ID's, but also interacts with unique IDs which tell it which specific objects are colliding from all the objects with some same collision ID. The unique IDs allow us to get around some referencing issues caused by the data group's method of storing data and actually interact with different objects.

The GameObjectAction is what takes care of all movement of GameObjects as well as other actions like shooting bullets from the player. This class contains many general types of movements as well as several predefined "patterns" that objects can follow. This class is passed in necessary parameters from the object using it when that object is constructed, and uses reflection based on the "behavior" String to perform certain movement methods depending on whether the object is a player or AI, for example.

We still use the Game class which represents an entire game created by the user. Game has all of the basic functionality for things such as getting the game from the data file and advancing levels. One of the constructors just takes in a data file and creates the Game from that GSON data file. This Game, holds a list of Level objects, which is the other primary class of the engine.

A level is the highest layer of a game where all of the details are defined. A level is a collection of information and GameObjects which define how that level will look and act. This extra information includes things like a background image and takes care of features like scrolling. For example, a level object representing level 2 might hold 15 GameObjects that act as enemies which have been passed specific parameters such as starting position and behavior. 
Levels also check a list of Goal objects, which will represent what the player must do to advance from that level to the next or to any other possible level which can be advanced to depending on what the player does (for example mario could find a secret pipe, allowing him to skip a level). These goals will be checked each frame for completion so that the Game knows when to switch which Level object it is currently using. Basically, the idea here is to represent every part of a typical sidescrolling platformer as an object which is flexible enough to be able to be significantly changed as needed based on its parameters.

The GamePlayerGui extends JGEngine and deals with all of the front-end parts of the game. It is passed in a Game. The class also deals with user input. Within and between many of our classes, we were able to use reflection in order to increase the flexibility of our code.

We have partly combined the Game Engine group with the Game Player group, shown in the GamePlayerGui class, because we are both have the same general goals of making the backend of games work.

Example games that we want to support in our engine include normal platform games like Mario, but also gravity-switching games like GravityGuy, and puzzle games like Trine, and simple platform games like Doodle Jump.

Some examples of public constructors to be used by GamePlayer/Level-Editor are shown below.

Some sample code:

	Some Sample Constructors:
	
	public Game(String dirPath){	// where you pass in the json path to be converted into a real "Game" object
	}
	
	public GameObject(String name, JGPoint position, int colid, String sprite, Map<Integer, Tuple<String,Integer>> inputMap, boolean screenFollow, double gravity, int uniqueID) {
		super(name, true, position.x, position.y, colid, sprite);
	}
	
	public UninstantiatedGameObject(String name, JGPoint position, int colid, String sprite, Map<Integer, Tuple<String,Integer>> inputMap, boolean screenFollow, double gravity, int uniqueID) {
	}
	
	Example of how we instantiate an uninstantiated GameObject:
	public GameObject instantiate(){
		return new GameObject(objectName, objectPosition, objectColid, objectSprite, objectInputMap, objectFloating, objectScreenFollow, objectID);
	}

	public Level(String levelName, JGPoint size, List<UninstantiatedGameObject> objects, String background) {
	}

	Example of user input handled in GamePlayerGUI:
	for(GameObject obj: currentObjects){
			GameObjectAction move= obj.getMovement();
			Map<Integer, Tuple<String,Integer>> characterMap =obj.getCharMap();
			if(characterMap!=null){
				boolean keyPressed=false;
				for(Integer c : characterMap.keySet()){
					if(getKey(c)){
						keyPressed=true;
						java.lang.reflect.Method method = null;
						try {
							method = move.getClass().getMethod(characterMap.get(c).x, GameObject.class, int.class);
						} catch (SecurityException e) {
						} catch (NoSuchMethodException e) {}	
						try {
							method.invoke(move, obj, characterMap.get(c).y);
						} catch (IllegalArgumentException e) {
						} catch (IllegalAccessException e) {
						} catch (InvocationTargetException e) {}
					}
				}
			}
	}

Alternative Design:

As mentioned above, one of the big decisions we made was deciding to use a general GameObject rather than having a hierarchy of objects like having an Enemy and Platform class. We believe that our design increases flexibility because any type of object can have the same actions as any other type of object. One of the downsides to our design is that each GameObject has a ton of parameters and our code doesn't look as nice without the hierachy.
Another way to design our program would be to use more of the JGame features. For example, we could have used the JGame level-changing feature of using different states. However, this would have restricted us a lot in terms of having a variable number of levels. Similarly, we could have used the JGame tile features to make the platforms. However, this would restrict the flexibility of our platforms and would make it so we couldn't do things like have moving platforms.

Game Engine Team:

Ethan Gottlieb: Focusing on more specific cases like user input, scores, hitpoints, lives, customization options, etc. which are small and seem specific but are used in every platformer.

Sam Ginsburg: Focusing on broader subjects including collisions, movement patterns/types, switching levels, making sure the screen scrolls, etc.

We have worked very closely together including significant amounts of pair programming.

We have also worked closely with the Game Player team.

Game Player:
----
--
Design goals:

Player - Serve as the I/O interface connecting engine with player. Will initialize game from data and allow user to play. Highscores+savestate+outer menu.

Assumptions: All games use same data structure, are locally played. Users have scores of some kind. 

Flexible: Multiple players, Goals (any state can be victory condition), Player - world interactions through multiple input methods, Text/graphics/menu options/stats


The Game Player is an extension or the front end of the game engine. It does the job of actually constructing the games, using the engine’s objects. Both the engine and the data are incorporated and used in the Game Player to actually run and create games.

The GamePlayerGUI class is essentially the game player. It extends JGEngine and contains the methods for instantiating a game. It is a generic game loop built on top of JGame using our data structures and addons. The GamePlayerRunner class instantiates the GamePlayerGUI with a game object.

The GamePlayerGui takes care of keeping track of which level the game is on, instantiating the objects in each level, calling the frame by frame methods to keep the game running, and keeps track of the game state.

Sample Code:

GamePlayerRunner:
new GamePlayerGUI(new ExampleGame());

GamePlayerGUI
public GamePlayerGUI(Game loadedGame){  //Loads the game object passed in by the GAE through JSON data

public void doInputs(){ //Keeps track of user input

public void doLevel() //Per frame checks in the game

public void constructGame(){

private void endLevel()


