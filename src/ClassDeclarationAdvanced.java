/**
 * Advanced: Multiple related classes working together.
 * School system: Student, Teacher, Classroom.
 */
import java.util.*;

public class ClassDeclarationAdvanced {

    static class Student {
        private final String name;
        private final int    studentId;
        private final List<Integer> grades = new ArrayList<>();
        private static int idCounter = 1001;

        Student(String name) {
            this.name      = name;
            this.studentId = idCounter++;
        }

        void addGrade(int grade) { grades.add(grade); }

        double average() {
            return grades.isEmpty() ? 0 :
                grades.stream().mapToInt(Integer::intValue).average().orElse(0);
        }

        String letterGrade() {
            double avg = average();
            if (avg >= 90) return "A";
            if (avg >= 80) return "B";
            if (avg >= 70) return "C";
            if (avg >= 60) return "D";
            return "F";
        }

        @Override public String toString() {
            return String.format("Student[%d] %-10s avg=%.1f (%s)", studentId, name, average(), letterGrade());
        }
    }

    static class Teacher {
        final String name;
        final String subject;
        Teacher(String name, String subject) { this.name=name; this.subject=subject; }

        void grade(Student s, int... scores) {
            for (int sc : scores) s.addGrade(sc);
            System.out.printf("  %s graded %s: %s → avg %.1f%n",
                name, s.name, Arrays.toString(scores), s.average());
        }
    }

    static class Classroom {
        final String roomName;
        final Teacher teacher;
        final List<Student> students = new ArrayList<>();

        Classroom(String roomName, Teacher teacher) {
            this.roomName = roomName; this.teacher = teacher;
        }

        void enroll(Student s)  { students.add(s); }
        void printReport() {
            System.out.println("\n=== " + roomName + " — " + teacher.subject + " ===");
            students.forEach(s -> System.out.println("  " + s));
            double classAvg = students.stream().mapToDouble(Student::average).average().orElse(0);
            System.out.printf("  Class average: %.1f%n", classAvg);
        }
    }

    public static void main(String[] args) {
        Teacher t1 = new Teacher("Mr. Smith", "Mathematics");
        Teacher t2 = new Teacher("Ms. Jones", "Science");

        Student alice = new Student("Alice");
        Student bob   = new Student("Bob");
        Student carol = new Student("Carol");

        Classroom math    = new Classroom("Room 101", t1);
        Classroom science = new Classroom("Room 202", t2);

        math.enroll(alice); math.enroll(bob); math.enroll(carol);
        science.enroll(alice); science.enroll(carol);

        System.out.println("=== Grading ===");
        t1.grade(alice, 95, 88, 92);
        t1.grade(bob,   70, 75, 68);
        t1.grade(carol, 85, 90, 87);
        t2.grade(alice, 88, 91, 94);
        t2.grade(carol, 78, 82, 80);

        math.printReport();
        science.printReport();
    }
}
