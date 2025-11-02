Queues Management Application – Java, Multithreading, Synchronization, GUI

This project implements a **multi-threaded simulation system** that manages multiple client queues in parallel.
The main goal is to optimize client waiting time through efficient task scheduling and synchronized access to shared resources.

The project demonstrates a solid understanding of **concurrent programming**, **thread synchronization**, and **event-driven GUI design** using **Java Swing**.

The simulation models a service management scenario where clients arrive randomly, are distributed to queues, and are served concurrently.

Each queue is represented by a **dedicated thread**, and the simulation dynamically balances the workload between them.

Key functionalities include:
- Parallel queue management, one thread per queue
- Real-time GUI visualization of queues and client flow
- Dynamic client distribution based on shortest waiting time
- Statistical computation of average waiting and service times
- Logging and performance reporting

Technologies Used
- Java 17
- Swing for GUI
- Multithreading
-Synchronization 
- Collections Framework
- OOP principles 

Simulation Logic
The application generates a configurable number of **clients**, each with arrival time and service time

Graphical User Interface

Swing-based GUI allows setting:
  - Number of queues
  - Number of clients
  - Arrival and service time intervals
  - Simulation speed
  Displays each queue and the clients in real time
  Shows computed statistics:
  - Average waiting time
  - Average service time
  - Peak hour (highest load interval)

