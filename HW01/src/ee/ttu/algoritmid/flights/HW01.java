package ee.ttu.algoritmid.flights;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HW01 implements FlightCrewRegistrationSystem {
    private BST bst = new BST();
    private BSTPilot bstPilot = new BSTPilot();
    private BSTCoPilot bstCoPilot = new BSTCoPilot();
    private BSTFlightAttendant bstFlightAttendant = new BSTFlightAttendant();
    //////////private List<FlightCrewMember> waitingList = new ArrayList<>();


    @Override
    public FlightCrew registerToFlight(FlightCrewMember participant) throws IllegalArgumentException {
        // TODO
        if (!crewMemberCorrect(participant)) {
            throw new IllegalArgumentException();
        } else {

            FlightCrewMember.Role participantRole = participant.getRole();
            if (participantRole.equals(FlightCrewMember.Role.PILOT)) {

                FlightCrewMember copilot = getCopilot(participant);
//                WaitingList.Node node = addNode();
                if (copilot == null) {
                    bst.add(participant);
                    bstPilot.add(participant);
                    return null;
                } else {
                    FlightCrewMember flightAttendant = getFlightAttendantByCopilot(copilot);
                    if (flightAttendant == null) {
                        bst.add(participant);
                        bstPilot.add(participant);
                        return null;
                    } else {
                        removeCrewFromWaitingList(participant, copilot, flightAttendant);
                        return new Crew(participant, copilot, flightAttendant);
                    }
                }
            } else if (participantRole.equals(FlightCrewMember.Role.COPILOT)) {

                FlightCrewMember pilot = getPilotByCopilot(participant);
                FlightCrewMember flightAttendant = getFlightAttendantByCopilot(participant);

                if (pilot == null || flightAttendant == null) {
                    bst.add(participant);
                    bstCoPilot.add(participant);
                    return null;
                } else {

                    removeCrewFromWaitingList(pilot, participant, flightAttendant);
                    return new Crew(pilot, participant, flightAttendant);
                }

            } else if (participantRole.equals(FlightCrewMember.Role.FLIGHT_ATTENDANT)) {

                FlightCrewMember copilot = getCopilot(participant);

                if (copilot == null) {
                    bst.add(participant);
                    bstFlightAttendant.add(participant);
                    return null;
                } else {
                    FlightCrewMember pilot = getPilotByCopilot(copilot);
                    if (pilot == null) {
                        bst.add(participant);
                        bstFlightAttendant.add(participant);
                        return null;
                    } else {
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
        //bst.inorder(); // rooti peab välja mõtlema siia

        return bst.getWaitingList().stream().map(Node::getCrewMember).collect(Collectors.toList());
    }

    private FlightCrewMember getPilotByCopilot(FlightCrewMember copilot) {
        return bstPilot.search(copilot);
    }

    private FlightCrewMember getCopilot(FlightCrewMember member) {
        return bstCoPilot.search(member);
    }


    private FlightCrewMember getFlightAttendantByCopilot(FlightCrewMember copilot) {
        return bstFlightAttendant.search(copilot);
    }

    public void removeCrewFromWaitingList(FlightCrewMember pilot, FlightCrewMember copilot, FlightCrewMember flightAttendant) {
        bst.remove(pilot);
        bstPilot.remove(pilot);
        bst.remove(copilot);
        bstCoPilot.remove(copilot);
        bst.remove(flightAttendant);
        bstFlightAttendant.remove(flightAttendant);
        bst.inorder();
    }


    public boolean crewMemberCorrect(FlightCrewMember participant) {
        return participant != null && participant.getName() != null && !participant.getName().isEmpty() && participant.getWorkExperience() >= 0 && participant.getRole() != null;
    }


    public static void main(String[] args) {
        BST bst = new BST();
        BSTPilot bstPilot = new BSTPilot();
        BSTCoPilot bstCoPilot = new BSTCoPilot();
        BSTFlightAttendant bstFlightAttendant = new BSTFlightAttendant();


        FlightCrewMember member1 = new SomeTests.CrewMemberTemp("copilot", FlightCrewMember.Role.COPILOT, 45);
        FlightCrewMember member2 = new SomeTests.CrewMemberTemp("pilot", FlightCrewMember.Role.PILOT, 10);
        FlightCrewMember member3 = new SomeTests.CrewMemberTemp("pilot2", FlightCrewMember.Role.PILOT, 7);
        FlightCrewMember member4 = new SomeTests.CrewMemberTemp("assistant", FlightCrewMember.Role.FLIGHT_ATTENDANT, 12);

        bstCoPilot.add(member1);
        bstPilot.add(member2);
        bstPilot.add(member3);
        bstFlightAttendant.add(member4);
        bst.add(member1);
        bst.add(member2);
        bst.add(member3);
        bst.add(member4);

        //bstCoPilot.inorder();
        System.out.println(bstPilot.getWaitingList().stream().map(Node::getCrewMember).collect(Collectors.toList()));
    }
}