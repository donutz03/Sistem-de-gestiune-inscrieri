import java.util.Scanner;
import java.util.List;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;


class Guest {

    // TO DO: define fields
    String lastName;
    String firstName;
    String email;
    String phoneNumber;

    public Guest(String lastName, String firstName, String email, String phoneNumber) {
        // TO DO:
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // TO DO: Implement getters and setters
    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int hashCode() {
        // TO DO:
        return Objects.hash(getLastName(), getFirstName(), getEmail(), getPhoneNumber());

    }

    @Override
    public boolean equals(Object obj) {
        // TO DO:
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Guest guest = (Guest) obj;
        return Objects.equals(getLastName(), guest.getLastName()) && Objects.equals(getFirstName(), guest.getFirstName()) && Objects.equals(getEmail(), guest.getEmail()) && Objects.equals(getPhoneNumber(), guest.getPhoneNumber());
    }

    @Override
    public String toString() {
        // TO DO:
        return "Nume: " + fullName() + ", Email: " + email + ", Telefon: " + phoneNumber;
    }

    public String fullName() {
        // TO DO:
        return this.lastName + " " + this.firstName;
    }
}

class GuestsList {

    // TO DO: add fields
    private int guestCapacity;
    private List<Guest> guests;
    private List<Guest> waitingList;

    public GuestsList(int guestsCapacity) {
        // TO DO:
        this.guestCapacity = guestsCapacity;
        this.guests = new ArrayList<>();
        this.waitingList = new ArrayList<>();
    }

    /**
     * Add a new, unique guest to the list.
     *
     * @param g the guest to be added
     * @return '-1' if the guest is already present, '0' if is a guest, or the
     * number on the waiting list
     */
    public int add(Guest g) {
        // TO DO:
        if (isOnTheListAlready(g)) {
            return -1;
        }

        if (guests.size() < guestCapacity) {
            guests.add(g);
            System.out.println("[" + g.fullName() + "] Felicitari! Locul tau la eveniment este confirmat. Te asteptam!");
            return 0;
        } else {
            waitingList.add(g);
            int position = waitingList.size();
            System.out.println("[" + g.fullName() + "] Te-ai inscris cu succes in lista de asteptare si ai primit numarul de ordine " + position + ". Te vom notifica daca un loc devine disponibil.");
            return position;
        }
    }

    /**
     * Check if someone is already registered ( as a guest, or on the waiting
     * list).
     *
     * @param g the guest we are searching for
     * @return true if present, false if not
     */
    private boolean isOnTheListAlready(Guest g) {
        // TO DO:
        return guests.contains(g) || waitingList.contains(g);
    }

    /**
     * Search for a guest based on first and last name. Return the first result.
     *
     * @param firstName first name of the guest
     * @param lastName  last name of the guest
     * @return the guest if found, null if not
     */
    public Guest search(String firstName, String lastName) {
        // TO DO:
        for (Guest guest : guests) {
            if (guest.getFirstName().equalsIgnoreCase(firstName) && guest.getLastName().equalsIgnoreCase(lastName)) {
                return guest;
            }
        }
        for (Guest guest : waitingList) {
            if (guest.getFirstName().equalsIgnoreCase(firstName) && guest.getLastName().equalsIgnoreCase(lastName)) {
                return guest;
            }
        }
        return null;
    }

    /**
     * Search for a guest based on email or phone number. Return the first result.
     *
     * @param opt   option to use for searching: 2 for email, 3 for phone number
     * @param match what is searched for
     * @return the guest if found, null if not
     */
    public Guest search(int opt, String match) {
        // TO DO:
        List<Guest> searchList;
        if (opt == 2) {
            searchList = Stream.concat(guests.stream(), waitingList.stream()).filter(g -> g.getEmail().equalsIgnoreCase(match)).collect(Collectors.toList());
        } else if (opt == 3) {
            searchList = Stream.concat(guests.stream(), waitingList.stream()).filter(g -> g.getPhoneNumber().equalsIgnoreCase(match)).collect(Collectors.toList());
        } else {
            return null;
        }

        if (!searchList.isEmpty()) {
            return searchList.get(0);
        } 
        return null;
    }

    /**
     * Remove a guest based on first and last name. Remove the first result.
     *
     * @param firstName first name of the guest
     * @param lastName  last name of the guest
     * @return true if removed, false if not
     */
    public boolean remove(String firstName, String lastName) {
        // TO DO:
        Guest toRemove = search(firstName, lastName);
        if (toRemove != null) {
            if (guests.remove(toRemove) || waitingList.remove(toRemove)) {
               // System.out.println("Persoana " + toRemove.fullName() + " a fost stearsa cu succes.");
                if (!waitingList.isEmpty() && guests.size() < guestCapacity) {
                    Guest firstWaiting = waitingList.remove(0);
                    guests.add(firstWaiting);
                    System.out.println("["+firstWaiting.fullName() + "] Felicitari! Locul tau la eveniment este confirmat. Te asteptam!");

                }
                return true;
            }
        }
        System.out.println("Eroare: Persoana nu era inscrisa.");
        return false;
    }

    /**
     * Remove a guest based on email or phone number. Remove the first result.
     *
     * @param opt   option to use for searching: 2 for email, 3 for phone number
     * @param match the match we are searching for
     * @return true if removed, false if not
     */
    public boolean remove(int opt, String match) {
        // TO DO:
        Guest toRemove = search(opt, match);
        if (toRemove != null) {
            if (guests.remove(toRemove) || waitingList.remove(toRemove)) {
               // System.out.println("Persoana " + toRemove.fullName() + " a fost stearsa cu succes.");
                if (!waitingList.isEmpty() && guests.size() < guestCapacity) {
                    Guest firstWaiting = waitingList.remove(0);
                    guests.add(firstWaiting);
                    System.out.println("["+firstWaiting.fullName() + "] Felicitari! Locul tau la eveniment este confirmat. Te asteptam!");
                }
                return true;
            }
        }
        System.out.println("Eroare: Persoana nu era inscrisa.");
        return false;
    }

    // Show the list of guests.
    public void showGuestsList() {
        // TO DO:
      // System.out.println("Lista de participanti:");
        for (int i = 0; i < guests.size(); i++) {
        System.out.println((i + 1) + ". " + guests.get(i).toString());
    }
    }

    // Show the people on the waiting list.
    public void showWaitingList() {
        // TO DO:
        //System.out.println("Lista de asteptare:");
    for (int i = 0; i < waitingList.size(); i++) {
        System.out.println((i + 1) + ". " + waitingList.get(i).toString());
    }
    if (waitingList.size() == 0) {
        System.out.println("Lista de asteptare este goala...");
    }
    }

    /**
     * Show how many free spots are left.
     *
     * @return the number of spots left for guests
     */
    public int numberOfAvailableSpots() {
        // TO DO:
        return guestCapacity - guests.size();

    }

    /**
     * Show how many guests there are.
     *
     * @return the number of guests
     */
    public int numberOfGuests() {
        // TO DO:
        return guests.size();
    }

    /**
     * Show how many people are on the waiting list.
     *
     * @return number of people on the waiting list
     */
    public int numberOfPeopleWaiting() {
        // TO DO:
        return waitingList.size();
    }

    /**
     * Show how many people there are in total, including guests.
     *
     * @return how many people there are in total
     */
    public int numberOfPeopleTotal() {
        // TO DO:
        return guests.size() + waitingList.size();
    }

    /**
     * Find all people based on a partial value search.
     *
     * @param match the match we are looking for
     * @return a list of people matching the criteria
     */
    public List<Guest> partialSearch(String match) {
        // TO DO:
        List<Guest> searchResults = new ArrayList<>();
        String lowerCaseMatch = match.toLowerCase();
        for (Guest guest : guests) {
            if (guest.getLastName().toLowerCase().contains(lowerCaseMatch) || guest.getFirstName().toLowerCase().contains(lowerCaseMatch) || guest.getEmail().toLowerCase().contains(lowerCaseMatch) || guest.getPhoneNumber().toLowerCase().contains(lowerCaseMatch)) {
                searchResults.add(guest);
            }
        }
        for (Guest guest : waitingList) {
            if (guest.getLastName().toLowerCase().contains(lowerCaseMatch) || guest.getFirstName().toLowerCase().contains(lowerCaseMatch) || guest.getEmail().toLowerCase().contains(lowerCaseMatch) || guest.getPhoneNumber().toLowerCase().contains(lowerCaseMatch)) {
                searchResults.add(guest);
                //System.out.println("Nu in partial search");
            }
        }
        return searchResults;
    }

    @Override
    public String toString() {
        // TO DO:
        return "";
    }
}

 class Main {
    private static void showCommands() {
        System.out.println("help         - Afiseaza aceasta lista de comenzi");
        System.out.println("add          - Adauga o noua persoana (inscriere)");
        System.out.println("check        - Verifica daca o persoana este inscrisa la eveniment");
        System.out.println("remove       - Sterge o persoana existenta din lista");
        System.out.println("update       - Actualizeaza detaliile unei persoane");
        System.out.println("guests       - Lista de persoane care participa la eveniment");
        System.out.println("waitlist     - Persoanele din lista de asteptare");
        System.out.println("available    - Numarul de locuri libere");
        System.out.println("guests_no    - Numarul de persoane care participa la eveniment");
        System.out.println("waitlist_no  - Numarul de persoane din lista de asteptare");
        System.out.println("subscribe_no - Numarul total de persoane inscrise");
        System.out.println("search       - Cauta toti invitatii conform sirului de caractere introdus");
        System.out.println("save         - Salveaza lista cu invitati");
        System.out.println("restore      - Completeaza lista cu informatii salvate anterior");
        System.out.println("reset        - Sterge informatiile salvate despre invitati");
        System.out.println("quit         - Inchide aplicatia");
    }

    private static void addNewGuest(Scanner sc, GuestsList list) {
        // TO DO:
        //System.out.println("Introduceti numele de familie:");
        String lastName = sc.nextLine();
        //System.out.println("Introduceti prenumele:");
        String firstName = sc.nextLine();
        //System.out.println("Introduceti adresa de email:");
        String email = sc.nextLine();
        //System.out.println("Introduceti numarul de telefon:");
        String phoneNumber = sc.nextLine();

        Guest newGuest = new Guest(lastName, firstName, email, phoneNumber);
        int result = list.add(newGuest);
        if (result == -1) {
            System.out.println("Eroare: Persoana este deja inscrisa la eveniment.");
        } else if (result == 0) {
           // System.out.println("Persoana a fost adaugata cu succes la eveniment.");
            //System.out.println("Nu in addNewGuesst");
        } else {
            //System.out.println("Persoana a fost adaugata pe lista de asteptare cu numarul de ordine " + result + ".");
        }
    }

    private static void checkGuest(Scanner sc, GuestsList list) {
        // TO DO:
       int option = sc.nextInt();
    sc.nextLine(); // Consume newline

    Guest foundGuest = null;

    switch (option) {
        case 1:
            // Search by last name and first name
            String lastName = sc.nextLine();
            String firstName = sc.nextLine();
            foundGuest = list.search(firstName, lastName);
            break;
        case 2:
            // Search by email
            String email = sc.nextLine();
            foundGuest = list.search(2, email);
            break;
        case 3:
            // Search by phone number
            String phoneNumber = sc.nextLine();
            foundGuest = list.search(3, phoneNumber);
            break;
        default:
            return; // Exit the method
    }

    if (foundGuest != null) {
        System.out.println("Nume: " + foundGuest.fullName() + 
        ", Email: " + foundGuest.getEmail() + ", Telefon: " + 
        foundGuest.getPhoneNumber());

       
    } else {
        System.out.println("Guest not found.");
    }

              //System.out.println("aiciiiii");
               // System.out.println("Nume: " + foundGuest.fullName() + ", Email: " + foundGuest.getEmail() + ", Telefon: " + foundGuest.getPhoneNumber());
    }

    private static void removeGuest(Scanner sc, GuestsList list) {
        // TO DO:
       int option = sc.nextInt();
    sc.nextLine(); // Consume newline

    boolean removed = false;

    switch (option) {
        case 1:
            // Search by last name and first name
            String lastName = sc.nextLine();
            String firstName = sc.nextLine();
            removed = list.remove(firstName, lastName);
            break;
        case 2:
            // Search by email
            String email = sc.nextLine();
            removed = list.remove(2, email);
            break;
        case 3:
            // Search by phone number
            String phoneNumber = sc.nextLine();
            removed = list.remove(3, phoneNumber);
            break;
        default:
            return; // Exit the method
    }

    if (removed) {
        //System.out.println("Guest removed successfully.");
    } else {
        System.out.println("Error: Guest not found or could not be removed.");
    }
    }


    private static void updateGuest(Scanner sc, GuestsList list) {
    // TO DO:
     int option = sc.nextInt();
    sc.nextLine(); // Consume newline

    Guest foundGuest = null;

    switch (option) {
        case 1:
            // Search by last name and first name
            String lastName = sc.nextLine();
            String firstName = sc.nextLine();
            foundGuest = list.search(firstName, lastName);
            break;
        case 2:
            // Search by email
            String email = sc.nextLine();
            foundGuest = list.search(2, email);
            break;
        case 3:
            // Search by phone number
            String phoneNumber = sc.nextLine();
            foundGuest = list.search(3, phoneNumber);
            break;
        default:
            return; // Exit the method
    }

    if (foundGuest != null) {
       
        int updateOption = sc.nextInt();
        sc.nextLine(); // Consume newline

        switch (updateOption) {
            case 1:
                String newLastName = sc.nextLine();
                if (!newLastName.isEmpty()) {
                    foundGuest.setLastName(newLastName);
                }
                break;
            case 2:
                String newFirstName = sc.nextLine();
                if (!newFirstName.isEmpty()) {
                    foundGuest.setFirstName(newFirstName);
                }
                break;
            case 3:
                String newEmail = sc.nextLine();
                if (!newEmail.isEmpty()) {
                    foundGuest.setEmail(newEmail);
                }
                break;
            case 4:
                String newPhoneNumber = sc.nextLine();
                if (!newPhoneNumber.isEmpty()) {
                    foundGuest.setPhoneNumber(newPhoneNumber);
                }
                break;
            default:
                return; // Exit the method
        }

        // System.out.println("Information updated successfully.");
    } else {
        // System.out.println("Error: Guest not found.");
    }
}


    private static void searchList(Scanner sc, GuestsList list) {
        // TO DO:
       // System.out.println("Introduceti un sir de caractere pentru cautare:");
        String searchStr = sc.nextLine();
        List<Guest> searchResults = list.partialSearch(searchStr);
        if (!searchResults.isEmpty()) {
          //  System.out.println("Rezultatele cautarii:");
            for (Guest guest : searchResults) {
                System.out.println(guest);
            }
        } else {
          //  System.out.println("Nu s-au gasit rezultate pentru cautarea \"" + searchStr + "\".");
                    System.out.println("Nothing found");

        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        scanner.nextLine();

        GuestsList list = new GuestsList(size);

        boolean running = true;
        while (running) {
            String command = scanner.nextLine();

            switch (command) {
                case "help":
                    showCommands();
                    break;
                case "add":
                    addNewGuest(scanner, list);
                    break;
                case "check":
                    checkGuest(scanner, list);
                    break;
                case "remove":
                    removeGuest(scanner, list);
                    break;
                case "update":
                    updateGuest(scanner, list);
                    //System.out.println("here");
                    break;
                case "guests":
                    list.showGuestsList();
                    break;
                case "waitlist":
                    list.showWaitingList();
                    break;
                case "available":
                    System.out.println("Numarul de locuri ramase: " + list.numberOfAvailableSpots());
                    break;
                case "guests_no":
                    System.out.println("Numarul de participanti: " + list.numberOfGuests());
                    break;
                case "waitlist_no":
                    System.out.println("Dimensiunea listei de asteptare: " + list.numberOfPeopleWaiting());
                    break;
                case "subscribe_no":
                    System.out.println("Numarul total de persoane: " + list.numberOfPeopleTotal());
                    break;
                case "search":
                    searchList(scanner, list);
                    break;
                case "quit":
                    System.out.println("Aplicatia se inchide...");
                    scanner.close();
                    running = false;
                    break;
                default:
                   // System.out.println("Comanda introdusa nu este valida.");
                    //System.out.println("Incercati inca o data.");

            }
        }
    }
}
