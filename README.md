# CycleCare – Menstrual Cycle Tracker (OOPS Project)
A simple Java-based menstrual cycle tracking system built using Object-Oriented Programming (OOP) concepts. The application helps users record period dates, monitor symptoms and moods, calculate cycle durations, and receive basic wellness insights.

# Project Overview:
CycleCare is a console-based application designed to support menstrual health awareness by allowing users to:
- Enter personal details
- Record period start and end dates
- Track symptoms and moods
- View period history and durations
- Calculate averages and provide cycle predictions
- Generate general wellness observations

The project demonstrates strong implementation of OOP principles such as abstraction, encapsulation, inheritance, exception handling, and modular design.

# OOP Concepts Implemented:
- Encapsulation: All attributes are private with controlled access via getters.
- Polymorphism: Monitor abstract class declares the abstract method displayRecords() in which Subclasses like MoodMonitor and SymptomMonitor override this method differently.
- Abstraction: Abstract Monitor class for wellness tracking.
- Inheritance: Wellness package consist of classes SymptomTracker & MoodTracker that inherit from a common abstract class called Monitor
- Exception Handling: Custom exceptions like InvalidInputException, InvalidCycleDayException & InvalidCycleDataException
- Modularity: Packages are organized according to its functionality.



