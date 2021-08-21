package helpers;

import logic.DataCache;
import logic.entities.Appointment;

import java.time.ZoneId;
import java.util.List;

/**
 * Abstract class with static methods to organize input validation functions
 */
public abstract class InputValidators {
    /**
     * Validates whether an appointment's start time is before its end time
     * @param appointment Appointment object to check
     * @return boolean status of validation
     */
    public static boolean validateAppointmentStartIsBeforeEnd (Appointment appointment){
        return appointment.getStart().isBefore(appointment.getEnd());
    }

    /**
     * Validates whether an appointment overlaps with an existing appointment for the same customer.
     * Appointment supplied is compared against all existing appointments from customer.
     * Immediately returns true if appointment being checked has the same ID as an existing appointment
     * for customer to guard against false positives.
     *
     * Lambda method is used to allow for clean application of a predicate with Stream API.
     * @param appointment Appointment object to validate
     * @return boolean validation status
     */
    public static boolean validateAppointmentOverlap(Appointment appointment){
        List<Appointment> customerAppointments = DataCache.getAppointmentsByCustomer(appointment.getCustomer());
        return customerAppointments.stream().noneMatch(b -> {
            if (appointment.getAppointmentID() == b.getAppointmentID())
                return false;
            return (appointment.getStart().isAfter(b.getStart())?
                    appointment.getStart()
                    : b.getStart())
                    .isBefore((appointment.getEnd().isBefore(b.getEnd())?
                            appointment.getEnd()
                            : b.getEnd()));
        });
    }

    /**
     * Validates whether the appointment supplied has as start or end time outside of business hours.
     *
     * @param appointment Appointment object to validate
     * @return boolean validation status
     */
    public static boolean validateAppointmentTime(Appointment appointment){
        return appointment.getStart().atZone(ZoneId.of("-05:00")).getHour() >= 8 && appointment.getEnd().atZone(ZoneId.of("-05:00")).getHour() <= 22;
    }
}
