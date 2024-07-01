---
title: Gameplay
description: An overview on the implemented gameplay functions.
---
<h1>General Gameplay:</h1>
<p>The Game works in (time-based) cycles.
Each cycle the pet will change it's status and therefore will require attention from the player.
For example the hunger of the pet will increase over time, sometimes the pet will get sick and over time the pet will get bored. The player can interact with the pet through defined interactions:</p>
<h2>Interactions:</h2>
<ul>
  <li>
    <h3>Feeding:</h3>
    <p>
    Enables the pet to be fed. when fed the pets hunger will decrease by a certain amount.
    If the hunger gets to 0 the pet will receive some XP, however if the hunger was 0 to begin with nothing will happen.
    </p>
</li>
  <li>
    <h3>Playing:</h3>
    <p>
    Enables the user to play with the pet. When done the pet will increase it's fun-value. 
    If the fun gets to 100 the pet will receive some XP, however if the fun-value was 100 to begin with nothing will happen.
    </p>
</li>
  <li>
<h3>Cleaning:</h3>
<p>cleans the pet and will increase it's cleanliness-value. if the cleanliness reaches 100 the pet will receive XP, however if the cleanliness was 100 to begin with nothing will happen</p>
</li>
  <li><h3>Medicating:</h3>
<p>Gives the pet medicine and increases its health. If the health reaches 100 the pet will receive XP, however if the health was 100 to begin with nothing will happen
</p></li>
</ul> 
<p>The interactions have an impact on the happiness and well-being of the pet, which are the key-attribute sof each pet, indicating it's overall Condition.
If the player manages to reach the maximum of one of these he will be rewarded with a huge XP-increase</p>
<h2>Leveling:</h2>
<p>The Pet has level's to provide some sense of progress towards the player. The level are based on the XP and each level will require more XP than the one before.</p>
<h2>Future gameplay-features:</h2>
<ul>
<li>Dying: a pet will die if the player does not provide for its needs</li>
<li>Statistics: we will provide historical data of the pet, to enable the player to see how his pet has improved over time</li>
<li>And much more....</li></ul>
