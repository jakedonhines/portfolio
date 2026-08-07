---
---
@import "{{ site.theme }}";

body {
  background-color: #D6B588
}

<div align="center">
# Jacob Hines Computer Science ePortfolio
### SNHU CS 499 Capstone
  
## Welcome
This ePortfolio showcases my growth throughout the SNHU Computer Science program through a software development project, written narratives, and a professional self-assessment.
The project shown here demonstrates experience with Java, Object-Oriented Programming, Software Engineering and Design, Algorithms and Data Structures, and Security.

## Table of Content

- [Professional Self-Assessment](#professional-self-assessment)
- [Project Introduction](#project-introduction)
- [Software Design and Engineering](#software-design-and-engineering)
- [Algorithms and Data Structures](#algorithms-and-data-structures)
- [Databases](#databases)

# Rescue Animal Project

## Professional Self-Assessment

I began my computer science education in 2017 at Northern Essex Community College (NECC). After earning an associate degree in Computer Science in 2023, I continued my education at Southern New Hampshire University (SNHU) to complete my bachelor's degree. My interest in computers and technology began long before I started college, but my experience with programming was limited. Before attending NECC, I had only taken one programming course in high school, where I used Processing, an introductory language designed to teach programming concepts by having students create digital art and simple games. Since then, my education has given me the opportunity to develop a much broader understanding of computer science and software development.

Throughout the program, I have developed both technical and professional skills that have shaped how I approach software development. I have learned how to break complex problems into manageable components, evaluate different approaches to solving a problem, design software with maintainability in mind, and communicate technical concepts to others. I have also gained experience with the software development lifecycle, object-oriented programming, data structures and algorithms, databases, security, and software architecture. These experiences have allowed me to progress from writing relatively simple programs to designing and implementing larger applications with multiple interconnected components.

One of the areas in which my understanding has grown significantly is software engineering and object-oriented design. Early in my education, I primarily focused on making programs function correctly. As I progressed through the program, I began to place greater emphasis on how programs are structured, maintained, and extended.

I have learned to break larger problems into smaller responsibilities and design classes and components around those responsibilities. This approach results in code that is easier to understand, test, maintain, and modify. My capstone project demonstrates this development through the separation of responsibilities among multiple classes and services. For example, AnimalService manages application-level interactions and user operations, while AnimalSearchService is responsible for retrieving, filtering, and searching animal data. AnimalRepository manages the persistence and retrieval of animal data from the database.

The capstone also allowed me to apply several object-oriented concepts in a larger application, including inheritance, polymorphism, generics, and interfaces. I also incorporated functional programming techniques such as predicates, method references, and functional interfaces. These techniques allowed me to create reusable methods rather than duplicating similar logic throughout the application.

My coursework has also given me a strong foundation in data structures and algorithms. I have worked with structures including arrays, lists, queues, rings, trees, and maps, and have learned to evaluate their strengths, weaknesses, performance characteristics, and appropriate use cases.

One example comes from a project I completed at NECC involving a 20 Questions game. The program used a tree structure in which each node represented either a question or a possible answer. As the computer learned from incorrect guesses, it could modify the tree by adding new questions and answers. This project helped me understand how a data structure can represent relationships between pieces of information and how an algorithm can use that structure to solve a problem.

I applied this knowledge again in my capstone project. The AnimalRepository uses an ArrayList to maintain the collection of animals and a HashMap to provide efficient lookup by animal ID. The AnimalSearchService also implements filtering and binary searching for numerical ranges. For example, users can search for animals based on ranges such as age, weight, or physical measurements. These features allowed me to apply algorithmic concepts to a practical application rather than using data structures and algorithms only as isolated programming exercises.

Database development is another area in which I have developed practical experience throughout my education. I have worked with both SQL and MongoDB and have learned how persistent data can be structured, stored, retrieved, and modified by an application.

For my capstone, I incorporated SQLite databases to provide persistent storage for the rescue animals. Instead of relying exclusively on hard-coded objects, the application creates and interacts with database tables for dogs and monkeys. The AnimalRepository implements CRUD functionality, allowing the application to create, read, update, and delete animal records.

This project also helped me understand the relationship between application architecture and persistent data. The repository acts as an abstraction between the rest of the application and the database, allowing AnimalService and other application components to work with animal objects without needing to manage database connections and SQL statements directly. This separation of responsibilities makes the application easier to maintain and provides a foundation that could be extended to support additional types of persistent storage.

Security has also become an important consideration in how I approach software development. One of the most important lessons I have learned is that security should be considered throughout the development process rather than treated as a final step after an application has already been built.

A practical example from my capstone is the validation of user input. The application contains a dedicated ValidationService that checks information before it is accepted by the system. This includes validating fields such as animal names, ages, weights, countries, identification numbers, dates, training statuses, and other animal attributes. Validating data before it is processed helps protect data integrity and prevents unexpected or invalid information from being stored in the database.

The capstone also uses prepared statements when interacting with SQLite. Rather than directly inserting user-provided values into SQL statements, values are passed through PreparedStatement parameters. This demonstrates my understanding of safer database practices and the importance of considering how external input interacts with an application's underlying systems.

In addition to technical skills, my education has strengthened my ability to collaborate with others and communicate with stakeholders. Software development is rarely performed entirely in isolation, and being able to communicate effectively is an important part of creating a successful solution.

Through coursework involving group projects and collaborative assignments, I have gained experience dividing responsibilities, reviewing other people's solutions, discussing design decisions, and incorporating feedback. These experiences taught me that there is rarely only one correct way to solve a programming problem. Working with others can expose weaknesses in an approach, introduce alternative solutions, and ultimately produce a stronger result.

I have also learned the importance of communicating with stakeholders and considering the needs of the person who will actually use a system. A technically functional application is not necessarily a successful application if it does not solve the user's problem or meet the project's requirements. Understanding what a stakeholder needs, clarifying requirements, and considering how users will interact with a system can influence decisions about functionality, organization, and design.

These experiences have shaped how I approach software development. I now try to consider not only whether I can implement a feature, but also why the feature is needed, who will use it, and how it fits into the larger purpose of the application.

My ePortfolio brings these areas of experience together and demonstrates how my skills have developed throughout my computer science education. Rather than viewing each artifact as an isolated assignment, the portfolio demonstrates the progression from learning individual concepts to applying those concepts together in larger software solutions.

The capstone project is particularly representative of this progression. It combines object-oriented programming, software architecture, data structures, algorithms, database development, data validation, and application logic into a single application. The project demonstrates my ability to take an existing application and identify areas where its architecture and functionality could be improved. It also demonstrates my ability to apply concepts learned throughout the computer science program to a practical software project.

Other coursework and experiences provide additional evidence of my development. Projects involving data structures and algorithms helped me understand how to select appropriate approaches for solving problems, while database coursework gave me experience working with persistent data. Coursework involving software engineering and security expanded my understanding beyond simply writing code and taught me to consider maintainability, reliability, data integrity, and potential risks throughout the development process.

Completing SNHU's computer science program and developing this ePortfolio has prepared me to enter the computer science field with a foundation in both the technical and professional aspects of software development. I have developed experience solving programming problems, designing software, working with data, applying algorithms, communicating technical ideas, collaborating with others, and considering security and maintainability.

Most importantly, my education has taught me how to approach unfamiliar problems methodically. Rather than immediately attempting to write code, I have learned to understand the requirements, break the problem into smaller components, evaluate possible solutions, and then implement and refine the resulting design. This problem-solving approach is one of the most valuable skills I will carry into my professional career.

As I enter the computer science field, I intend to continue building on these skills and expanding my knowledge through professional experience. My goal is to contribute as a software developer while continuing to learn new technologies, improve my engineering practices, and develop solutions that are useful, maintainable, and reliable.

## Project Introduction

The primary artifact in this portfolio is my Rescue Animal Management System, a Java console application originally developed as Project Two for IT 145. The project was designed to manage information about rescue animals, specifically dogs and monkeys, for an animal rescue organization. The original application allowed users to intake new animals, view lists of animals, and reserve available animals based on their service location and status. It used Java classes and object-oriented programming concepts to represent the animals and provide the application's functionality.

I selected this project for my ePortfolio because it provided a strong foundation for demonstrating how my programming and software development skills developed throughout the remainder of the Computer Science program. The following sections describe the changes and enhancements I made to the original project and the skills demonstrated by those changes.
[Original Project Files](Rescue%20Animal%20Project/IT145%20Project%202/)
[Original Project Informal Code Review Video](https://www.youtube.com/watch?v=YwI9aojZnUw)
[Original Project Informal Code Review Script (.docx)](Week%20Two%20Informal%20Code%20Review%20Script.docx) 
[Milestone One - Proposal (.docx)](Milestone%20One%20Proposal.docx)
[Enhanced Project Files](Rescue%20Animal%20Project/)

## Software Design and Engineering

In the original project, nearly all of the application logic was contained in a single class, [Driver.java](Rescue%20Animal%20Project/IT145%20Project%202/Driver.java), making it very difficult to maintain and expand. In the enhanced version, most of that logic was separated out into a few different classes, creating a modular architecture. Each new class is now responsible for a specific set of things, instead of jamming everything together. [Driver.java](Rescue%20Animal%20Project/Driver.java) is now solely responsible for starting the program, while [Menu.java](Rescue%20Animal%20Project/Menu.java) handles the program's interface and user navigation. I created [AnimalService.java](Rescue%20Animal%20Project/AnimalService.java) which deals with the application logic of the program and [AnimalRepository.java](Rescue%20Animal%20Project/AnimalRepository.java) stores all of the animals.

Part of this enhancement was also creating a class that will validate the user's inputs, [ValidatationService.java](Rescue%20Animal%20Project/ValidationService.java). This class, which wasn't apart of the original project's scope, checks to make sure that all of the user's inputs are valid, specifically checking for things like dates, measurements, countries, etc... Doing this prevents them from adding invalid data that could cause unexpected behavior or potentially system crashes.

Redesigning the original base program has helped create much cleaner and more maintainable code, making future enhancements easier to implement.
[Milestone Two Narrative (.docx)](Milestone%20Two%20Narrative.docx)

## Algorithms and Data Structures

In the original project, very little thought was put into using algorithms and data structures. Because of this, adding more complex systems would require using old inefficient methods. For this enhancement, I added [AnimalSearchService.java](Rescue%20Animal%20Project/AnimalSearchService.java) which allows the user to get lists of animals based on certain criteria. To support this, I upgraded [RescueAnimal.java](Rescue%20Animal%20Project/RescueAnimal.java) to have an ID number, so users could search for a specific animal with a look up of ~0(1) using a hash map. From there I added [SearchCriteria.java](Rescue%20Animal%20Project/SearchCriteria.java) which stores the user's search criteria and generic methods (in AnimalSearchService) that will filter out animals based on that criteria.

Most of the of the filtering is done using java's ArrayList.removeIf() method to remove items that do not match the given criteria in linear time. For ranges, I created another function which uses a custom binary search method to find all potential valid animals in a specific range. This is slower than if I used the removeIf method because before you can use the search function, the list of animals needs to be sorted. To sort I used the inbuilt sort function which uses the tim sort algorithm, with a worst case time complexity of O(n log n). After the list is sorted, the binary search is performed with a worst case time complexity of ~O(log n). To get around the less efficient filtering method, I could have made lists of animals that were presorted, but because there are so many different criteria I allowed the user to filter by, that would balloon the memory requirements of the project.
[Milestone Three Narrative (.docx)](Milestone%20Three%20Narrative.docx)

## Databases

Unlike the other aspects, which had parts of them already included which just needed to be modified, the original project had absolutely no thought put into the use of a database, and one was not included. Instead of saving data, it relied on using a set of hard-coded test animals. This prevented any updates to the data from persisting across sessions. If a user wanted to add an animal, it would be missing when they restart the program. To remedy this, I implemented two SQLite tables, one for each type of animal the program supports, dogs and monkeys. All the data each animal requires is saved in their respective table. I also added proper CRUD functionality to [AnimalRepository.java](Rescue%20Animal%20Project/AnimalRepository.java), something that was only partially implemented before. To do this, I had to heavily update the add method, allowing it to write to the database. I also had to add an initialize method which can load the animals in the database for the program to use, and an update and delete method, allowing the user to change details about an animal, or remove them entirely.
[Milestone Four Narrative (.docx)](Milestone%20Four%20Narrative.docx)

</div>
