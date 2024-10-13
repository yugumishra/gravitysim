# gravitysim
A 3D Gravity Simulator which simulates orbital mechanics made in Java.

For a demonstration video you may look [here](https://youtu.be/4Uca_t-YDrg).

The program is written entirely in Java. You are able to manipulate and create tons of different types of gravitational systems (n-bodies & variable positions, masses, and velocities). You can then press start and view the resulting dynamics. Graphics were done with Java2D. All orbital mechanics/math and 3D projection was done from scratch. As per integration schemes, the system uses euler integration (implicit). Gravity is modeled using Newton's Law of Gravitation (so no relativistic effects). The system is relatively stable for 60 frames per second (despite the basic integration scheme and Newtonian model of gravity). You can model orbiting planets, dual star systems, central mass systems, etc. 
