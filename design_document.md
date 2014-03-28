OOGASandwich Design Doc

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

Graphical Authoring Environment
----
Design Goals:
--
The goals of the Authoring Environment are as follows: the environment should be modular to allow for isolation of elements and a minimization of dependencies, the environment should be extendable to allow for future elements to be added to a game through the authoring environment, and the environment should be intuitive to use, making it easier for developers to use the environment than to manually code an entire game.

The goal of modularity can be accomplished by separating elements into separate classes, with one centralized class (a level or gameobject class) holding all of the individual components together.  In this way, the only dependency between all of the objects is the commmon level they share - the behavior of one object or force should not rely on the behavior of another object or class.

The goal of extendability can be accomplished through the use of object and force templates to allow for new types of objects and forces to be created from the same source.  The methods used to add the objects or forces to the level of a game will rely on the templates as opposed to the individual classes, meaning there will be minimal code modification required to add in new objects or forces.

The goal of intuitiveness will be more difficult to plan from the start, as how "intuitive" a certain program is depends on feedback from individual users.  However, to set a solid foundation to make the program more intuitive, the program can use an interactive GUI that minimizes user input through text and makes adjustments simple (i.e. using sliders to toggle variables, checkboxes to add/remove forces, the ability to immediately save information in the enviornment for quick-play capabilities).  We believe that such features of our code will make it easier for the designer to use the authoring environment, and thereby make the front-end authoring GUI seem more intuitive.




Primary Classes and Methods for authoring environment module:
-
writeToData: Takes in data from the authoring environment (namely the variables of interest from each object class that represents each object in the environment) and puts them into a format that works with the data module's writing method.  Currently, this format will be a linked list implementation built in the form of a tree: the highest level on the tree will be the game object we create as a starting node, the next level on the tree will be all of the levels that the game has (main levels, minigame levels, boss levels, etc...), and the third level will hold all of the objects and objectives for the particular level that is the parent node.

addLevel: This method will create the framework for a particular level in a game.  Since we are developing a side-scrolling platformer, the level will be one long stretch of game real estate that will hold all of the platforms, obstacles, objects, and other elements added to the game.  This method will create the basic rules needed for the level, and establish the particular node that will later be used in our writeToData tree.

addObject: The method will add the class associated with a particular object to the level in question.  Instead of having a bunch of individual classes, an object template will be established, from which each object (enemy, block, powerup, etc...) will extend from.  This will allow for one method to add items to the game, even though those items may have their own behaviors or movements that differ from other types of object.

addEnvironmentalForce: This method will implement forces on the objects, both computer and player controlled, by adding them in the same object level to the writeToData tree as other objects.  However, because environmental forces will interact with the engine differently than standard objects will, a new method needs to be implemented to add these forces to the game itself. Forces could include gravity, repulsion, wind, etc.... depending on the requirements of the game.

deleteLevel: This method is fairly self-explanatory - it removes a particular level from the writeToData tree, and therefore removes all of the objects, forces, rules, and states attached to it.

deleteObject: This method is fairly self-explanatory - it removes a particular object from a level based on the user input.  It will delete the object either because the user has manually deleted the object, or because the object no longer is part of the environment due to other means (replacement of one object with another, etc...)

deleteEnviornmentalForce: This method is fairly self-explanatory - it removes a particular environmental force from the given level, either because it has been manually deleted or because it has been replaced by another force.

changeVariable: This method is what will be used to change a variable to a particular object.  This method will be called when the user changes the characteristics of a certain object or force.  Since each object or force will ideally have a set of variables tied to it, this single method can change the variable based on what is put in as a parameter and the new value tied to it.

Object template: This template will be available for implementation by all of the objects in the authoring environment.  By using a single template that other objects implement, the authoring environment can simplify addition, subtraction, or changing of objects.

Force template: This template will be available for implementation by all of the forces in the authoring environment.  By using a single template that other objects implement, the authoring environment can simplify addition, subtraction, or changing of objects.

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
				
			
			
ALternative methods considered: 
We considered storing all objects in a separate tree, but decided storing them all as parameters of others would build on the engine's
plan more effectively, and would be easier for us to implement.

We considered several ideas for the GUI, and decided on drag-and-drop with both pop-up and internal menus, as it uses space more efficeintly than text boxes/only tabs.

We considered initializing all objects externally and then copying them into the game. This was deemed unnecessary, and we're creating every object individually.

Team Breakdown:
Brett Fox, Chris Dee, Dennis Lynch
--Roles within GAE as yet undecided, and most likely will be fluid.

Game Engine
----
--

The game engine API will have to manage JGame in a way that is general enough as to be able to play any sidescrolling platformer, but specific enough to be able to make each game significantly different from one another. The way we plan on doing this is through first defining some qualities of all sidescrolling platformers and then based on those universal qualities, making Object representations of those in Java which can be easily used in a JGame game. These objects will also allow for specificity by taking in all of the parameters which describe more about the object. As an example, all platformers use some sort of tiles, ground or platforms for the player to walk/jump on, so we would have a “Platform” class that takes in as a parameter the image that should represent it in the actual game. We could do a similar thing with “Enemy”, as most platformers have at least a couple enemies, but with different behavior, which would be defined as parameters.

The primary class of the engine will be a class called “Game” which represents an entire game created by the user. Game will have all of the basic functionality for things such as the start screen, advancing levels, and taking user input, but will not have hard coded in anything specific to each game. This Game, however, will be able to hold a list of “Level” objects, which is the other primary class of the engine. A level is the highest layer of a game where all of the details are defined. A level is a collection of information and other objects which define how that level will look and act. Each other component of a game that we have defined as being almost universal will have its own classes and objects which are stored by each “Level” if needed. For example, a level object representing level 2 might hold 15 Objects of class “Enemy” which have been passed specific parameters such as starting position and behavior. The level could also hold 200 “Platform” objects with images to represent them and a single “Player” object with its behavior included as parameters when created. The level will also hold its own image that will be the background for the level, as well as a Goal object, which will represent what the player must do to advance from that level to the next. This goal will be checked each frame for completion so that the Game knows when to switch which Level object it is currently using. Basically, the idea here is to represent every part of a typical “sidescrolling platformer” as an object which is flexible enough to be able to be significantly changed as needed based on its parameters.

The main method that will be called on the engine will be the “playGame()” method in the Game class. There aren’t a large variety of public methods in the engine because the GamePlayer group will be constructing the Game and its Levels and other objects based on our specifications and then just running the game.

Example games that we want to support in our engine include normal platform games like Mario, but also gravity-switching games like GravityGuy, and puzzle games like Trine, and simple platform games like Doodle Jump. To do this, we will have separate classes for each part of the game including a forces class and a platform class. These classes will take in many parameters when created including position, image, and movement.

Some sample code:

	Public class Game {
		private List<Level> allLevels;

		doFrame(){
		currentLevel.doFrame();
		}
	}

	Public class Player {
		Player (int x, int y, image){

		}
		doFrame(){
			Inputs.checkKeys();
		}
	}

	Public class Level {
		private List<Enemy> allEnemies;
		private List<Platform> allPlatforms;
		private Player myPlayer;

		doFrame(){
			Forces.doForces();
			For (Enemy E: allEnemies){
				E.act();
			}
			For (Platform P: allPlatforms){
				P.act();
			}
		}
	}

Alternative Design:

We have decided to use many parameters to interact with the data group. Another way to design our program would be to use more of the JGame features. For example, we could have used the JGame level-changing feature of using different states. However, this would have restricted us a lot in terms of having a variable number of levels. Similarly, we could have used the JGame tile features to make the platforms. However, this would restrict the flexibility of our platforms and would make it so we couldn’t do things like have moving platforms.

Game Engine Team:

Ethan Gottlieb: Focusing on interaction with Data team.

Sam Ginsburg: Focusing on interaction with Game Player team.

Game Player:
----
--

Design goals:

Player - Serve as the I/O interface connecting engine with player. Will initialize game from data and allow user to play. Highscores+savestate+outer menu.

Assumptions: All games use same data structure, are locally played. Users have scores of some kind. 

Flexible: Multiple players, Goals (any state can be victory condition), Player - world interactions through multiple input methods, Text/graphics/menu options/stats

Classes and methods:

	Player.java load(Data)
	new() 
	run()
	Parser.java parseData()

Example Code:

	main(){ 
		Data gameData = load(Data newGame); 
		run(); 
	}

Alternatives: Handle data storage/user interactions from Player.

