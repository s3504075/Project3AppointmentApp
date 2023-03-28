package AppointmentApp;



import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

enum Reminder { none, TEXT, EMAIL }


class Contact {
    private StringBuilder name;
    private String email;
    private String phone;

    private Reminder remind;
    private ZoneId zone;

    Contact (String fName, String lName, String email, String phone, Reminder r, ZoneId z) {
        this.name = new StringBuilder();
        this.name.append(fName).append(" ").append(lName);
        this.email = email;
        this.phone = phone;
        this.remind = r;
        this.zone = z;
    } //constructor

    public StringBuilder getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
    public Reminder getReminder()
    {
        return remind;
    }

    public ZoneId getZone()
    {
        return zone;
    }
    @Override
    public String toString() {
        String s = this.name + " email: " + this.email + " phone: " + this.phone + " reminder: " + this.remind + " time zone: " + this.zone;
        return s;
    }
}
class Appointment {

    private Contact contact;
    private String appointment_title;
    private String appointment_desc;
    private ZonedDateTime appointmenttime;
    private ZonedDateTime remindertime;



    // accessor and mutator for user
    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public void setAppointment(String apptitle, String apptdesc, ZonedDateTime appt, ZonedDateTime rm)
    {
        this.appointment_title=apptitle;
        this.appointment_desc=apptdesc;
        this.appointmenttime=appt;
        this.remindertime=rm;
    }

    Appointment() //blank constructor
    {

    }

    public void display() //needed to get all info of the object when called
    {
        System.out.println("\n\nTitle: " + this.appointment_title);
        System.out.println("Description: " + this.appointment_desc);
        System.out.println("Client: " + this.contact.toString());
        System.out.println("Appointment Date and Time: " + this.appointmenttime);
        System.out.println("Reminder Time: " + this.remindertime);
    }




}

public class AppointmentApp {

    public static final Scanner input = new Scanner(System.in); //a global scanner input

    private ArrayList<Appointment> clientappts = new ArrayList<>();

    public AppointmentApp() {
    }

    public void addAppointments(Appointment... appointments) {
        for (Appointment A : appointments) {
            clientappts.add(A);
        }
    }

    public static int getRandomMonth() {
        // define the range
        int max = 12;
        int min = 1;
        int range = max - min + 1;

        // generate random numbers within 1 to 10
        int rand = (int) (Math.random() * range) + min;
        System.out.println("Months ahead appt:" + rand); //testing random month
        return (rand);

    }

    public static int getRandomHours() {
        // define the range
        int max = 24;
        int min = 2;
        int range = max - min + 1;

        // generate random numbers within 1 to 10
        int rand = (int) (Math.random() * range) + min;
        //System.out.println("hours before reminder:" + rand); testing random hours
        return (rand);

    }


    public static void main(String[] args) {


        int month, currentyr, currentmonth;
        ZonedDateTime currentTime, appttime, apptdate, reminder;
        var zone = ZoneId.of("US/Eastern");
        int minushours;
        int numberappts;

        //default info
        Reminder R = Reminder.TEXT; //destination of reminder
        Contact client = new Contact("Olivia", "Migiano", "OliviaM@att.net", "904-666-2424", R, zone);
        String apptitle = "Medical Appointment with Dr. IC Spots.";
        String description = "Pending Appointment.";
        AppointmentApp A1 = new AppointmentApp();  //object
       // Appointment clientappt = new Appointment(); //pass info to constructor here
       // clientappt.setContact(client); //set the client info


        System.out.print("Enter number of random appointments for the client: ");
        numberappts = input.nextInt();

        //set appt general information
        for (int i = 0; i < numberappts; i++) //create n random appts
        {
            Appointment clientappt = new Appointment(); //pass info to constructor here
            clientappt.setContact(client); //set the client info
            currentTime = ZonedDateTime.now(); //get current time
            String formattedZdt = currentTime.format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
            ZonedDateTime zoneddatetime = ZonedDateTime.parse(formattedZdt);
            month = AppointmentApp.getRandomMonth(); //get random month
            currentmonth = zoneddatetime.getMonthValue();
            System.out.println(currentmonth);
            currentyr = zoneddatetime.getYear();
            minushours = AppointmentApp.getRandomHours();

            //System.out.println(currentTime + " " + month + " " + currentmonth + " " + currentyr + " " + minushours);

            if (month + currentmonth < 12) {
                apptdate = currentTime.plusMonths(month); //set to random month n months into the future
                //appttime = currentTime.plusHours(0);
                reminder = apptdate.minusHours(minushours);
                clientappt.setAppointment(apptitle, description, apptdate, reminder);
                A1.addAppointments(clientappt);
            } else //overlapped to 13 mos
            {
                apptdate = currentTime.plusYears(1); //add year
                apptdate = apptdate.plusMonths(month + currentmonth - 12); //add month
                //appttime = apptdate.plusHours(0);
                reminder = apptdate.minusHours(minushours);
                clientappt.setAppointment(apptitle, description, apptdate, reminder);
                A1.addAppointments(clientappt);

            }
            //var appointmenttime = ZonedDateTime.of(apptdate, appttime, zone);
            //var remindertime = ZonedDateTime.of(reminder, appttime, zone);
            //clientappt.display();
            //System.out.println(apptitle + " " + description + " " + apptdate);
            //System.out.println(appttime + " " + reminder);
        }
        if (A1.clientappts.isEmpty() == false) {
            for (Appointment A : A1.clientappts) {
                A.display();
            }
        }
        /*
        //create objects
        AppointmentApp A1 = new AppointmentApp();
        Appointment  clientappt = new Appointment(); //pass info to constructor here


        // set the information
        clientappt.setContact(client); //set the client info
        clientappt(apptitle, description, appttime,reminder);
        clientappt.display();
        A1.addAppointments(); //add the created appointmnts
        */

    }
}


