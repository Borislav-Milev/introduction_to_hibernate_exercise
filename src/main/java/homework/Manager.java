package homework;

import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Comparator;
import java.util.List;

public final class Manager {

    private final EntityManager entityManager;
    private final Util util;

    public Manager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.util = new Util(this.entityManager);
    }

    public void changeCasingEx2() {
        List<Town> townList = this.entityManager
                .createQuery(Queries.EX2_TOWNS_BY_LENGTH, Town.class)
                .getResultList();

        this.entityManager.getTransaction().begin();
        townList.forEach(this.entityManager::detach);

        for (Town town : townList) {
            town.setName(town.getName().toLowerCase());
        }
        townList.forEach(this.entityManager::merge);
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
        System.out.printf("%d towns were altered.%n", townList.size());
    }

    public void containsEmployeeEx3(String fullName) {
        List<Employee> employees = this.entityManager
                .createQuery(Queries.EX3_FULL_NAME, Employee.class)
                .setParameter("name", fullName)
                .getResultList();

        System.out.println(employees.size() == 0 ? "No" : "Yes");
    }

    public void employeesWithSalaryOver5000Ex4() {
        this.entityManager
                .createQuery(Queries.EX4_SALARIES_OVER, Employee.class)
                .getResultStream()
                .map(Employee::getFirstName)
                .forEach(System.out::println);
    }

    public void employeesFromDepartmentsEx5() {
        this.entityManager
                .createQuery(Queries.EX5_FROM_DEPARTMENT, Employee.class)
                .getResultStream()
                .forEach(employee ->
                        System.out.printf("%s %s from Research and Development - $%.2f%n",
                                employee.getFirstName(), employee.getLastName(), employee.getSalary()));
    }


    public void addingNewAddressAndUpdatingEmployeeEx6(String lastName) {
        Employee employee = this.util.findEntityByName(Employee.class, "lastName", lastName);

        if(employee == null) {
            System.out.printf("There is no employee with last name %s%n", lastName);
            return;
        }
        Address address = this.util.createAddressIfNotExist();
        this.entityManager.getTransaction().begin();
        employee.setAddress(address);
        this.entityManager.getTransaction().commit();

        System.out.printf("%s has been set to employee with last name %s%n",
                employee.getAddress().getText(), employee.getLastName());
        System.out.println("Program needs to refresh in order to continue.");
        System.exit(0);
    }

    public void addressesWithEmployeeCountEx7() {
        List<Address> addresses = this.entityManager
                .createQuery(Queries.EX7_ADDRESSES_WITH_EMPLOYEES, Address.class)
                .setMaxResults(10)
                .getResultList();

        addresses.forEach(address -> System.out.printf("%s, %s - %d%n",
                address.getText(), address.getTown().getName(), address.getEmployees().size())
        );
    }

    public void getEmployeeWithProjectsEx8(int id) {
        Employee employee = this.entityManager.find(Employee.class, id);

        System.out.printf("%s %s - %s%n", employee.getFirstName(), employee.getLastName(), employee.getJobTitle());

        employee.getProjects()
                .stream()
                .sorted(Comparator.comparing(Project::getName))
                .forEach(project -> {
                    System.out.printf("\t%s%n", project.getName());
                });
    }

    public void findLatest10ProjectsEX9() {
        StringBuilder sb = new StringBuilder();
        List<Project> projects = this.entityManager
                .createQuery(Queries.EX_9_LATEST_10_PROJECTS, Project.class)
                .setMaxResults(10)
                .getResultList();

        projects.sort(Comparator.comparing(Project::getName));
        projects.forEach(project -> {
            sb.append("Project name: ").append(project.getName()).append(System.lineSeparator())
                    .append("\tProject Description: ").append(project.getDescription()).append(System.lineSeparator())
                    .append("\tProject Start Date: ").append(project.getStartDate()).append(System.lineSeparator())
                    .append("\tProject End Date: ").append(project.getEndDate()).append(System.lineSeparator());
        });
        System.out.println(sb.toString());
    }

    public void getIncreasedSalariesEx10() {
        this.entityManager.getTransaction().begin();
        int affectedRows = this.entityManager
                .createQuery(Queries.EX10_SALARY_INCREASE_BY_DEPARTMENT)
                .executeUpdate();
        this.entityManager.getTransaction().commit();

        System.out.println("Affected rows: " + affectedRows);
        this.entityManager
                .createQuery(Queries.EX10_SELECT_INCREASE, Employee.class)
                .getResultStream()
                .forEach(employee -> {
                    System.out.printf("%s %s ($%.2f)%n",
                            employee.getFirstName(), employee.getLastName(), employee.getSalary());
                });
    }

    public void findEmployeesByFirstNameEx11(String pattern) {
        List<Employee> employees = this.entityManager
                .createQuery(Queries.EX11_SELECT_FROM_PATTERN, Employee.class)
                .setParameter("name", pattern)
                .getResultList();
        if (employees.isEmpty()) {
            System.out.printf("No employees found with pattern: %s%n", pattern);
            return;
        }
        employees.forEach(employee ->
                System.out.printf("%s %s - %s - (%s)%n",
                        employee.getFirstName(), employee.getLastName(), employee.getJobTitle(), employee.getSalary()));
    }

    @SuppressWarnings("unchecked")
    public void employeesMaximumSalariesEx12() {

        //Keep in mind that the example given in the exercise description is with polluted data.
        //You will have to execute exercise 10 once in order to give the exact output as
        // in the description example in exercise 12.

        Query q = entityManager.createNativeQuery(Queries.EX12_SALARY_IN_DEPARTMENTS);
        List<Object[]> resultList = q.getResultList();

        resultList.forEach(object -> System.out.println(object[0] + " " + object[1]));
    }


    public void removeTownsEx13(String townName) {
        Town town = this.util.findEntityByName(Town.class,"name",  townName);
        if (town == null) {
            System.out.printf("No such town with name %s%n", townName);
            return;
        }

        this.entityManager.getTransaction().begin();
        this.entityManager.createNativeQuery(Queries.ALTER_EMPLOYEE_ADDRESSES)
                .setParameter("townId", town.getId()).executeUpdate();

        int affectedRows = this.entityManager.createQuery(Queries.DELETE_ADDRESSES)
                .setParameter("id", town.getId()).executeUpdate();

        this.entityManager.createQuery(Queries.DELETE_TOWNS).setParameter("id", town.getId())
                .executeUpdate();
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();


        if (affectedRows == 0) {
            System.out.printf("No addresses in %s deleted.%n", townName);
        } else if (affectedRows == 1) {
            System.out.printf("%d address in %s deleted.%n", affectedRows, townName);
        } else {
            System.out.printf("%d addresses in %s deleted.%n", affectedRows, townName);
        }
    }
}
