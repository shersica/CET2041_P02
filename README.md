
# CET2041_P02

### Requirements:
1. There must be at least 4 endpoints implemented based on the following:
   - An endpoint for getting **all the names and department numbers** of all departments in the company. 
   **Does not consume any user input.**
   - An endpoint for returning the **full employee record** given the employee number. 
   **User inputs an employee number.**
   - An endpoint for getting **all employee records** (info to return: employee number, first name, last name and hire date) from a given department number and an optional page number that defaults to 1. 
   The results are to be **paged** and each page has a **maximum of 20 records**. User inputs a department number and an optional page number to retrieve the data. 
   **_Note_** that in terms of computing, page numbers start from 0 to n-1 but from the users point of view, pages start from 1 to n.
   - An endpoint that is used for **employee promotion**. 
   **Consumes a JSON string of the data required** for an employee's promotion. 
   You are to decide which set of information is required to be sent for this task.  
   At the minimum, promotions are to be carried out 1 at a time.
2. Composite primary keys are to be implemented using either `IdClass` or `EmbeddedId` annotation. **Pick one approach.**
3. All data **returned** from the server is in the form of a **JSON string** using the **appropriate HTTP status codes**.
4. All related files are to be stored in **related folders**.
5. Safe to assume that user input data is always cleaned (aka numerical inputs will always be numerical) but **does not mean that they are always valid**.
6. The package file **produced** by the Maven build pipeline is a .war file.
7. Comments are **still required** as it should be used to convey information to yourself and to **justify** your way of implementation.

### Employees Database Schema

![](/src/main/resources/employees-schema.png)

### Notes:
- Full employee record:

        {
            emp_no: ,
            birth_date: ,
            first_name: , 
            last_name: , 
            gender: ,
            hire_date: , 
            title: {
                        title: ,
                        from_date: ,
                        to_date: 
                    }
            salary: {
                        salary: ,
                        from_date: ,
                        to_date: 
                    },
            dept_manager: [],
            dept_name: {
                            dept_no: ,
                            dept_name: ,
                            from_date: ,
                            to_date: 
                        },
        }

- Employee promotion is not tier-based; simply update title (and manager status if the person is promoted to a manager), dept_name, salary, from_ & to_date. 
<br>**Note**: 
  - Must support addition of title(s).
  - DO NOT pass a single JSON packet.
- **ONLY** 1 EMF exist in a project. 
- **AT LEAST** 1 entity for every table. 


