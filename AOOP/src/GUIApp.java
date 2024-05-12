import Controller.NumberleController;
import Model.INumberleModel;
import Model.NumberleModel;
import View.NumberleView;

public class GUIApp {
    public static void main(String[] args) {

        javax.swing.SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        createAndShowGUI();
                    }
                }
        );
    }

    public static void createAndShowGUI() {
        INumberleModel model = new NumberleModel();
        NumberleController controller = new NumberleController(model);
        while (!controller.isGameOver()) {

        }
        NumberleView view = new NumberleView(model, controller);
    }
}