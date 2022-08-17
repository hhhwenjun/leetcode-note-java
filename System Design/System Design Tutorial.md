# System Design Tutorial

This course is divided into three main chapters -

-   **Basics**: This chapter is specially designed for novices to build a strong foundation of System Design fundamentals that are useful in understanding the dynamics of highly scalable systems.
-   **Case Studies**: This chapter aims to model the System Design of some of the most frequently asked interview questions, like designing an e-commerce app.
-   **Problem Solving**: This chapter lets you apply your understanding of the preceding chapters with the help of some practice problems and quizzes.

### **High-Level Design**

#### Structure

*   Overall architecture
    *    How can the system be built such that it takes care of scalability, latency, and other performance requirements
*   Systems and Services
    *   In this part of high-level design, we need to decide how the system will be broken down, what the microservices will be, and their scope.
*   Interaction between Systems
    *   How will the systems interact with each other? 
    *   What protocols will you use? 
    *   Will it be synchronous or asynchronous? 
    *   You need to make these decisions based on the requirements
*   Database(Design database schema)
    *   hat databases will you need? 
    *   What kind of data do you need to store? 
    *   SQL or NoSQL, what will be the database schema? 
*   Examples

#### Expectation

*   Requirements: fulfill requirements, highly scalable
*   Futuristic: open to future improvement, non-restrictive to specific items, modular

<img src="https://leetcode.com/explore/learn/card/Figures/System_Design/Chapter_1/HLD_img1.png" alt="img" style="zoom: 67%;" />

#### Approach

*   Functional requirements
    *   Features
    *   User Journeys
*   Non-functional requirements
    *   Scale
    *   Latency
    *   Consistency

#### Approach Solution

<img src="https://leetcode.com/explore/learn/card/Figures/System_Design/Chapter_1/HLD_img2.png" alt="img" style="zoom:50%;" />

*   Propose a design
    *   This means breaking down the system into multiple components and identifying which microservices are needed, setting up interactions between these microservices, finalizing databases, etc.
*   Talk about trade-offs
    *   What database would be more suitable? Do you need SQL or NoSQL? You keep adjusting your design based on your **design decisions** and tradeoffs until the requirements have been satisfactorily met.
*   Discuss design choices
*   Limitations of the design
    *   If your system does not have a monitoring component, it would be good to mention this at this stage.