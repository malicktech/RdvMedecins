## DB Tables

doctors (This table will have details about the doctor)
patients (This table will have details about the patient)
specialization (It defines the specialization of a doctor eg. Dentist etc.)
TimeSlots (timeslot fo doctor)
appointment_schedule (Doctor will create an appointment schedule from his timeslot)
Appointments

## link : 
http://stackoverflow.com/questions/25563459/db-schema-for-appointment-booking-app-what-is-the-correct-relationship-between

Doctors, Appointments, TimeSlots, Patients?

Question: Given the following information, what is the correct relationship between doctors, patients, appointments, chairs and time_slots?

Patients need to make appointments with a doctor's office. Depending on the type of appointment, each appointment is scheduled for one or more adjacent time_slots, and whether or not there can be two appointments scheduled for time_slots with the same start_time and end_time is determined by appointment type. (Double-booking is allowed based on the type of appointments.)

## App Specs:

Registered users make appointments via an appointment request form on the website.
Appointments take up a certain pre-set amount of adjacent time_slots. 
This is determined by appointment category/type. This length can be adjusted by the admin, as well as the length of each time_slot.
To help speed up the request process, unavailable/already-booked times are hidden from the calendar on the appointment request form.
On the admin-facing interface, administrators can confirm an appointment-request and make an appointment, and they can also update, create and delete scheduled appointments.
All appointments are held in a "chair"--like a dentist's chair. An office has multiple chairs. One patient per chair for a given booked time slot.
Appointments have fields for date, time of day, length appointment_type, double_bookable? (determined by appointment_type). Time_slots have a start_time and end_time, and a date.
There is only one doctor in this office. But, certain types of appointments--that are less demanding of the doc's time-can be double-booked. In essence, two teeth cleanings can be booked for the same time slot, as long as they are held in **separate chairs**.


__
http://stackoverflow.com/questions/16669197/doctor-scheduling-database-design

 would go with dividing the day into segments of 15 or 30 minutes, and creating a record for every doctor for each slot that they are available. It takes care of uniqueness very well.
 An appointment can be a single record and the time slots that it covers can reference the appointment record.

 _________
 http://www.ncbi.nlm.nih.gov/pmc/articles/PMC4288107/

 http://dba.stackexchange.com/questions/68876/optimal-table-design-for-a-medical-center-rental-service-and-recurrent-shifts

 http://forums.mcapoint.com/Thread-MySQL-Doctors-appointment-management-system-Database-Schema-Structuret5


 ###  Database - data modele

Les rendez-vous sont gérés par les tables suivantes :
• [medecins] : contient la liste des médecins du cabinet ;
• [clients] : contient la liste des patienst du cabinet ;
• [creneaux] : contient les créneaux horaires de chacun des médecins ;
• [rv] : contient la liste des rendez-vous des médecins.
• Les tables [roles], [users] et [users_roles] sont des tables liées à l'authentification. 

Les relations entre les tables gérant les rendez-vous sont les suivantes :

• un créneau horaire appartient à un médecin – un médecin a 0 ou plusieurs créneaux horaires : 1:p bidirectionellle
• un rendez-vous réunit à la fois un client et un médecin via un créneau horaire de ce dernier ;
• un client a 0 ou plusieurs rendez-vous : 1:p bidirectionellle
• à un créneau horaire est associé 0 ou plusieurs rendez-vous (à des jours différents)

 contrainte d'unicité sur les valeurs des colonnes jointes (JOUR, ID_CRENEAU) de la table rv, pour empêcher que que deux RV ont été pris au même moment pour le même médecin

[schemas workbench]