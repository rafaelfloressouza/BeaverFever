# BeaverFever ~ CalgaryHacks 2021



## Inspiration

Beaver Fever is designed as a roguelike adventure game heavily based on widespread Canadian stereotypes.

## What it does

There are a few interesting systems implemented in Beaver Fever. The world is procedurally generated using an elaborate algorithm to create an interconnected map. Enemies in the form of hockey players, citizens, and Canadian geese, are then placed in the world to wander around.

The player is tasked with exploring the world and defeating enemies, in order to collect enough monopoly money to go to Jim Hortons.

The game was built to be extremely simple due to the short time period. The arrow keys are the only in-game controls, along with "i" to see the information screen.

## How we built it

The game was written in java, using only basic javaFX to display the game. The world is developed as a two dimensional grid of tiles. There are a few interesting algorithms in place to make everything work correctly.

First, the world has to be generated to be always connected. This is done through a combination of placing random rooms and generating mazes to act as hallways, before recursively removing all dead ends.

Then we needed a system too keep track of line of sight and field of view. After all, what good is a maze if you know where everything is from the start. This is done by tracking an additional grid of tiles in the players memory, that keeps track of all tiles it has seen and stores null otherwise. The game then draws the grid of remembered tiles to the screen.

Finally, we needed some sort of AI for the enemies. This utilizes standard A* pathfinding which calculates the shortest distance to the player. Each time the player is in sight, the enemy will store the players location in memory and move towards it. This way, if the player moves out of sight then the enemy will be able to track them through the maze.

All of the image assets are original and created during the hackathon

## Challenges we ran into

The biggest problem was enabling javaFX for all of our group members. This took several hours and we ran into several bugs with getting everything working. Our use of version control with github also ran into some errors but everything worked out in the end.

## Accomplishments that we're proud of

We built a fully functional game in less than 24 hours that implements several complicated algorithms and systems and meshes them all together in a reasonably satisfying end product. The world generation and pathfinding algorithms were particularly satisfying to get working.

## What we learned

A lot more of git, it was incredibly useful for this project despite the few problems. Additionally, several of our group members were quite rusty in java so everybody was brought up to speed with our various systems.

## What's next for Beaver Fever

There are many things that we can implement that we simply did not have time for.

Audio and a background soundtrack would be pretty easy to implement, we thought that the Canadian National Anthem would be quite funny.

Mouse controls would also be cool, but quite difficult. It would require implementing pathfinding for the player and a way to simulate and build multiple keypresses at once to navigate to selected tiles.

More complicated systems. Right now, you only move in the 4 cardinal directions and bump to attack enemies. Eventually, other systems could be added to make combat more interesting.

Real animations and effects to the screen. Realtime games in java are something we expected would be quite difficult to do, so we stuck with turn based and only repainted the screen on player input. This could be expanded to incorporate other player animations and smoother movement.

## Some Pictures of the Game

![gallery](https://user-images.githubusercontent.com/51538046/108149074-741ff480-70a8-11eb-95e9-7a50804acdf8.jpg)
![1](https://user-images.githubusercontent.com/51538046/108149085-7a15d580-70a8-11eb-834e-5cd34fa85ae6.jpg)
![2](https://user-images.githubusercontent.com/51538046/108149090-7b470280-70a8-11eb-9820-8c3a49316211.jpg)
![3](https://user-images.githubusercontent.com/51538046/108149096-7c782f80-70a8-11eb-8a5b-a27de011e7f1.jpg)
![4](https://user-images.githubusercontent.com/51538046/108149099-7d10c600-70a8-11eb-9dc0-c89097a95fa7.jpg)
![5](https://user-images.githubusercontent.com/51538046/108149101-7da95c80-70a8-11eb-8411-a6f58dbfabfd.jpg)






