package com.example.worksheetone;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import java.io.File;
import java.util.Random;

public class HelloController {



    public Slider brightnessSlider;
    public ImageView randImage;
    public Button randButton;
    //public Label thresholdLabel;
    //public Slider thresholdSlider;
    ColorAdjust colorAdjust = new ColorAdjust();

    public GaussianBlur gaussianBlur;
   public  Slider noiseSlider;
   public  Label noiseLabel;

    private JLabel rgbLabel;

    public Button redButton;
    public Button greenButton;
    public ImageView greenImage;
    public ImageView redImage;
    public Button greyButton;
    PixelReader preader; // Used to read the pixel of the image
    PixelWriter pwriter; // Used to write the pixel
    @FXML
    MenuItem browseFile;

    int[] newDisjointArray;

    HashMap<Integer, ArrayList<Integer>> map = new HashMap();
    @FXML
    ImageView colourImage;

    @FXML
    ImageView greyImage;

    @FXML
    MenuItem menuImage;
    Image chosenImage;


    public void initialize(){
        //PixelReader preader = colourImage.getImage().getPixelReader();

        colourImage.getFitWidth();
        colourImage.getFitHeight();

        noiseSlider = new Slider();
        noiseSlider.setShowTickLabels(true);
        noiseSlider.setShowTickMarks(true);
        noiseSlider.setMajorTickUnit(0.25);
        noiseSlider.setBlockIncrement(0.1);
        noiseSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            //update the noise label
            noiseLabel.setText("Noise: " + String.format("%.2f", newValue.doubleValue()));
            //update the image effect
            colourImage.setEffect(createNoiseEffect(newValue.doubleValue()));
        });

        noiseLabel = new Label("Noise: 0.00");

    }

    @FXML
    protected void onHelloButtonClick() {
        //Label welcomeText;
        // welcomeText.setText("Welcome to JavaFX Application!");
    }

    public void displayImage() {
        // myImageView.setImage(myImage);
    }


    //this gaussian blue is an image filtering technique used in image processing
   //
//    public void blur(){
//        gaussianBlur = new GaussianBlur();
//        Slider slider = new Slider(0, 10, 15);
//        slider.setShowTickLabels(true);
//        slider.setShowTickLabels(true);
//
//        slider.valueProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
//                //update the radius of the gaussianBlur effect
//                gaussianBlur.setRadius(t1.doubleValue());
//
//                colourImage.setEffect(gaussianBlur);
//            }
//        });
//
//    }

//   public void noiseMinMax() {
//
//       PixelReader preader = colourImage.getImage().getPixelReader();
//
//     colourImage.getFitWidth();
//     colourImage.getFitHeight();
//
//       noiseSlider = new Slider();
//       noiseSlider.setShowTickLabels(true);
//       noiseSlider.setShowTickMarks(true);
//       noiseSlider.setMajorTickUnit(0.25);
//       noiseSlider.setBlockIncrement(0.1);
//       noiseSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
//           //update the noise label
//           noiseLabel.setText("Noise: " + String.format("%.2f", newValue.doubleValue()));
//           //update the image effect
//           colourImage.setEffect(createNoiseEffect(newValue.doubleValue()));
//       });
//
//       noiseLabel = new Label("Noise: 0.00");
//
//
//   }

   public Effect createNoiseEffect(double noise){
        ColorAdjust colorAdjust1 = new ColorAdjust();
        colorAdjust1.setBrightness(0.1 * noise);
        colorAdjust1.setContrast(0.1 * noise);
        colorAdjust1.setSaturation(0.1 * noise);
        return  colorAdjust1;
   }





    public void onClickChooseFile(ActionEvent actionEvent) {

        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);

        if (selectedFile != null) {
            int width = (int) colourImage.getFitWidth();
            int height = (int) colourImage.getFitHeight();
            chosenImage = new Image(selectedFile.toURI().toString(), width, height, false, true); //toURI is getting the file
            colourImage.setImage(chosenImage);
        }

    }


    public void onClickGreyScale() {
        System.out.println("hello");
        int threshold = 100;
        PixelReader preader = colourImage.getImage().getPixelReader();

        int width = (int) colourImage.getFitWidth();
        int height = (int) colourImage.getFitHeight();

        newDisjointArray = new int[width * height];

        WritableImage grayImage = new WritableImage(width, height);

        for (int loop = 0; loop < height; loop++) {
            for (int column = 0; column < width; column++) {
                int pixel = preader.getArgb(column, loop);

                int red = ((pixel >> 16) & 0xff);
                int green = ((pixel >> 8) & 0xff);
                int blue = (pixel & 0xff);


                int avg = (red + green + blue) / 3;
//                int grey = 0xff000000 + (avg << 16) + (avg << 8) + avg;

                if (avg > threshold) {
                    grayImage.getPixelWriter().setColor(column, loop, Color.WHITE);
                    //goes through each pixel in a loop and runs this formula and gives it a value in a 2d array
                    newDisjointArray[loop * width + column] = loop * width + column;
                    //goes through the pixel below the current pixel
                    //  newDisjointArray[loop + width] = loop + width;


                } else {
                    grayImage.getPixelWriter().setColor(column, loop, Color.BLACK);
                    //goes through each pixel in a loop and runs this formula abd gives it a negative value if the pixel is black.
                    newDisjointArray[loop * width + column] = -1;
                }


//
//
            }


        }
        mergeElements(newDisjointArray, width, height);

        printArray(newDisjointArray, width);

        greyImage.setImage(grayImage);
        storeInHashMap();

        blueCircles();
       // sequentialNumbering();


    }

    public Color randomCol() {
        float col1, col2, col3;
        Random rand = new Random();
        col1 = rand.nextFloat();
        col2 = rand.nextFloat();
        col3 = rand.nextFloat();
        return new Color(col1, col2, col3, 1);
    }

    //this method will get the furthest left x axis point and the bottom right y point
    public void blueCircles() {
        //getting the root in the hash map
        int countt = 0;
        for (int hRoot : map.keySet()) {
            countt ++;
            //print out the root key
            System.out.println(hRoot + " " + map.get(hRoot));

            //the array list is filled with the root and its values
            ArrayList<Integer> rList = map.get(hRoot);
            //rList.get(hRoot);


            //giving the dimensions to use for the image in question
            int width = (int) colourImage.getFitWidth();
            int height = (int) colourImage.getFitHeight();

            //the most left point
            int startingX = width;
            //the highest point in the imag
            int startingY = height;
            int endX = 0;
            int endY = 0;


            float red = 0;
            float blue = 0;
            float green = 0;
int counter =0;

            //If the element we are on in the x-axis is less than startX which is the width
            //then we have found the most left point for this set
            for (int i = 0; i < rList.size(); i++) {
counter++;
                //modulas gets the x-axis
                if (rList.get(i) % width < startingX) {
                    startingX = rList.get(i) % width;
                }

                Color color = findColor(rList.get(i), width);
                red += color.getRed();
                blue += color.getBlue();
                green += color.getGreen();

                // division by the width will get the y-axis position
                //for (int i = 0; i < rList.size(); i++){
                //if the value on the y-axis is above starting y then it is the highest y point
                if (rList.get(i) / width < startingY) {
                    startingY = rList.get(i) / width;
                }


                if (rList.get(i) % width > endX) {
                    endX = rList.get(i) % width;
                }

                if (rList.get(i) / width > endY) {
                    endY = rList.get(i) / width;
                }



            }


            float sulpher = (red / newDisjointArray.length);
            float oxygen = (blue / newDisjointArray.length);
            float hydrogen = (green / newDisjointArray.length);

            if (red > blue && red > green) {
                red = sulpher;
            }

            if (blue > red && blue > green) {
                blue = oxygen;
            }

            if (green > red && green > blue) {
                green = hydrogen;
            }


//find average of each col





            //find the image view in it
            Bounds bounds = colourImage.getLayoutBounds();
            double xScale = bounds.getWidth() / colourImage.getImage().getWidth();
            double yScale = bounds.getHeight() / colourImage.getImage().getHeight();
            startingX *= xScale;
            startingY *= yScale;
            endX *= xScale;
            endY *= yScale;


            int midpointX = startingX + (endX - startingX) / 2;
            int midpointY = startingY + (endY - startingY) / 2;
            //once we
            // startX + (endX - startX)/2; midpointX
            //startY + (endY - startY) / 2; midpointY
            //newCircle(midpointX, midpointY)


            Circle circle = new Circle();
            circle.setCenterX(midpointX);
            circle.setCenterY(midpointY);
            circle.setRadius(Math.max(midpointX - startingX, midpointY - startingY));
            //circle.setRadius(20);
            circle.setStroke(Color.BLUE);
            //circle.setStrokeWidth(2);
            circle.setFill(Color.TRANSPARENT);
            // Translate translate = new Translate();
            //translate.setX();
            //  translate.setY(40);

            circle.setTranslateX(colourImage.getLayoutX());
            circle.setTranslateY(colourImage.getLayoutY());
            //circle.getTransforms().addAll(circle);


            ((Pane) colourImage.getParent()).getChildren().add(circle);


            int finalCounter = counter;
            int finalCountt = countt;
            circle.setOnMouseEntered(event -> {
                int size = map.get(hRoot).size();


                Tooltip tTip = new Tooltip("estimated size (pixel units): " + size +
                        "\n estimated oxygen: " + oxygen +
                        "\n estimated hydrogen: " + hydrogen +
                        "\n estimated sulpher: " + sulpher +
                        "\n celestial object number: " + finalCountt);
                // Tooltip ttTip = new Tooltip("estimated sulpher: " + sulpher);


                Tooltip.install(circle, tTip);
                // Tooltip.install(circle, ttTip);

            });


        }
    }



    public Color findColor(int element, int width) {
        PixelReader preaderr = colourImage.getImage().getPixelReader();
        int col = element % width; // gets the x axis
        int row = element / width; // gets the y axis
        return preaderr.getColor(col, row);


    }


    //this method will give each disjoint set in the image a random color
    public void onClickRandomColorSets() {

        //for each root int the hash map and the values in the set



        int width = (int) greyImage.getFitWidth();
        int height = (int) greyImage.getFitHeight();


        WritableImage randImagee = (WritableImage) greyImage.getImage();

        for (int hhroot : map.keySet()) {
            Color rc = randomCol();


            ArrayList<Integer> rList = map.get(hhroot);
            for (int elem : rList) {
                randImagee.getPixelWriter().setColor(elem % width, elem / width, rc);
                //goes through each pixel in a loop and runs this formula and gives it a value in a 2d array
            }
        }

        randImage.setImage(randImagee);

    }

    public void onNoiseRemoval(){
        int width = (int) greyImage.getFitWidth();
        int height = (int) greyImage.getFitHeight();


        WritableImage randImagee = (WritableImage) greyImage.getImage();

        for (int hhroot : map.keySet()) {
            if (map.size() < 15){
                Slider noiseSlider = new Slider(0, 15, 30);
            }
        }
    }




//   public void luminanceThreshold(){
//
//       int width = (int) colourImage.getFitWidth();
//       int height = (int) colourImage.getFitHeight();
//
//       colorAdjust = new  ColorAdjust();
//
//       thresholdLabel = new Label("Luminance threshold: " +  colorAdjust.;
//       thresholdSlider = new Slider(0,1, colorAdjust.get)
//
//   }


















                public void sequentialNumbering(){
        //create an arraylist with populated with circle objects
                       ArrayList<Circle> cList =new ArrayList<>();
                       for(Object o : ((Pane) colourImage.getParent()).getChildren())
                           //if the object in question is a circle add to list of circles
                        if(o instanceof Circle)
                            cList.add((Circle) o);

                            //sort the circles from smallest to highest
                            //with the largest circle numbered 1
                    //if a is bigger than b the number will be negative
                    //add it to list
                            Collections.sort(cList, (a, b) -> (int) (b.getRadius() - a.getRadius()));
                            System.out.println(cList);
                            int numInt =1;
                            for(Circle c:cList){
                                Text num=new Text(c.getCenterX(),c.getCenterY(),numInt+"");
                                num.setStroke(Color.WHITE);
                                num.setTranslateX(colourImage.getLayoutX());
                                num.setTranslateY(colourImage.getLayoutY());
                                ((Pane) colourImage.getParent()).getChildren().add(num);

                                numInt++;
//                                if (numInt > 30){
//                                    break;
                               // System.out.println("Number of celestial objects in this image: " +numInt);


                                }

                                   System.out.println("Number of celestial objects in this image: " +numInt);
                            }














    public void printArray(int[] disjointArray, int width) {
        for (int i = 0; i < disjointArray.length; i++) {
            if (i % width == 0) {
                System.out.println("");
            }
            System.out.print(disjointArray[i] + " ");
        }
    }

    public void storeInHashMap() {
        //method goes through the array that I created with each pixel with either a positive or negative value
        //if it has a positive value meaning that it is white, check if it has an arraylist created for it.
        //an arraylist will be made of each matching value
        for (int i = 0; i < newDisjointArray.length; i++) {
            if (newDisjointArray[i] >= 0) {
                //retrieving list based on key which is set root
                //the map.get will fetch the value  in the array mapped by key mentioned in the parameter
                ArrayList<Integer> aList = map.get(find(newDisjointArray, i));
                //when we tried to retrieve an arraylist from this element in the array list
                //if there is no arraylist for this element then create one and put the element into a hash map.
                if (aList ==  null) {
                    aList = new ArrayList<>();
                    //the map.put will insert a specific key and the value it is mapping to
                    // a map
                    map.put(find(newDisjointArray, i), aList);
                }

                //aList is valid list in the map
                aList.add(i);



            }
        }

        //show all of the leaders
        for (int root: map.keySet()){
            //show me the leader and all the values stored for that
            System.out.println("root " + root + " values "  + map.get(root));
            int size = map.get(root).size();


        }



    }



    public void mergeElements(int[] disjointArray, int width, int height) {
        //goes through the array
        for (int i = 0; i < disjointArray.length; i++) {
            //if element is greater than 0
            if (disjointArray[i] >= 0) {
                //if next element on the x-axis and is not 0 merge current and next element
                if ((i / width == (i + 1) / width) && disjointArray[i + 1] >= 0) {
                    Union(disjointArray, i, i + 1);
                }//if element underneath current element is greater than 0 merge the elements
                if (i + width < disjointArray.length && disjointArray[i + width] >= 0) {
                    Union(disjointArray, i, i + width);
                }
            }
        }
    }


    public static void Union(int[] disjointArray, int element, int elementTouch) {
        //go to the root of the elements that's touching it and make that element equal to whatever is stored in element
        disjointArray[find(disjointArray, elementTouch)] = find(disjointArray, element);
    }

    //Iterative version of find
//    public static int findOld(int[] a, int id) {
//        while (a[id] != id) id = a[id];
//        return id;
//    }

    //Recursive version of find
    public static int find(int[] a, int id) {
        //added this line which merges black pixels also as would not merge without
        if (a[id] < 0) return a[id];
        if (a[id] == id) return id;
        else return find(a, a[id]);
    }

    //Recursive version of find using the ternary (question mark) operator
//    public static int find3(int[] a, int id) {
//        return a[id] == id ? id : find3(a, a[id]);
//    }

















  public void onClickGreen(ActionEvent actionEvent) {
//        PixelReader preader = colourImage.getImage().getPixelReader();
//
//        int width = (int) colourImage.getFitWidth();
//        int height = (int) colourImage.getFitHeight();
//
//        WritableImage greennImage = new WritableImage(width, height);
//
//        for (int loop = 0; loop < height; loop++) {
//            for (int column = 0; column < width; column++) {
//                int pixel = preader.getArgb(column, loop);
//
//                int red = ((pixel >> 16) & 0xff);
//                int green = ((pixel >> 8) & 0xff);
//                int blue = (pixel & 0xff);
//
//                //int avg = (red + green + blue) / 3;
//                int greenn = 0xff000000 + (green >> 58) + (green << 8);
//
//
//                greennImage.getPixelWriter().setArgb(column, loop, greenn);
//            }
//            greenImage.setImage(greennImage);
//
//
//
//        }
    }

//    public void brightnessAdjust(double value){
//
//        colorAdjust.setBrightness(value);
//        colourImage.setEffect(colorAdjust);
//    }


}

