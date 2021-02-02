package homework;

import java.io.BufferedReader;
import java.io.IOException;

public final class Engine implements Runnable{
    private final Manager manager;
    private final BufferedReader reader;

    public Engine(Manager manager, BufferedReader reader) {
        this.manager = manager;
        this.reader = reader;
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.printf("Please enter exercise from 2 to 13 you want to test.%n" +
                        "If you want to exit, press 0.%n");
                int input = Integer.parseInt(this.reader.readLine());
                if(input == 0) break;
                switch (input) {
                    case 2 -> this.manager.changeCasingEx2();
                    case 3 -> {
                        System.out.println("Please enter employee full name:");
                        this.manager.containsEmployeeEx3(this.reader.readLine());
                    }
                    case 4 -> this.manager.employeesWithSalaryOver5000Ex4();
                    case 5 -> this.manager.employeesFromDepartmentsEx5();
                    case 6 -> {
                        System.out.println("Please enter employee last name:");
                        this.manager.addingNewAddressAndUpdatingEmployeeEx6(this.reader.readLine());
                    }
                    case 7 -> this.manager.addressesWithEmployeeCountEx7();
                    case 8 -> {
                        System.out.println("Please enter employee ID:");
                        input = Integer.parseInt(reader.readLine());
                        this.manager.getEmployeeWithProjectsEx8(input);
                    }
                    case 9 -> this.manager.findLatest10ProjectsEX9();
                    case 10 -> this.manager.getIncreasedSalariesEx10();
                    case 11 -> {
                        System.out.println("Please enter pattern:");
                        this.manager.findEmployeesByFirstNameEx11(this.reader.readLine());
                    }
                    case 12 -> this.manager.employeesMaximumSalariesEx12();
                    case 13 -> {
                        System.out.println("Please enter town you wish to remove:");
                        this.manager.removeTownsEx13(this.reader.readLine());
                    }
                    default -> System.out.println("There is no such exercise.");
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
