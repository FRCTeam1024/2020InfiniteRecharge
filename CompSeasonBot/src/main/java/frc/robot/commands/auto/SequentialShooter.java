package frc.robot.commands.auto;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.RunShooterFeed;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.ShooterFeed;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class SequentialShooter extends SequentialCommandGroup {
  /**
   * Creates a new SequentialShooter.
   */
  public SequentialShooter(Shooter shooter, ShooterFeed shooterFeed) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new WaitUntilCommand(shooter::isAtMaxRPM),
          new RunShooterFeed(shooterFeed, 1.0).withInterrupt(shooter::isBelowMaxRPM),
          new WaitUntilCommand(shooter::isAtMaxRPM),
          new RunShooterFeed(shooterFeed, 1.0).withInterrupt(shooter::isBelowMaxRPM),
          new WaitUntilCommand(shooter::isAtMaxRPM),
          new RunShooterFeed(shooterFeed, 1.0).withInterrupt(shooter::isBelowMaxRPM)
          );
  }
}