package Subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkFlexConfig;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Arm extends SubsystemBase{

    public static SparkFlexConfig DefaultConfig = new SparkFlexConfig();
    
    private SparkFlex arm; 
    
    public static final int kmotorCanID5 = 5;
    
    public double speedlimit = 0.2;
    private static final double kfineScale = 0.5;



    static {
        DefaultConfig.smartCurrentLimit(50);
        DefaultConfig.idleMode(IdleMode.kCoast);
        DefaultConfig.openLoopRampRate(1.0);
        DefaultConfig.inverted(true);
    }

    
    public void init(){
        arm = new SparkFlex(kmotorCanID5, MotorType.kBrushless);
        arm.configure(DefaultConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public void raise(double speed){
        raise(speed, false);
    }

    public void raise(double speed, boolean fine){
        speed = MathUtil.applyDeadband(speed, 0.05);

        if (speed != 0.0) {
            double limit = fine ? speedlimit*kfineScale : speedlimit;
            arm.set(speed*limit);
        }
        else {
            arm.stopMotor();
        }
    }
}
