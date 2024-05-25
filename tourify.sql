-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Εξυπηρετητής: 127.0.0.1
-- Χρόνος δημιουργίας: 25 Μάη 2024 στις 12:59:20
-- Έκδοση διακομιστή: 10.4.28-MariaDB
-- Έκδοση PHP: 8.0.28

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Βάση δεδομένων: `tourify`
--

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `activities`
--

CREATE TABLE `activities` (
  `activity_id` int(11) NOT NULL,
  `activity_location` text NOT NULL,
  `activity_type` text NOT NULL,
  `activity_description` text NOT NULL,
  `activity_price` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `activities`
--

INSERT INTO `activities` (`activity_id`, `activity_location`, `activity_type`, `activity_description`, `activity_price`) VALUES
(1, 'Αθήνα', 'Μουσείο', 'Επίσκεψη στο Μουσείο Ακρόπολης, ένα από τα σημαντικότερα αρχαιολογικά μουσεία στον κόσμο.', 15),
(2, 'Αθήνα', 'Αξιοθέατα', 'Περιήγηση στην Πλάκα και την Αρχαία Αγορά, μία από τις πιο γραφικές γειτονιές της πόλης.', 5),
(3, 'Αθήνα', 'Νυχτερινή ζωή', 'Βραδιά στα μπαρ και τα κλαμπ του Γκάζι, το κέντρο της νυχτερινής ζωής της Αθήνας.', 20),
(4, 'Θεσσαλονίκη', 'Εστιατόρια', 'Γεύμα σε παραδοσιακό ταβερνάκι με θέα την Παραλία Θεσσαλονίκης.', 25),
(5, 'Θεσσαλονίκη', 'Μουσείο', 'Επίσκεψη στο Βυζαντινό Μουσείο, πλούσια συλλογή με εκθέματα από τη Βυζαντινή εποχή.', 10),
(6, 'Βόλος', 'Εστιατόρια', 'Δοκιμάστε τσιπουράδικα στον Βόλο με παραδοσιακούς μεζέδες.', 15),
(7, 'Βόλος', 'Extreme sports', 'Ιστιοπλοΐα στον Παγασητικό κόλπο, ιδανικές συνθήκες για ιστιοπλοΐα.', 50),
(8, 'Ιωάννινα', 'Αξιοθέατα', 'Επίσκεψη στη λίμνη Παμβώτιδα και το νησί της Λίμνης, ένας μαγευτικός τόπος με ιστορία.', 10),
(9, 'Ιωάννινα', 'Extreme sports', 'Πεζοπορία στα Ζαγοροχώρια, απόλαυση της φύσης και των παραδοσιακών γεφυριών.', 30),
(10, 'Χανιά', 'Παραλίες', 'Ημέρα στην Ελαφονήσι, με την εξωτική της άμμο και τα κρυστάλλινα νερά.', 10),
(11, 'Χανιά', 'Εστιατόρια', 'Δείπνο σε ψαροταβέρνα στα Χανιά με φρέσκο ψάρι και θαλασσινά.', 40),
(12, 'Χανιά', 'Νυχτερινή ζωή', 'Ποτό στον Παλιό Λιμανι των Χανίων, τοπικά κοκτέιλ και ζωντανή μουσική.', 15),
(13, 'Βόλος', 'Παραλίες', 'Μπάνιο στην παραλία της Αγριάς, ιδανική για χαλάρωση και ηλιοθεραπεία.', 5),
(14, 'Ιωάννινα', 'Μουσείο', 'Επίσκεψη στο Μουσείο Αργυροτεχνίας, με εκθέματα που παρουσιάζουν την τοπική τέχνη και παράδοση.', 8),
(15, 'Θεσσαλονίκη', 'Αξιοθέατα', 'Περπάτημα στον Λευκό Πύργο και τον παρακείμενο πεζόδρομο με θέα το Θερμαϊκό.', 3);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `book_activity`
--

CREATE TABLE `book_activity` (
  `id_of_booking` int(11) NOT NULL,
  `id` int(11) NOT NULL,
  `email` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `book_activity`
--

INSERT INTO `book_activity` (`id_of_booking`, `id`, `email`) VALUES
(1, 10, 'dokimastiko@gmail.com'),
(2, 11, 'ddd@gmail.com'),
(4, 8, 'dok@gmail.com'),
(5, 1, 'dok@gmail.com'),
(6, 12, 'sdk@gmail.com'),
(7, 7, 'grehgiu@gmail.com'),
(8, 10, 'alex@gmail.com'),
(9, 3, 'nik_g@gmail.com');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `book_offer`
--

CREATE TABLE `book_offer` (
  `book_offer_id` int(11) NOT NULL,
  `email` text NOT NULL,
  `offer_description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `book_offer`
--

INSERT INTO `book_offer` (`book_offer_id`, `email`, `offer_description`) VALUES
(2, 'dokimastiko@gmail.com', '04/07 με 05/07!!! 2 βραδιά 150 ευρώ στο ξενοδοχείο Mountain High στον Βόλο.'),
(3, 'sdp@gmail.com', '03/08 με 05/08!!! 3 βραδιά 220 ευρώ στο ξενοδοχείο Seaside Resort στα Χανιά.'),
(4, 'nik_g@gmail.com', '09/07 με 10/07!!! 2 βραδιά 200 ευρώ στο ξενοδοχείο Marina Sands στα Χανιά.');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `destinations`
--

CREATE TABLE `destinations` (
  `destination_id` int(11) NOT NULL,
  `destination_name` text NOT NULL,
  `dest_description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `destinations`
--

INSERT INTO `destinations` (`destination_id`, `destination_name`, `dest_description`) VALUES
(1, 'Αθήνα', 'The capital of Greece, known for its rich history and landmarks like the Acropolis.'),
(2, 'Θεσσαλονίκη', 'The second largest city in Greece, famous for its vibrant cultural scene and festivals.'),
(3, 'Ιωάννινα', 'A city in northwestern Greece, noted for its beautiful lake and historic castle.'),
(4, 'Βόλος', 'A coastal port city in Greece, renowned for its waterfront and proximity to Mount Pelion.'),
(5, 'Χανιά', 'A city on the island of Crete, known for its picturesque Venetian harbor and old town.');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `hotels`
--

CREATE TABLE `hotels` (
  `accomodation_id` int(11) NOT NULL,
  `hotel_name` text NOT NULL,
  `hotel_address` text NOT NULL,
  `destination_name` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `hotels`
--

INSERT INTO `hotels` (`accomodation_id`, `hotel_name`, `hotel_address`, `destination_name`) VALUES
(1, 'Hotel Grande Athen', 'Πλατεία Συντάγματος 1, Αθήνα', 'Αθήνα'),
(2, 'Athenian Riviera Hotel', 'Λεωφόρος Ποσειδώνος 32, Αθήνα', 'Αθήνα'),
(3, 'The White Tower Hotel', 'Λεωφόρος Νίκης 45, Θεσσαλονίκη', 'Θεσσαλονίκη'),
(4, 'Macedonia Palace', 'Μεγάλου Αλεξάνδρου 2, Θεσσαλονίκη', 'Θεσσαλονίκη'),
(5, 'Volos Palace Hotel', 'Ξενοφώντος και Θράκης, Βόλος', 'Βόλος'),
(6, 'Hotel Jason', 'Αργοναυτών 1, Βόλος', 'Βόλος'),
(7, 'Epirus Lux Palace', 'Μιχαήλ Αγγέλου 18, Ιωάννινα', 'Ιωάννινα'),
(8, 'Hotel Du Lac Congress Center', 'Ακτή Μιαούλη 2, Ιωάννινα', 'Ιωάννινα'),
(9, 'Samaria Hotel', 'Κίδωνος 69, Χανιά', 'Χανιά'),
(10, 'Creta Palm Resort', 'Κάτω Σταλός, Χανιά', 'Χανιά');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `issues`
--

CREATE TABLE `issues` (
  `issue_id` int(11) NOT NULL,
  `email` text NOT NULL,
  `issue_description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `issues`
--

INSERT INTO `issues` (`issue_id`, `email`, `issue_description`) VALUES
(1, 'hhhh@gmail.com', 'thema me pliromi'),
(2, 'aaa@gmail.com', 'kollaei'),
(4, 'dokimh@gmail.com', 'thema me krathsh'),
(5, 'sdk@gmail.com', 'akirosi kratisis amesa'),
(6, 'nik_g@gmail.com', 'thelo na akiroso mia kratisi');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `offers`
--

CREATE TABLE `offers` (
  `offer_id` int(11) NOT NULL,
  `offer_description` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `offers`
--

INSERT INTO `offers` (`offer_id`, `offer_description`) VALUES
(1, '19/8 με 20/8!!! 2 βραδιά 150 ευρώ στο ξενοδοχείο Grand Palace στην Θεσσαλονίκη. Προλάβετε!!!'),
(2, '02/08 με 03/08!!! 2 βραδιά 180 ευρώ στο ξενοδοχείο Acropolis View στην Αθήνα. Κλείστε σύντομα!!!'),
(3, '01/07 με 03/07!!! 3 βραδιά 210 ευρώ στο ξενοδοχείο Nefeli στον Βόλο. Μοναδική ευκαιρία!!!'),
(4, '09/07 με 10/07!!! 2 βραδιά 200 ευρώ στο ξενοδοχείο Marina Sands στα Χανιά.'),
(5, '10/08 με 12/08!!! 3 βραδιά 250 ευρώ στο ξενοδοχείο Lake View στα Ιωάννινα.'),
(6, '13/07 με 14/07!!! 2 βραδιά 160 ευρώ στο ξενοδοχείο City Central στην Θεσσαλονίκη.'),
(7, '11/08 με 12/08!!! 2 βραδιά 170 ευρώ στο ξενοδοχείο Urban Retreat στην Αθήνα.'),
(8, '03/08 με 05/08!!! 3 βραδιά 220 ευρώ στο ξενοδοχείο Seaside Resort στα Χανιά.'),
(9, '04/07 με 05/07!!! 2 βραδιά 150 ευρώ στο ξενοδοχείο Mountain High στον Βόλο.'),
(10, '01/08 με 02/08!!! 2 βραδιά 190 ευρώ στο ξενοδοχείο Panoramic στα Ιωάννινα.');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `payments`
--

CREATE TABLE `payments` (
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `email` text NOT NULL,
  `check_in` date NOT NULL,
  `check_out` date NOT NULL,
  `guests` double NOT NULL,
  `total_cost` double NOT NULL,
  `hotel_name` text NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `payments`
--

INSERT INTO `payments` (`name`, `surname`, `email`, `check_in`, `check_out`, `guests`, `total_cost`, `hotel_name`, `id`) VALUES
('ΚΩΣΤΑΣ ', 'ΑΝΤΕΤΟΚΟΥΜΠΟ', 'kostakis@gmail.com', '2024-08-12', '2024-08-15', 8, 300, 'Epirus Lux Palace', 1),
('ΓΕΩΡΓΙΟΣ', 'ΠΡΙΝΤΕΖΗΣ', 'pripri@gmail.com', '2024-08-01', '2024-08-04', 4, 300, 'Creta Palm Resort', 2),
('ΓΙΑΝΝΗΣ', 'ΑΝΤΕΤΟΚΟΥΜΠΟ', 'greekfreak@gmail.com', '2024-08-24', '2024-08-27', 4, 300, 'Volos Palace Hotel', 4),
('ΝΙΚΟΣ', 'ΠΑΠΠΑΣ', 'nikkkk@gmaill.com', '2024-08-10', '2024-08-15', 4, 500, 'Samaria Hotel', 5),
('Νίκος', 'Γιαννόπουλος', 'nik_g@gmail.com', '0024-07-02', '0024-07-04', 3, 200, 'Creta Palm Resort', 12);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `register`
--

CREATE TABLE `register` (
  `onoma` varchar(255) NOT NULL,
  `eponimo` varchar(255) NOT NULL,
  `tilefono` double NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` text NOT NULL,
  `verify_password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `register`
--

INSERT INTO `register` (`onoma`, `eponimo`, `tilefono`, `email`, `password`, `verify_password`) VALUES
('ΑΛΕΞΑΝΔΡΟΣ', 'ΚΟΛΛΩΣΗΣ', 6907683030, 'alex_kol@gmail.com', 'alex131084562', 'alex131084562'),
('Δημήτρης', 'Παπαδόπουλος', 6903453420, 'dim_pap@gmail.com', '220507dp', '220507dp'),
('ΜΙΛΤΟΣ', 'ΚΟΥΡΑΚΟΣ', 6977777777, 'miltoscurry@gmail.com', 'στεπηεν', 'στεπηεν'),
('ΝΙΚΟΣ', 'ΠΑΠΑΔΟΥΡΗΣ', 6988745610, 'nik_pap@gmail.com', 'nikos12345', 'nikos12345'),
('KENDRICK', 'NUNN', 6913131313, 'nunnbetter@gmail.com', 'nunnbetter13', 'nunnbetter13');

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `reservations`
--

CREATE TABLE `reservations` (
  `id` int(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `surname` varchar(255) NOT NULL,
  `email` text NOT NULL,
  `check_in` date NOT NULL,
  `check_out` date NOT NULL,
  `guests` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `reservations`
--

INSERT INTO `reservations` (`id`, `name`, `surname`, `email`, `check_in`, `check_out`, `guests`) VALUES
(3, 'ΚΩΣΤΑΣ', 'ΣΛΟΥΚΑΣ', 'slouki_look@gmail.com', '2024-07-10', '2024-07-11', 3),
(4, 'ΙΩΑΝΝΗΣ ', 'ΠΑΠΑΠΕΤΡΟΥ', 'pap_33@gmail.com', '2024-07-15', '2024-07-17', 4),
(5, 'ΠΑΝΑΓΙΩΤΗΣ', 'ΚΑΛΑΙΤΖΑΚΗΣ', 'panos_kal@gmail.com', '2024-07-09', '2024-07-12', 5),
(6, 'ΚΩΣΤΑΣ ', 'ΑΝΤΕΤΟΚΟΥΜΠΟ', 'kostakis@gmail.com', '2024-08-12', '2024-08-15', 8),
(7, 'ΓΕΩΡΓΙΟΣ', 'ΠΡΙΝΤΕΖΗΣ', 'pripri@gmail.com', '2024-08-01', '2024-08-04', 4),
(9, 'ΓΙΑΝΝΗΣ', 'ΑΝΤΕΤΟΚΟΥΜΠΟ', 'greekfreak@gmail.com', '2024-08-24', '2024-08-27', 4),
(10, 'ΝΙΚΟΣ', 'ΠΑΠΠΑΣ', 'nikkkk@gmaill.com', '2024-08-10', '2024-08-15', 4),
(11, 'MILTOS', 'KOURAKOS', 'miltoscurry@gmail.com', '2024-07-10', '2024-07-12', 4),
(17, 'Νίκος', 'Γιαννόπουλος', 'nik_g@gmail.com', '0024-07-02', '0024-07-04', 3);

-- --------------------------------------------------------

--
-- Δομή πίνακα για τον πίνακα `transportation`
--

CREATE TABLE `transportation` (
  `full_name` varchar(255) NOT NULL,
  `starting_point` text NOT NULL,
  `destination` text NOT NULL,
  `date_time` text NOT NULL,
  `tickets` int(11) NOT NULL,
  `total_cost` int(11) NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Άδειασμα δεδομένων του πίνακα `transportation`
--

INSERT INTO `transportation` (`full_name`, `starting_point`, `destination`, `date_time`, `tickets`, `total_cost`, `id`) VALUES
('ΓΙΑΝΝΗΣ ΠΑΠΑΔΗΜΗΤΡΙΟΥ', 'Χανιά', 'Αθήνα', '08/06/2024 10:00 AM', 4, 80, 1),
('ΝΙΚΟΣ ΠΑΠΠΑΣ', 'Αθήνα', 'Χανιά', '08/06/2024 10:00 AM', 4, 80, 2),
('ΔΟΚΙΜΗ ΠΡΩΤΗ', 'Αθήνα', 'Χανιά', '30/06/2024 12:00 PM', 4, 80, 3),
('ΝΙΚΟΣ ΓΙΑΝΝΟΠΟΥΛΟΣ', 'Αθήνα', 'Θεσσαλονίκη', '02/06/2024 10:00 AM', 3, 60, 5);

--
-- Ευρετήρια για άχρηστους πίνακες
--

--
-- Ευρετήρια για πίνακα `activities`
--
ALTER TABLE `activities`
  ADD PRIMARY KEY (`activity_id`);

--
-- Ευρετήρια για πίνακα `book_activity`
--
ALTER TABLE `book_activity`
  ADD PRIMARY KEY (`id_of_booking`);

--
-- Ευρετήρια για πίνακα `book_offer`
--
ALTER TABLE `book_offer`
  ADD PRIMARY KEY (`book_offer_id`);

--
-- Ευρετήρια για πίνακα `destinations`
--
ALTER TABLE `destinations`
  ADD PRIMARY KEY (`destination_id`);

--
-- Ευρετήρια για πίνακα `hotels`
--
ALTER TABLE `hotels`
  ADD PRIMARY KEY (`accomodation_id`);

--
-- Ευρετήρια για πίνακα `issues`
--
ALTER TABLE `issues`
  ADD PRIMARY KEY (`issue_id`);

--
-- Ευρετήρια για πίνακα `offers`
--
ALTER TABLE `offers`
  ADD PRIMARY KEY (`offer_id`);

--
-- Ευρετήρια για πίνακα `payments`
--
ALTER TABLE `payments`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `register`
--
ALTER TABLE `register`
  ADD PRIMARY KEY (`email`);

--
-- Ευρετήρια για πίνακα `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`id`);

--
-- Ευρετήρια για πίνακα `transportation`
--
ALTER TABLE `transportation`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT για άχρηστους πίνακες
--

--
-- AUTO_INCREMENT για πίνακα `activities`
--
ALTER TABLE `activities`
  MODIFY `activity_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT για πίνακα `book_activity`
--
ALTER TABLE `book_activity`
  MODIFY `id_of_booking` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT για πίνακα `book_offer`
--
ALTER TABLE `book_offer`
  MODIFY `book_offer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT για πίνακα `destinations`
--
ALTER TABLE `destinations`
  MODIFY `destination_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT για πίνακα `hotels`
--
ALTER TABLE `hotels`
  MODIFY `accomodation_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT για πίνακα `issues`
--
ALTER TABLE `issues`
  MODIFY `issue_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT για πίνακα `offers`
--
ALTER TABLE `offers`
  MODIFY `offer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT για πίνακα `payments`
--
ALTER TABLE `payments`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT για πίνακα `reservations`
--
ALTER TABLE `reservations`
  MODIFY `id` int(255) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT για πίνακα `transportation`
--
ALTER TABLE `transportation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
