package Subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.REVLibError;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase{

    public static SparkFlexConfig DefaultConfig = new SparkFlexConfig();
    
    private SparkFlex arm; 
    
    public static final int kmotorCanID5 = 5;
    
    public double speedlimit = 0.2;
    private static final double kfineScale = 0.5;

    // Set to the signed encoder turns measured from fully lowered to fully raised.
    // With limits enabled, start the robot with the arm fully lowered.
    private static final double kencoderTurnsFor180Degrees = 0.0;
    private double encoderZero = Double.NaN;

    static {
        DefaultConfig.smartCurrentLimit(50);
        DefaultConfig.idleMode(IdleMode.kCoast);
        DefaultConfig.openLoopRampRate(1.0);
        DefaultConfig.inverted(true);
    }

    
    public void init(){

        arm = new SparkFlex(kmotorCanID5, MotorType.kBrushless);
        arm.configure(DefaultConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        if (kencoderTurnsFor180Degrees != 0.0) {
            double position = arm.getEncoder().getPosition();
            if (arm.getLastError() == REVLibError.kOk) {
                encoderZero = position;
            }
        }
    }

    public void raise(double speed){
        raise(speed, false);
    }

    public void raise(double speed, boolean fine){
        speed = MathUtil.applyDeadband(speed, 0.05);

        if (kencoderTurnsFor180Degrees != 0.0 && speed != 0.0) {
            double position = arm.getEncoder().getPosition();
            double travel = (position-encoderZero)/kencoderTurnsFor180Degrees;
            if (arm.getLastError() != REVLibError.kOk || !Double.isFinite(travel)
                || (speed > 0.0 && travel >= 1.0) || (speed < 0.0 && travel <= 0.0)) {
                speed = 0.0;
            }
        }

        if (speed != 0.0) {
            double limit = fine ? speedlimit*kfineScale : speedlimit;
            arm.set(speed*limit);
        }
        else {
            arm.stopMotor();
        }
    }
}
