package homework;

public final class Queries {

    private Queries(){
    }


    //EX_2
    public static final String EX2_TOWNS_BY_LENGTH =
            "select t from Town t " +
            "where length(t.name) > 5";

    //EX_3
    public static final String EX3_FULL_NAME =
            "select e from Employee e "+
            "where concat(e.firstName, ' ', e.lastName) = :name";

    //EX_4
    public static final String EX4_SALARIES_OVER =
            "select e from Employee e " +
            "where e.salary > 50000";

    //EX_5
    public static final String EX5_FROM_DEPARTMENT =
            "select e from Employee e " +
            "where e.department.name like 'Research and Development' " +
            "order by e.salary, e.id";

    //EX_6
    public static final String EX6_FIND_EMPLOYEE =
            "select e from Employee e " +
            "where e.lastName like :name";

    //EX_7
    public static final String EX7_ADDRESSES_WITH_EMPLOYEES =
            "select a from Address a " +
            "order by a.employees.size desc";

    //EX_9
    public static final String EX_9_LATEST_10_PROJECTS =
            "select p from Project p " +
            "order by p.startDate desc";

    //EX_10
    public static final String EX10_SALARY_INCREASE_BY_DEPARTMENT =
            "update  Employee e set e.salary = e.salary * 1.12 " +
            "where e.department.id in " +
            "(1,2,4,11)";

    public static final String EX10_SELECT_INCREASE =
            "select e from Employee e " +
            "where e.department.id in " +
            "(1,2,4,11)";

    //EX_13
    public static final String EX11_SELECT_FROM_PATTERN =
            "select e from Employee e " +
            "where e.firstName like concat(:name, '%')";

    //EX_12
    public static final String EX12_SALARY_IN_DEPARTMENTS =
            "select d.name, max(e.salary) from departments d " +
            "join employees e on e.department_id = d.department_id " +
            "group by d.name " +
            "having max(e.salary) not between 30000 and 70000";

    //EX_13
    public static final String FIND_ENTITY_BY_NAME =
            "select e from %s e " +
            "where %s like :name";

    public static final String ALTER_EMPLOYEE_ADDRESSES =
            "update employees e join addresses a on " +
            "e.address_id = a.address_id join " +
            "towns t on t.town_id = a.town_id " +
            "set e.address_id = null where a.town_id = :townId";

    public static final String DELETE_ADDRESSES =
            "delete from Address a " +
            "where a.town.id = :id";

    public static final String DELETE_TOWNS =
            "delete from Town t " +
            "where t.id = :id";
}
