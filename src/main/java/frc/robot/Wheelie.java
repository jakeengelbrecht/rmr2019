package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

//Activates the solenoids at the front of the robot to angle it to get up onto the step.

public class Wheelie implements Component
{
    //Keeps track of whether we are up(wheelie) or down(normal). up = true, down = false.
    boolean upOrDown;

    //Solenoid
    Solenoid pushRobotUp;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Constructor; initializes all variables.
    public Wheelie()
    {
        //fix RobotMap bit
        pushRobotUp = new Solenoid(RobotMap.INSERT_SOLENOID_HERE);
        upOrDown = false;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void update()
    {
        if(RobotMap.driveController.getRawButton(XboxMap.A)==true)
        {
            //Change boolean to keep track of solenoid state.
            upOrDown = !upOrDown;
            //Push out / bring in solenoid
            pushRobotUp.set(upOrDown);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void autoUpdate()
    {

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void disable()
    {
        //puts robot back on ground when disabled.
        reset();
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void reset()
    {
        //resets everything to default (not extended and upOrDown false)
        upOrDown = false;
        pushRobotUp.set(false);
    }

}