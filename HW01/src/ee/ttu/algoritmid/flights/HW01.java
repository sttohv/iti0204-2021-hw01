package ee.ttu.algoritmid.flights;

import java.util.*;
import java.util.stream.Stream;

public class HW01 implements FlightCrewRegistrationSystem {
    private List<FlightCrewMember> waitingList;

    @Override
    public FlightCrew registerToFlight(FlightCrewMember participant) throws IllegalArgumentException {
        // TODO
        if (!crewMemberCorrect(participant)) {
            throw new IllegalArgumentException();
        } else {

            FlightCrewMember.Role participantRole = participant.getRole();
            if (participantRole.equals(FlightCrewMember.Role.PILOT)) {

                Optional<FlightCrewMember> optionalCopilot = getCopilotByPilot(participant);

                if (optionalCopilot.isEmpty()) {
                    waitingList.add(participant);
                    return null;
                } else {
                    FlightCrewMember copilot = optionalCopilot.get();
                    Optional<FlightCrewMember> optionalFlightAttendant = getFlightAttendantByCopilot(copilot);
                    if (optionalFlightAttendant.isEmpty()) {
                        waitingList.add(participant);
                        return null;
                    } else {
                        FlightCrewMember flightAttendant = optionalFlightAttendant.get();
                        removeCrewFromWaitingList(participant, copilot, flightAttendant);
                        return new Crew(participant, copilot, flightAttendant);
                    }
                }
            } else if (participantRole.equals(FlightCrewMember.Role.COPILOT)) {

                Optional<FlightCrewMember> optionalPilot = getPilotByCopilot(participant);
                Optional<FlightCrewMember> optionalFlightAttendant = getFlightAttendantByCopilot(participant);

                if (optionalFlightAttendant.isEmpty() || optionalPilot.isEmpty()) {
                    waitingList.add(participant);
                    return null;
                } else {
                    FlightCrewMember pilot = optionalPilot.get();
                    FlightCrewMember flightAttendant = optionalFlightAttendant.get();
                    removeCrewFromWaitingList(pilot, participant, flightAttendant);
                    return new Crew(pilot, participant, flightAttendant);
                }

            } else if (participantRole.equals(FlightCrewMember.Role.FLIGHT_ATTENDANT)) {

                Optional<FlightCrewMember> optionalCopilot = getCopilotByPilot(participant);

                if (optionalCopilot.isEmpty()) {
                    waitingList.add(participant);
                    return null;
                } else {
                    FlightCrewMember copilot = optionalCopilot.get();
                    Optional<FlightCrewMember> optionalPilot = getPilotByCopilot(copilot);
                    if (optionalPilot.isEmpty()) {
                        waitingList.add(participant);
                        return null;
                    } else {
                        FlightCrewMember pilot = optionalPilot.get();
                        removeCrewFromWaitingList(pilot, copilot, participant);
                        return new Crew(pilot, copilot, participant);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public List<FlightCrewMember> crewMembersWithoutTeam() {
        return waitingList;
    }

    private Optional<FlightCrewMember> getPilotByCopilot(FlightCrewMember copilot) {
        double copilotWorkExperience = copilot.getWorkExperience();
        Stream<FlightCrewMember> pilot = waitingList
                .stream()
                .filter(member -> member.getRole().equals(FlightCrewMember.Role.PILOT)
                        && 10 >= (member.getWorkExperience() - copilotWorkExperience) && 5 <= (member.getWorkExperience()) - copilotWorkExperience);
        return pilot.min(Comparator.comparingDouble(FlightCrewMember::getWorkExperience));
    }

    private Optional<FlightCrewMember> getCopilotByPilot(FlightCrewMember pilot) {
        double pilotWorkExperience = pilot.getWorkExperience();
        Stream<FlightCrewMember> copilots = crewMembersWithoutTeam()
                .stream()
                .filter(crewMember -> (crewMember.getRole().equals(FlightCrewMember.Role.COPILOT)
                        && -10 >= (crewMember.getWorkExperience() - pilotWorkExperience)
                        && (crewMember.getWorkExperience() - pilotWorkExperience) <= -5));
        return copilots.max(Comparator.comparingDouble(FlightCrewMember::getWorkExperience));
    }

    private Optional<FlightCrewMember> getCopilotByFlightAssistant(FlightCrewMember flightAssistant) {
        double flightAssistantWorkExperience = flightAssistant.getWorkExperience();
        Stream<FlightCrewMember> copilots = waitingList
                .stream()
                .filter(member -> member.getRole().equals(FlightCrewMember.Role.COPILOT)
                        && 3 >= (member.getWorkExperience()) - flightAssistantWorkExperience);
        return copilots.min(Comparator.comparingDouble(FlightCrewMember::getWorkExperience));
    }

    //annad sisse copiloti
    private Optional<FlightCrewMember> getFlightAttendantByCopilot(FlightCrewMember copilot) {
        double copilotWorkExperience = copilot.getWorkExperience();
        Stream<FlightCrewMember> flightAttendants = waitingList
                .stream()
                .filter(member -> member.getRole().equals(FlightCrewMember.Role.FLIGHT_ATTENDANT)
                        && 3 >= (copilotWorkExperience - member.getWorkExperience()));
        return flightAttendants.max(Comparator.comparingDouble(FlightCrewMember::getWorkExperience));
    }

    public void removeCrewFromWaitingList(FlightCrewMember pilot, FlightCrewMember copilot, FlightCrewMember flightAttendant) {
        waitingList.remove(pilot);
        waitingList.remove(copilot);
        waitingList.remove(flightAttendant);
    }


    public boolean crewMemberCorrect(FlightCrewMember participant) {
        return !participant.getName().isEmpty() && participant.getWorkExperience() >= 0 && participant.getRole() != null;
    }
}