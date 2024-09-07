package org.firstinspires.ftc.teamcode.AbstractClasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.roboctopi.cuttlefish.controller.MecanumController;
import com.roboctopi.cuttlefish.utils.Direction;
import com.roboctopi.cuttlefishftcbridge.opmodeTypes.GamepadOpMode;
import org.firstinspires.ftc.teamcode.AbstractClasses.AbstractRobot;

import com.roboctopi.cuttlefishftcbridge.devices.CuttleMotor;
import com.roboctopi.cuttlefishftcbridge.devices.CuttleRevHub;

public abstract class AbstractOpMode extends GamepadOpMode {
    private AbstractRobot robot;
    public abstract AbstractRobot instantiateRobot();

    public CuttleRevHub ctrlHub;
    public CuttleRevHub expHub;

    public CuttleMotor leftFrontMotor ;
    public CuttleMotor rightFrontMotor;
    public CuttleMotor rightBackMotor ;
    public CuttleMotor leftBackMotor  ;
    public MecanumController drive;

    public AbstractRobot getRobot() {
        return robot;
    }

    public void onInit() {
        CuttleRevHub ctrlHub = new CuttleRevHub(hardwareMap,CuttleRevHub.HubTypes.CONTROL_HUB);


        leftFrontMotor  = ctrlHub.getMotor(0);
        rightFrontMotor = ctrlHub.getMotor(1);
        leftBackMotor   = ctrlHub.getMotor(2);
        rightBackMotor  = ctrlHub.getMotor(3);

        leftBackMotor .setDirection(Direction.REVERSE);
        leftFrontMotor.setDirection(Direction.REVERSE);

        drive = new MecanumController(rightFrontMotor, rightBackMotor, leftFrontMotor, leftBackMotor);
    }

    public void onStop() {
        super.stop();
    }
}