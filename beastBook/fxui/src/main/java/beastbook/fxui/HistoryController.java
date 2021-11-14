package beastbook.fxui;

import beastbook.core.User;
import javafx.fxml.FXML;
import beastbook.core.Exercise;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import java.io.IOException;

public class HistoryController extends AbstractController {

  @FXML
  private TableView<Exercise> historyTable;

  @FXML
  private Text title;

  @FXML
  private Text date;

  private TableColumn<Exercise, String> exerciseNameColumn;
  private TableColumn<Exercise, Integer> repGoalColumn;
  private TableColumn<Exercise, Double> weightColumn;
  private TableColumn<Exercise, Integer> setsColumn;
  private TableColumn<Exercise, Integer> repsPerSetColumn;
  private TableColumn<Exercise, Integer> restTimeColumn;

  private String historyName;
  private String historyDate;

  @FXML
  public void initialize() throws IOException {
    user = user.loadUser(user.getUserName());
    setTable();
    title.setText(historyName);
    date.setText(historyDate);
  }

  public void setTable() {
    historyTable.getColumns().clear();
    exerciseNameColumn = new TableColumn<>("Exercise name:");
    exerciseNameColumn.setCellValueFactory(
            new PropertyValueFactory<>("exerciseName")
    );
    repGoalColumn = new TableColumn<>("Rep goal:");
    repGoalColumn.setCellValueFactory(
            new PropertyValueFactory<>("repGoal")
    );

    weightColumn = new TableColumn<>("Weight:");
    weightColumn.setCellValueFactory(
            new PropertyValueFactory<>("weight")
    );

    setsColumn = new TableColumn<>("Nr of sets:");
    setsColumn.setCellValueFactory(
            new PropertyValueFactory<>("sets")
    );

    repsPerSetColumn = new TableColumn<>("Reps per set:");
    repsPerSetColumn.setCellValueFactory(
            new PropertyValueFactory<>("repsPerSet")
    );

    restTimeColumn = new TableColumn<>("Rest time in sec:");
    restTimeColumn.setCellValueFactory(
            new PropertyValueFactory<>("restTime")
    );

    historyTable.getColumns().add(exerciseNameColumn);
    historyTable.getColumns().add(repGoalColumn);
    historyTable.getColumns().add(weightColumn);
    historyTable.getColumns().add(setsColumn);
    historyTable.getColumns().add(repsPerSetColumn);
    historyTable.getColumns().add(restTimeColumn);
    customizeHistoryTable();
    historyTable.getItems().setAll(user.getHistory(historyName, historyDate).getSavedWorkout().getExercises());
  }

  private void customizeHistoryTable() {
    exerciseNameColumn.setPrefWidth(100);
    repGoalColumn.setPrefWidth(75);
    weightColumn.setPrefWidth(75);
    setsColumn.setPrefWidth(75);
    repsPerSetColumn.setPrefWidth(80);
    restTimeColumn.setPrefWidth(106);

    /*
    exerciseNameColumn.setStyle("-fx-background-color:lightgrey");
    repGoalColumn.setStyle("-fx-background-color:lightgrey");
    weightColumn.setStyle("-fx-background-color:lightgrey");
    setsColumn.setStyle("-fx-background-color:lightgrey");
    repsPerSetColumn.setStyle("-fx-background-color:lightgrey");
    restTimeColumn.setStyle("-fx-background-color:lightgrey");
    */

  }
  
  TableView<Exercise> getHistoryTable() {
    return historyTable;
  }

  public void setHistoryName(String historyName) {
    this.historyName = historyName;
  }

  public void setHistoryDate(String historyDate) {
    this.historyDate = historyDate;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
