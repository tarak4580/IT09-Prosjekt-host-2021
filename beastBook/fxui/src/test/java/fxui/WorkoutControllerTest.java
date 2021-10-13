package fxui;


import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.matcher.control.TextMatchers;
import core.Exercise;
import core.Workout;
import core.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

public class WorkoutControllerTest extends ApplicationTest{
    
    private WorkoutController wc;
    private User user;

    @Override
    public void start(final Stage stage) throws IOException {
        wc = new WorkoutController();
        final FXMLLoader loader = new FXMLLoader(this.getClass().getResource("Workout.fxml"));
        loader.setController(wc);
        user = new User();
        user.setUserName("test");
        addWorkoutsToUser();
        wc.setUser(user);
        final Parent root = loader.load();
        stage.setScene(new Scene(root));
        stage.show();
    }
   
    @Test
    void testEditSelectedCell() throws IOException{
        Assertions.assertEquals("Benchpress", user.getWorkout("Pull workout").getExercises().get(0).getExerciseName());
        wc.getWorkoutTable().getColumns().get(0).setId("exerciseName");
        Node node = lookup("#exerciseName").nth(1).query();
        doubleClickOn(node, MouseButton.PRIMARY).write("Biceps curl");
        press(KeyCode.ENTER).release(KeyCode.ENTER);
        //FxAssert.verifyThat("#exerciseName", TableViewMatchers.hasTableCell("Biceps curl"));
        Assertions.assertNotEquals("Benchpress", user.getWorkout("Pull workout").getExercises().get(0).getExerciseName());
        Assertions.assertEquals("Biceps curl", user.getWorkout("Pull workout").getExercises().get(0).getExerciseName());
    }

    @Test
    void testExceptionFeedback() throws IOException{
        wc.getWorkoutTable().getColumns().get(1).setId("repGoal");
        Node node = lookup("#repGoal").nth(1).query();
        doubleClickOn(node, MouseButton.PRIMARY).write("-50");
        press(KeyCode.ENTER).release(KeyCode.ENTER);
      
        Assertions.assertNotEquals(-50, user.getWorkout("Pull workout").getExercises().get(0).getRepGoal());
        Assertions.assertEquals(20, user.getWorkout("Pull workout").getExercises().get(0).getRepGoal());
        FxAssert.verifyThat("#exceptionFeedback", TextMatchers.hasText("RepGoal can not be 0 or less than 0. Value was not changed."));

        doubleClickOn(node, MouseButton.PRIMARY).write("50");
        press(KeyCode.ENTER).release(KeyCode.ENTER);

        Assertions.assertNotEquals(-20, user.getWorkout("Pull workout").getExercises().get(0).getRepGoal());
        Assertions.assertEquals(50, user.getWorkout("Pull workout").getExercises().get(0).getRepGoal());      
        FxAssert.verifyThat("#exceptionFeedback", TextMatchers.hasText(""));
    }

    private void addWorkoutsToUser(){
        Workout workout1 = new Workout("Pull workout");
        workout1.addExercise(new Exercise("Benchpress", 20, 30, 40, 50));
        workout1.addExercise(new Exercise("Leg press", 25, 50, 75, 100));
        workout1.addExercise(new Exercise("Deadlift", 20, 20, 20, 20));
        workout1.addExercise(new Exercise("Biceps curl", 20, 20, 20, 20));
        user.addWorkout(workout1);

        wc.setWorkout(workout1);
    }

    @AfterAll
    static void cleanUp() {
        File file = new File(System.getProperty("user.home") + "/test");
        file.delete();
    }

}