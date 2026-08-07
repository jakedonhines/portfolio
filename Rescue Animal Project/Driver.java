public class Driver {
    private static Menu menu = Menu.getInstance();

    public static void main(String[] args) {

        // Run the program's menu, this loop will conintue indefanitly until the user enters the quit sequince in the menu
        while (true){
            menu.runMenu();
        }

    }
}

