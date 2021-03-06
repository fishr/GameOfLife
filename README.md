# GameOfLife
16.35 Project


##Project Notes
###Simulator class
	Time steps, has exclusive lock on it
	Update screen
	Set number of each agent thread running
	
###Grid class
	origin is top left
	0 indexing
	Holds tiles
	Has the actual grid
	Does the repaint op since it has all the info
	
###Tile class
	x, y
	ID = y*maxX + x
	Color type (enum)
	On/off
	Decay'edness to black or white
	neighbors
	
###Agent superclass
	Break circular wait for these, this is done by 
	Runs every time step
		Grabs non-exclusive lock on timestep
		Some probability of affecting the board
	Holds instance of grid
	Locks onto tiles, modifies them once all are gotten
	Agents read locked tiles into temp buffer, then immediately write changes to tiles
	Can be temporary (run once for keyboard) or permanent (simulator started agents)
	
	Types of agents:
		Blinker Generator - Blue
		Tile flipper - Red
		Beacon generator - Purple
		Glider generator - Green
		Default, which performs the standard GoL update laws to the entire grid, decays colors, and uses democracy for color selection
		
###KeyboardWatcher class
	Listens for keypresses
	Spawns agent thread with temporary condition

###Shared resources (lock object)
	Time (Sim)
	Tile onOff (Tile)
	Tile color (Tile)
	Tile decay (Tile)
