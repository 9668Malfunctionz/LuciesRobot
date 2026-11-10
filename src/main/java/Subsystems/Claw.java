package Subsystems;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Claw extends SubsystemBase{

    private Servo claw;

    public static final int kservoPwmPort = 0;
    private static final int kstopPulse = 1500;
    private static final int kopenPulse = 1700;
    private static final int kclosePulse = 1300;

    public void init(){
        claw = new Servo(kservoPwmPort);
        stop();
    }

    public void move(boolean open, boolean close){
        if (open == close) {
            stop();
        }
        else {
            claw.setPulseTimeMicroseconds(open ? kopenPulse : kclosePulse);
        }
    }

    public void stop(){
        claw.setPulseTimeMicroseconds(kstopPulse);
    }
}
