package ee.ttu.algoritmid.flights;

import java.util.List;

public class HW01 implements FlightCrewRegistrationSystem {

    @Override
    public FlightCrew registerToFlight(FlightCrewMember participant) throws IllegalArgumentException {
        // TODO
        if (!crewMemberCorrect(participant)) {
            throw new IllegalArgumentException();
        } else {

        }
        return null;
    }

    @Override
    public List<FlightCrewMember> crewMembersWithoutTeam() {
        // TODO
        return null;
    }

    public boolean crewMemberCorrect(FlightCrewMember participant) {
        return !participant.getName().isEmpty() && participant.getWorkExperience() >= 0 && participant.getRole() != null;
    }
}