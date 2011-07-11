
	* ------- *
	|  JARIO  |
	* ------- *

 -								-
 - 	A terrible, terrible game 	-
 -	  by Oliver Lade (Pie21)	-
 -    http://piemaster.net/		-
 -								-
 
 
 	1. INTRODUCTION
   -----------------
 	
 	Jario is a simple platformer game, essentially a Mario clone, written in Java using the Slick
 game library (http://slick.cokeandcode.com/) and the Artemis entity system framework
 (http://gamadu.com/artemis/).
 
 	More than anything else, Jario is a proof of technology, an exploration of the practicality of
 applying the Artemis framework to a platformer game. It's not being developed as a serious game,
 but Artemis has shown a great deal of promise, and so I'll continue implementing features as I get
 to them (for practice and future re-use).
 	
  - Read more about it at http://piemaster.net/projects/jario/
 
  - Play the Web Start at http://piemaster.net/apps/jario/
 
  - Check out the source code at https://bitbucket.org/piemaster/jario/
 
 
 	2. HOW TO PLAY
   ----------------
 
 	As all the Mario games since forever, the player can run left and right, jump, and occasionally
 shoot fireballs. Jumping on enemies tends to hurt them, and jumping into item boxes from below will
 pop items out of them.
 
 ← & →		- Move
 SPACEBAR 	- Jump
 F			- Shoot
 SHIFT		- Sprint
 CTRL		- Walk
 ESCAPE		- Menu/Exit
 ENTER		- Player
 
 F1			- Debug display
 I			- Temporary invulnerability
 
 
 	3. TODO
   ---------
 	
 - Add breakable blocks
 - [Add invulnerability stars]
 - Add more level geometry
 - Add [level end], more levels and transitions
 - Add more efficient (paged) level scrolling, enemy suspension
 - Implement some kind of collision memory for greater accuracy
 - Fix reset/add New Game option
 - Add high scores
 - So much else...
 
 
 	4. ACKNOWLEDGEMENTS
   ---------------------
   
 Thanks go to:
  - Kevin Glass, author of the fantastic Slick game library.
  - Arni Arent (aka appel) and Tiago Costa (aka Spiegel), authors of the fantastic Artemis
    framework, and particularly appel for all his generous assistance on the forums.
  - All the contributors to the fantastic ecosystem of open-source software that lets people like
    me do what they love as a hobby (Java (sort of), Eclipse, LWJGL, Slick, Artemis, and even GIMP).
    