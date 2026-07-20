# Tourify – Εφαρμογή Οργάνωσης Ταξιδιών / Travel Planning Application

## Ελληνικά

### Περιγραφή
Το **Tourify** είναι μια εφαρμογή επιφάνειας εργασίας για την οργάνωση ταξιδιών. Αναπτύχθηκε σε **Java** με γραφικό περιβάλλον **Java Swing** και χρησιμοποιεί βάση δεδομένων **MySQL/MariaDB**.

Η εφαρμογή επιτρέπει στον χρήστη να δημιουργήσει λογαριασμό, να συνδεθεί και να οργανώσει βασικά στοιχεία ενός ταξιδιού.

### Βασικές λειτουργίες
- Εγγραφή και σύνδεση χρήστη
- Επιλογή ταξιδιωτικού προορισμού
- Αναζήτηση ξενοδοχείων και διαμονής
- Προβολή και κράτηση δραστηριοτήτων
- Επιλογή μεταφορικών μέσων
- Προβολή και κράτηση προσφορών
- Καταχώριση πληρωμών και προβολή ιστορικού
- Επικοινωνία με την υποστήριξη
- Περιβάλλον διαχειριστή για διαχείριση δεδομένων

### Τεχνολογίες
- Java
- Java Swing
- JDBC
- MySQL ή MariaDB
- phpMyAdmin, προαιρετικά

### Απαιτήσεις
Για την εκτέλεση της εφαρμογής χρειάζονται:

1. **JDK 8** ή νεότερο
2. **MySQL** ή **MariaDB**
3. Ο οδηγός **MySQL Connector/J**
4. Ένα IDE, όπως IntelliJ IDEA, Eclipse ή NetBeans, προαιρετικά

### Ρύθμιση βάσης δεδομένων
1. Εκκινήστε τον MySQL ή MariaDB server.
2. Δημιουργήστε μια βάση δεδομένων με όνομα:

```sql
CREATE DATABASE tourify;
```

3. Εισαγάγετε το αρχείο `tourify.sql` στη βάση δεδομένων.
4. Οι προεπιλεγμένες ρυθμίσεις σύνδεσης του project είναι:

```text
URL: jdbc:mysql://localhost:3306/tourify
Username: root
Password: κενό
```

Αν οι δικές σας ρυθμίσεις είναι διαφορετικές, αλλάξτε τα στοιχεία σύνδεσης στα αρχεία Java.

### Εκτέλεση
Η αρχική κλάση της εφαρμογής είναι η:

```text
user.java
```

Μέσα από IDE, ανοίξτε το project, προσθέστε το MySQL Connector/J στις βιβλιοθήκες και εκτελέστε τη μέθοδο `main` της κλάσης `user`.

Παράδειγμα από γραμμή εντολών σε Windows:

```bash
javac -cp ".;mysql-connector-j.jar" *.java
java -cp ".;mysql-connector-j.jar" user
```

Σε Linux ή macOS χρησιμοποιήστε `:` αντί για `;`:

```bash
javac -cp ".:mysql-connector-j.jar" *.java
java -cp ".:mysql-connector-j.jar" user
```

### Σημαντική σημείωση για την εικόνα
Σε ορισμένα αρχεία υπάρχει απόλυτη διαδρομή εικόνας από τον υπολογιστή του δημιουργού. Για να εμφανίζεται σωστά η εικόνα σε άλλον υπολογιστή, αντικαταστήστε τη διαδρομή με σχετική διαδρομή, για παράδειγμα:

```java
ImageIcon imageIcon = new ImageIcon("tourify.jpg");
```

---

## English

### Description
**Tourify** is a desktop application for organizing trips. It was developed in **Java**, uses **Java Swing** for its graphical interface and stores data in a **MySQL/MariaDB** database.

The application allows users to create an account, log in and organize the main parts of a trip.

### Main features
- User registration and login
- Travel destination selection
- Hotel and accommodation search
- Activity viewing and booking
- Transportation selection
- Offer viewing and booking
- Payment recording and history viewing
- Contact and support form
- Administrator interface for data management

### Technologies
- Java
- Java Swing
- JDBC
- MySQL or MariaDB
- phpMyAdmin, optional

### Requirements
To run the application, you need:

1. **JDK 8** or newer
2. **MySQL** or **MariaDB**
3. **MySQL Connector/J**
4. An IDE such as IntelliJ IDEA, Eclipse or NetBeans, optional

### Database setup
1. Start the MySQL or MariaDB server.
2. Create a database named:

```sql
CREATE DATABASE tourify;
```

3. Import the `tourify.sql` file into the database.
4. The default database settings used by the project are:

```text
URL: jdbc:mysql://localhost:3306/tourify
Username: root
Password: empty
```

If your database settings are different, update the connection information inside the Java files.

### Running the application
The main entry class is:

```text
user.java
```

Using an IDE, open the project, add MySQL Connector/J to the project libraries and run the `main` method of the `user` class.

Windows command-line example:

```bash
javac -cp ".;mysql-connector-j.jar" *.java
java -cp ".;mysql-connector-j.jar" user
```

On Linux or macOS, use `:` instead of `;`:

```bash
javac -cp ".:mysql-connector-j.jar" *.java
java -cp ".:mysql-connector-j.jar" user
```

### Important image note
Some source files contain an absolute image path from the original developer's computer. To display the image correctly on another computer, replace it with a relative path, for example:

```java
ImageIcon imageIcon = new ImageIcon("tourify.jpg");
```

---

## Project structure / Δομή project

```text
Tourify/
├── Main.java
├── user.java
├── Admin.java
├── Accommodation.java
├── Activities.java
├── Destination.java
├── Offer.java
├── Payment.java
├── Profile.java
├── reservation.java
├── transportation.java
├── db.java
├── tourify.sql
├── tourify.jpg
└── README.md
```

## Academic project / Ακαδημαϊκή εργασία

Το project δημιουργήθηκε στο πλαίσιο εργαστηριακής άσκησης του μαθήματος **Τεχνολογία Λογισμικού**.

This project was created as part of a laboratory assignment for the **Software Engineering** course.
