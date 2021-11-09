package ee.ttu.algoritmid.flights;

public class Crew implements FlightCrew{
    private FlightCrewMember pilot;
    private FlightCrewMember copilot;
    private FlightCrewMember flightAttendant;

    public Crew(FlightCrewMember pilot, FlightCrewMember copilot, FlightCrewMember flightAttendant){
        this.pilot = pilot;
        this.copilot = copilot;
        this.flightAttendant = flightAttendant;
    }
    @Override
    public FlightCrewMember getPilot() {
        return null;
    }

    @Override
    public FlightCrewMember getCopilot() {
        return null;
    }

    @Override
    public FlightCrewMember getFlightAttendant() {
        return null;
    }
}
