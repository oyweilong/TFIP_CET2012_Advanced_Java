# commands.Command Design Pattern Structure (Figure 1) Explanation

## Overview
The commands.Command Design Pattern encapsulates a request as an object, thereby allowing you to parameterize clients with different requests, queue operations, and support undo operations.

## Components Breakdown

### 1. **Client**
- **Role**: Creates and configures concrete command objects
- **Responsibilities**:
  - Creates the receiver object
  - Creates concrete command objects
  - Passes the receiver to the command constructor
  - Associates commands with the invoker
- **In Your Assignment**: The `Main` class with user input handling
- **Example**:
  ```java
  import commands.AddCommand;   // Client creates receiver
  DataStore dataStore = new DataStore();
  
  // Client creates concrete command with receiver
  AddCommand addCmd = new AddCommand(dataStore, "John", "Doe", "john@example.com");
  
  // Client gives command to invoker
  CommandInvoker invoker = new CommandInvoker();
  invoker.setCommand(addCmd);
  ```

### 2. **invoker.Invoker**
- **Role**: Asks the command to carry out the request
- **Responsibilities**:
  - Stores reference to command object
  - Triggers command execution by calling `execute()`
  - Doesn't know what the command does or how it works
  - May store commands for delayed execution or undo
- **In Your Assignment**: `CommandInvoker` class that manages command history
- **Key Features**:
  - Has a field to store command reference: `commands.Command command`
  - Method to execute: `command.execute()`
  - Manages command history: `Stack<commands.Command> history`
- **Example**:
  ```java
  public class CommandInvoker {
      private Stack<commands.Command> history = new Stack<>();
      
      public void executeCommand(commands.Command command) {
          command.execute();
          if (command.isUndoable()) {
              history.push(command);
          }
      }
  }
  ```

### 3. **commands.Command (Interface)**
- **Role**: Declares interface for executing operations
- **Responsibilities**:
  - Defines the contract that all concrete commands must follow
  - Usually declares just `execute()` method
  - May include `undo()` for reversible operations
- **In Your Assignment**: The `commands.Command` interface
- **Structure**:
  ```java
  public interface commands.Command {
      void execute() throws CustomException;
      void undo() throws CustomException;
      boolean isUndoable();
  }
  ```

### 4. **ConcreteCommand**
- **Role**: Implements the commands.Command interface and defines the binding between data.Receiver and action
- **Responsibilities**:
  - Stores reference to receiver object
  - Stores parameters needed for the operation
  - Implements `execute()` by calling methods on receiver
  - Implements `undo()` if applicable
  - **Does NOT perform the work itself** - delegates to receiver
- **In Your Assignment**: `commands.AddCommand`, `commands.UpdateCommand`, `commands.DeleteCommand`, `commands.ListCommand`, `commands.UndoCommand`
- **Structure**:
  ```java
  public class commands.AddCommand implements commands.Command {
      private DataStore receiver;  // Reference to receiver
      private String firstName, lastName, email;  // Parameters
      
      public commands.AddCommand(DataStore receiver, String firstName, String lastName, String email) {
          this.receiver = receiver;
          this.firstName = firstName;
          this.lastName = lastName;
          this.email = email;
      }
      
      public void execute() {
          receiver.addEmployee(firstName, lastName, email);  // Delegates to receiver
      }
  }
  ```

### 5. **data.Receiver**
- **Role**: Contains business logic and knows how to perform operations
- **Responsibilities**:
  - Performs the actual work when command calls its methods
  - Contains domain-specific business logic
  - Independent of commands (doesn't know about them)
  - Can be any object that can perform the required operations
- **In Your Assignment**: `DataStore` class
- **Characteristics**:
  - Contains the `ArrayList<Employee>` data
  - Has methods like `addEmployee()`, `updateEmployee()`, `deleteEmployee()`
  - Handles file I/O operations
  - Performs validation and error handling
- **Example**:
  ```java
  public class DataStore {  // This is the data.Receiver
      private ArrayList<Employee> employees;
      
      public void addEmployee(String firstName, String lastName, String email) {
          // Business logic here - validation, formatting, storage
          validateEmail(email);
          Employee emp = new Employee(toTitleCase(firstName), toTitleCase(lastName), email);
          employees.add(emp);
      }
  }
  ```

## Interaction Flow

```
1. Client creates data.Receiver
   ↓
2. Client creates ConcreteCommand with data.Receiver reference
   ↓
3. Client gives ConcreteCommand to invoker.Invoker
   ↓
4. invoker.Invoker calls command.execute()
   ↓
5. ConcreteCommand calls receiver.performAction()
   ↓
6. data.Receiver performs the actual work
```

## Key Relationships

### **Client → ConcreteCommand**
- Client creates and configures commands
- Passes all necessary parameters
- Associates commands with invoker

### **ConcreteCommand → data.Receiver**
- commands.Command stores reference to receiver
- commands.Command delegates work to receiver
- commands.Command doesn't do the work itself

### **invoker.Invoker → commands.Command**
- invoker.Invoker only knows the commands.Command interface
- invoker.Invoker triggers execution via `execute()`
- invoker.Invoker may store commands for history/undo

### **commands.Command → ConcreteCommand**
- Interface defines the contract
- All concrete commands implement this interface
- Provides polymorphic behavior

## Benefits of This Structure

### **Decoupling**
- invoker.Invoker doesn't know about receivers
- Commands encapsulate all request information
- Easy to add new commands without changing existing code

### **Flexibility**
- Commands can be stored, queued, logged
- Support for undo/redo operations
- Commands can be executed at different times

### **Extensibility**
- New commands can be added easily
- Existing commands can be composed into macro commands
- Support for different types of receivers

## Real-World Analogy

Think of a **restaurant ordering system**:

- **Client**: Customer who places an order
- **invoker.Invoker**: Waiter who takes the order to the kitchen
- **commands.Command**: Written order slip with all details
- **ConcreteCommand**: Specific orders like "Burger Order", "Pizza Order"
- **data.Receiver**: Kitchen staff who prepare the food

The waiter (invoker) doesn't need to know how to cook - they just pass the order (command) to the kitchen (receiver) who knows how to prepare the food.

## Your Assignment Mapping

| Pattern Component | Your Implementation |
|------------------|-------------------|
| **Client** | `Main` class (handles user input) |
| **invoker.Invoker** | `CommandInvoker` class (manages execution & history) |
| **commands.Command** | `commands.Command` interface |
| **ConcreteCommand** | `commands.AddCommand`, `commands.UpdateCommand`, `commands.DeleteCommand`, `commands.ListCommand`, `commands.UndoCommand` |
| **data.Receiver** | `DataStore` class (contains business logic) |

This structure ensures that your tool is flexible, maintainable, and follows object-oriented design principles while supporting the required undo functionality.