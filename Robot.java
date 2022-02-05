// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.lang.Math;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.XboxController;
//import com.kaualilabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.MecanumDrive;


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
  private double speedDecreaser = 1;
  XboxController controller = new XboxController(0);//change port value later
  CANSparkMax frontLeftMotor = new CANSparkMax(1, MotorType.kBrushless);
  CANSparkMax frontRightMotor = new CANSparkMax(2,MotorType.kBrushless);
  CANSparkMax backLeftMotor = new CANSparkMax(3, MotorType.kBrushless);
  CANSparkMax backRightMotor = new CANSparkMax(4, MotorType.kBrushless);
  CANSparkMax climbFrontRightMotor = new CANSparkMax(5, MotorType.kBrushless);
  CANSparkMax climbFrontLeftMotor = new CANSparkMax(7, MotorType.kBrushless);
  CANSparkMax climbBackRightMotor = new CANSparkMax(6, MotorType.kBrushless);
  CANSparkMax climbBackLeftMotor = new CANSparkMax(8, MotorType.kBrushless);
  MecanumDrive drive;

  //AHRS gyro = new AHRS(SPI.Port.kMXP);

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    frontLeftMotor.setInverted(true);
    backLeftMotor.setInverted(true);
    drive = new MecanumDrive(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  
    
    


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
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    //gyro.calibrate();
    //gyro.reset();
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    SmartDashboard.putNumber("Speed:", speedDecreaser);
    if(controller.getRightBumper()){
     speedDecreaser = 0.5;

    }
    if(controller.getLeftBumper()){
      speedDecreaser = 1;
 
     }
     
     if(controller.getLeftTriggerAxis() > 0.5 && controller.getRightTriggerAxis() >0.5){

     }
     else if(controller.getRightTriggerAxis() >0.5){
       climbFrontLeftMotor.set(-0.25*(controller.getRightTriggerAxis()-.5));
       climbFrontRightMotor.set(-0.25*(controller.getRightTriggerAxis()-.5));
       climbBackLeftMotor.set(-0.25*(controller.getRightTriggerAxis()-.5));
       climbBackRightMotor.set(-0.25*(controller.getRightTriggerAxis()-.5));
     }
     else if(controller.getLeftTriggerAxis() >0.5){
      climbFrontLeftMotor.set(0.25*(controller.getRightTriggerAxis()-.5));
      climbFrontRightMotor.set(0.25*(controller.getRightTriggerAxis()-.5));
      climbBackLeftMotor.set(0.25*(controller.getRightTriggerAxis()-.5));
      climbBackRightMotor.set(0.25*(controller.getRightTriggerAxis()-.5));    }
    drive.driveCartesian(controller.getLeftY()*speedDecreaser, controller.getLeftX()*speedDecreaser, controller.getRightX()*speedDecreaser);

    /*if(Math.abs(controller.getLeftY())>.1)
      y = 0.25*controller.getLeftY();
    else
      y=0;
    if(Math.abs(controller.getLeftX())>.1)
      x = 0.25*controller.getLeftX();
    else
      x=0;
    if(Math.abs(controller.getRightX())>.1)
      z = 0.25*controller.getRightX();
    else 
      z=0;
    motorSpeed[0] = -x + y - z;
    motorSpeed[1] = x - y - z;
    motorSpeed[2] = x + y - z;
    motorSpeed[3] = x + y + z;
    for(double speed : motorSpeed){
      if(Math.abs(speed)> highestSpeed ){
        highestSpeed = Math.abs(speed);
      }
    }
    if(highestSpeed > 1.0){
        for(int index = 0; index<motorSpeed.length; index++){
          motorSpeed[index] = motorSpeed[index]/highestSpeed;
        }
    }
    frontLeftMotor.set(motorSpeed[0]);
    frontRightMotor.set(motorSpeed[1]);
    backLeftMotor.set(motorSpeed[2]);
    backRightMotor.set(motorSpeed[3]);*/

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
