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
    *   What databases will you need? 
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

### Low-Level Design

<img src="/Users/wenjunhan/Library/Application Support/typora-user-images/image-20220821113249954.png" alt="image-20220821113249954" style="zoom:67%;" />

#### Structure

*   Dive deeper into the implemention of the individual system
*   Covers a sub-system
*   Optimisations within the sub-system
*   Sample: Design a parking lot, Uber's pricing system

#### Expection

*   Requirements: 
    *   fulfulls function and non-function requirement, good code structure, working + clean code
    *   Modular, futuristic, non-restrictive
*   Code:
    *   Interfaces
    *   Class diagrams
    *   Entities
    *   Data model
    *   Design patterns
*   Quality:
    *   Working code, pass basic tests
    *   Modular: able to add new features, code consistent when adding new features(old codes works well)
    *   Testable: unit test cases
*   Expected Outcome (Example of Uber's pricing engine)
    *   `getFareEstimate()` which fetches the `basePrice` and `surgePrice` and accordingly returns the total estimated fare.`getBasePrice()` will return the base price based on distance, time, and ride type. If there are too many requests from users for the current number of drivers, we will apply a surge price and increase the fare.
    *   We could use the Factory Design Pattern here and add two factories, `BasePriceCalculatorFactory` for calculating `basePrice` and `SurgePriceCalculatorFactory` for calculating `surgePrice`.

 ![image-20220821113020030](/Users/wenjunhan/Library/Application Support/typora-user-images/image-20220821113020030.png)

#### How to Approach LLD Interview

*   Lock down the requirements, both functional and non-functional. The idea is to limit the scope early on so we don't get lost trying to build too many things.
*   The next step is the code. The first thing to do here will be to define the interfaces.
*   Next are class diagrams, where we define what classes we need, how they will interact, what hierarchy they will follow, etc. 
    *   We will further define our entities and data models, and while doing all this, we need to consider what design pattern will be most suitable.
*   Talking about design patterns, the next step would be to ensure code quality. This means we should have a working code with some basic test cases that are passing for the logic we have written.