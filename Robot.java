// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.beans.Encoder;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMVictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  private final PWMVictorSPX left1 = new PWMVictorSPX(0);
  private final PWMVictorSPX left2 = new PWMVictorSPX(1);
  private final PWMVictorSPX left3 = new PWMVictorSPX(2);
  private final PWMVictorSPX right1 = new PWMVictorSPX(3);
  private final PWMVictorSPX right2 = new PWMVictorSPX(4);
  private final PWMVictorSPX right3 = new PWMVictorSPX(5);

  

  private final PWMVictorSPX elevator1 = new PWMVictorSPX(6);
  private final PWMVictorSPX elevator2 = new PWMVictorSPX(7);

  private final PWMVictorSPX intake1 = new PWMVictorSPX(8);
  private final PWMVictorSPX intake2 = new PWMVictorSPX(9);

  private final SpeedControllerGroup leftmotors = new SpeedControllerGroup(left1, left2,left3);
  private final SpeedControllerGroup rightmotors = new SpeedControllerGroup(right1, right2,right3);

   private final SpeedControllerGroup elevatormotors = new SpeedControllerGroup(elevator1, elevator2);

  private final SpeedControllerGroup intakemotors = new SpeedControllerGroup(intake1, intake2);

  private final DifferentialDrive drive = new DifferentialDrive(leftmotors,rightmotors);

  private final Joystick joystick = new Joystick (0);
  private final JoystickButton buttonx = new JoystickButton(joystick, 2);
  private final JoystickButton buttony = new JoystickButton(joystick, 3);
  private final JoystickButton buttona = new JoystickButton(joystick, 0);
  private final JoystickButton buttonb = new JoystickButton(joystick, 1);

  private final Timer timer1 = new Timer(); 

  private final Encoder encoder1 = new Encoder (0,1);

  private boolean intaketest = false ;

  private Timer intaketime = new Timer();





  

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    encoder1.setDistancePerPulse(100/360);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
   
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
   if (timer1.get() < 5 ) {
    drive.tankDrive(0.5, 0.5);
     }
    else {
    drive.tankDrive(0.3,0.3);
     }
    
     if(encoder1.get() < 15 ){elevatormotors.set(0.8);    }
     if(encoder1.get() > 15) {elevatormotors.set(-0.8);   }


  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {

    drive.tankDrive(joystick.getRawAxis(1), joystick.getRawAxis(3));

    if(buttona.get()){
      if(encoder1.get() < 15 ){elevatormotors.set(0.8);    }
      if(encoder1.get() > 15) {elevatormotors.set(-0.8);   }

    
    if(buttonx.get()){
      if(encoder1.get() < 40 ){elevatormotors.set(0.8);    }
      if(encoder1.get() > 40) {elevatormotors.set(-0.8);   }
    
    
    if(buttony.get()){
      if(encoder1.get() < 60 ){elevatormotors.set(0.8);    }
      if(encoder1.get() > 60 ) {elevatormotors.set(-0.8);   }  



   }
   if (buttonb.get() && intaketest == false){
    intaketime.start();
    if(intaketime.get()<=5){
      intakemotors.set(-0.8);
      intaketest=true;
   }

   if (buttonb.get() && intaketest == true){
    intaketime.start();
    if(intaketime.get()<=5){
      intakemotors.set(-0.8);
      intaketest=false;
   }






  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
