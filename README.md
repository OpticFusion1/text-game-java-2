# Pure Java 8 text command line game
This is a simple text game, built on pure Java 8 and Maven for the build process. 

This game allows you to:
* Create a new character
* Move through the world
* Fight with creatures and bosses
* Gain experience by actions
* Get level up
* Increase your health points (or heal up) using potions
* Save your progress
* Load saved progress
* Provide your own config.xml file with world objects


# Build the project
To build project you can use the command below in the root of the project:

`./mvnw package`

# Run tests
To run all test of the project you can use this command:

`./mvnw test`

# Run the game
After you have build the project you can run the game using the command:

`java -jar target/textGameJava-0.1.jar`

There is also a possibility to run the game with your own config (rooms, creatures). In order to do that just add an argument with a full path to file. For example:

`java -jar target/textGameJava-0.1.jar /home/acmilanfan/IdeaProjects/textGameJava/target/custom_config.xml`

# Config example

If you want to create your own world, you can use pretty simple xml template. There are a few important things:
* Each room should have a unique id
* Properties order is very important, look at an example below
* To create rooms relationship (possibility to move from one to another), put a room id into *left, right, top* or *up* property
* The *left, right, top*, and *up* properties order is not important


A simple 5 rooms example:

```
<rooms>
     <room>
         <id>1</id>
         <message>1 room</message>
         <left>2</left>
         <right>3</right>
         <up>4</up>
         <down>5</down>
     </room>
     <room>
         <id>2</id>
         <message>2 room</message>
         <creatures>
             <creature>
                 <name>Mob 1</name>
                 <health>50</health>
                 <attack>10</attack>
                 <isBoss>false</isBoss>
             </creature>
             <creature>
                 <name>Mob 1</name>
                 <health>50</health>
                 <attack>10</attack>
                 <isBoss>false</isBoss>
             </creature>
         </creatures>
         <right>1</right>
     </room>
     <room>
         <id>3</id>
         <message>3 room</message>
         <left>1</left>
     </room>
     <room>
         <id>4</id>
         <message>4 room</message>
         <down>1</down>
     </room>
     <room>
         <id>5</id>
         <message>5 room</message>
         <creatures>
             <creature>
                 <name>Big Boss</name>
                 <health>500</health>
                 <attack>50</attack>
                 <isBoss>true</isBoss>
             </creature>
         </creatures>
         <up>1</up>
     </room>
 </rooms>
```

# Possible improvements
* Draw a world map with current character position
* Make menu commands truly related to the world state (for instance, now you could use move while in the battle state)
* Colorize console output
* Implement more game mechanics, for instance, classes, abilities, weapons, inventory, loot, money, stores, achievements, etc.