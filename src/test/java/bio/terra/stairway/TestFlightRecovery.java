package bio.terra.stairway;

public class TestFlightRecovery extends Flight {

    public TestFlightRecovery(FlightMap inputParameters, Object applicationContext, UserRequestInfo testUser) {
        super(inputParameters, applicationContext, testUser);

        // Step 1 - increment
        addStep(new TestStepIncrement());

        // Step 2 - stop - allow for failure
        addStep(new TestStepStop());

        // Step 3 - increment
        addStep(new TestStepIncrement());
    }

}
