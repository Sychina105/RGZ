package com.example.rgz;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    private Model model;
    private View view;

    @Override
    public void start(Stage primaryStage) {
        model = new Model();
        view = new View();

        // Создаем контроллеры для параметров модели
        ParameterController widthController = new ParameterController(model.widthProperty());
        ParameterController heightController = new ParameterController(model.heightProperty());

        // Устанавливаем обработчики изменений параметров в представление
        view.setWidthController(widthController);
        view.setHeightController(heightController);

        // Создаем сцену и устанавливаем на нее представление
        Scene scene = new Scene(view.getView(), 400, 300);

        // Устанавливаем сцену на подмостки и отображаем их
        primaryStage.setScene(scene);
        primaryStage.setTitle("Model Parameters");
        primaryStage.show();
    }

    // Класс модели, представляющий параметры физического объекта
    class Model {
        private DoubleProperty width = new SimpleDoubleProperty(200);
        private DoubleProperty height = new SimpleDoubleProperty(100);

        public double getWidth() {
            return width.get();
        }

        public void setWidth(double width) {
            this.width.set(width);
        }

        public DoubleProperty widthProperty() {
            return width;
        }

        public double getHeight() {
            return height.get();
        }

        public void setHeight(double height) {
            this.height.set(height);
        }

        public DoubleProperty heightProperty() {
            return height;
        }
    }

    // Класс представления, отображающий интерфейс
    class View {
        private GridPane view;
        private TextField widthField;
        private TextField heightField;
        private Canvas canvas;
        private GraphicsContext gc;

        public View() {
            view = new GridPane();
            view.setHgap(10);
            view.setVgap(10);

            widthField = new TextField();
            heightField = new TextField();

            Label widthLabel = new Label("Width:");
            Label heightLabel = new Label("Height:");

            canvas = new Canvas(300, 200);
            gc = canvas.getGraphicsContext2D();

            redrawObject();

            view.add(widthLabel, 0, 0);
            view.add(widthField, 1, 0);
            view.add(heightLabel, 0, 1);
            view.add(heightField, 1, 1);
            view.add(canvas, 0, 2, 2, 1);
        }

        public void setWidthController(ParameterController controller) {
            widthField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.isEmpty() && newValue.matches("\\d*\\.?\\d*")) {
                    controller.setValue(Double.parseDouble(newValue));
                    redrawObject();
                }
            });
        }

        public void setHeightController(ParameterController controller) {
            heightField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue.isEmpty() && newValue.matches("\\d*\\.?\\d*")) {
                    controller.setValue(Double.parseDouble(newValue));
                    redrawObject();
                }
            });
        }

        public void redrawObject() {
            gc.clearRect(0, 0, 300, 200);
            gc.setFill(Color.BLUE);
            gc.fillRect(50, 50, model.getWidth(), model.getHeight());
        }

        public GridPane getView() {
            return view;
        }
    }

    // Класс контроллера параметра
    class ParameterController {
        private DoubleProperty value;

        public ParameterController(DoubleProperty value) {
            this.value = value;
        }

        public void setValue(double value) {
            this.value.set(value);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
