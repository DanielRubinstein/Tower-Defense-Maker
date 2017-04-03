Project Plan

Prompt: *Write your project plan in a single file, PLAN.md, that describes the program modules each team member plans to take primary and secondary responsibility for and a high-level plan of how the team will complete the program. Specifically, each person should take responsibility for specific features and use cases they intend to work on during the first sprint (core features) and during the second sprint (extensions).*

Engine Team:
Daniel- Behavior

Christian- Engines/EngineController/Attributes

Alex- Engines/EngineController/Attributes


Environment Team:
Derek-Environment/State/Tile

Riley-Structure/Behavior

Juan- Mode/Stage Progression


View/Skeleton Team:
Miguel - Sidebars, status indicators, mode indicator
* Specifically, Miguel will take primary responsibility for use cases 2 and 4 (regarding the HUD and visual display when user is trying to drop a tower or some other objects). He will take secondary responsibility for use case 5 (involing switching from Player and God mode) and will work with Tim on the back-end side of saving/loading (with the appropriate team working on the Data module).

Tim - settings menu, game over presentation, play button, pause button
* Specifically, Tim will take primary responsbility for use cases 3&8 (pausing, unpausing/playing the game) and the use cases involving the settings menu (9, 10). The use cases involving saving and loading will be more of a joint effort, but Tim will take primary responsibility for these buttons and the user interaction, and Tim and Miguel will both work on working with the appropriate Data module. Tim will take secondary responsibility for the positive and negative feedback between back end and user when trying to change something on screen (like adding a new object).


During the second sprint, our plans are likely subject to changing depending on what aspects we find are most valuable to the game's experience after the first sprint, but we plan on integrating some sort of social center. In this extension, Tim plans on taking primary responsibility for connecting to the social database (perhaps even connecting to Facebook or some other social network) and keeping track of friends, etc. while Miguel plans on taking primary responsibility for the visual aspects of this (like an avatar) and communication (via a private messagebox, for instance). 

High-Level Completion Plan:
Basic hierarchy and interfaces are written first. When people begin to finish their parts, they will swap over to Data or to other teams that are lagging behind. No extensions will begin until core functionality is working on all basic modules. Biweekly team meetings will allow us to stay on track with completing basic implementation in two weeks, since it will be apparent then which parts are behind schedule.
We are planning to build our special extension, live editing, in from the start, so this will force us to design the basic implementation in a more flexible way, but pay off in the third sprint by saving time for optimizing.
