/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.SerialPort;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.CvSource;




public class RobotCamThatWorks extends TimedRobot {

  //camera init
  SerialPort cam;

  //Creates an int to hold the number of times the x or y values exceed their limit for whatever reason.
  int exceedsCap = 0;
  int totalNums = 0;
  int largestX = 0;
  int largestY = 0;
  //UsbCamera jevois = CameraServer.getInstance().startAutomaticCapture();

  @Override
  public void robotInit() {
    cam = new SerialPort(115200, SerialPort.Port.kUSB);
  }
  //this also repeats
  @Override
  public void teleopPeriodic() {
    /*System.out.println("pinging Jevois");
    String cmd = "ping";
    int bytes = cam.writeString(cmd + "\n");
    System.out.println("wrote  "+ bytes + "/" + cmd.length() + "bytes");*/
    //try to read the string
      try{
        if (cam.getBytesReceived() > 0)
        {
          //This just reads the data from the camera and puts it into a string.
          String cameraInput = cam.readString();

          //Outputs the string we just made.
          //System.out.println(cameraInput);

          //Splits the camera data because sometimes it gives us two inputs shoved together
          String [] output = cameraInput.split(" ");

          //Gets rid of invisible characters in the string
          output[output.length - 1] = output[output.length - 1].replaceAll("[^0-9-]", "");
          
          //Creates an array to hold the 4 x and 4 y values
          int [] points = new int[8]; 
          int j = 0; 
          for(int i = output.length-8; i < output.length; i++){
              //puts all STRING points into an INT array so we can use them properly. 
              points[j] = Integer.valueOf(output[i]);   
              j++;  
          }

          //Prints out all the int values in the array.
          for(int i = 0; i < points.length; i++){
            System.out.print(points[i] + ", ");

            totalNums++;

            //Checks even (x values) in the array to make sure they aren't larger than 1000
            if(i%2 == 0)
            {
              if(points[i] > 1000 || points[i] < -1000)
              {
                //Adds 1 to the number of times x or y exceed their cap
                exceedsCap++;
                
              }

              if(points[i] > largestX)
                {
                  largestX = points[i];
                }
            }
            //Checks y values
            else
            {
              if(points[i] > 750 || points[i] < -750)
              {
                exceedsCap++;
              }

              if(points[i] > largestY)
                {
                  largestY = points[i];
                }
            }
          }
          System.out.println(exceedsCap); 
          System.out.println(); 
        }
        else
        {
          //System.out.println("No data read");
        }
      } catch (Exception e) {
        //if it fails, print an error message
        System.out.println("Error: " + e);
      }
      //System.out.println("Total Values:" + totalNums);
      //System.out.println("Total out of bounds:" + exceedsCap);
      //System.out.println("X: " + largestX + "Y: " + largestY);
  }
  @Override
  public void disabledInit()
  {
    System.out.println("Total Values:" + totalNums);
    System.out.println("Total out of bounds:" + exceedsCap);
    System.out.println("X: " + largestX + "Y: " + largestY);
    
  }
}
