package org.firstinspires.ftc.teamcode.Autonomous;

import static org.firstinspires.ftc.teamcode.Robots.BasicRobot.op;
import static java.lang.Math.toRadians;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Robots.BradBot;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.roadrunner.trajectorysequence.TrajectorySequenceBuilder;
@Autonomous(name = "RedRightPreParkAuto")
public class RedRightAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        BradBot robot = new BradBot(this, false);
        robot.roadrun.setPoseEstimate(new Pose2d(15.5, -56, Math.toRadians(-90)));
        int pos = 0;
        TrajectorySequence[] preload = new TrajectorySequence[3];
        preload[0] = robot.roadrun.trajectorySequenceBuilder(new Pose2d(15.5, -56, Math.toRadians(-90)))
                .setReversed(true)
                .splineTo(new Vector2d(7, -41.5), toRadians(155))
                .addTemporalMarker(robot::done)
                .build();
        preload[1] = robot.roadrun.trajectorySequenceBuilder(new Pose2d(15.5, -56, Math.toRadians(-90)))
                .setReversed(true)
                .splineTo(new Vector2d(11, -33), toRadians(90))
                .addTemporalMarker(robot::done)
                .build();
        preload[2] = robot.roadrun.trajectorySequenceBuilder(new Pose2d(15.5, -56, Math.toRadians(-90)))
                .setReversed(true)
                .splineTo(new Vector2d(37,-30),toRadians(0))
                .lineToLinearHeading(new Pose2d(30,-30, toRadians(0)))
                .setReversed(false)
                .addTemporalMarker(robot::done)
                .build();
        TrajectorySequence[] throughTruss = new TrajectorySequence[3];
        throughTruss[0] =
                robot
                        .roadrun
                        .trajectorySequenceBuilder(preload[0].end())
                        .setReversed(true)
                        .lineToLinearHeading(new Pose2d(45,-40, toRadians(180)))
                        .waitSeconds(1.0)
                        .lineToLinearHeading(new Pose2d(51.5, -28, toRadians(181)))
                        .addTemporalMarker(robot::done)
                        .build();
        throughTruss[1] =
                robot
                        .roadrun
                        .trajectorySequenceBuilder(preload[1].end())
                        .setReversed(true)
                        .lineToLinearHeading(new Pose2d(45,-40, toRadians(180)))
                        .waitSeconds(1.0)
                        .lineToLinearHeading(new Pose2d(51.5, -36, toRadians(181)))
                        .addTemporalMarker(robot::done)
                        .build();
        throughTruss[2] =
                robot
                        .roadrun
                        .trajectorySequenceBuilder(preload[2].end())
                        .setReversed(true)
                        .lineToLinearHeading(new Pose2d(45,-44, toRadians(180)))
                        .waitSeconds(1.0)
                        .lineToLinearHeading(new Pose2d(51.5, -42.5, toRadians(181)))
                        .addTemporalMarker(robot::done)
                        .build();
        TrajectorySequence[] dropAndPark = new TrajectorySequence[3];
        dropAndPark[0] =
                robot
                        .roadrun
                        .trajectorySequenceBuilder(throughTruss[0].end())
                        .lineToLinearHeading(new Pose2d(49, -20, toRadians(180)))
                        .addTemporalMarker(robot::done)
                        .build();
        dropAndPark[1] =
                robot
                        .roadrun
                        .trajectorySequenceBuilder(throughTruss[1].end())
                        .lineToLinearHeading(new Pose2d(49, -20, toRadians(180)))
                        .addTemporalMarker(robot::done)
                        .build();
        dropAndPark[2] =
                robot
                        .roadrun
                        .trajectorySequenceBuilder(throughTruss[2].end())
                        .lineToLinearHeading(new Pose2d(49, -20, toRadians(180)))
                        .addTemporalMarker(robot::done)
                        .build();
          while (!isStarted()) {
            pos = robot.getRightSpikePos();
            op.telemetry.addData("spike pos", pos);
            op.telemetry.update();
            robot.update();
          }
        while(!isStopRequested()&&opModeIsActive()&&!robot.queuer.isFullfilled()){
            robot.followTrajSeq(preload[pos]);
            robot.queuer.addDelay(0.5);
            robot.preloadAuto();
            robot.queuer.addDelay(1.5);
            robot.followTrajSeq(throughTruss[pos]);
            robot.queuer.addDelay(5.5);
            robot.flipAuto();
            robot.loadAuto();
            robot.queuer.addDelay(1.2);
            robot.dropWrist();
            robot.queuer.addDelay(0.8);
            robot.drop();
            robot.queuer.addDelay(2.5);
            robot.followTrajSeq(dropAndPark[pos]);
            robot.queuer.addDelay(1.0);
            robot.resetAuto();
            robot.queuer.addDelay(7);
            robot.resetLift();
            robot.queuer.setFirstLoop(false);
            robot.update();
        }
    }
}