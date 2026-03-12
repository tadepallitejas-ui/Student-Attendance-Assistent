import java.util.*;

// CO2 – Abstract Data Type
class Subject {
    String name;
    int totalClasses;
    int attendedClasses;

    Subject(String name) {
        this.name = name;
        totalClasses = 0;
        attendedClasses = 0;
    }

    void markPresent() {
        totalClasses++;
        attendedClasses++;
    }

    void markAbsent() {
        totalClasses++;
    }

    double getAttendance() {
        if (totalClasses == 0) return 0;
        return (attendedClasses * 100.0) / totalClasses;
    }
}

public class StudySyncBackend {

    // CO3 – HashMap for fast lookup
    static HashMap<String, Subject> subjectMap = new HashMap<>();

    // CO3 – Stack for recent activities
    static Stack<String> recentActivities = new Stack<>();

    // CO3 – Queue for attendance updates
    static Queue<String> attendanceQueue = new LinkedList<>();

    // CO4 – Tree structure to organize subjects
    static TreeSet<String> subjectTree = new TreeSet<>();

    // CO5 – Graph concept (subject relationships)
    static Map<String, List<String>> timetableGraph = new HashMap<>();


    // Add Subject
    static void addSubject(String name) {

        Subject s = new Subject(name);

        subjectMap.put(name, s);
        subjectTree.add(name);

        recentActivities.push("Added subject: " + name);

        timetableGraph.put(name, new ArrayList<>());
    }


    // Mark Attendance
    static void markAttendance(String name, boolean present) {

        Subject s = subjectMap.get(name);

        if (s == null) {
            System.out.println("Subject not found");
            return;
        }

        if (present) {
            s.markPresent();
            attendanceQueue.add(name + " - Present");
        }
        else {
            s.markAbsent();
            attendanceQueue.add(name + " - Absent");
        }

        recentActivities.push("Attendance updated for " + name);
    }


    // CO1 – Linear Search
    static Subject linearSearch(String name) {

        for (Subject s : subjectMap.values()) {
            if (s.name.equalsIgnoreCase(name)) {
                return s;
            }
        }
        return null;
    }


    // CO1 – Sorting subjects by attendance
    static void sortSubjects() {

        List<Subject> list = new ArrayList<>(subjectMap.values());

        list.sort((a, b) -> Double.compare(
                b.getAttendance(),
                a.getAttendance()
        ));

        System.out.println("\nSorted Subjects by Attendance:");

        for (Subject s : list) {
            System.out.println(s.name + " : " + s.getAttendance() + "%");
        }
    }


    // CO5 – Graph relationship
    static void connectSubjects(String s1, String s2) {

        timetableGraph.get(s1).add(s2);
    }


    // Display Graph
    static void showGraph() {

        System.out.println("\nTimetable Graph:");

        for (String key : timetableGraph.keySet()) {
            System.out.println(key + " -> " + timetableGraph.get(key));
        }
    }


    // Process Queue
    static void processAttendanceQueue() {

        System.out.println("\nProcessing Attendance Queue:");

        while (!attendanceQueue.isEmpty()) {
            System.out.println(attendanceQueue.poll());
        }
    }


    // Display Subjects using Tree
    static void displaySubjects() {

        System.out.println("\nSubjects (Tree Structure):");

        for (String s : subjectTree) {
            System.out.println(s);
        }
    }


    // CO6 – Complete Application
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in); // Scanner declared

        addSubject("Math");
        addSubject("Java");
        addSubject("AI");

        markAttendance("Math", true);
        markAttendance("Math", false);
        markAttendance("Java", true);

        displaySubjects();

        Subject found = linearSearch("Math");

        if (found != null)
            System.out.println("\nMath Attendance: " + found.getAttendance());

        sortSubjects();

        connectSubjects("Math", "Java");
        connectSubjects("Java", "AI");

        showGraph();

        processAttendanceQueue();

        System.out.println("\nRecent Activity: " + recentActivities.peek());

        sc.close();
    }
}
